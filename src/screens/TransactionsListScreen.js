import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  RefreshControl,
  Alert,
  TouchableOpacity,
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
import { formatCurrency, formatDate } from '../utils/helpers';

const TransactionsListScreen = () => {
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

      setTransactions(transactionsData);
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

  const handleAddTransaction = () => {
    navigation.navigate('AddTransaction');
  };

  const handleDeleteTransaction = async (transactionId) => {
    Alert.alert(
      'Confirmar Exclusão',
      'Tem certeza que deseja excluir esta transação?',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Excluir',
          style: 'destructive',
          onPress: async () => {
            try {
              await transactionService.deleteTransaction(transactionId);
              loadTransactions(); // Recarregar lista
              Alert.alert('Sucesso', 'Transação excluída com sucesso');
            } catch (error) {
              Alert.alert('Erro', 'Erro ao excluir transação');
            }
          },
        },
      ]
    );
  };

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

  const renderHeader = () => (
    <View style={styles.header}>
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
        <View style={styles.summaryItem}>
          <Text style={styles.summaryLabel}>Saldo</Text>
          <Text style={[styles.summaryValue, {
            color: (totalIncome - totalExpense) >= 0 ? COLORS.success : COLORS.danger
          }]}>
            {formatCurrency(totalIncome - totalExpense)}
          </Text>
        </View>
      </View>
    </View>
  );

  if (loading) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.loadingContainer}>
          <Text>Carregando transações...</Text>
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      {/* Header com resumo */}
      {renderHeader()}

      {/* Lista de transações */}
      <View style={styles.listContainer}>
        <FlatList
          data={transactions}
          renderItem={renderTransaction}
          keyExtractor={(item) => item.id}
          ListEmptyComponent={renderEmpty}
          refreshControl={
            <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
          }
          showsVerticalScrollIndicator={false}
          contentContainerStyle={transactions.length === 0 ? styles.emptyList : null}
        />
      </View>

      {/* Botão flutuante para adicionar transação */}
      <View style={styles.fabContainer}>
        <TouchableOpacity style={styles.fab} onPress={handleAddTransaction}>
          <Ionicons name="add" size={24} color={COLORS.white} />
        </TouchableOpacity>
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
  summaryContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  summaryItem: {
    flex: 1,
    alignItems: 'center',
  },
  summaryLabel: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginBottom: SPACING.xs,
  },
  summaryValue: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
  },
  listContainer: {
    flex: 1,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: SPACING.xl,
  },
  emptyList: {
    flexGrow: 1,
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
});

export default TransactionsListScreen;
