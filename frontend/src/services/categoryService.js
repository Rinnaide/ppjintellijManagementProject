import AsyncStorage from '@react-native-async-storage/async-storage';

// Base URL for the backend API
const API_BASE_URL = 'http://academico3.rj.senac.br/projectmanagement/api';

class CategoryService {
  async getCategoriesByUser(userId) {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) throw new Error('User not authenticated');

      const userData = JSON.parse(user);
      const token = userData.token;

      const response = await fetch(`${API_BASE_URL}/categories/user/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch categories');
      }
      const categories = await response.json();
      return categories;
    } catch (error) {
      throw error;
    }
  }

  async createCategory(categoryData) {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) throw new Error('User not authenticated');

      const userData = JSON.parse(user);
      const token = userData.token;

      const response = await fetch(`${API_BASE_URL}/categories`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(categoryData),
      });
      if (!response.ok) {
        throw new Error('Failed to create category');
      }
      const newCategory = await response.json();
      return newCategory;
    } catch (error) {
      throw error;
    }
  }

  async updateCategory(id, categoryData) {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) throw new Error('User not authenticated');

      const userData = JSON.parse(user);
      const token = userData.token;

      const response = await fetch(`${API_BASE_URL}/categories/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(categoryData),
      });
      if (!response.ok) {
        throw new Error('Failed to update category');
      }
      const updatedCategory = await response.json();
      return updatedCategory;
    } catch (error) {
      throw error;
    }
  }

  async deleteCategory(id) {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) throw new Error('User not authenticated');

      const userData = JSON.parse(user);
      const token = userData.token;

      const response = await fetch(`${API_BASE_URL}/categories/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to delete category');
      }
      return { message: 'Category deleted' };
    } catch (error) {
      throw error;
    }
  }

  async getCategoryById(id) {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) throw new Error('User not authenticated');

      const userData = JSON.parse(user);
      const token = userData.token;

      const response = await fetch(`${API_BASE_URL}/categories/${id}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch category');
      }
      const category = await response.json();
      return category;
    } catch (error) {
      throw error;
    }
  }
}

export default new CategoryService();
