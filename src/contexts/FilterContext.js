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

  useEffect(() => {
    loadTransactions();
  }, []);

  useEffect(() => {
    filterTransactions();
  }, [searchQuery, allTransactions]);

  const loadTransactions = async () => {
    try {
      const user = await AsyncStorage.getItem('user');
      if (!user) return;

      const userData = JSON.parse(user);
      const transactionsData = await transactionService.getTransactionsByUser(userData.id);
      setAllTransactions(transactionsData);
      setFilteredTransactions(transactionsData);
    } catch (error) {
      console.error('Erro ao carregar transações:', error);
    }
  };

  const filterTransactions = () => {
    if (!searchQuery.trim()) {
      setFilteredTransactions(allTransactions);
      setIsFiltered(false);
      return;
    }

    const filtered = allTransactions.filter(transaction =>
      transaction.description.toLowerCase().includes(searchQuery.toLowerCase())
    );
    setFilteredTransactions(filtered);
    setIsFiltered(true);
  };

  const clearFilters = () => {
    setSearchQuery('');
    setIsFiltered(false);
    setFilteredTransactions(allTransactions);
  };

  const updateSearchQuery = (query) => {
    setSearchQuery(query);
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
    }}>
      {children}
    </FilterContext.Provider>
  );
};
