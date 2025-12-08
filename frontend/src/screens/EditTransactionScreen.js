import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  KeyboardAvoidingView,
  Platform,
  TouchableOpacity,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { Ionicons } from '@expo/vector-icons';
import DateTimePicker from '@react-native-community/datetimepicker';
import { useNavigation, useRoute } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';
import categoryService from '../services/categoryService';
import { useTransaction } from '../contexts/TransactionContext';
import { COLORS, SPACING } from '../utils/constants';
import { isValidAmount, formatCurrencyInput, parseBRLAmount, formatNumberToBRL, formatDate, sanitizeNumericInput } from '../utils/helpers';

const EditTransactionScreen = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const { transaction, onUpdate } = route.params;
  const { updateTransaction } = useTransaction();

  const [formData, setFormData] = useState({
    description: '',
    amount: '',
    type: 'expense',
    categoryId: '',
    transactionDate: '',
    transactionTime: '',
  });
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [showDatePicker, setShowDatePicker] = useState(false);
  const [showTimePicker, setShowTimePicker] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    // When transaction type changes, check if current category is still valid
    if (formData.categoryId) {
      const filteredCategories = categories.filter(cat => cat.type === formData.type);
      const isCategoryValid = filteredCategories.some(cat => cat.id === formData.categoryId);
      if (!isCategoryValid) {
        updateFormData('categoryId', '');
      }
    }
  }, [formData.type, categories]);

  const loadData = async () => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const userData = JSON.parse(user);
      const userCategories = await categoryService.getCategoriesByUser(userData.id);
      setCategories(userCategories);

      // Pre-fill form with transaction data
      if (transaction) {
        setFormData({
          description: transaction.description || '',
          amount: transaction.amount ? formatNumberToBRL(transaction.amount) : '',
          type: transaction.type || 'expense',
          categoryId: transaction.categoryId || '',
          transactionDate: transaction.transactionDate || new Date().toISOString().split('T')[0],
          transactionTime: transaction.transactionTime || new Date().toTimeString().split(' ')[0],
        });
      }
    } catch (error) {
      Alert.alert('Erro', 'Erro ao carregar dados');
    }
  };

  const updateFormData = (field, value) => {
    setFormData({ ...formData, [field]: value });
    if (errors[field]) {
      setErrors({ ...errors, [field]: null });
    }
  };

  const onDateChange = (event, selectedDate) => {
    setShowDatePicker(Platform.OS === 'ios');
    if (selectedDate) {
      const dateString = selectedDate.toISOString().split('T')[0];
      updateFormData('transactionDate', dateString);
    }
  };

  const onTimeChange = (event, selectedTime) => {
    setShowTimePicker(Platform.OS === 'ios');
    if (selectedTime) {
      const timeString = selectedTime.toTimeString().split(' ')[0];
      updateFormData('transactionTime', timeString);
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.description.trim()) {
      newErrors.description = 'Descrição é obrigatória';
    }

    if (!formData.amount.trim()) {
      newErrors.amount = 'Valor é obrigatório';
    } else if (isNaN(parseBRLAmount(formData.amount)) || parseBRLAmount(formData.amount) <= 0) {
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

  const handleUpdate = async () => {
    if (!validateForm()) return;

    setLoading(true);
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const userData = JSON.parse(user);
      const updateData = {
        ...formData,
        amount: parseBRLAmount(formData.amount),
        userId: userData.id,
      };

      const updatedTransaction = await transactionService.updateTransaction(transaction.id, updateData);
      await updateTransaction(updatedTransaction);
      if (onUpdate) {
        onUpdate(updatedTransaction);
      }
      Alert.alert(
        'Sucesso',
        'Transação atualizada com sucesso!',
        [{ text: 'OK', onPress: () => navigation.goBack() }]
      );
    } catch (error) {
      Alert.alert('Erro', 'Erro ao atualizar transação');
    } finally {
      setLoading(false);
    }
  };

  const filteredCategories = categories.filter(cat => cat.type === formData.type);

  const renderCategoryItem = (category) => (
    <TouchableOpacity
      key={category.id}
      style={[
        styles.categoryItem,
        formData.categoryId === category.id && styles.categoryItemSelected,
      ]}
      onPress={() => updateFormData('categoryId', category.id)}
    >
      <View style={[styles.categoryColor, { backgroundColor: category.color }]} />
      <Text style={[
        styles.categoryText,
        formData.categoryId === category.id && styles.categoryTextSelected,
      ]}>
        {category.name}
      </Text>
    </TouchableOpacity>
  );

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <KeyboardAvoidingView
        style={styles.keyboardAvoidingView}
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      >
        <ScrollView style={styles.scrollView} showsVerticalScrollIndicator={false}>
          <View style={styles.content}>
            <Text style={styles.title}>Editar Transação</Text>

            {/* Type Selection */}
            <View style={styles.typeContainer}>
              <Text style={styles.sectionTitle}>Tipo</Text>
              <View style={styles.typeButtons}>
                <TouchableOpacity
                  style={[
                    styles.typeButton,
                    formData.type === 'income' && styles.typeButtonSelected,
                  ]}
                  onPress={() => updateFormData('type', 'income')}
                >
                  <Text style={[
                    styles.typeButtonText,
                    formData.type === 'income' && styles.typeButtonTextSelected,
                  ]}>
                    Receita
                  </Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={[
                    styles.typeButton,
                    formData.type === 'expense' && styles.typeButtonSelected,
                  ]}
                  onPress={() => updateFormData('type', 'expense')}
                >
                  <Text style={[
                    styles.typeButtonText,
                    formData.type === 'expense' && styles.typeButtonTextSelected,
                  ]}>
                    Despesa
                  </Text>
                </TouchableOpacity>
              </View>
            </View>

            {/* Form Fields */}
            <View style={styles.form}>
              <CustomInput
                label="Descrição"
                value={formData.description}
                onChangeText={(text) => updateFormData('description', text)}
                placeholder="Digite a descrição"
                error={errors.description}
              />

              <CustomInput
                label="Valor"
                value={formData.amount}
                onChangeText={(text) => {
                  const sanitized = sanitizeNumericInput(text);
                  updateFormData('amount', sanitized);
                }}
                onBlur={() => updateFormData('amount', formatCurrencyInput(formData.amount))}
                placeholder="0,00"
                keyboardType="numeric"
                error={errors.amount}
              />

              {/* Data */}
              <View style={styles.dateTimeContainer}>
                <TouchableOpacity
                  style={styles.dateTimeButton}
                  onPress={() => setShowDatePicker(true)}
                >
                  <Ionicons name="calendar-outline" size={20} color={COLORS.gray} />
                  <Text style={styles.dateTimeText}>
                    {formatDate(formData.transactionDate)}
                  </Text>
                </TouchableOpacity>
                {showDatePicker && (
                  <DateTimePicker
                    value={new Date(formData.transactionDate)}
                    mode="date"
                    display={Platform.OS === 'ios' ? 'spinner' : 'default'}
                    onChange={onDateChange}
                  />
                )}
              </View>

              {/* Hora */}
              <View style={styles.dateTimeContainer}>
                <TouchableOpacity
                  style={styles.dateTimeButton}
                  onPress={() => setShowTimePicker(true)}
                >
                  <Ionicons name="time-outline" size={20} color={COLORS.gray} />
                  <Text style={styles.dateTimeText}>
                    {formData.transactionTime}
                  </Text>
                </TouchableOpacity>
                {showTimePicker && (
                  <DateTimePicker
                    value={new Date(`1970-01-01T${formData.transactionTime}`)}
                    mode="time"
                    display={Platform.OS === 'ios' ? 'spinner' : 'default'}
                    onChange={onTimeChange}
                  />
                )}
              </View>

              {/* Category Selection */}
              <View style={styles.categorySection}>
                <Text style={styles.sectionTitle}>Categoria</Text>
                {filteredCategories.length > 0 ? (
                  <View style={styles.categoryGrid}>
                    {filteredCategories.map(renderCategoryItem)}
                  </View>
                ) : (
                  <Text style={styles.noCategoriesText}>
                    Nenhuma categoria encontrada para este tipo. Adicione uma categoria primeiro.
                  </Text>
                )}
                {errors.categoryId && (
                  <Text style={styles.errorText}>{errors.categoryId}</Text>
                )}
              </View>

              <CustomButton
                title="Atualizar Transação"
                onPress={handleUpdate}
                loading={loading}
                style={styles.updateButton}
              />
            </View>
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.white,
  },
  keyboardAvoidingView: {
    flex: 1,
  },
  scrollView: {
    flex: 1,
  },
  content: {
    padding: SPACING.lg,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginBottom: SPACING.xl,
    textAlign: 'center',
  },
  typeContainer: {
    marginBottom: SPACING.lg,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '600',
    color: COLORS.dark,
    marginBottom: SPACING.sm,
  },
  typeButtons: {
    flexDirection: 'row',
    gap: SPACING.sm,
  },
  typeButton: {
    flex: 1,
    paddingVertical: SPACING.md,
    paddingHorizontal: SPACING.lg,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    alignItems: 'center',
  },
  typeButtonSelected: {
    backgroundColor: COLORS.primary,
    borderColor: COLORS.primary,
  },
  typeButtonText: {
    fontSize: 16,
    color: COLORS.gray,
    fontWeight: '500',
  },
  typeButtonTextSelected: {
    color: COLORS.white,
  },
  form: {
    width: '100%',
  },
  categorySection: {
    marginBottom: SPACING.lg,
  },
  categoryGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: SPACING.sm,
  },
  categoryItem: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: SPACING.sm,
    paddingHorizontal: SPACING.md,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    minWidth: 120,
  },
  categoryItemSelected: {
    backgroundColor: COLORS.primary,
    borderColor: COLORS.primary,
  },
  categoryColor: {
    width: 16,
    height: 16,
    borderRadius: 8,
    marginRight: SPACING.sm,
  },
  categoryText: {
    fontSize: 14,
    color: COLORS.dark,
    fontWeight: '500',
  },
  categoryTextSelected: {
    color: COLORS.white,
  },
  noCategoriesText: {
    fontSize: 14,
    color: COLORS.gray,
    fontStyle: 'italic',
    textAlign: 'center',
    paddingVertical: SPACING.md,
  },
  errorText: {
    fontSize: 12,
    color: COLORS.danger,
    marginTop: SPACING.xs,
  },
  updateButton: {
    marginTop: SPACING.md,
  },
  dateTimeContainer: {
    marginBottom: SPACING.md,
  },
  dateTimeButton: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: SPACING.md,
    paddingHorizontal: SPACING.lg,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    backgroundColor: COLORS.white,
  },
  dateTimeText: {
    fontSize: 16,
    color: COLORS.dark,
    marginLeft: SPACING.sm,
  },
});

export default EditTransactionScreen;
