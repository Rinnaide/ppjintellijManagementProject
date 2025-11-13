# Personal Finance Manager

Um aplicativo móvel de gerenciamento financeiro pessoal desenvolvido com React Native e Expo. Permite aos usuários controlar suas finanças pessoais, gerenciar transações de receitas e despesas, organizar categorias e acompanhar estatísticas financeiras.

## Funcionalidades

### 📊 Dashboard Principal

- Visualização de saldo total atual
- Totais de receitas e despesas separadas
- Lista das últimas transações recentes
- Estatísticas rápidas e resumo financeiro
- Navegação intuitiva para outras seções

### 💰 Gerenciamento de Transações

- **Adicionar transações**: Criação de novas transações (receitas/despesas) com data, hora e categoria
- **Visualizar transações**: Lista completa de transações com paginação infinita
- **Detalhes de transação**: Visualização completa de cada transação individual
- **Editar transações**: Modificação de transações existentes
- **Excluir transações**: Remoção segura de transações com confirmação
- **Filtragem avançada**: Por tipo (receita/despesa), categoria e período
- **Pesquisa em tempo real**: Busca por descrição de transações
- **Ordenação automática**: Transações mais recentes primeiro

### 📂 Sistema de Categorias

- **Criar categorias**: Categorias personalizadas com cores distintas
- **Organização por tipo**: Separação automática entre receitas e despesas
- **Editar categorias**: Modificação de nome, cor e tipo
- **Excluir categorias**: Remoção com verificação de dependências
- **Seleção intuitiva**: Interface visual para escolher categorias nas transações

### 👤 Perfil do Usuário

- **Informações pessoais**: Nome, email, telefone
- **Estatísticas detalhadas**:
  - Número total de transações
  - Valor total de receitas
  - Valor total de despesas
  - Número de categorias criadas
  - Saldo atual calculado
- **Editar perfil**: Atualização de dados pessoais e senha
- **Logout seguro**: Limpeza completa da sessão

### 🔍 Sistema de Filtros e Pesquisa

- **Pesquisa inteligente**: Busca em tempo real por descrição
- **Filtros avançados**: Combinação de período, categoria e tipo
- **Estado compartilhado**: Filtros mantidos entre navegações
- **Interface responsiva**: Filtros adaptáveis à tela

### 🔐 Autenticação e Segurança

- **Cadastro seguro**: Registro de novos usuários com validação
- **Login protegido**: Autenticação com armazenamento local
- **Isolamento de dados**: Cada usuário vê apenas suas próprias informações
- **Migração automática**: Correção de dados duplicados na primeira autenticação
- **Logout completo**: Remoção de dados de sessão

## Tecnologias Utilizadas

- **React Native 0.81.5**: Framework principal para desenvolvimento mobile
- **Expo 54.0.23**: Plataforma para desenvolvimento e build
- **React 19.1.0**: Biblioteca base do React
- **React Navigation**: Sistema completo de navegação (Stack + Bottom Tabs)
- **AsyncStorage**: Armazenamento local persistente e seguro
- **@expo/vector-icons/Ionicons**: Biblioteca de ícones vetoriais
- **date-fns**: Manipulação avançada de datas
- **@react-native-community/datetimepicker**: Seletores nativos de data/hora
- **React Native Paper**: Componentes de UI adicionais
- **Axios**: Cliente HTTP para futuras integrações

## Pré-requisitos

- Node.js (versão 18 ou superior)
- npm ou yarn
- Expo CLI (`npm install -g @expo/cli`)
- Dispositivo físico ou emulador Android/iOS

## Como Executar

1. **Baixe o repositório ou Git Clone:**

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
   - **Android**: Pressione `a` no terminal ou escaneie QR code com Expo Go
   - **iOS**: Pressione `i` no terminal ou escaneie QR code com Expo Go

### Scripts Disponíveis
- `npm i && npm start`: Instala as depêndencias e inicia o servidor de desenvolvimento Expo logo após

## Estrutura do Projeto

```
src/
├── components/          # Componentes reutilizáveis
│   ├── CustomButton.js      # Botão personalizado com variantes
│   ├── CustomInput.js       # Campo de entrada com validação
│   └── TransactionItem.js   # Item de lista de transação
├── contexts/            # Contextos React para estado global
│   ├── FilterContext.js     # Gerenciamento de filtros e busca
│   └── TransactionContext.js # Estado de transações e operações
├── navigation/          # Configuração de navegação
│   └── AppNavigator.js      # Navegação principal (Stack + Tabs)
├── screens/             # Telas do aplicativo
│   ├── LoginScreen.js       # Tela de login
│   ├── RegisterScreen.js    # Tela de cadastro
│   ├── HomeScreen.js        # Dashboard principal
│   ├── TransactionsListScreen.js # Lista de transações
│   ├── AddTransactionScreen.js   # Adicionar transação
│   ├── TransactionDetailScreen.js # Detalhes da transação
│   ├── EditTransactionScreen.js   # Editar transação
│   ├── CategoriesListScreen.js    # Lista de categorias
│   ├── AddCategoryScreen.js       # Adicionar categoria
│   ├── EditCategoryScreen.js      # Editar categoria
│   ├── ProfileScreen.js           # Perfil do usuário
│   ├── EditProfileScreen.js       # Editar perfil
│   └── ListFilterScreen.js        # Tela de filtros
├── services/            # Serviços e APIs
│   ├── api.js               # Configuração base da API
│   ├── authService.js       # Serviço de autenticação
│   ├── userService.js       # Serviço de usuários
│   ├── transactionService.js # Serviço de transações
│   └── categoryService.js   # Serviço de categorias
└── utils/               # Utilitários e constantes
    ├── constants.js         # Cores, espaçamentos, tamanhos
    ├── helpers.js           # Funções auxiliares (formatação, etc.)
    └── theme.js             # Configuração de tema
```

## Funcionalidades Técnicas

- **Armazenamento Local Seguro**: Dados isolados por usuário usando AsyncStorage
- **Navegação Híbrida**: Bottom tabs para navegação principal + Stack para modais
- **Validação Robusta**: Formulários com validação em tempo real
- **Formatação Brasileira**: Suporte completo para moeda (R$), datas e números
- **Responsividade**: Interface adaptável a diferentes tamanhos de tela
- **Tema Consistente**: Sistema de cores padronizado
- **Context API**: Gerenciamento de estado global para transações e filtros
- **UUID Generation**: Identificadores únicos para transações
- **Migração Automática**: Correção de dados duplicados
- **Paginação Infinita**: Carregamento sob demanda de transações
- **Busca em Tempo Real**: Filtragem instantânea de resultados


### Arquitetura e Segurança
- **Isolamento de Dados**: Cada usuário acessa apenas seus próprios dados
- **UUIDs Únicos**: Identificadores únicos para operações seguras
- **Migração Transparente**: Correção automática de dados inconsistentes
- **Validação de Input**: Prevenção de dados malformados
- **Tratamento de Erros**: Logs detalhados e tratamento graceful
- **Contextos Seguros**: Estado global com isolamento por usuário

### Convenções de Código
- Componentes funcionais com React Hooks
- Estilos organizados por componente (StyleSheet)
- Nomes de arquivos em PascalCase
- Constantes em SCREAMING_SNAKE_CASE
- Funções assíncronas com tratamento de erros
- Suporte completo ao formato brasileiro
