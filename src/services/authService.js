import api from './api';
import AsyncStorage from '@react-native-async-storage/async-storage';

class AuthService {
  async login(email, password) {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { token } = response.data;
      await AsyncStorage.setItem('token', token);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async logout() {
    try {
      await AsyncStorage.removeItem('token');
      await AsyncStorage.removeItem('user');
    } catch (error) {
      throw error;
    }
  }

  async getCurrentUser() {
    try {
      const user = await AsyncStorage.getItem('user');
      return user ? JSON.parse(user) : null;
    } catch (error) {
      throw error;
    }
  }

  async isAuthenticated() {
    try {
      const token = await AsyncStorage.getItem('token');
      return !!token;
    } catch (error) {
      return false;
    }
  }
}

export default new AuthService();
