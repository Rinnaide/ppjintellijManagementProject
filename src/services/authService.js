import AsyncStorage from '@react-native-async-storage/async-storage';

class AuthService {
  async login(email, password) {
    try {
      const users = await AsyncStorage.getItem('users');
      const userList = users ? JSON.parse(users) : [];
      const user = userList.find(u => u.usuarioEmail === email && u.usuario_senha === password);
      if (user) {
        const token = `fake-token-${user.id}`;
        await AsyncStorage.setItem('token', token);
        await AsyncStorage.setItem('user', JSON.stringify(user));
        return { token, user };
      } else {
        throw new Error('Invalid credentials');
      }
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
