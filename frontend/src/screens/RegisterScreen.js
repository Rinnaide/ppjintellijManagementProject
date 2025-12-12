import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Alert,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';
import userService from '../services/userService';
import { isValidEmail, isValidPassword } from '../utils/helpers';
import { COLORS, SPACING } from '../utils/constants';
import api from '../services/api'
const RegisterScreen = () => {
  const navigation = useNavigation();
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: ''
  });
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  const updateFormData = (field, value) => {
    setFormData({ ...formData, [field]: value });
    if (errors[field]) {
      setErrors({ ...errors, [field]: null });
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.firstName.trim()) {
      newErrors.firstName = 'Nome é obrigatório';
    }

    if (!formData.lastName.trim()) {
      newErrors.lastName = 'Sobrenome é obrigatório';
    }

    if (!formData.phone.trim()) {
      newErrors.phone = 'Número é obrigatório';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email é obrigatório';
    } else if (!isValidEmail(formData.email)) {
      newErrors.email = 'Email inválido';
    }

    if (!formData.password) {
      newErrors.password = 'Senha é obrigatória';
    } else if (!isValidPassword(formData.password)) {
      newErrors.password = 'Senha deve ter pelo menos 6 caracteres';
    }

    if (!formData.confirmPassword) {
      newErrors.confirmPassword = 'Confirmação de senha é obrigatória';
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Senhas não coincidem';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleRegister = async () => {
    if (!validateForm()) return;

    setLoading(true);
    try {
      const userData = {
        'email': formData.email,
        'password': formData.password,
        'firstName': formData.firstName,
        'lastName': formData.lastName,
        'phone': formData.phone,
        'defaultCurrency': 'BRL',
        'timezone': 'America/Sao_Paulo',
        'isActive': true,
        'emailVerified': true

      };
      console.log(userData)
      await api.post('/users', userData)
      Alert.alert(
        'Sucesso',
        'Conta criada com sucesso! Faça login para continuar.',
        [{ text: 'OK', onPress: () => navigation.navigate('Login') }]
      );
    } catch (error) {
      Alert.alert(
        'Erro no Cadastro',
        error.message || 'Erro ao criar conta'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />
      <KeyboardAvoidingView
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={styles.keyboardAvoidingView}
      >
        <ScrollView
          contentContainerStyle={styles.scrollContainer}
          showsVerticalScrollIndicator={false}
        >
          <View style={styles.header}>
            <Text style={styles.title}>Criar Conta</Text>
            <Text style={styles.subtitle}>
              Preencha os dados para criar sua conta
            </Text>
          </View>

          <View style={styles.form}>
            <View style={styles.row}>
              <CustomInput
                label="Nome"
                value={formData.firstName}
                onChangeText={(text) => updateFormData('firstName', text)}
                placeholder="Digite seu nome"
                style={styles.halfInput}
                error={errors.firstName}
              />
              <CustomInput
                label="Sobrenome"
                value={formData.lastName}
                onChangeText={(text) => updateFormData('lastName', text)}
                placeholder="Digite seu sobrenome"
                style={styles.halfInput}
                error={errors.lastName}
              />
            </View>

            <CustomInput
              label="Email"
              value={formData.email}
              onChangeText={(text) => updateFormData('email', text)}
              placeholder="Digite seu email"
              keyboardType="email-address"
              autoCapitalize="none"
              leftIcon="mail"
              error={errors.email}
            />

            <CustomInput
              label="Numero de telefone"
              value={formData.phone}
              onChangeText={(text) => updateFormData('phone', text)}
              placeholder="Digite seu numero de telefone"
              style={styles.halfInput}
              error={errors.confirmPassword}
            />

            <CustomInput
              label="Senha"
              value={formData.password}
              onChangeText={(text) => updateFormData('password', text)}
              placeholder="Digite sua senha"
              secureTextEntry
              leftIcon="lock-closed"
              error={errors.password}
            />

            <CustomInput
              label="Confirmar Senha"
              value={formData.confirmPassword}
              onChangeText={(text) => updateFormData('confirmPassword', text)}
              placeholder="Confirme sua senha"
              secureTextEntry
              leftIcon="lock-closed"
              error={errors.confirmPassword}
            />



            <CustomButton
              title="Criar Conta"
              onPress={handleRegister}
              loading={loading}
              style={styles.registerButton}
            />

            <CustomButton
              title="Já tenho conta"
              onPress={() => navigation.navigate('Login')}
              variant="outline"
              style={styles.loginButton}
            />
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
  scrollContainer: {
    flexGrow: 1,
    justifyContent: 'center',
    paddingHorizontal: SPACING.lg,
  },
  header: {
    alignItems: 'center',
    marginBottom: SPACING.xl,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginBottom: SPACING.sm,
  },
  subtitle: {
    fontSize: 16,
    color: COLORS.gray,
    textAlign: 'center',
  },
  form: {
    width: '100%',
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  halfInput: {
    width: '48%',
  },
  registerButton: {
    marginTop: SPACING.md,
  },
  loginButton: {
    marginTop: SPACING.md,
  },
});

export default RegisterScreen;
