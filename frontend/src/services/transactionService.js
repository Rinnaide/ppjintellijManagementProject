import api from './api';

class TransactionService {
  async migrateTransactionIds(userId) {
    // Migration not needed with backend
    console.log('Migration not needed with backend');
  }

  async getTransactionsByUser(userId, limit = null, offset = 0) {
    try {
      if (!userId) return [];
      let url = `/transactions/user/${userId}`;
      if (limit !== null) {
        url += `/paged?limit=${limit}&offset=${offset}`;
      }
      const response = await api.get(url);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getTransactionsByUserAndDateRange(userId, startDate, endDate) {
    try {
      const response = await api.get(`/transactions/user/${userId}/date-range?startDate=${startDate}&endDate=${endDate}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getTransactionById(id) {
    try {
      const response = await api.get(`/transactions/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async createTransaction(transactionData) {
    try {
      const response = await api.post('/transactions', transactionData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async updateTransaction(id, transactionData) {
    try {
      const response = await api.put(`/transactions/${id}`, transactionData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async deleteTransaction(id) {
    try {
      const response = await api.delete(`/transactions/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getTotalIncome(userId) {
    try {
      const response = await api.get(`/transactions/user/${userId}/total-income`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getTotalExpense(userId) {
    try {
      const response = await api.get(`/transactions/user/${userId}/total-expense`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getFilteredTransactions(userId, searchQuery, categoryId, type, startDate, endDate) {
    try {
      let url = `/transactions/user/${userId}/filtered?`;
      const params = [];
      if (searchQuery) params.push(`searchQuery=${encodeURIComponent(searchQuery)}`);
      if (categoryId) params.push(`categoryId=${categoryId}`);
      if (type) params.push(`type=${type}`);
      if (startDate) params.push(`startDate=${startDate}`);
      if (endDate) params.push(`endDate=${endDate}`);
      url += params.join('&');
      const response = await api.get(url);
      return response;
    } catch (error) {
      throw error;
    }
  }
}

export default new TransactionService();
