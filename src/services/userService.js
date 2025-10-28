import api from './api';

class UserService {
  async getAllUsers() {
    try {
      const response = await api.get('/users');
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async getUserById(id) {
    try {
      const response = await api.get(`/users/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async createUser(userData) {
    try {
      const response = await api.post('/users', userData);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async updateUser(id, userData) {
    try {
      const response = await api.put(`/users/${id}`, userData);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async deleteUser(id) {
    try {
      const response = await api.delete(`/users/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  }
}

export default new UserService();
