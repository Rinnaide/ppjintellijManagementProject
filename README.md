# Personal Finance Manager

Um aplicativo móvel de gerenciamento financeiro pessoal desenvolvido com React Native e Expo. Permite aos usuários controlar suas finanças pessoais, gerenciar transações de receitas e despesas, organizar categorias e acompanhar estatísticas financeiras.

## Funcionalidades

### 📊 Dashboard Principal

- Visualização de saldo total
- Totais de receitas e despesas
- Lista das últimas transações
- Estatísticas rápidas

### 💰 Gerenciamento de Transações

- Adicionar novas transações (receitas/despesas) com data e hora
- Visualizar lista completa de transações
- Detalhes completos de cada transação
- Editar transações existentes
- Excluir transações
- Filtragem por tipo (receita/despesa)
- Pesquisa por descrição

### 📂 Sistema de Categorias

- Criar categorias personalizadas com cores
- Organizar por tipo (receita/despesa)
- Editar categorias existentes
- Excluir categorias
- Seleção de categorias nas transações

### 👤 Perfil do Usuário

- Informações pessoais
- Estatísticas detalhadas:
  - Número total de transações
  - Total de receitas
  - Total de despesas
  - Número de categorias
- Editar perfil (nome, email, telefone, senha)
- Logout seguro

### 🔍 Sistema de Filtros e Pesquisa

- Pesquisa em tempo real por descrição de transações
- Filtros avançados por período, categoria e tipo
- Contexto de filtros compartilhado entre telas

### 🔐 Autenticação

- Cadastro de novos usuários
- Login seguro
- Armazenamento local de dados

## Tecnologias Utilizadas

- **React Native**: Framework para desenvolvimento mobile
- **Expo**: Plataforma para desenvolvimento e build
- **React Navigation**: Navegação entre telas com abas e stack
- **AsyncStorage**: Armazenamento local de dados
- **Ionicons**: Biblioteca de ícones
- **Date-fns**: Manipulação e formatação de datas
- **@react-native-community/datetimepicker**: Seleção de data e hora
- **React Native Paper**: Componentes de UI adicionais
- **Axios**: Cliente HTTP (para futuras integrações)

## Pré-requisitos

- Node.js (versão 18 ou superior)
- npm ou yarn
- Expo CLI
- Dispositivo físico ou emulador Android/iOS

## Como Executar

1. **Clone o repositório:**

   ```bash
   git clone <url-do-repositorio>
   cd ppjintellijManagementProject
   ```
2. **Instale as dependências:**

   ```bash
   npm install
   ```
3. **Inicie o servidor de desenvolvimento:**

   ```bash
   npm start
   ```
4. **Execute no dispositivo:**

   - **Android**: Pressione `a` no terminal ou use o app Expo Go
   - **iOS**: Pressione `i` no terminal ou use o app Expo Go
   - **Web**: Pressione `w` no terminal

## Estrutura do Projeto

```
src/
├── components/          # Componentes reutilizáveis
│   ├── CustomButton.js
│   ├── CustomInput.js
│   └── TransactionItem.js
├── contexts/            # Contextos React para estado global
│   └── FilterContext.js
├── navigation/          # Configuração de navegação
│   └── AppNavigator.js
├── screens/             # Telas do aplicativo
│   ├── LoginScreen.js
│   ├── RegisterScreen.js
│   ├── HomeScreen.js
│   ├── TransactionsListScreen.js
│   ├── AddTransactionScreen.js
│   ├── TransactionDetailScreen.js
│   ├── EditTransactionScreen.js
│   ├── CategoriesListScreen.js
│   ├── AddCategoryScreen.js
│   ├── EditCategoryScreen.js
│   ├── ProfileScreen.js
│   ├── EditProfileScreen.js
│   └── ListFilterScreen.js
├── services/            # Serviços e APIs
│   ├── api.js
│   ├── authService.js
│   ├── userService.js
│   ├── transactionService.js
│   └── categoryService.js
└── utils/               # Utilitários
    ├── constants.js
    ├── helpers.js
    └── theme.js
```

## Funcionalidades Técnicas

- **Armazenamento Local**: Todos os dados são armazenados localmente usando AsyncStorage
- **Navegação**: Sistema de navegação com abas (Home, Transações, Categorias, Perfil) e navegação stack para modais
- **Validação**: Validação de formulários em tempo real com suporte a formato brasileiro
- **Formatação BRL**: Suporte completo para entrada e exibição de valores em reais (R$), incluindo separadores de milhares (.) e decimal (,)
- **Responsividade**: Interface adaptável para diferentes tamanhos de tela
- **Tema**: Sistema de cores consistente
- **Ícones**: Biblioteca de ícones vetoriais
- **Context API**: Gerenciamento de estado global para filtros
- **Date/Time Pickers**: Seleção de data e hora nativa

### Scripts Disponíveis

- `npm start`: Inicia o servidor de desenvolvimento
- `npm run android`: Executa no Android
- `npm run ios`: Executa no iOS
- `npm run web`: Executa na web

### Convenções de Código

- Componentes funcionais com hooks
- Estilos organizados por componente
- Nomes de arquivos em PascalCase
- Constantes em maiúsculo
- Funções assíncronas com try/catch
- Suporte a formato brasileiro para moedas e datas
