import React, { createContext, useContext, useState, useEffect, useRef } from 'react';
import transactionService from '../services/transactionService';
import api from '../services/api';
import { useAuth } from './AuthContext';
const FilterContext = createContext({
  resetContext: () => {},
});

export const useFilter = () => {
  const context = useContext(FilterContext);
  if (!context) {
    throw new Error('useFilter must be used within a FilterProvider');
  }
  return context;
};

export const FilterProvider = ({ children }) => {
  const { user } = useAuth();
  const [allTransactions, setAllTransactions] = useState([]);
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [isFiltered, setIsFiltered] = useState(false);
  const [offset, setOffset] = useState(0);
  const [loadingMore, setLoadingMore] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const userIdRef = useRef(null);

  useEffect(() => {
    const loadUserTransactions = async () => {
      if (user && user.usuario_id) {
        const transactionsData = await api.get(`/transactions/user/${user.usuario_id}`)
        setAllTransactions(transactionsData)
        loadTransactions(true);
      }
    };
    loadUserTransactions();
  }, [user]);

  useEffect(() => {
    filterTransactions();
  }, [searchQuery]);

  const loadTransactions = async (reset = false) => {
    try {
      if (!user || !user.usuario_id) return;

      if (reset) {
        setOffset(0);
        setHasMore(true);
        const transactionsData = await api.get(`/transactions/user/${user.usuario_id}`)
        setAllTransactions(transactionsData);
        setFilteredTransactions(transactionsData);
        setOffset(10);
      } else {
        const transactionsData = await api.get(`/transactions/user/${user.usuario_id}`)
        if (transactionsData.length === 0) {
          setHasMore(false);
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
      if (!user || !user.usuario_id) return;

      const transactionsData = await api.get(`/transactions/user/${user.usuario_id}`)
      setAllTransactions(transactionsData);
      let filtered = transactionsData.filter(transaction =>
        transaction.description.toLowerCase().includes(searchQuery.toLowerCase())
      );
      if (filtered.length < 1){
        filtered = transactionsData.filter(transaction =>
        transaction.amount.toString().includes(searchQuery)
      );
        console.log(searchQuery)
        setFilteredTransactions(filtered)
        setIsFiltered(true);
        return
      }
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

  const resetContext = () => {
    setAllTransactions([]);
    setFilteredTransactions([]);
    setSearchQuery('');
    setIsFiltered(false);
    setOffset(0);
    setLoadingMore(false);
    setHasMore(true);
    userIdRef.current = null;
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
      resetContext,
    }}>
      {children}
    </FilterContext.Provider>
  );
};
