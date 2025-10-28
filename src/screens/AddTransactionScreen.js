import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { Ionicons } from '@expo/vector-icons';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';
import categoryService from '../services/categoryService';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';

const AddTransactionScreen = ({ navigation }) => {
  const [formData, setFormData] = useState({
    description: '',
    amount: '',
    type: 'EXPENSE', // 'INCOME' or 'EXPENSE'
    categoryId: '',
    transactionDate: new Date().toISOString().split('T')[0], // YYYY-MM-DD format
  });
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const userId = 1; // TODO: Get from auth context

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      const categoriesData = await categoryService.getCategoriesByUser(userId);
      setCategories(categoriesData);
    } catch (error) {
      Alert.alert('Erro', 'Erro ao carregar categorias');
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.description.trim()) {
      newErrors.description = 'Descrição é obrigatória';
    }

    if (!formData.amount || isNaN(formData.amount) || parseFloat(formData.amount) <= 0) {
      newErrors.amount = 'Valor deve ser maior que zero';
    }

    if (!formData.categoryId) {
      newErrors.categoryId = 'Categoria é obrigatória';
    }

    if (!formData.transactionDate) {
      newErrors.transactionDate = 'Data é obrigatória';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleInputChange = (field, value) => {
    setFormData(prev => ({
      ...prev,
      [field]: value,
    }));
    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({
        ...prev,
        [field]: '',
      }));
    }
  };

  const handleTypeChange = (type) => {
    setFormData(prev => ({
      ...prev,
      type,
    }));
  };

  const handleSubmit = async () => {
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      const transactionData = {
        ...formData,
        amount: parseFloat(formData.amount),
        categoryId: parseInt(formData.categoryId),
        userId,
      };

      await transactionService.createTransaction(transactionData);
      Alert.alert('Sucesso', 'Transação criada com sucesso!', [
        {
          text: 'OK',
          onPress: () => navigation.goBack(),
        },
      ]);
    } catch (error) {
      Alert.alert('Erro', 'Erro ao criar transação');
    } finally {
      setLoading(false);
    }
  };

  const renderTypeSelector = () => (
    <View style={styles.typeContainer}>
      <Text style={styles.label}>Tipo de Transação</Text>
      <View style={styles.typeButtons}>
        <TouchableOpacity
          style={[
            styles.typeButton,
            formData.type === 'INCOME' && styles.typeButtonActive,
          ]}
          onPress={() => handleTypeChange('INCOME')}
        >
          <Ionicons
            name="trending-up"
            size={20}
            color={formData.type === 'INCOME' ? COLORS.white : COLORS.success}
          />
          <Text
            style={[
              styles.typeButtonText,
              formData.type === 'INCOME' && styles.typeButtonTextActive,
            ]}
          >
            Receita
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.typeButton,
            formData.type === 'EXPENSE' && styles.typeButtonActive,
          ]}
          onPress={() => handleTypeChange('EXPENSE')}
        >
          <Ionicons
            name="trending-down"
            size={20}
            color={formData.type === 'EXPENSE' ? COLORS.white : COLORS.danger}
          />
          <Text
            style={[
              styles.typeButtonText,
              formData.type === 'EXPENSE' && styles.typeButtonTextActive,
            ]}
          >
            Despesa
          </Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  const renderCategorySelector = () => (
    <View style={styles.categoryContainer}>
      <Text style={styles.label}>Categoria</Text>
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        style={styles.categoryScroll}
      >
        {categories.map((category) => (
          <TouchableOpacity
            key={category.categoriesId}
            style={[
              styles.categoryButton,
              formData.categoryId === category.categoriesId.toString() && styles.categoryButtonActive,
            ]}
            onPress={() => handleInputChange('categoryId', category.categoriesId.toString())}
          >
            <Text
              style={[
                styles.categoryButtonText,
                formData.categoryId === category.categoriesId.toString() && styles.categoryButtonTextActive,
              ]}
            >
              {category.name}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>
      {errors.categoryId && (
        <Text style={styles.errorText}>{errors.categoryId}</Text>
      )}
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <ScrollView style={styles.scrollView} showsVerticalScrollIndicator={false}>
        <View style={styles.content}>
          <Text style={styles.title}>Nova Transação</Text>

          {/* Tipo de transação */}
          {renderTypeSelector()}

          {/* Descrição */}
          <CustomInput
            label="Descrição"
            placeholder="Ex: Salário, Conta de luz..."
            value={formData.description}
            onChangeText={(value) => handleInputChange('description', value)}
            error={errors.description}
          />

          {/* Valor */}
          <CustomInput
            label="Valor"
            placeholder="0,00"
            value={formData.amount}
            onChangeText={(value) => handleInputChange('amount', value)}
            keyboardType="numeric"
            error={errors.amount}
          />

          {/* Categoria */}
          {renderCategorySelector()}

          {/* Data */}
          <CustomInput
            label="Data"
            placeholder="YYYY-MM-DD"
            value={formData.transactionDate}
            onChangeText={(value) => handleInputChange('transactionDate', value)}
            error={errors.transactionDate}
          />

          {/* Botões */}
          <View style={styles.buttonContainer}>
            <CustomButton
              title="Cancelar"
              onPress={() => navigation.goBack()}
              variant="secondary"
              style={styles.cancelButton}
            />
            <CustomButton
              title="Salvar"
              onPress={handleSubmit}
              loading={loading}
              style={styles.saveButton}
            />
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.white,
  },
  scrollView: {
    flex: 1,
  },
  content: {
    padding: SPACING.lg,
  },
  title: {
    fontSize: FONT_SIZES.xlarge,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginBottom: SPACING.lg,
    textAlign: 'center',
  },
  typeContainer: {
    marginBottom: SPACING.lg,
  },
  label: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
    color: COLORS.dark,
    marginBottom: SPACING.sm,
  },
  typeButtons: {
    flexDirection: 'row',
    gap: SPACING.md,
  },
  typeButton: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    padding: SPACING.md,
    borderRadius: 8,
    borderWidth: 2,
    borderColor: COLORS.lightGray,
    backgroundColor: COLORS.white,
  },
  typeButtonActive: {
    backgroundColor: COLORS.primary,
    borderColor: COLORS.primary,
  },
  typeButtonText: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
    color: COLORS.gray,
    marginLeft: SPACING.xs,
  },
  typeButtonTextActive: {
    color: COLORS.white,
  },
  categoryContainer: {
    marginBottom: SPACING.lg,
  },
  categoryScroll: {
    marginBottom: SPACING.sm,
  },
  categoryButton: {
    paddingHorizontal: SPACING.md,
    paddingVertical: SPACING.sm,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    backgroundColor: COLORS.white,
    marginRight: SPACING.sm,
  },
  categoryButtonActive: {
    backgroundColor: COLORS.primary,
    borderColor: COLORS.primary,
  },
  categoryButtonText: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
  },
  categoryButtonTextActive: {
    color: COLORS.white,
  },
  errorText: {
    fontSize: FONT_SIZES.small,
    color: COLORS.danger,
    marginTop: SPACING.xs,
  },
  buttonContainer: {
    flexDirection: 'row',
    gap: SPACING.md,
    marginTop: SPACING.xl,
  },
  cancelButton: {
    flex: 1,
  },
  saveButton: {
    flex: 1,
  },
});

export default AddTransactionScreen;
