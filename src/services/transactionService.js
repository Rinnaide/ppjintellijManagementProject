import AsyncStorage from '@react-native-async-storage/async-storage';

class TransactionService {
  async getTransactionsByUser(userId, limit = null, offset = 0) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      let userTransactions = transactionList.filter(t => t.userId === userId);

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
      return transactionList.find(t => t.id === id);
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

      const newTransaction = {
        id: Date.now().toString(),
        ...transactionData,
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

  async updateTransaction(id, transactionData) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      const index = transactionList.findIndex(t => t.id === id);
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

  async deleteTransaction(id) {
    try {
      const transactions = await AsyncStorage.getItem('transactions');
      const transactionList = transactions ? JSON.parse(transactions) : [];
      const filteredTransactions = transactionList.filter(t => t.id !== id);
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
