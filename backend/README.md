# ProjectManagement 🌟

**ProjectManagement** é uma API backend completa desenvolvida com Spring Boot para gerenciamento financeiro pessoal. Ela permite aos usuários gerenciar suas finanças de forma eficiente, incluindo controle de usuários, transações (receitas e despesas), categorias, saldos, notificações, logs de auditoria, tokens de autenticação, sincronização de dispositivos e gerenciamento de roles. A API é totalmente alinhada com o esquema de banco de dados fornecido e oferece uma arquitetura robusta com autenticação JWT, documentação Swagger interativa e tratamento abrangente de erros. 💰

## O que é o ProjectManagement? 🤔

O ProjectManagement é uma aplicação backend RESTful construída em Java com Spring Boot, projetada para servir como a camada de backend de um sistema de gerenciamento financeiro pessoal. Ela fornece endpoints seguros para operações CRUD em todas as entidades do banco de dados, incluindo usuários, transações, categorias, saldos, notificações, logs de auditoria, tokens de reset de senha, tokens de refresh, status de sincronização e roles de usuário. A API garante integridade de dados, segurança e escalabilidade, utilizando melhores práticas de desenvolvimento.

## Funcionalidades 📋

### Gerenciamento Completo
- **Gerenciamento de Usuários:** Registro, login, atualização de perfil, verificação de email e controle de status ativo
- **Controle de Transações:** Criação, leitura, atualização e exclusão de transações (receitas/despesas) com categorização automática
- **Organização por Categorias:** Gerenciamento de categorias personalizáveis com suporte a tipos (INCOME, EXPENSE, BOTH), ícones e cores
- **Acompanhamento de Saldos:** Monitoramento automático de saldos atuais, total de receitas e despesas por usuário

### Funcionalidades Avançadas
- **Sistema de Notificações:** Envio de lembretes, alertas e informações personalizadas com agendamento e rastreamento de leitura
- **Auditoria e Logs:** Rastreamento completo de todas as ações (CREATE, UPDATE, DELETE) com metadados (IP, user-agent, timestamps)
- **Autenticação JWT:** Sistema robusto com tokens de acesso e refresh, suporte a roles e permissões
- **Recuperação de Senha:** Geração e validação segura de tokens para reset de senha
- **Sincronização Multi-dispositivo:** Controle de status de sincronização entre dispositivos com informações de versão
- **Gerenciamento de Roles:** Sistema de roles e associações user-role para controle de acesso baseado em permissões
- **Documentação Interativa:** Integração completa com Swagger UI para testes e documentação

## Como Funciona? ⚙️

### Arquitetura
A aplicação segue uma arquitetura em camadas típica de Spring Boot:

- **Controllers:** Recebem requests HTTP, validam dados e delegam para services
- **Services:** Contêm a lógica de negócio, orquestram operações e aplicam regras
- **Repositories:** Interfaces JPA para acesso a dados com queries customizadas
- **Entities:** Modelos JPA mapeados diretamente para tabelas do banco de dados
- **DTOs:** Objetos de transferência para requests/responses
- **Security:** Filtros JWT para autenticação e autorização em endpoints protegidos

### Fluxo de Funcionamento
1. **Autenticação:** Usuário faz login e recebe tokens JWT (access + refresh)
2. **Operações CRUD:** Usuário acessa endpoints protegidos com token e realiza operações
3. **Auditoria:** Todas as operações são logadas automaticamente na tabela audit_log
4. **Cálculo de Saldos:** Transações atualizam automaticamente o user_balance
5. **Notificações:** Sistema envia notificações baseadas em eventos (ex: saldo baixo)
6. **Sincronização:** Apps móveis sincronizam dados usando sync_status

### Segurança
- Autenticação JWT com tokens de curta (access) e longa duração (refresh)
- Autorização baseada em roles para controle granular de permissões
- Validação de entrada em todos os endpoints
- Tratamento de erros consistente com códigos HTTP apropriados

## Tecnologias Utilizadas ⚙️

- **Spring Boot** (versão 3.5.6) - Framework principal
- **Java** (versão 21) - Linguagem de programação
- **Spring Data JPA** - ORM para acesso a dados
- **Spring Security** - Segurança e autenticação
- **JWT** (java-jwt 4.4.0) - Tokens de autenticação
- **MySQL** - Banco de dados principal (conector MySQL)
- **H2 Database** - Banco para testes
- **SpringDoc OpenAPI** (versão 2.7.0) - Documentação Swagger
- **Maven** - Gerenciamento de dependências e build

## Como Começar 🚀

