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

  if (currency === 'BRL') {
    // Formatação brasileira: separador de milhares "." e decimal ","
    return `${symbol} ${value.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    })}`;
  }

  // Para outras moedas, usar formatação padrão
  return `${symbol} ${value.toFixed(2)}`;
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

// Função para formatar data e hora completa
export const formatDateTimeFull = (date, formatString = 'dd/MM/yyyy HH:mm:ss') => {
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

// Função para formatar entrada de moeda em tempo real (formato brasileiro)
export const formatCurrencyInput = (value) => {
  if (!value) return '';

  // Remove tudo exceto números, pontos e vírgula
  let cleanValue = value.replace(/[^0-9.,]/g, '');

  // Permite apenas uma vírgula
  const commaIndex = cleanValue.indexOf(',');
  if (commaIndex !== -1) {
    cleanValue = cleanValue.substring(0, commaIndex + 1) + cleanValue.substring(commaIndex + 1).replace(/[,]/g, '');
  }

  // Divide em parte inteira e decimal
  const parts = cleanValue.split(',');
  let integerPart = parts[0];
  let decimalPart = parts[1] || '';

  // Remove pontos da parte inteira (separadores de milhares)
  integerPart = integerPart.replace(/\./g, '');

  // Limita decimal a 2 dígitos
  if (decimalPart.length > 2) {
    decimalPart = decimalPart.substring(0, 2);
  }

  // Adiciona separadores de milhares à parte inteira
  integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

  // Combina as partes
  return decimalPart ? `${integerPart},${decimalPart}` : integerPart;
};

// Função para parsear valor em formato brasileiro (BRL)
export const parseBRLAmount = (input) => {
  if (!input || typeof input !== 'string') return NaN;
  input = input.trim();
  if (input === '') return NaN;

  if (input.includes(',')) {
    // Contém vírgula: separador decimal
    const parts = input.split(',');
    let whole = parts[0].replace(/\./g, ''); // Remove separadores de milhares
    let decimal = parts[1] || '00';
    decimal = decimal.padEnd(2, '0').substring(0, 2);
    const num = parseFloat(`${whole}.${decimal}`);
    return isNaN(num) ? NaN : num;
  } else if (input.includes('.')) {
    // Apenas pontos: separadores de milhares
    const parts = input.split('.');
    if (parts.length === 2) {
      const thousands = parseInt(parts[0], 10);
      const rest = parts[1];
      const restNum = parseInt(rest, 10);
      if (isNaN(thousands) || isNaN(restNum)) return NaN;
      // For "1.5", it should be 1.5, not 1005
      // So, if rest has 1 digit, it's decimal, else thousands
      if (rest.length === 1) {
        return thousands + restNum / 10;
      } else {
        return thousands * 1000 + restNum;
      }
    } else {
      // Múltiplos pontos, não suportado, tratar como número simples
      const num = parseFloat(input.replace(/\./g, ''));
      return isNaN(num) ? NaN : num;
    }
  } else {
    // Apenas números: valor inteiro
    const num = parseFloat(input);
    return isNaN(num) ? NaN : num;
  }
};

// Função para formatar número para formato brasileiro (BRL) sem símbolo
export const formatNumberToBRL = (value) => {
  if (value === null || value === undefined || isNaN(value)) return '';
  return value.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};

// Função para validar valor (deve ser um número positivo, aceitando formato brasileiro)
export const isValidAmount = (amount) => {
  if (!amount || typeof amount !== 'string') return false;
  const num = parseBRLAmount(amount);
  return !isNaN(num) && num > 0;
};

// Função para sanitizar entrada numérica (remove caracteres não numéricos, exceto vírgula/ponto)
export const sanitizeNumericInput = (value) => {
  if (!value) return '';

  // Remove espaços no início e fim
  let cleanValue = value.trim();

  // Permite apenas números, vírgula e ponto
  cleanValue = cleanValue.replace(/[^0-9.,]/g, '');

  // Permite apenas uma vírgula ou ponto como separador decimal
  const commaIndex = cleanValue.indexOf(',');
  const dotIndex = cleanValue.indexOf('.');

  if (commaIndex !== -1 && dotIndex !== -1) {
    // Se tem ambos, mantém apenas a primeira ocorrência
    if (commaIndex < dotIndex) {
      cleanValue = cleanValue.substring(0, dotIndex) + cleanValue.substring(dotIndex + 1);
    } else {
      cleanValue = cleanValue.substring(0, commaIndex) + cleanValue.substring(commaIndex + 1);
    }
  }

  // Limita a parte decimal a 2 dígitos
  const separatorIndex = cleanValue.indexOf(',') !== -1 ? cleanValue.indexOf(',') : cleanValue.indexOf('.');
  if (separatorIndex !== -1) {
    const integerPart = cleanValue.substring(0, separatorIndex);
    let decimalPart = cleanValue.substring(separatorIndex + 1);
    if (decimalPart.length > 2) {
      decimalPart = decimalPart.substring(0, 2);
    }
    cleanValue = integerPart + ',' + decimalPart;
  }

  return cleanValue;
};
