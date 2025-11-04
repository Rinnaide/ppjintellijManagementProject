import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  RefreshControl,
  Alert,
} from 'react-native';
import { useNavigation, useFocusEffect } from '@react-navigation/native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import TransactionItem from '../components/TransactionItem';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import { formatCurrency } from '../utils/helpers';

const HomeScreen = ({ route }) => {
  const navigation = useNavigation();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpense, setTotalExpense] = useState(0);

  const loadTransactions = useCallback(async () => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) {
        Alert.alert('Erro', 'Usuário não encontrado');
        return;
      }

      const userData = JSON.parse(user);
      const [transactionsData, incomeData, expenseData] = await Promise.all([
        transactionService.getTransactionsByUser(userData.id),
        transactionService.getTotalIncome(userData.id),
        transactionService.getTotalExpense(userData.id),
      ]);

      setTransactions(transactionsData.slice(0, 10)); // Mostrar apenas as 10 mais recentes
      setTotalIncome(incomeData);
      setTotalExpense(expenseData);
    } catch (error) {
      Alert.alert('Erro', 'Erro ao carregar transações');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useFocusEffect(
    React.useCallback(() => {
      loadTransactions();
    }, [])
  );

  const onRefresh = () => {
    setRefreshing(true);
    loadTransactions();
  };

  const handleTransactionPress = (transaction) => {
    navigation.navigate('TransactionDetail', { transaction });
  };

  const balance = totalIncome - totalExpense;

  const renderTransaction = ({ item }) => (
    <TransactionItem
      transaction={item}
      onPress={() => handleTransactionPress(item)}
    />
  );

  const renderEmpty = () => (
    <View style={styles.emptyContainer}>
      <Ionicons name="receipt-outline" size={64} color={COLORS.lightGray} />
      <Text style={styles.emptyText}>Nenhuma transação encontrada</Text>
      <Text style={styles.emptySubtext}>
        Adicione sua primeira transação para começar
      </Text>
    </View>
  );

  if (loading) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.loadingContainer}>
          <Text>Carregando...</Text>
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      {/* Header com saldo */}
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Saldo Atual</Text>
        <Text style={[styles.balance, { color: balance >= 0 ? COLORS.success : COLORS.danger }]}>
          {formatCurrency(balance)}
        </Text>
        <View style={styles.summaryContainer}>
          <View style={styles.summaryItem}>
            <Text style={styles.summaryLabel}>Receitas</Text>
            <Text style={[styles.summaryValue, { color: COLORS.success }]}>
              +{formatCurrency(totalIncome)}
            </Text>
          </View>
          <View style={styles.summaryItem}>
            <Text style={styles.summaryLabel}>Despesas</Text>
            <Text style={[styles.summaryValue, { color: COLORS.danger }]}>
              -{formatCurrency(totalExpense)}
            </Text>
          </View>
        </View>
      </View>

      {/* Lista de transações */}
      <View style={styles.transactionsContainer}>
        <View style={styles.transactionsHeader}>
          <Text style={styles.transactionsTitle}>Transações Recentes</Text>
          <CustomButton
            title="Ver Todas"
            onPress={() => navigation.navigate('Transactions')}
            variant="outline"
            size="small"
          />
        </View>

        <FlatList
          data={transactions}
          renderItem={renderTransaction}
          keyExtractor={(item) => item.id}
          ListEmptyComponent={renderEmpty}
          refreshControl={
            <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
          }
          showsVerticalScrollIndicator={false}
        />
      </View>

      {/* Botão flutuante para adicionar transação */}
      <View style={styles.fabContainer}>
        <CustomButton
          title=""
          onPress={() => navigation.navigate('AddTransaction')}
          style={styles.fab}
          textStyle={styles.fabText}
        >
          <Ionicons name="add" size={24} color={COLORS.white} />
        </CustomButton>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.light,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  header: {
    backgroundColor: COLORS.white,
    padding: SPACING.lg,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  headerTitle: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.gray,
    marginBottom: SPACING.sm,
  },
  balance: {
    fontSize: FONT_SIZES.xxlarge,
    fontWeight: 'bold',
    marginBottom: SPACING.md,
  },
  summaryContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  summaryItem: {
    flex: 1,
  },
  summaryLabel: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginBottom: SPACING.xs,
  },
  summaryValue: {
    fontSize: FONT_SIZES.large,
    fontWeight: '600',
  },
  transactionsContainer: {
    flex: 1,
    backgroundColor: COLORS.light,
  },
  transactionsHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: SPACING.lg,
    backgroundColor: COLORS.white,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  transactionsTitle: {
    fontSize: FONT_SIZES.large,
    fontWeight: '600',
    color: COLORS.dark,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: SPACING.xl,
  },
  emptyText: {
    fontSize: FONT_SIZES.large,
    fontWeight: '600',
    color: COLORS.gray,
    marginTop: SPACING.md,
    marginBottom: SPACING.sm,
  },
  emptySubtext: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.lightGray,
    textAlign: 'center',
  },
  fabContainer: {
    position: 'absolute',
    bottom: SPACING.lg,
    right: SPACING.lg,
  },
  fab: {
    width: 56,
    height: 56,
    borderRadius: 28,
    backgroundColor: COLORS.primary,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: COLORS.black,
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  fabText: {
    display: 'none', // Esconder texto, mostrar apenas ícone
  },
});

export default HomeScreen;
