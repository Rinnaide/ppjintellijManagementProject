<<<<<<< HEAD
# ProjectManagement 🌟
=======
 # ProjectManagement 🌟
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178

**ProjectManagement** é uma API backend desenvolvida com Spring Boot para gerenciamento financeiro. Ela ajuda a gerenciar finanças pessoais de forma eficiente, mantendo o controle sobre usuários, transações, categorias e saldos. 💰

## Funcionalidades 📋

- **Gerenciamento de Usuários:** Cadastro, login, atualização e exclusão de usuários com autenticação JWT.
- **Gerenciamento de Transações:** Acompanhe receitas e despesas, categorize transações e organize dados.
- **Organização de Categorias:** Crie e gerencie categorias para transações, com tipos (receita/despesa).
- **Acompanhamento de Saldos:** Monitore saldos atuais, total de receitas e despesas por usuário.
- **Autenticação Segura:** Utiliza JWT para segurança e controle de acesso.
- **Documentação API:** Integração com Swagger para documentação interativa.
<<<<<<< HEAD
- **Auditoria e Logs:** Rastreamento de ações com logs de auditoria.
- **Notificações:** Sistema de notificações para usuários.
- **Tokens de Reset de Senha:** Gerenciamento de tokens para redefinição de senha.
- **Tokens de Refresh:** Suporte para tokens de refresh JWT.
- **Sincronização de Status:** Controle de sincronização de dados entre dispositivos.
- **Gerenciamento de Funções e Papéis:** Controle de acesso baseado em roles.
=======
- **Integração com Projetos:** Suporte para entidades de projetos via Feign Client.
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178

## Tecnologias Utilizadas ⚙️
- **Spring Boot** (versão 3.5.6)
- **Java** (versão 21)
- **Spring Data JPA**
- **Spring Security**
- **JWT** (java-jwt 4.4.0)
- **MySQL** (conector MySQL)
- **H2 Database** (para testes)
<<<<<<< HEAD
- **SpringDoc OpenAPI** (versão 2.7.0 para Swagger UI)
=======
- **Spring Cloud OpenFeign** (versão 4.0.4)
- **SpringDoc OpenAPI** (para Swagger UI)
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
- **Maven** (para build e dependências)

## Como Começar 🚀
Para executar o ProjectManagement localmente, siga estas etapas:

1. **Clone o repositório:**
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd projectmanagement

2. **Instale as dependências:**
   ```bash
<<<<<<< HEAD
   ./mvnw.cmd clean install
=======
   mvn clean install
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178

3. **Configure o banco de dados:**
   - Certifique-se de que o MySQL esteja rodando.
   - As configurações estão em `src/main/resources/application.properties` (já configurado para o banco fornecido).

4. **Inicie o servidor:**
   ```bash
<<<<<<< HEAD
   ./mvnw.cmd spring-boot:run

5. **Acesse a aplicação:**
   - API rodando em: http://localhost:8089
   - Documentação Swagger: http://localhost:8089/swagger-ui.html

## Estrutura do Projeto
- **Controllers:** Endpoints REST para usuários, transações, categorias, autenticação, audit logs, notificações, etc.
- **Entities:** Modelos de dados (User, Transaction, Category, UserBalance, AuditLog, Notification, PasswordResetToken, RefreshToken, SyncStatus, Role, UserRoles, etc.).
- **Services:** Lógica de negócio para cada domínio.
- **Repositories:** Acesso a dados com JPA.
- **Security:** Configuração de JWT e filtros de autenticação.
- **Config:** Configurações do Swagger e segurança.
- **DTOs:** Objetos de transferência de dados para requests e responses.
=======
   mvn spring-boot:run

5. **Acesse a aplicação:**
   - API rodando em: http://localhost:8086
   - Documentação Swagger: http://localhost:8086/swagger-ui/index.html

## Estrutura do Projeto
- **Controllers:** Endpoints REST para usuários, transações, categorias e autenticação.
- **Entities:** Modelos de dados (User, Transaction, Category, UserBalance, Projeto, etc.).
- **Services:** Lógica de negócio.
- **Repositories:** Acesso a dados com JPA.
- **Security:** Configuração de JWT e filtros de autenticação.
- **Config:** Configurações do Swagger e Feign.
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
