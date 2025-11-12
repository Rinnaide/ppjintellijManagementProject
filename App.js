import React from 'react';
import { Provider as PaperProvider } from 'react-native-paper';
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import AppNavigator from './src/navigation/AppNavigator';
import { theme } from './src/utils/theme';
import { TransactionProvider } from './src/contexts/TransactionContext';

export default function App() {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <PaperProvider theme={theme}>
        <TransactionProvider>
          <AppNavigator />
        </TransactionProvider>
      </PaperProvider>
    </GestureHandlerRootView>
  );
}
