import React, { createContext, useContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import transactionService from '../services/transactionService';

const FilterContext = createContext();

export const useFilter = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error('useFilter must be used within a FilterProvider');
  }
  return context;
};

export const FilterProvider = ({ children }) => {
  const [allTransactions, setAllTransactions] = useState([]);
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [isFiltered, setIsFiltered] = useState(false);
  const [offset, setOffset] = useState(0);
  const [loadingMore, setLoadingMore] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    loadTransactions(true);
  }, []);

  useEffect(() => {
    filterTransactions();
  }, [searchQuery, allTransactions]);

  const loadTransactions = async (reset = false) => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) return;

      const userData = JSON.parse(user);
      if (reset) {
        setOffset(0);
        setHasMore(true);
        const transactionsData = await transactionService.getTransactionsByUser(userData.id, 10, 0);
        setAllTransactions(transactionsData);
        setFilteredTransactions(transactionsData);
        setOffset(10);
      } else {
        const transactionsData = await transactionService.getTransactionsByUser(userData.id, 10, offset);
        if (transactionsData.length === 0) {
          setHasMore(false);
        } else {
          setAllTransactions(prev => [...prev, ...transactionsData]);
          setFilteredTransactions(prev => [...prev, ...transactionsData]);
          setOffset(prev => prev + 10);
        }
      }
    } catch (error) {
      console.error('Erro ao carregar transações:', error);
    } finally {
      setLoadingMore(false);
    }
  };

  const filterTransactions = () => {
    if (!searchQuery.trim()) {
      setFilteredTransactions(allTransactions);
      setIsFiltered(false);
      return;
    }

    // If filtering, load all transactions to search properly
    if (hasMore) {
      loadAllTransactionsForFilter();
    } else {
      const filtered = allTransactions.filter(transaction =>
        transaction.description.toLowerCase().includes(searchQuery.toLowerCase())
      );
      setFilteredTransactions(filtered);
      setIsFiltered(true);
    }
  };

  const loadAllTransactionsForFilter = async () => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) return;

      const userData = JSON.parse(user);
      const transactionsData = await transactionService.getTransactionsByUser(userData.id);
      setAllTransactions(transactionsData);
      const filtered = transactionsData.filter(transaction =>
        transaction.description.toLowerCase().includes(searchQuery.toLowerCase())
      );
      setFilteredTransactions(filtered);
      setIsFiltered(true);
    } catch (error) {
      console.error('Erro ao carregar transações para filtro:', error);
    }
  };

  const clearFilters = () => {
    setSearchQuery('');
    setIsFiltered(false);
    setFilteredTransactions(allTransactions);
  };

  const updateSearchQuery = (query) => {
    setSearchQuery(query);
  };

  const loadMore = async () => {
    if (loadingMore || !hasMore || isFiltered) return;
    setLoadingMore(true);
    await loadTransactions(false);
  };

  const refreshTransactions = async () => {
    await loadTransactions(true);
  };

  return (
    <FilterContext.Provider value={{
      allTransactions,
      filteredTransactions,
      searchQuery,
      isFiltered,
      updateSearchQuery,
      clearFilters,
      loadTransactions,
      loadMore,
      loadingMore,
      hasMore,
      refreshTransactions,
    }}>
      {children}
    </FilterContext.Provider>
  );
};
