import api, { setApiToken } from './api';

const authService = {
  login: async (email, password) => {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { usuario_id, usuario_nome, usuario_token } = response;
      const user = {
        id: usuario_id,
        name: usuario_nome,
      };
      const token = usuario_token;
      setApiToken(token);
      return { user, token };
    } catch (error) {
      throw error;
    }
  },

  logout: async () => {
    setApiToken(null);
    return Promise.resolve();
  },
};

export default authService;
