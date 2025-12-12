import api, { setApiToken } from './api';

const authService = {
  login: async (email, password) => {
    try {
      const response = await api.post('/auth/login', {"email": email ,"password":  password});
      const { usuario_id, usuario_nome, usuario_token } = response;
      setApiToken(usuario_token);
      return { user: { id: usuario_id, name: usuario_nome }, token: usuario_token };
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
