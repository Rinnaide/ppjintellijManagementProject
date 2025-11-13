import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
  Platform,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { Ionicons } from '@expo/vector-icons';
import DateTimePicker from '@react-native-community/datetimepicker';
import AsyncStorage from '@react-native-async-storage/async-storage';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';
import categoryService from '../services/categoryService';
import { useTransaction } from '../contexts/TransactionContext';
import { useFilter } from '../contexts/FilterContext';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import { formatCurrencyInput, parseBRLAmount, formatDate, sanitizeNumericInput } from '../utils/helpers';

const AddTransactionScreen = () => {
  const navigation = useNavigation();
  const { addTransaction } = useTransaction();
  const { refreshTransactions } = useFilter();
  const [formData, setFormData] = useState({
    description: '',
    amount: '',
    type: '', // 'income' or 'expense' - start empty
    categoryId: '',
    transactionDate: new Date().toISOString().split('T')[0], // YYYY-MM-DD format
    transactionTime: new Date().toTimeString().split(' ')[0], // HH:MM:SS format
  });
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [showDatePicker, setShowDatePicker] = useState(false);
  const [showTimePicker, setShowTimePicker] = useState(false);

  useEffect(() => {
    // Load categories only if type is selected
    if (formData.type) {
      loadCategories(formData.type);
    } else {
      setCategories([]);
    }
  }, [formData.type]);

  const loadCategories = async (type = null) => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (user) {
        const userData = JSON.parse(user);
        const categoriesData = await categoryService.getCategoriesByUser(userData.id);
        const filteredCategories = type ? categoriesData.filter(cat => cat.type === type) : categoriesData;
        setCategories(filteredCategories);
      }
    } catch (error) {
      Alert.alert('Erro', 'Erro ao carregar categorias');
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.type) {
      newErrors.type = 'Tipo de transação é obrigatório';
    }

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

  const onDateChange = (event, selectedDate) => {
    setShowDatePicker(Platform.OS === 'ios');
    if (selectedDate) {
      const dateString = selectedDate.toISOString().split('T')[0];
      handleInputChange('transactionDate', dateString);
    }
  };

  const onTimeChange = (event, selectedTime) => {
    setShowTimePicker(Platform.OS === 'ios');
    if (selectedTime) {
      const timeString = selectedTime.toTimeString().split(' ')[0];
      handleInputChange('transactionTime', timeString);
    }
  };

  const handleTypeChange = (type) => {
    setFormData(prev => ({
      ...prev,
      type,
      categoryId: '', // Reset category when type changes
    }));
    // Filter categories based on type
    loadCategories(type);
  };

  const handleSubmit = async () => {
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const userData = JSON.parse(user);
      const transactionData = {
        ...formData,
        amount: parseBRLAmount(formData.amount),
        categoryId: formData.categoryId,
        userId: userData.id,
      };

      const newTransaction = await addTransaction(transactionData);

      // Refresh FilterContext to update Transaction List screen
      refreshTransactions();

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
            formData.type === 'income' && styles.typeButtonActive,
          ]}
          onPress={() => handleTypeChange('income')}
        >
          <Ionicons
            name="trending-up"
            size={20}
            color={formData.type === 'income' ? COLORS.white : COLORS.success}
          />
          <Text
            style={[
              styles.typeButtonText,
              formData.type === 'income' && styles.typeButtonTextActive,
            ]}
          >
            Receita
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[
            styles.typeButton,
            formData.type === 'expense' && styles.typeButtonActive,
          ]}
          onPress={() => handleTypeChange('expense')}
        >
          <Ionicons
            name="trending-down"
            size={20}
            color={formData.type === 'expense' ? COLORS.white : COLORS.danger}
          />
          <Text
            style={[
              styles.typeButtonText,
              formData.type === 'expense' && styles.typeButtonTextActive,
            ]}
          >
            Despesa
          </Text>
        </TouchableOpacity>
      </View>
      {errors.type && (
        <Text style={styles.errorText}>{errors.type}</Text>
      )}
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
            key={category.id}
            style={[
              styles.categoryButton,
              formData.categoryId === category.id && styles.categoryButtonActive,
            ]}
            onPress={() => handleInputChange('categoryId', category.id)}
          >
            <View style={styles.categoryContent}>
              <View style={[styles.categoryColor, { backgroundColor: category.color || COLORS.primary }]} />
              <Text
                style={[
                  styles.categoryButtonText,
                  formData.categoryId === category.id && styles.categoryButtonTextActive,
                ]}
              >
                {category.name}
              </Text>
            </View>
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
            onChangeText={(value) => {
              const sanitized = sanitizeNumericInput(value);
              handleInputChange('amount', sanitized);
            }}
            onBlur={() => handleInputChange('amount', formatCurrencyInput(formData.amount))}
            keyboardType="numeric"
            error={errors.amount}
          />

          {/* Categoria */}
          {renderCategorySelector()}

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
  categoryContent: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  categoryColor: {
    width: 12,
    height: 12,
    borderRadius: 6,
    marginRight: SPACING.xs,
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
  dateTimeContainer: {
    marginBottom: SPACING.lg,
  },
  dateTimeButton: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: SPACING.md,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    borderRadius: 8,
    backgroundColor: COLORS.white,
  },
  dateTimeText: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.dark,
    marginLeft: SPACING.sm,
  },
});

export default AddTransactionScreen;
