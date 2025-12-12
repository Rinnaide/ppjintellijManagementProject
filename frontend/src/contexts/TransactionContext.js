import React, { createContext, useContext, useState, useEffect, useCallback, useRef } from 'react';
import transactionService from '../services/transactionService';
import api from '../services/api';
import { useAuth } from './AuthContext';
const TransactionContext = createContext({
  resetContext: () => {},
});

export const useTransaction = () => {
  const context = useContext(TransactionContext);
  if (!context) {
    throw new Error('useTransaction must be used within a TransactionProvider');
  }
  return context;
};

export const TransactionProvider = ({ children }) => {
  const { user } = useAuth();
  const [transactions, setTransactions] = useState([]);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpense, setTotalExpense] = useState(0);
  const [loading, setLoading] = useState(true);
  const [migrationDone, setMigrationDone] = useState(false);
  const userIdRef = useRef(null);

  const loadTransactions = useCallback(async () => {
    try {
      if (!user || !user.id) return;

      // Run migration only once per app session after user is authenticated
      // if (!migrationDone) {
      //   await transactionService.migrateTransactionIds(user.id);
      //   setMigrationDone(true);
      // }

      const transactionsData = await api.get(`/transactions/user/${user.id}`)
      const incomeData = await api.get(`/transactions/user/${user.id}/total-income`)
      const totalExpense = await api.get(`/transactions/user/${user.id}/total-expense`)
      console.log(transactionsData)
      console.log(incomeData)
      // console.log(transactionsData)
      // const [transactionsData, incomeData, expenseData] = await Promise.all([
      //   transactionService.getTransactionsByUser(user.id),
      //   transactionService.getTotalIncome(user.id),
      //   transactionService.getTotalExpense(user.id),
      // ]);

      setTransactions(transactionsData);
      setTotalIncome(incomeData);
      setTotalExpense(totalExpense);
    } catch (error) {
      console.error('Erro ao carregar transações:', error);
    } finally {
      setLoading(false);
    }
  }, [migrationDone, user]);

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

  const updateTransaction = useCallback(async (uniqueId, transactionData) => {
    try {
      const updatedTransaction = await transactionService.updateTransaction(uniqueId, transactionData);

      // Update local state
      setTransactions(prev => prev.map(t => t.uniqueId === uniqueId ? updatedTransaction : t));

      // Recalculate totals (simplified - could be optimized)
      await loadTransactions();

      return updatedTransaction;
    } catch (error) {
      throw error;
    }
  }, [loadTransactions]);

  const deleteTransaction = useCallback(async (uniqueId) => {
    try {
      await transactionService.deleteTransaction(uniqueId);

      // Update local state
      const transactionToDelete = transactions.find(t => t.uniqueId === uniqueId);
      setTransactions(prev => prev.filter(t => t.uniqueId !== uniqueId));

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

  // Only load transactions when user is authenticated, not on app mount
  useEffect(() => {
    if (user && user.id) {
      userIdRef.current = user.id;
      loadTransactions();
    } else {
      setLoading(false);
    }
  }, [user, loadTransactions]);

  const resetContext = () => {
    setTransactions([]);
    setTotalIncome(0);
    setTotalExpense(0);
    setLoading(true);
    setMigrationDone(false);
    userIdRef.current = null;
  };

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
      resetContext,
    }}>
      {children}
    </TransactionContext.Provider>
  );
};
