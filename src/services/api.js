import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

// Base URL da API (ajuste conforme necessário)
const BASE_URL = 'http://10.0.2.2:8080/api'; // Substitua pela URL do seu backend Spring Boot

// Criar instância do Axios
const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
});

// Interceptor para adicionar token JWT nas requisições
api.interceptors.request.use(
  async (config) => {
    const token = await AsyncStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor para lidar com respostas e erros
api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response?.status === 401) {
      // Token expirado, redirecionar para login
      await AsyncStorage.removeItem('token');
      // Aqui você pode adicionar lógica para redirecionar para a tela de login
    }
    return Promise.reject(error);
  }
);

export default api;
