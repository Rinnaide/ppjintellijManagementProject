import api from './api';

class TransactionService {
  async getTransactionsByUser(userId) {
    try {
      const response = await api.get(`/transactions/user/${userId}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async getTransactionsByUserAndDateRange(userId, startDate, endDate) {
    try {
      const response = await api.get(`/transactions/user/${userId}/date-range`, {
        params: { startDate, endDate }
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async getTransactionById(id) {
    try {
      const response = await api.get(`/transactions/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async createTransaction(transactionData) {
    try {
      const response = await api.post('/transactions', transactionData);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async updateTransaction(id, transactionData) {
    try {
      const response = await api.put(`/transactions/${id}`, transactionData);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async deleteTransaction(id) {
    try {
      const response = await api.delete(`/transactions/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async getTotalIncome(userId) {
    try {
      const response = await api.get(`/transactions/user/${userId}/total-income`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async getTotalExpense(userId) {
    try {
      const response = await api.get(`/transactions/user/${userId}/total-expense`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }
}

export default new TransactionService();
