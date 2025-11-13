import AsyncStorage from '@react-native-async-storage/async-storage';

// UUID v4 generation function
const generateUUID = () => {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
};

class TransactionService {
  // Migration function to fix existing duplicate IDs - should only run after user authentication
  async migrateTransactionIds(userId) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      if (!transactions) return;

      const transactionList = JSON.parse(transactions);
      // Filter transactions for current user only
      const userTransactions = transactionList.filter(t => t.userId && t.userId === userId);
      const otherTransactions = transactionList.filter(t => !t.userId || t.userId !== userId);

      if (userTransactions.length === 0) return; // No transactions for this user

      const idSet = new Set();
      let hasDuplicates = false;

      // Check for duplicates in user's transactions only
      for (const transaction of userTransactions) {
        if (idSet.has(transaction.id)) {
          hasDuplicates = true;
          break;
        }
        idSet.add(transaction.id);
      }

      let migratedUserTransactions = userTransactions;

      if (hasDuplicates) {
        console.log('Migrating transaction IDs to fix duplicates');
        migratedUserTransactions = userTransactions.map(transaction => ({
          ...transaction,
          id: generateUUID(),
          uniqueId: generateUUID(), // Add uniqueId for safe deletion
        }));
      } else {
        // Even if no duplicates, ensure all user's transactions have uniqueId
        migratedUserTransactions = userTransactions.map(transaction => ({
          ...transaction,
          uniqueId: transaction.uniqueId || generateUUID(),
        }));
        console.log('Ensuring all transactions have uniqueId');
      }

      // Combine migrated user transactions with other users' transactions
      const allTransactions = [...migratedUserTransactions, ...otherTransactions];
      await AsyncStorage.setItem('transactions', JSON.stringify(allTransactions));
      console.log('Migration completed successfully');
    } catch (error) {
      console.error('Error during transaction ID migration:', error);
    }
  }

  async getTransactionsByUser(userId, limit = null, offset = 0) {
    try {
      if (!userId) return [];
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      let userTransactions = transactionList.filter(t => t.userId && t.userId === userId);

      // Sort by date descending (newest first), fallback to createdAt
      userTransactions.sort((a, b) => new Date(b.date || b.createdAt) - new Date(a.date || a.createdAt));

      // Apply pagination if limit is provided
      if (limit !== null) {
        userTransactions = userTransactions.slice(offset, offset + limit);
      }

      return userTransactions;
    } catch (error) {
      throw error;
    }
  }

  async getTransactionsByUserAndDateRange(userId, startDate, endDate) {
    try {
      const transactions = await this.getTransactionsByUser(userId);
      return transactions.filter(t => {
        const transactionDate = new Date(t.date);
        return transactionDate >= new Date(startDate) && transactionDate <= new Date(endDate);
      });
    } catch (error) {
      throw error;
    }
  }

  async getTransactionById(id) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      return transactionList.find(t => t.uniqueId === id);
    } catch (error) {
      throw error;
    }
  }

  async createTransaction(transactionData) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];

      // Get category name for display
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      const category = categoryList.find(c => c.id === transactionData.categoryId);

      // Destructure to exclude any provided id, ensuring we always generate a new unique one
      const { id, ...restTransactionData } = transactionData;

      const newTransaction = {
        id: generateUUID(),
        uniqueId: generateUUID(), // Add uniqueId for safe deletion
        ...restTransactionData,
        categoryName: category ? category.name : 'Categoria não definida',
        createdAt: new Date().toISOString(),
      };
      transactionList.push(newTransaction);
      await AsyncStorage.setItem('transactions', JSON.stringify(transactionList));
      return newTransaction;
    } catch (error) {
      throw error;
    }
  }

  async updateTransaction(uniqueId, transactionData) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      const index = transactionList.findIndex(t => t.uniqueId === uniqueId);
      if (index !== -1) {
        // Get updated category name
        const categories = await AsyncStorage.getItem('categories');
        const categoryList = categories ? JSON.parse(categories) : [];
        const category = categoryList.find(c => c.id === transactionData.categoryId);

        const updatedTransaction = {
          ...transactionList[index],
          ...transactionData,
          categoryName: category ? category.name : 'Categoria não definida',
        };

        transactionList[index] = updatedTransaction;
        await AsyncStorage.setItem('transactions', JSON.stringify(transactionList));
        return updatedTransaction;
      }
      throw new Error('Transaction not found');
    } catch (error) {
      throw error;
    }
  }

  async deleteTransaction(uniqueId) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      const filteredTransactions = transactionList.filter(t => t.uniqueId !== uniqueId);
      await AsyncStorage.setItem('transactions', JSON.stringify(filteredTransactions));
      return { message: 'Transaction deleted' };
    } catch (error) {
      throw error;
    }
  }

  async getTotalIncome(userId) {
    try {
      const transactions = await this.getTransactionsByUser(userId);
      return transactions
        .filter(t => t.type === 'income')
        .reduce((total, t) => total + parseFloat(t.amount), 0);
    } catch (error) {
      throw error;
    }
  }

  async getTotalExpense(userId) {
    try {
      const transactions = await this.getTransactionsByUser(userId);
      return transactions
        .filter(t => t.type === 'expense')
        .reduce((total, t) => total + parseFloat(t.amount), 0);
    } catch (error) {
      throw error;
    }
  }
}

export default new TransactionService();
