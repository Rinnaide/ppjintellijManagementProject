import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

// Função para formatar moeda
export const formatCurrency = (value, currency = 'BRL') => {
  const currencies = {
    BRL: 'R$',
    USD: '$',
    EUR: '€',
  };

  const symbol = currencies[currency] || 'R$';

  return `${symbol} ${value.toFixed(2).replace('.', ',')}`;
};

// Função para formatar data
export const formatDate = (date, formatString = 'dd/MM/yyyy') => {
  if (!date) return '';
  return format(new Date(date), formatString, { locale: ptBR });
};

// Função para formatar data e hora
export const formatDateTime = (date, formatString = 'dd/MM/yyyy HH:mm') => {
  if (!date) return '';
  return format(new Date(date), formatString, { locale: ptBR });
};

// Função para validar email
export const isValidEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

// Função para validar senha (mínimo 6 caracteres)
export const isValidPassword = (password) => {
  return password && password.length >= 6;
};

// Função para capitalizar primeira letra
export const capitalize = (str) => {
  if (!str) return '';
  return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
};

// Função para truncar texto
export const truncateText = (text, maxLength = 50) => {
  if (!text || text.length <= maxLength) return text;
  return text.substring(0, maxLength) + '...';
};

// Função para calcular diferença de dias
export const daysDifference = (date1, date2) => {
  const oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
  return Math.round(Math.abs((new Date(date1) - new Date(date2)) / oneDay));
};
