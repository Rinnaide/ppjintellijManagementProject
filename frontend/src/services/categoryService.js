import api from './api';

class CategoryService {
  async getCategoriesByUser(userId) {
    try {
      const response = await api.get(`/categories/user/${userId}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async createCategory(categoryData) {
    try {
      const response = await api.post('/categories', categoryData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async updateCategory(id, categoryData) {
    try {
      const response = await api.put(`/categories/${id}`, categoryData);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async deleteCategory(id) {
    try {
      const response = await api.delete(`/categories/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async getCategoryById(id) {
    try {
      const response = await api.get(`/categories/${id}`);
      return response;
    } catch (error) {
      throw error;
    }
  }
}

export default new CategoryService();
