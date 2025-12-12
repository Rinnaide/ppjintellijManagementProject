import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { useNavigation, useRoute } from '@react-navigation/native';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';
import userService from '../services/userService';
import { useAuth } from '../contexts/AuthContext';
import { COLORS, SPACING } from '../utils/constants';
import { isValidEmail, isValidPassword } from '../utils/helpers';

const EditProfileScreen = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const { user: initialUser } = route.params || {};
  const { user } = useAuth();

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialUser) {
      setFormData({
        firstName: initialUser.firstName || '',
        lastName: initialUser.lastName || '',
        email: initialUser.usuarioEmail || '',
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
      });
    }
  }, [initialUser]);

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

    if (!formData.email.trim()) {
      newErrors.email = 'Email é obrigatório';
    } else if (!isValidEmail(formData.email)) {
      newErrors.email = 'Email inválido';
    }

    if (formData.newPassword) {
      if (!formData.currentPassword) {
        newErrors.currentPassword = 'Senha atual é obrigatória para alterar senha';
      }

      if (!isValidPassword(formData.newPassword)) {
        newErrors.newPassword = 'Nova senha deve ter pelo menos 6 caracteres';
      }

      if (formData.newPassword !== formData.confirmPassword) {
        newErrors.confirmPassword = 'Senhas não coincidem';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleUpdate = async () => {
    if (!validateForm()) return;

    setLoading(true);
    try {
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const updateData = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        usuarioEmail: formData.email,
      };

      // If password change is requested
      if (formData.newPassword) {
        // Verify current password
        const isValidCurrentPassword = await userService.verifyPassword(user.id, formData.currentPassword);
        if (!isValidCurrentPassword) {
          Alert.alert('Erro', 'Senha atual incorreta');
          setLoading(false);
          return;
        }
        updateData.usuario_senha = formData.newPassword;
      }

      await userService.updateUser(user.id, updateData);

      Alert.alert(
        'Sucesso',
        'Perfil atualizado com sucesso!',
        [{ text: 'OK', onPress: () => navigation.goBack() }]
      );
    } catch (error) {
      Alert.alert('Erro', 'Erro ao atualizar perfil');
    } finally {
      setLoading(false);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <KeyboardAvoidingView
        style={styles.keyboardAvoidingView}
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      >
        <ScrollView style={styles.scrollView} showsVerticalScrollIndicator={false}>
          <View style={styles.content}>
            <Text style={styles.title}>Editar Perfil</Text>

            <View style={styles.form}>
              <CustomInput
                label="Nome"
                value={formData.firstName}
                onChangeText={(text) => updateFormData('firstName', text)}
                placeholder="Digite seu nome"
                error={errors.firstName}
              />

              <CustomInput
                label="Sobrenome"
                value={formData.lastName}
                onChangeText={(text) => updateFormData('lastName', text)}
                placeholder="Digite seu sobrenome"
                error={errors.lastName}
              />

              <CustomInput
                label="Email"
                value={formData.email}
                onChangeText={(text) => updateFormData('email', text)}
                placeholder="Digite seu email"
                keyboardType="email-address"
                autoCapitalize="none"
                error={errors.email}
              />



              <Text style={styles.sectionTitle}>Alterar Senha (opcional)</Text>

              <CustomInput
                label="Senha Atual"
                value={formData.currentPassword}
                onChangeText={(text) => updateFormData('currentPassword', text)}
                placeholder="Digite sua senha atual"
                secureTextEntry
                error={errors.currentPassword}
              />

              <CustomInput
                label="Nova Senha"
                value={formData.newPassword}
                onChangeText={(text) => updateFormData('newPassword', text)}
                placeholder="Digite sua nova senha"
                secureTextEntry
                error={errors.newPassword}
              />

              <CustomInput
                label="Confirmar Nova Senha"
                value={formData.confirmPassword}
                onChangeText={(text) => updateFormData('confirmPassword', text)}
                placeholder="Confirme sua nova senha"
                secureTextEntry
                error={errors.confirmPassword}
              />

              <CustomButton
                title="Salvar Alterações"
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
  form: {
    width: '100%',
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: '600',
    color: COLORS.dark,
    marginTop: SPACING.lg,
    marginBottom: SPACING.sm,
  },
  updateButton: {
    marginTop: SPACING.md,
  },
});

export default EditProfileScreen;
