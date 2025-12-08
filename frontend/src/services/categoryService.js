import AsyncStorage from '@react-native-async-storage/async-storage';

class CategoryService {
  async getCategoriesByUser(userId) {
    try {
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      return categoryList.filter(c => c.userId === userId);
    } catch (error) {
      throw error;
    }
  }

  async getCategoryById(id) {
    try {
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      return categoryList.find(c => c.id === id);
    } catch (error) {
      throw error;
    }
  }

  async createCategory(categoryData) {
    try {
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      const newCategory = {
        id: Date.now().toString(),
        ...categoryData,
        createdAt: new Date().toISOString(),
      };
      categoryList.push(newCategory);
      await AsyncStorage.setItem('categories', JSON.stringify(categoryList));
      return newCategory;
    } catch (error) {
      throw error;
    }
  }

  async updateCategory(id, categoryData) {
    try {
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      const index = categoryList.findIndex(c => c.id === id);
      if (index !== -1) {
        categoryList[index] = { ...categoryList[index], ...categoryData };
        await AsyncStorage.setItem('categories', JSON.stringify(categoryList));
        return categoryList[index];
      }
      throw new Error('Category not found');
    } catch (error) {
      throw error;
    }
  }

  async deleteCategory(id) {
    try {
      const categories = await AsyncStorage.getItem('categories');
      const categoryList = categories ? JSON.parse(categories) : [];
      const filteredCategories = categoryList.filter(c => c.id !== id);
      await AsyncStorage.setItem('categories', JSON.stringify(filteredCategories));
      return { message: 'Category deleted' };
    } catch (error) {
      throw error;
    }
  }
}

export default new CategoryService();
