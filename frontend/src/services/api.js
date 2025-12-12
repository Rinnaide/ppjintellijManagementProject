import axios from 'axios';

// Base URL da API (ajuste conforme necessário)
const BASE_URL = 'http://academico3.rj.senac.br/projectmanagement/api'; // URL do backend implantado

// Criar instância do Axios
const api = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
});

// Global token variable
let globalToken = null;

// Function to set the token
export const setApiToken = (token) => {
  globalToken = token;
};

// Interceptor para adicionar token JWT nas requisições
api.interceptors.request.use(
  async (config) => {
    if (globalToken) {
      config.headers.Authorization = `Bearer ${globalToken}`;
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
  (error) => {
    console.log(error)
    if (error.response?.status === 401) {
      // Token expirado, limpar token global
      globalToken = null;
      // Aqui você pode adicionar lógica para redirecionar para a tela de login
    }
    return Promise.reject(error);
  }
);

export default api;
