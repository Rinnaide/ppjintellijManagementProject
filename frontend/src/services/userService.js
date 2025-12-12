import api from './api';

class UserService {
  async getAllUsers() {
    try {
      const response = await api.get('/users');
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getUserById(id) {
    try {
      const response = await api.get(`/users/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async createUser(userData) {
    try {
      const response = await api.post('/users', userData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async updateUser(id, userData) {
    try {
      const response = await api.put(`/users/${id}`, userData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async deleteUser(id) {
    try {
      const response = await api.delete(`/users/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async verifyPassword(id, password) {
    // This might not be needed if backend handles password verification
    // If needed, we can implement a specific endpoint
    throw new Error('Password verification should be handled by backend');
  }
}

export default new UserService();
