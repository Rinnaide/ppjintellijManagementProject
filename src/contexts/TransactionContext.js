import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import transactionService from '../services/transactionService';

const TransactionContext = createContext();

export const useTransaction = () => {
  const context = useContext(TransactionContext);
  if (!context) {
    throw new Error('useTransaction must be used within a TransactionProvider');
  }
  return context;
};

export const TransactionProvider = ({ children }) => {
  const [transactions, setTransactions] = useState([]);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpense, setTotalExpense] = useState(0);
  const [loading, setLoading] = useState(true);

  const loadTransactions = useCallback(async () => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) return;

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
      console.error('Erro ao carregar transações:', error);
    } finally {
      setLoading(false);
    }
  }, []);

  const addTransaction = useCallback(async (transactionData) => {
    try {
      const newTransaction = await transactionService.createTransaction(transactionData);

      // Update local state immediately
      setTransactions(prev => [newTransaction, ...prev]);

      // Update totals based on transaction type
      if (newTransaction.type === 'income') {
        setTotalIncome(prev => prev + parseFloat(newTransaction.amount));
      } else if (newTransaction.type === 'expense') {
        setTotalExpense(prev => prev + parseFloat(newTransaction.amount));
      }

      return newTransaction;
    } catch (error) {
      throw error;
    }
  }, []);

  const updateTransaction = useCallback(async (id, transactionData) => {
    try {
      const updatedTransaction = await transactionService.updateTransaction(id, transactionData);

      // Update local state
      setTransactions(prev => prev.map(t => t.id === id ? updatedTransaction : t));

      // Recalculate totals (simplified - could be optimized)
      await loadTransactions();

      return updatedTransaction;
    } catch (error) {
      throw error;
    }
  }, [loadTransactions]);

  const deleteTransaction = useCallback(async (id) => {
    try {
      await transactionService.deleteTransaction(id);

      // Update local state
      const transactionToDelete = transactions.find(t => t.id === id);
      setTransactions(prev => prev.filter(t => t.id !== id));

      // Update totals
      if (transactionToDelete) {
        if (transactionToDelete.type === 'income') {
          setTotalIncome(prev => prev - parseFloat(transactionToDelete.amount));
        } else if (transactionToDelete.type === 'expense') {
          setTotalExpense(prev => prev - parseFloat(transactionToDelete.amount));
        }
      }
    } catch (error) {
      throw error;
    }
  }, [transactions]);

  useEffect(() => {
    loadTransactions();
  }, [loadTransactions]);

  const balance = totalIncome - totalExpense;

  return (
    <TransactionContext.Provider value={{
      transactions,
      totalIncome,
      totalExpense,
      balance,
      loading,
      loadTransactions,
      addTransaction,
      updateTransaction,
      deleteTransaction,
    }}>
      {children}
    </TransactionContext.Provider>
  );
};
