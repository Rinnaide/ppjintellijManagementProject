import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { COLORS, FONT_SIZES, SPACING } from '../utils/constants';
import { formatCurrency, formatDate } from '../utils/helpers';

const TransactionItem = ({ transaction, onPress }) => {
  const isIncome = transaction.type === 'income';

  return (
    <TouchableOpacity style={styles.container} onPress={onPress}>
      <View style={styles.iconContainer}>
        <Ionicons
          name={isIncome ? 'arrow-up-circle' : 'arrow-down-circle'}
          size={24}
          color={isIncome ? COLORS.success : COLORS.danger}
        />
      </View>
      <View style={styles.content}>
        <Text style={styles.description} numberOfLines={1}>
          {transaction.description}
        </Text>
        <Text style={styles.category}>
          {transaction.categoryName || 'Categoria não definida'}
        </Text>
        <Text style={styles.date}>
          {formatDate(transaction.transactionDate)}
        </Text>
      </View>
      <View style={styles.amountContainer}>
        <Text style={[
          styles.amount,
          { color: isIncome ? COLORS.success : COLORS.danger }
        ]}>
          {isIncome ? '+' : '-'}{formatCurrency(transaction.amount)}
        </Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: COLORS.white,
    padding: SPACING.md,
    marginVertical: SPACING.xs,
    marginHorizontal: SPACING.md,
    borderRadius: 8,
    shadowColor: COLORS.black,
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
  iconContainer: {
    marginRight: SPACING.sm,
  },
  content: {
    flex: 1,
  },
  description: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
    color: COLORS.dark,
    marginBottom: 2,
  },
  category: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginBottom: 2,
  },
  date: {
    fontSize: FONT_SIZES.small,
    color: COLORS.lightGray,
  },
  amountContainer: {
    alignItems: 'flex-end',
  },
  amount: {
    fontSize: FONT_SIZES.medium,
    fontWeight: '600',
  },
});

export default TransactionItem;
