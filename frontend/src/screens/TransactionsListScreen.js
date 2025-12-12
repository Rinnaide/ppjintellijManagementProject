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
import TransactionItem from '../components/TransactionItem';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import { formatCurrency, formatDate } from '../utils/helpers';
import { useFilter } from '../contexts/FilterContext';
import { useTransaction } from '../contexts/TransactionContext';

const TransactionsListScreen = () => {
  const navigation = useNavigation();
  const { allTransactions, filteredTransactions, searchQuery, isFiltered, clearFilters, loadTransactions: loadTransactionsFromContext, loadMore, loadingMore, hasMore, refreshTransactions } = useFilter();
  const { totalIncome, totalExpense } = useTransaction();
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);

  const  loadData = async () => {

  }

  useFocusEffect(
    React.useCallback(() => {
      loadTransactionsFromContext();

      
      setLoading(false);
    }, [])
  );

  const onRefresh = () => {
    setRefreshing(true);
    refreshTransactions();
    setRefreshing(false);
  };

  const handleTransactionPress = (transaction) => {
    navigation.navigate('TransactionDetail', { transaction });
  };

  const handleFilterPress = () => {
    navigation.navigate('ListFilter');
  };

  const handleClearFilters = () => {
    clearFilters();
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
              await transactionService.deleteTransaction(transactionId.uniqueId);
              refreshTransactions(); // Recarregar lista
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

      {isFiltered && (
        <View style={styles.filterContainer}>
          <View style={styles.filterInfo}>
            <Ionicons name="funnel" size={16} color={COLORS.primary} />
            <Text style={styles.filterText}>
              Filtrado por: "{searchQuery}" ({filteredTransactions.length} resultado{filteredTransactions.length !== 1 ? 's' : ''})
            </Text>
          </View>
          <TouchableOpacity onPress={handleClearFilters} style={styles.clearFilterButton}>
            <Ionicons name="close-circle" size={20} color={COLORS.gray} />
          </TouchableOpacity>
        </View>
      )}
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
          data={isFiltered ? filteredTransactions : allTransactions}
          renderItem={renderTransaction}
          keyExtractor={(item) => item.uniqueId}
          ListEmptyComponent={renderEmpty}
          refreshControl={
            <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
          }
          showsVerticalScrollIndicator={false}
          contentContainerStyle={(isFiltered ? filteredTransactions.length === 0 : allTransactions.length === 0) ? styles.emptyList : null}
          onEndReached={isFiltered ? null : loadMore}
          onEndReachedThreshold={0.5}
          ListFooterComponent={loadingMore && !isFiltered ? <View style={styles.loadingMore}><Text>Carregando mais...</Text></View> : null}
        />
      </View>

      {/* Botão flutuante para adicionar transação */}
      <View style={styles.fabContainer}>
        <TouchableOpacity style={styles.fab} onPress={handleAddTransaction}>
          <Ionicons name="add" size={24} color={COLORS.white} />
        </TouchableOpacity>
      </View>

      {/* Botão flutuante para filtrar transações */}
      <View style={[styles.fabContainer, { bottom: SPACING.lg + 70, right: SPACING.lg }]}>
        <TouchableOpacity style={[styles.fab, { backgroundColor: COLORS.secondary }]} onPress={handleFilterPress}>
          <Ionicons name="funnel" size={24} color={COLORS.white} />
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
  filterContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: SPACING.md,
    padding: SPACING.md,
    backgroundColor: COLORS.light,
    borderRadius: 8,
  },
  filterInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  filterText: {
    fontSize: FONT_SIZES.small,
    color: COLORS.dark,
    marginLeft: SPACING.sm,
    flex: 1,
  },
  clearFilterButton: {
    padding: SPACING.xs,
  },
  loadingMore: {
    padding: SPACING.md,
    alignItems: 'center',
  },
});

export default TransactionsListScreen;
