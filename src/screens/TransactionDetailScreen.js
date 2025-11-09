import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { useNavigation, useRoute } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { COLORS, SPACING, FONT_SIZES } from '../utils/constants';
import { formatCurrency, formatDate, formatDateTimeFull } from '../utils/helpers';
import CustomButton from '../components/CustomButton';
import transactionService from '../services/transactionService';

const TransactionDetailScreen = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const { transaction: initialTransaction } = route.params;
  const [transaction, setTransaction] = React.useState(initialTransaction);

  const handleDelete = async () => {
    Alert.alert(
      'Excluir Transação',
      'Tem certeza que deseja excluir esta transação?',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Excluir',
          style: 'destructive',
          onPress: async () => {
            try {
              await transactionService.deleteTransaction(transaction.id);
              Alert.alert('Sucesso', 'Transação excluída com sucesso!');
              navigation.goBack();
            } catch (error) {
              Alert.alert('Erro', 'Erro ao excluir transação');
            }
          },
        },
      ]
    );
  };

  const handleEdit = () => {
    navigation.navigate('EditTransaction', { transaction, onUpdate: setTransaction });
  };

  if (!transaction) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.errorContainer}>
          <Text style={styles.errorText}>Transação não encontrada</Text>
          <CustomButton
            title="Voltar"
            onPress={() => navigation.goBack()}
            style={styles.backButton}
          />
        </View>
      </SafeAreaView>
    );
  }

  const isIncome = transaction.type === 'income';

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar style="dark" />

      <ScrollView style={styles.scrollView} showsVerticalScrollIndicator={false}>
        <View style={styles.content}>
          {/* Transaction Icon and Amount */}
          <View style={styles.amountSection}>
            <View style={[styles.iconContainer, { backgroundColor: isIncome ? COLORS.success : COLORS.danger }]}>
              <Ionicons
                name={isIncome ? 'arrow-up' : 'arrow-down'}
                size={32}
                color={COLORS.white}
              />
            </View>
            <Text style={[styles.amount, { color: isIncome ? COLORS.success : COLORS.danger }]}>
              {isIncome ? '+' : '-'}{formatCurrency(transaction.amount)}
            </Text>
            <Text style={styles.transactionType}>
              {isIncome ? 'Receita' : 'Despesa'}
            </Text>
          </View>

          {/* Transaction Details */}
          <View style={styles.detailsSection}>
            <View style={styles.detailItem}>
              <Ionicons name="document-text-outline" size={20} color={COLORS.gray} />
              <View style={styles.detailContent}>
                <Text style={styles.detailLabel}>Descrição</Text>
                <Text style={styles.detailValue}>{transaction.description}</Text>
              </View>
            </View>

            <View style={styles.detailItem}>
              <Ionicons name="folder-outline" size={20} color={COLORS.gray} />
              <View style={styles.detailContent}>
                <Text style={styles.detailLabel}>Categoria</Text>
                <Text style={styles.detailValue}>{transaction.categoryName || 'Categoria não definida'}</Text>
              </View>
            </View>

            <View style={styles.detailItem}>
              <Ionicons name="calendar-outline" size={20} color={COLORS.gray} />
              <View style={styles.detailContent}>
                <Text style={styles.detailLabel}>Data</Text>
                <Text style={styles.detailValue}>{formatDate(transaction.transactionDate)}</Text>
              </View>
            </View>

            <View style={styles.detailItem}>
              <Ionicons name="time-outline" size={20} color={COLORS.gray} />
              <View style={styles.detailContent}>
                <Text style={styles.detailLabel}>Data de Criação</Text>
                <Text style={styles.detailValue}>
                  {transaction.createdAt ? formatDateTimeFull(transaction.createdAt) : 'N/A'}
                </Text>
              </View>
            </View>
          </View>

          {/* Action Buttons */}
          <View style={styles.actionsSection}>
            <CustomButton
              title="Editar Transação"
              onPress={handleEdit}
              variant="outline"
              style={styles.editButton}
            />
            <CustomButton
              title="Excluir Transação"
              onPress={handleDelete}
              variant="danger"
              style={styles.deleteButton}
            />
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.white,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: SPACING.lg,
    paddingVertical: SPACING.md,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  backButton: {
    padding: SPACING.xs,
  },
  headerTitle: {
    fontSize: FONT_SIZES.large,
    fontWeight: 'bold',
    color: COLORS.dark,
  },
  headerRight: {
    width: 32,
  },
  scrollView: {
    flex: 1,
  },
  content: {
    padding: SPACING.lg,
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: SPACING.lg,
  },
  errorText: {
    fontSize: FONT_SIZES.large,
    color: COLORS.gray,
    marginBottom: SPACING.lg,
  },
  backButton: {
    marginTop: 0,
  },
  amountSection: {
    alignItems: 'center',
    paddingVertical: SPACING.xl,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
    marginBottom: SPACING.lg,
  },
  iconContainer: {
    width: 64,
    height: 64,
    borderRadius: 32,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: SPACING.md,
  },
  amount: {
    fontSize: FONT_SIZES.xxxlarge,
    fontWeight: 'bold',
    marginBottom: SPACING.xs,
  },
  transactionType: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.gray,
  },
  detailsSection: {
    marginBottom: SPACING.xl,
  },
  detailItem: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    paddingVertical: SPACING.md,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.lightGray,
  },
  detailContent: {
    flex: 1,
    marginLeft: SPACING.sm,
  },
  detailLabel: {
    fontSize: FONT_SIZES.small,
    color: COLORS.gray,
    marginBottom: SPACING.xs,
  },
  detailValue: {
    fontSize: FONT_SIZES.medium,
    color: COLORS.dark,
    fontWeight: '500',
  },
  actionsSection: {
    gap: SPACING.md,
  },
  editButton: {
    marginTop: 0,
  },
  deleteButton: {
    marginTop: 0,
  },
});

export default TransactionDetailScreen;
