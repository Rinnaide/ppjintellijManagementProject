import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  TextInput,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { Ionicons } from '@expo/vector-icons';
import TransactionItem from '../components/TransactionItem';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import { useFilter } from '../contexts/FilterContext';

const ListFilterScreen = () => {
  const navigation = useNavigation();
  const { updateSearchQuery, filteredTransactions } = useFilter();

  const [searchQuery, setSearchQuery] = useState('');

  const handleClearSearch = () => {
    setSearchQuery('');
  };

  const handleApplyFilters = () => {
    updateSearchQuery(searchQuery);
    navigation.goBack();
  };

  const handleTransactionPress = (transaction) => {
    navigation.navigate('TransactionDetail', { transaction });
  };

  const renderTransaction = ({ item }) => (
    <TransactionItem
      transaction={item}
      onPress={() => handleTransactionPress(item)}
    />
  );

  const renderEmpty = () => {
    if (searchQuery.trim()) {
      return (
        <View style={styles.emptyContainer}>
          <Ionicons name="search-outline" size={64} color={COLORS.lightGray} />
          <Text style={styles.emptyText}>Nenhum resultado encontrado</Text>
          <Text style={styles.emptySubtext}>
            Tente ajustar sua busca para "{searchQuery}"
          </Text>
        </View>
      );
    }

    return (
      <View style={styles.emptyContainer}>
        <Ionicons name="receipt-outline" size={64} color={COLORS.lightGray} />
        <Text style={styles.emptyText}>Nenhuma transação encontrada</Text>
        <Text style={styles.emptySubtext}>
          Você ainda não possui transações
        </Text>
      </View>
    );
  };

  const renderHeader = () => (
    <View style={styles.header}>
      <View style={styles.searchContainer}>
        <View style={styles.searchInputContainer}>
          <Ionicons name="search" size={20} color={COLORS.gray} style={styles.searchIcon} />
          <TextInput
            style={styles.searchInput}
            placeholder="Buscar por descrição..."
            placeholderTextColor={COLORS.gray}
            value={searchQuery}
            onChangeText={setSearchQuery}
            autoCapitalize="none"
            autoCorrect={false}
          />
          {searchQuery.length > 0 && (
            <TouchableOpacity onPress={handleClearSearch} style={styles.clearButton}>
              <Ionicons name="close-circle" size={20} color={COLORS.gray} />
            </TouchableOpacity>
          )}
        </View>
      </View>

      {searchQuery.trim() && (
        <View style={styles.resultsInfo}>
          <Text style={styles.resultsText}>
            {filteredTransactions.length} resultado{filteredTransactions.length !== 1 ? 's' : ''} encontrado{filteredTransactions.length !== 1 ? 's' : ''}
          </Text>
        </View>
      )}
    </View>
  );



  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      {/* Header com busca */}
      {renderHeader()}

      {/* Lista de transações filtradas */}
      <View style={styles.listContainer}>
        <FlatList
          data={filteredTransactions}
          renderItem={renderTransaction}
          keyExtractor={(item) => item.id}
          ListEmptyComponent={renderEmpty}
          showsVerticalScrollIndicator={false}
          contentContainerStyle={filteredTransactions.length === 0 ? styles.emptyList : null}
        />
      </View>

      {/* Botão Aplicar Filtros */}
      <View style={styles.footer}>
        <TouchableOpacity
          style={[styles.applyButton, !searchQuery.trim() && styles.applyButtonDisabled]}
          onPress={handleApplyFilters}
          disabled={!searchQuery.trim()}
        >
          <Text style={[styles.applyButtonText, !searchQuery.trim() && styles.applyButtonTextDisabled]}>
            Aplicar Filtros
          </Text>
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

  header: {
    backgroundColor: COLORS.white,
    padding: SPACING.lg,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  searchContainer: {
    marginBottom: SPACING.md,
  },
  searchInputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: COLORS.light,
    borderRadius: 8,
    paddingHorizontal: SPACING.md,
    paddingVertical: SPACING.sm,
  },
  searchIcon: {
    marginRight: SPACING.sm,
  },
  searchInput: {
    flex: 1,
    fontSize: FONT_SIZES.medium,
    color: COLORS.dark,
    paddingVertical: SPACING.xs,
  },
  clearButton: {
    padding: SPACING.xs,
  },
  resultsInfo: {
    alignItems: 'center',
  },
  resultsText: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    fontStyle: 'italic',
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
    textAlign: 'center',
  },
  emptySubtext: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.lightGray,
    textAlign: 'center',
  },
  footer: {
    backgroundColor: COLORS.white,
    padding: SPACING.lg,
    borderTopWidth: 1,
    borderTopColor: COLORS.lightGray,
  },
  applyButton: {
    backgroundColor: COLORS.primary,
    paddingVertical: SPACING.md,
    borderRadius: 8,
    alignItems: 'center',
  },
  applyButtonDisabled: {
    backgroundColor: COLORS.lightGray,
  },
  applyButtonText: {
    color: COLORS.white,
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
  },
  applyButtonTextDisabled: {
    color: COLORS.gray,
  },
});

export default ListFilterScreen;
