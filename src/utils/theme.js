import { DefaultTheme } from 'react-native-paper';
import { COLORS } from './constants';

export const theme = {
  ...DefaultTheme,
  colors: {
    ...DefaultTheme.colors,
    primary: COLORS.primary,
    accent: COLORS.secondary,
    background: COLORS.white,
    surface: COLORS.white,
    text: COLORS.dark,
    placeholder: COLORS.gray,
    error: COLORS.danger,
  },
};
