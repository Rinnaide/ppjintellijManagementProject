import api, { setApiToken } from './api';
import userService from './userService';

const authService = {
  login: async (email, password) => {
    try {
      const response = await api.post('/auth/login', {"email": email ,"password":  password});
      const { usuario_id, usuario_nome, usuario_token } = response;
      setApiToken(usuario_token);

      // Fetch full user details
      const fullUserData = await userService.getUserById(usuario_id);

      return {
        user: {
          id: usuario_id,
          name: usuario_nome,
          firstName: fullUserData.firstName,
          lastName: fullUserData.lastName,
          usuarioEmail: fullUserData.email
        },
        token: usuario_token
      };
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