Para executar o ProjectManagement localmente, siga estas etapas:

### 1. Clone o repositório
```bash
git clone https://github.com/Rinnaide/ppjintellijManagementProject.git
cd ppjintellijManagementProject-master
```

### 2. Instale as dependências
```bash
./mvnw.cmd clean install
```

### 3. Configure o banco de dados
- Certifique-se de que o MySQL esteja rodando
- As configurações estão em `src/main/resources/application.properties`
- Porta padrão: 8089

### 4. Inicie o servidor
```bash
./mvnw.cmd spring-boot:run
```

### 5. Acesse a aplicação
- **API:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/v3/api-docs

## Estrutura do Projeto 📁

```
src/main/java/com/projectmanagement/
├── config/              # Configurações (Swagger, Security)
├── controller/          # Endpoints REST
│   ├── AuthController
│   ├── UserController
│   ├── TransactionController
│   ├── CategoryController
│   ├── UserBalanceController
│   ├── NotificationController
│   ├── AuditLogController
│   ├── PasswordResetController
│   ├── RefreshTokenController
│   ├── SyncStatusController
│   └── RoleController
├── dto/                 # Data Transfer Objects
│   ├── request/        # DTOs para requests
│   └── response/       # DTOs para responses
├── entity/             # Modelos JPA
│   ├── User
│   ├── Transaction
│   ├── Category
│   ├── UserBalance
│   ├── AuditLog
│   ├── Notification
│   ├── PasswordResetToken
│   ├── RefreshToken
│   ├── SyncStatus
│   ├── Role
│   └── UserRole
├── repository/         # Interfaces JPA
├── service/           # Lógica de negócio
├── security/          # Filtros JWT e configurações
└── exception/         # Tratamento global de erros
```

## Endpoints Principais 🔗

### Autenticação
- `POST /api/auth/register` - Registro de novo usuário
- `POST /api/auth/login` - Login e geração de tokens JWT
- `POST /api/auth/refresh` - Renovar token de acesso
- `POST /api/auth/logout` - Logout e revogação de tokens

### Usuários
- `GET /api/users/{id}` - Buscar usuário por ID
- `PUT /api/users/{id}` - Atualizar perfil do usuário
- `DELETE /api/users/{id}` - Desativar usuário
- `GET /api/users` - Listar todos os usuários (admin)

### Transações
- `GET /api/transactions` - Listar transações do usuário
- `POST /api/transactions` - Criar nova transação
- `PUT /api/transactions/{id}` - Atualizar transação
- `DELETE /api/transactions/{id}` - Deletar transação (soft delete)
- `GET /api/transactions/{id}` - Buscar transação por ID

### Categorias
- `GET /api/categories` - Listar categorias do usuário
- `POST /api/categories` - Criar nova categoria
- `PUT /api/categories/{id}` - Atualizar categoria
- `DELETE /api/categories/{id}` - Desativar categoria

### Saldos
- `GET /api/user-balances/{userId}` - Buscar saldo do usuário
- `GET /api/user-balances/current` - Buscar saldo do usuário atual

### Notificações
- `GET /api/notifications` - Listar notificações do usuário
- `POST /api/notifications` - Criar notificação
- `PUT /api/notifications/{id}/read` - Marcar como lida
- `DELETE /api/notifications/{id}` - Deletar notificação

### Auditoria
- `GET /api/audit-logs` - Listar logs de auditoria
- `GET /api/audit-logs/user/{userId}` - Logs por usuário
- `GET /api/audit-logs/table/{tableName}` - Logs por tabela

### Sincronização
- `GET /api/sync-status/{userId}` - Buscar status de sincronização
- `POST /api/sync-status` - Atualizar status de sincronização

**Nota:** Todos os endpoints estão documentados no Swagger UI com exemplos de requests e responses.

## Documentação da API 📚

A documentação completa da API está disponível através do Swagger UI após iniciar a aplicação:

- **Swagger UI:** http://localhost:8089/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8089/v3/api-docs

O Swagger UI permite:
- Explorar todos os endpoints disponíveis
- Ver schemas de requests e responses
- Testar endpoints diretamente na interface
- Visualizar códigos de status HTTP e mensagens de erro

## Testes 🧪

```bash
# Executar todos os testes
./mvnw.cmd test

# Executar testes com relatório de cobertura
./mvnw.cmd test jacoco:report
```

## Configuração 🔧

### application.properties
```properties
# Server
server.port=808x

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/project_management
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=sua_chave_secreta
jwt.expiration=3600000
jwt.refresh.expiration=86400000

# Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
