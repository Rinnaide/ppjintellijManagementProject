import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Alert,
  RefreshControl,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { useNavigation, useFocusEffect } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import { useAuth } from '../contexts/AuthContext';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import CustomButton from '../components/CustomButton';
import api from '../services/api'
const CategoriesListScreen = () => {
  const navigation = useNavigation();
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [refreshing, setRefreshing] = useState(false);

  const loadCategories = async () => {
    try {
      if (user && user.id) {
        const res = await api.get(`/categories/user/${user.id}`)
        console.log(res)
        setCategories(res);
      }
    } catch (error) {
      console.error('Error loading categories:', error);
    }
  };

  const onRefresh = async () => {
    setRefreshing(true);
    await loadCategories();
    setRefreshing(false);
  };

  useFocusEffect(
    React.useCallback(() => {
      loadCategories();
    }, [])
  );

  const handleDeleteCategory = async (categoryId) => {
    Alert.alert(
      'Excluir Categoria',
      'Tem certeza que deseja excluir esta categoria?',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Excluir',
          style: 'destructive',
          onPress: async () => {
            try {
              console.log("destruiu")
              await api.delete(`/categories/${categoryId}`)
              await loadCategories();
              Alert.alert('Sucesso', 'Categoria excluída com sucesso!');
            } catch (error) {
              Alert.alert('Erro', 'Erro ao excluir categoria');
            }
          },
        },
      ]
    );
  };

  const renderCategory = ({ item }) => (
    <View style={styles.categoryItem}>
      <View style={styles.categoryInfo}>
        <View style={[styles.categoryColor, { backgroundColor: item.color || COLORS.primary }]} />
        <Text style={styles.categoryName}>{item.categoryName}</Text>
        <Text style={styles.categoryType}>
          {item.type === 'income' ? 'Receita' : 'Despesa'}
        </Text>
      </View>
      <View style={styles.categoryActions}>
        <TouchableOpacity
          style={styles.editButton}
          onPress={() => navigation.navigate('EditCategory', { category: item })}
        >
          <Ionicons name="pencil" size={20} color={COLORS.primary} />
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.deleteButton}
          onPress={() => handleDeleteCategory(item.id)}
        >
          <Ionicons name="trash" size={20} color={COLORS.danger} />
        </TouchableOpacity>
      </View>
    </View>
  );

  const renderEmpty = () => (
    <View style={styles.emptyContainer}>
      <Ionicons name="folder-open-outline" size={64} color={COLORS.lightGray} />
      <Text style={styles.emptyTitle}>Nenhuma categoria encontrada</Text>
      <Text style={styles.emptySubtitle}>
        Adicione sua primeira categoria para organizar suas finanças
      </Text>
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()}
        >
          <Ionicons name="arrow-back" size={24} color={COLORS.dark} />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Categorias</Text>
        <TouchableOpacity
          style={styles.addButton}
          onPress={() => navigation.navigate('AddCategory')}
        >
          <Ionicons name="add" size={24} color={COLORS.primary} />
        </TouchableOpacity>
      </View>

      {/* Categories List */}
      <FlatList
        data={categories}
        renderItem={renderCategory}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.listContainer}
        ListEmptyComponent={renderEmpty}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        showsVerticalScrollIndicator={false}
      />

      {/* Add Category Button */}
      {categories.length > 0 && (
        <View style={styles.footer}>
          <CustomButton
            title="Adicionar Categoria"
            onPress={() => navigation.navigate('AddCategory')}
            style={styles.addCategoryButton}
          />
        </View>
      )}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.white,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: SPACING.lg,
    paddingVertical: SPACING.md,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  backButton: {
    padding: SPACING.xs,
  },
  headerTitle: {
    fontSize: FONT_SIZES.large,
    fontWeight: 'bold',
    color: COLORS.dark,
  },
  addButton: {
    padding: SPACING.xs,
  },
  listContainer: {
    flexGrow: 1,
    padding: SPACING.lg,
  },
  categoryItem: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    backgroundColor: COLORS.white,
    borderRadius: 12,
    padding: SPACING.md,
    marginBottom: SPACING.sm,
    shadowColor: COLORS.dark,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  categoryInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  categoryColor: {
    width: 16,
    height: 16,
    borderRadius: 8,
    marginRight: SPACING.sm,
  },
  categoryName: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
    color: COLORS.dark,
    flex: 1,
  },
  categoryType: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginLeft: SPACING.sm,
  },
  categoryActions: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  editButton: {
    padding: SPACING.xs,
    marginRight: SPACING.sm,
  },
  deleteButton: {
    padding: SPACING.xs,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: SPACING.xl,
  },
  emptyTitle: {
    fontSize: FONT_SIZES.large,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginTop: SPACING.lg,
    marginBottom: SPACING.sm,
    textAlign: 'center',
  },
  emptySubtitle: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.gray,
    textAlign: 'center',
    lineHeight: 24,
  },
  footer: {
    padding: SPACING.lg,
    borderTopWidth: 1,
    borderTopColor: COLORS.lightGray,
  },
  addCategoryButton: {
    marginTop: 0,
  },
});

export default CategoriesListScreen;
