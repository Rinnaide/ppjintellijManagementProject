import AsyncStorage from '@react-native-async-storage/async-storage';

class UserService {
  async getAllUsers() {
    try {
      const users = await AsyncStorage.getItem('users');
      return users ? JSON.parse(users) : [];
    } catch (error) {
      throw error;
    }
  }

  async getUserById(id) {
    try {
      const users = await this.getAllUsers();
      return users.find(user => user.id === id);
    } catch (error) {
      throw error;
    }
  }

  async createUser(userData) {
    try {
      const users = await this.getAllUsers();

      // Check if email already exists
      const existingUser = users.find(user => user.usuarioEmail === userData.usuarioEmail);
      if (existingUser) {
        throw new Error('Este email já está cadastrado. Use um email diferente ou tente fazer login.');
      }

      const newUser = {
        id: Date.now().toString(), // Simple ID generation
        ...userData,
        createdAt: new Date().toISOString(),
      };
      users.push(newUser);
      await AsyncStorage.setItem('users', JSON.stringify(users));
      return newUser;
    } catch (error) {
      throw error;
    }
  }

  async updateUser(id, userData) {
    try {
      const users = await this.getAllUsers();
      const index = users.findIndex(user => user.id === id);
      if (index !== -1) {
        users[index] = { ...users[index], ...userData };
        await AsyncStorage.setItem('users', JSON.stringify(users));
        return users[index];
      }
      throw new Error('User not found');
    } catch (error) {
      throw error;
    }
  }

  async deleteUser(id) {
    try {
      const users = await this.getAllUsers();
      const filteredUsers = users.filter(user => user.id !== id);
      await AsyncStorage.setItem('users', JSON.stringify(filteredUsers));
      return { message: 'User deleted' };
    } catch (error) {
      throw error;
    }
  }

  async verifyPassword(id, password) {
    try {
      const users = await this.getAllUsers();
      const user = users.find(u => u.id === id);
      return user && user.usuario_senha === password;
    } catch (error) {
      throw error;
    }
  }
}

export default new UserService();
