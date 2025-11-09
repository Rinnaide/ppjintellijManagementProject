import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';

const AddCategoryScreen = () => {
  const navigation = useNavigation();
  const [formData, setFormData] = useState({
    name: '',
    type: 'expense', // 'income' or 'expense'
    color: COLORS.primary,
  });
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const colorOptions = [
    { color: COLORS.primary, name: 'Azul' },
    { color: COLORS.success, name: 'Verde' },
    { color: COLORS.danger, name: 'Vermelho' },
    { color: COLORS.warning, name: 'Amarelo' },
    { color: '#9C27B0', name: 'Roxo' },
    { color: '#FF9800', name: 'Laranja' },
    { color: '#607D8B', name: 'Cinza' },
    { color: '#E91E63', name: 'Rosa' },
  ];

  const updateFormData = (field, value) => {
    setFormData({ ...formData, [field]: value });
    if (errors[field]) {
      setErrors({ ...errors, [field]: null });
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Nome da categoria é obrigatório';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSave = async () => {
    if (!validateForm()) return;

    setLoading(true);
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const userData = JSON.parse(user);
      const categoriesData = await AsyncStorage.getItem('categories');
      const categories = categoriesData ? JSON.parse(categoriesData) : [];

      const newCategory = {
        id: Date.now().toString(),
        name: formData.name.trim(),
        type: formData.type,
        color: formData.color,
        userId: userData.id,
        createdAt: new Date().toISOString(),
      };

      categories.push(newCategory);
      await AsyncStorage.setItem('categories', JSON.stringify(categories));

      Alert.alert(
        'Sucesso',
        'Categoria criada com sucesso!',
        [{ text: 'OK', onPress: () => navigation.goBack() }]
      );
    } catch (error) {
      Alert.alert('Erro', 'Erro ao criar categoria');
    } finally {
      setLoading(false);
    }
  };

  const renderColorOption = (option) => (
    <TouchableOpacity
      key={option.color}
      style={[
        styles.colorOption,
        { borderColor: formData.color === option.color ? COLORS.primary : COLORS.lightGray },
      ]}
      onPress={() => updateFormData('color', option.color)}
    >
      <View style={[styles.colorCircle, { backgroundColor: option.color }]} />
      <Text style={styles.colorName}>{option.name}</Text>
    </TouchableOpacity>
  );

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <ScrollView
        style={styles.scrollView}
        contentContainerStyle={styles.scrollContent}
        showsVerticalScrollIndicator={false}
      >
        {/* Form */}
        <View style={styles.form}>
          <CustomInput
            label="Nome da Categoria"
            value={formData.name}
            onChangeText={(text) => updateFormData('name', text)}
            placeholder="Digite o nome da categoria"
            error={errors.name}
          />

          {/* Type Selection */}
          <View style={styles.typeContainer}>
            <Text style={styles.label}>Tipo</Text>
            <View style={styles.typeOptions}>
              <TouchableOpacity
                style={[
                  styles.typeOption,
                  formData.type === 'expense' && styles.typeOptionSelected,
                ]}
                onPress={() => updateFormData('type', 'expense')}
              >
                <Ionicons
                  name="trending-down"
                  size={20}
                  color={formData.type === 'expense' ? COLORS.white : COLORS.danger}
                />
                <Text
                  style={[
                    styles.typeOptionText,
                    formData.type === 'expense' && styles.typeOptionTextSelected,
                  ]}
                >
                  Despesa
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={[
                  styles.typeOption,
                  formData.type === 'income' && styles.typeOptionSelected,
                ]}
                onPress={() => updateFormData('type', 'income')}
              >
                <Ionicons
                  name="trending-up"
                  size={20}
                  color={formData.type === 'income' ? COLORS.white : COLORS.success}
                />
                <Text
                  style={[
                    styles.typeOptionText,
                    formData.type === 'income' && styles.typeOptionTextSelected,
                  ]}
                >
                  Receita
                </Text>
              </TouchableOpacity>
            </View>
          </View>

          {/* Color Selection */}
          <View style={styles.colorContainer}>
            <Text style={styles.label}>Cor</Text>
            <View style={styles.colorGrid}>
              {colorOptions.map(renderColorOption)}
            </View>
          </View>
        </View>

        {/* Save Button */}
        <View style={styles.footer}>
          <CustomButton
            title="Salvar Categoria"
            onPress={handleSave}
            loading={loading}
            style={styles.saveButton}
          />
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
  headerRight: {
    width: 32,
  },
  scrollView: {
    flex: 1,
  },
  scrollContent: {
    flexGrow: 1,
  },
  form: {
    padding: SPACING.lg,
  },
  label: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '500',
    color: COLORS.dark,
    marginBottom: SPACING.sm,
  },
  typeContainer: {
    marginBottom: SPACING.lg,
  },
  typeOptions: {
    flexDirection: 'row',
    gap: SPACING.md,
  },
  typeOption: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    padding: SPACING.md,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: COLORS.lightGray,
    backgroundColor: COLORS.white,
  },
  typeOptionSelected: {
    backgroundColor: COLORS.primary,
    borderColor: COLORS.primary,
  },
  typeOptionText: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '500',
    color: COLORS.dark,
    marginLeft: SPACING.xs,
  },
  typeOptionTextSelected: {
    color: COLORS.white,
  },
  colorContainer: {
    marginBottom: SPACING.lg,
  },
  colorGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: SPACING.sm,
  },
  colorOption: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: SPACING.sm,
    borderRadius: 8,
    borderWidth: 2,
    backgroundColor: COLORS.white,
    minWidth: 80,
  },
  colorCircle: {
    width: 20,
    height: 20,
    borderRadius: 10,
    marginRight: SPACING.xs,
  },
  colorName: {
    fontSize: FONT_SIZES.small,
    color: COLORS.dark,
  },
  footer: {
    padding: SPACING.lg,
    borderTopWidth: 1,
    borderTopColor: COLORS.lightGray,
  },
  saveButton: {
    marginTop: 0,
  },
});

export default AddCategoryScreen;
