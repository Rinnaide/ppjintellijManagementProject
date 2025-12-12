import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

// Base URL da API (ajuste conforme necessário)
const BASE_URL = 'http://academico3.rj.senac.br/projectmanagement/api'; // URL do backend implantado

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
    console.log(response)
    return response.data;
  },
  async (error) => {
    console.log(error)
    if (error.response?.status === 401) {
      // Token expirado, redirecionar para login
      await AsyncStorage.removeItem('token');
      // Aqui você pode adicionar lógica para redirecionar para a tela de login
    }
    return Promise.reject(error);
  }
);

export default api;
