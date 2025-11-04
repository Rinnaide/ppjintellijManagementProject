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
import { useNavigation, useFocusEffect } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import CustomButton from '../components/CustomButton';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import transactionService from '../services/transactionService';
import categoryService from '../services/categoryService';

const ProfileScreen = () => {
  const navigation = useNavigation();
  const [user, setUser] = useState(null);
  const [stats, setStats] = useState({
    totalTransactions: 0,
    totalIncome: 0,
    totalExpense: 0,
    totalCategories: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadUserData();
  }, []);

  useFocusEffect(
    React.useCallback(() => {
      loadUserData();
    }, [])
  );

  const loadUserData = async () => {
    try {
      const userData = await AsyncStorage.getItem('user');
      if (userData) {
        const parsedUser = JSON.parse(userData);
        setUser(parsedUser);

        // Load statistics
        const [transactions, income, expense, categories] = await Promise.all([
          transactionService.getTransactionsByUser(parsedUser.id),
          transactionService.getTotalIncome(parsedUser.id),
          transactionService.getTotalExpense(parsedUser.id),
          categoryService.getCategoriesByUser(parsedUser.id),
        ]);

        setStats({
          totalTransactions: transactions.length,
          totalIncome: income,
          totalExpense: expense,
          totalCategories: categories.length,
        });
      }
    } catch (error) {
      Alert.alert('Erro', 'Erro ao carregar dados do perfil');
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    Alert.alert(
      'Sair',
      'Tem certeza que deseja sair da conta?',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Sair',
          style: 'destructive',
          onPress: async () => {
            try {
              await AsyncStorage.removeItem('user');
              navigation.reset({
                index: 0,
                routes: [{ name: 'Login' }],
              });
            } catch (error) {
              Alert.alert('Erro', 'Erro ao fazer logout');
            }
          },
        },
      ]
    );
  };

  const handleEditProfile = () => {
    navigation.navigate('EditProfile', { user });
  };

  if (loading) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.loadingContainer}>
          <Text>Carregando perfil...</Text>
        </View>
      </SafeAreaView>
    );
  }

  if (!user) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.errorContainer}>
          <Text style={styles.errorText}>Usuário não encontrado</Text>
          <CustomButton
            title="Voltar ao Login"
            onPress={() => navigation.navigate('Login')}
            style={styles.backButton}
          />
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <ScrollView style={styles.scrollView} showsVerticalScrollIndicator={false}>
        <View style={styles.content}>
          {/* Header */}
          <View style={styles.header}>
            <View style={styles.avatarContainer}>
              <Ionicons name="person-circle" size={80} color={COLORS.primary} />
            </View>
            <Text style={styles.userName}>
              {user.firstName} {user.lastName}
            </Text>
            <Text style={styles.userEmail}>{user.usuarioEmail}</Text>
          </View>

          {/* Statistics */}
          <View style={styles.statsContainer}>
            <Text style={styles.sectionTitle}>Estatísticas</Text>
            <View style={styles.statsGrid}>
              <View style={styles.statItem}>
                <Ionicons name="receipt-outline" size={24} color={COLORS.primary} />
                <Text style={styles.statValue}>{stats.totalTransactions}</Text>
                <Text style={styles.statLabel}>Transações</Text>
              </View>
              <View style={styles.statItem}>
                <Ionicons name="trending-up-outline" size={24} color={COLORS.success} />
                <Text style={styles.statValue}>R$ {stats.totalIncome.toFixed(2)}</Text>
                <Text style={styles.statLabel}>Receitas</Text>
              </View>
              <View style={styles.statItem}>
                <Ionicons name="trending-down-outline" size={24} color={COLORS.danger} />
                <Text style={styles.statValue}>R$ {stats.totalExpense.toFixed(2)}</Text>
                <Text style={styles.statLabel}>Despesas</Text>
              </View>
              <View style={styles.statItem}>
                <Ionicons name="list-outline" size={24} color={COLORS.primary} />
                <Text style={styles.statValue}>{stats.totalCategories}</Text>
                <Text style={styles.statLabel}>Categorias</Text>
              </View>
            </View>
          </View>

          {/* User Information */}
          <View style={styles.infoContainer}>
            <Text style={styles.sectionTitle}>Informações da Conta</Text>
            <View style={styles.infoItem}>
              <Ionicons name="mail-outline" size={20} color={COLORS.gray} />
              <View style={styles.infoContent}>
                <Text style={styles.infoLabel}>Email</Text>
                <Text style={styles.infoValue}>{user.usuarioEmail}</Text>
              </View>
            </View>
            <View style={styles.infoItem}>
              <Ionicons name="person-outline" size={20} color={COLORS.gray} />
              <View style={styles.infoContent}>
                <Text style={styles.infoLabel}>Nome</Text>
                <Text style={styles.infoValue}>{user.firstName} {user.lastName}</Text>
              </View>
            </View>
            {user.phone && (
              <View style={styles.infoItem}>
                <Ionicons name="call-outline" size={20} color={COLORS.gray} />
                <View style={styles.infoContent}>
                  <Text style={styles.infoLabel}>Telefone</Text>
                  <Text style={styles.infoValue}>{user.phone}</Text>
                </View>
              </View>
            )}
          </View>

          {/* Actions */}
          <View style={styles.actionsContainer}>
            <CustomButton
              title="Editar Perfil"
              onPress={handleEditProfile}
              variant="outline"
              style={styles.editButton}
            />
            <CustomButton
              title="Sair da Conta"
              onPress={handleLogout}
              variant="danger"
              style={styles.logoutButton}
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
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: SPACING.lg,
  },
  errorText: {
    fontSize: FONT_SIZES.large,
    color: COLORS.gray,
    marginBottom: SPACING.lg,
  },
  backButton: {
    marginTop: 0,
  },
  header: {
    alignItems: 'center',
    paddingVertical: SPACING.xl,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
    marginBottom: SPACING.xl,
  },
  avatarContainer: {
    marginBottom: SPACING.md,
  },
  userName: {
    fontSize: FONT_SIZES.xlarge,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginBottom: SPACING.xs,
  },
  userEmail: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.gray,
  },
  statsContainer: {
    marginBottom: SPACING.xl,
  },
  sectionTitle: {
    fontSize: FONT_SIZES.large,
    fontWeight: '600',
    color: COLORS.dark,
    marginBottom: SPACING.md,
  },
  statsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: SPACING.md,
  },
  statItem: {
    flex: 1,
    minWidth: 120,
    alignItems: 'center',
    backgroundColor: COLORS.light,
    padding: SPACING.md,
    borderRadius: 8,
  },
  statValue: {
    fontSize: FONT_SIZES.large,
    fontWeight: 'bold',
    color: COLORS.dark,
    marginTop: SPACING.xs,
    marginBottom: SPACING.xs,
  },
  statLabel: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    textAlign: 'center',
  },
  infoContainer: {
    marginBottom: SPACING.xl,
  },
  infoItem: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    paddingVertical: SPACING.md,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  infoContent: {
    flex: 1,
    marginLeft: SPACING.sm,
  },
  infoLabel: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginBottom: SPACING.xs,
  },
  infoValue: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.dark,
    fontWeight: '500',
  },
  actionsContainer: {
    gap: SPACING.md,
  },
  editButton: {
    marginTop: 0,
  },
  logoutButton: {
    marginTop: 0,
  },
});

export default ProfileScreen;
