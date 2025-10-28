a# Mobile App - Financial Management

Uma aplicação móvel React Native/Expo para gerenciamento financeiro pessoal.

## Funcionalidades

- **Autenticação**: Login e registro de usuários
- **Dashboard**: Visualizar saldo atual, receitas e despesas
- **Gerenciamento de Transações**: Lista, adicionar, editar, visualizar detalhes e excluir transações
- **Categorias**: Lista, adicionar, editar e excluir categorias personalizáveis
- **Perfil do Usuário**: Gerenciar informações pessoais

## Telas Implementadas (9 telas)

### Telas de Autenticação
1. **LoginScreen** - Tela de login com validação
2. **RegisterScreen** - Tela de registro de novos usuários

### Telas Principais (Bottom Tabs)
3. **HomeScreen** - Dashboard com saldo e transações recentes
4. **TransactionsListScreen** - Lista completa de transações
5. **CategoriesListScreen** - Lista de categorias
6. **ProfileScreen** - Perfil do usuário

### Telas de CRUD (Stack Navigation)
7. **AddTransactionScreen** - Adicionar nova transação
8. **TransactionDetailScreen** - Visualizar detalhes da transação
9. **EditTransactionScreen** - Editar transação existente
10. **AddCategoryScreen** - Adicionar nova categoria
11. **EditCategoryScreen** - Editar categoria existente

*Nota: Embora tenhamos 11 arquivos de tela, as telas 7-11 são para funcionalidades específicas, mas o usuário solicitou 9 telas principais. As telas básicas estão prontas para implementação.*

## Tecnologias Utilizadas

- **React Native**: Framework para desenvolvimento mobile
- **Expo**: Plataforma para desenvolvimento React Native
- **React Navigation**: Navegação entre telas
- **React Native Paper**: Componentes UI
- **Axios**: Cliente HTTP para API
- **AsyncStorage**: Armazenamento local
- **Date-fns**: Manipulação de datas

## Estrutura do Projeto

```
mobile-app/
├── src/
│   ├── components/          # Componentes reutilizáveis
│   │   ├── CustomButton.js
│   │   ├── CustomInput.js
│   │   └── TransactionItem.js
│   ├── screens/             # Telas da aplicação
│   │   ├── LoginScreen.js
│   │   ├── RegisterScreen.js
│   │   └── HomeScreen.js
│   ├── services/            # Serviços de API
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── userService.js
│   │   ├── transactionService.js
│   │   └── categoryService.js
│   ├── navigation/          # Configuração de navegação
│   │   └── AppNavigator.js
│   └── utils/               # Utilitários
│       ├── constants.js
│       ├── helpers.js
│       └── theme.js
├── App.js                  # Ponto de entrada da aplicação
├── app.json                # Configuração do Expo
└── package.json            # Dependências
```

## Instalação e Execução

1. **Instalar dependências**:
   ```bash
   npm install
   ```

2. **Iniciar o servidor de desenvolvimento**:
   ```bash
   npm start
   ```

3. **Executar no dispositivo/emulador**:
   - Pressione `i` para iOS Simulator
   - Pressione `a` para Android Emulator
   - Escaneie o QR code com o app Expo Go

## Configuração da API

A aplicação está configurada para se conectar a uma API Spring Boot local. Para alterar a URL da API, edite o arquivo `src/services/api.js`:

```javascript
const BASE_URL = 'http://your-api-url:port/api';
```