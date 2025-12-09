package com.senac.projectmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {

    @GetMapping(value = "/projects", produces = "text/html")
    @ResponseBody
    public String getProjectsPage() {
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Apresentação - Personal Finance Manager</title>
                <style>
                    /* Reset básico */
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {
                        font-family: 'Arial', sans-serif;
                        line-height: 1.6;
                        color: #333;
                        background-color: #f4f4f4;
                        padding: 20px;
                    }

                    header {
                        text-align: center;
                        background-color: #2c3e50;
                        color: white;
                        padding: 40px 20px;
                        margin-bottom: 30px;
                        border-radius: 8px;
                    }

                    header h1 {
                        font-size: 2.5em;
                        margin-bottom: 10px;
                    }

                    header p {
                        font-size: 1.2em;
                        opacity: 0.9;
                    }

                    section {
                        background-color: white;
                        margin-bottom: 30px;
                        padding: 30px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                    }

                    section h2 {
                        color: #2c3e50;
                        margin-bottom: 20px;
                        font-size: 1.8em;
                        border-bottom: 2px solid #3498db;
                        padding-bottom: 10px;
                    }

                    .download-btn {
                        display: inline-block;
                        background-color: #27ae60;
                        color: white;
                        padding: 15px 30px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-size: 1.2em;
                        font-weight: bold;
                        transition: background-color 0.3s ease;
                        margin-top: 10px;
                    }

                    .download-btn:hover {
                        background-color: #229954;
                    }

                    .feature-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                        gap: 20px;
                    }

                    .feature {
                        border: 1px solid #ddd;
                        padding: 20px;
                        border-radius: 5px;
                        background-color: #f9f9f9;
                    }

                    .feature h3 {
                        color: #3498db;
                        margin-bottom: 10px;
                    }

                    .feature ul {
                        list-style-type: none;
                        padding-left: 0;
                    }

                    .feature li {
                        margin-bottom: 5px;
                        padding-left: 20px;
                        position: relative;
                    }

                    .feature li:before {
                        content: "✓";
                        color: #27ae60;
                        font-weight: bold;
                        position: absolute;
                        left: 0;
                    }

                    ul {
                        padding-left: 20px;
                    }

                    li {
                        margin-bottom: 10px;
                    }

                    pre {
                        background-color: #f8f8f8;
                        border: 1px solid #ddd;
                        border-radius: 5px;
                        padding: 15px;
                        overflow-x: auto;
                        font-family: 'Courier New', monospace;
                        font-size: 0.9em;
                    }

                    footer {
                        text-align: center;
                        padding: 20px;
                        background-color: #2c3e50;
                        color: white;
                        border-radius: 8px;
                        margin-top: 30px;
                    }

                    /* Responsividade */
                    @media (max-width: 768px) {
                        body {
                            padding: 10px;
                        }

                        header {
                            padding: 20px 10px;
                        }

                        header h1 {
                            font-size: 2em;
                        }

                        section {
                            padding: 20px;
                        }

                        .feature-grid {
                            grid-template-columns: 1fr;
                        }
                    }
                </style>
            </head>
            <body>
                <header>
                    <h1>Personal Finance Manager</h1>
                    <p>Um aplicativo móvel de gerenciamento financeiro pessoal</p>
                </header>

                <section id="introducao">
                    <h2>Introdução</h2>
                    <p>Desenvolvido com React Native e Expo, este aplicativo permite aos usuários controlar suas finanças pessoais, gerenciar transações de receitas e despesas, organizar categorias e acompanhar estatísticas financeiras.</p>
                </section>

                <section id="download">
                    <h2>Baixe o Aplicativo</h2>
                    <p>Clique no botão abaixo para baixar a versão Android do Personal Finance Manager.</p>
                    <a href="/download/app" class="download-btn" target="_blank">Baixar APK</a>
                    <br><br>
                    <p>Ou escaneie o QR Code:</p>
                    <img src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://academico3.rj.senac.br/projectmanagement/download/app" alt="QR Code para download do APK" style="border: 2px solid #007bff; border-radius: 5px; margin-top: 10px;">
                </section>

                <section id="funcionalidades">
                    <h2>Funcionalidades Principais</h2>
                    <div class="feature-grid">
                        <div class="feature">
                            <h3>📊 Dashboard Principal</h3>
                            <ul>
                                <li>Visualização de saldo total atual</li>
                                <li>Totais de receitas e despesas separadas</li>
                                <li>Lista das últimas transações recentes</li>
                                <li>Estatísticas rápidas e resumo financeiro</li>
                            </ul>
                        </div>
                        <div class="feature">
                            <h3>💰 Gerenciamento de Transações</h3>
                            <ul>
                                <li>Adicionar, visualizar, editar e excluir transações</li>
                                <li>Filtragem avançada por tipo, categoria e período</li>
                                <li>Pesquisa em tempo real</li>
                            </ul>
                        </div>
                        <div class="feature">
                            <h3>📂 Sistema de Categorias</h3>
                            <ul>
                                <li>Criar, editar e excluir categorias personalizadas</li>
                                <li>Organização por tipo (receitas/despesas)</li>
                                <li>Seleção intuitiva com cores distintas</li>
                            </ul>
                        </div>
                        <div class="feature">
                            <h3>👤 Perfil do Usuário</h3>
                            <ul>
                                <li>Informações pessoais e estatísticas detalhadas</li>
                                <li>Editar perfil e senha</li>
                                <li>Logout seguro</li>
                            </ul>
                        </div>
                        <div class="feature">
                            <h3>🔍 Sistema de Filtros e Pesquisa</h3>
                            <ul>
                                <li>Pesquisa inteligente e filtros avançados</li>
                                <li>Estado compartilhado entre navegações</li>
                            </ul>
                        </div>
                        <div class="feature">
                            <h3>🔐 Autenticação e Segurança</h3>
                            <ul>
                                <li>Cadastro e login seguros</li>
                                <li>Isolamento de dados por usuário</li>
                                <li>Migração automática de dados</li>
                            </ul>
                        </div>
                    </div>
                </section>

                <section id="tecnologias">
                    <h2>Tecnologias Utilizadas</h2>
                    <ul>
                        <li><strong>React Native 0.81.5:</strong> Framework principal para desenvolvimento mobile</li>
                        <li><strong>Expo 54.0.23:</strong> Plataforma para desenvolvimento e build</li>
                        <li><strong>React 19.1.0:</strong> Biblioteca base do React</li>
                        <li><strong>React Navigation:</strong> Sistema completo de navegação</li>
                        <li><strong>AsyncStorage:</strong> Armazenamento local persistente</li>
                        <li><strong>@expo/vector-icons/Ionicons:</strong> Biblioteca de ícones vetoriais</li>
                        <li><strong>date-fns:</strong> Manipulação avançada de datas</li>
                        <li><strong>@react-native-community/datetimepicker:</strong> Seletores nativos de data/hora</li>
                        <li><strong>React Native Paper:</strong> Componentes de UI adicionais</li>
                        <li><strong>Axios:</strong> Cliente HTTP para integrações</li>
                    </ul>
                </section>

                <section id="estrutura">
                    <h2>Estrutura do Projeto</h2>
                    <pre>
            src/
            ├── components/          # Componentes reutilizáveis
            │   ├── CustomButton.js      # Botão personalizado
            │   ├── CustomInput.js       # Campo de entrada
            │   └── TransactionItem.js   # Item de lista de transação
            ├── contexts/            # Contextos React para estado global
            │   ├── FilterContext.js     # Gerenciamento de filtros
            │   └── TransactionContext.js # Estado de transações
            ├── navigation/          # Configuração de navegação
            │   └── AppNavigator.js      # Navegação principal
            ├── screens/             # Telas do aplicativo
            │   ├── LoginScreen.js       # Tela de login
            │   ├── HomeScreen.js        # Dashboard principal
            │   ├── TransactionsListScreen.js # Lista de transações
            │   └── ... (outras telas)
            ├── services/            # Serviços e APIs
            │   ├── api.js               # Configuração base da API
            │   ├── authService.js       # Serviço de autenticação
            │   └── ... (outros serviços)
            └── utils/               # Utilitários e constantes
                ├── constants.js         # Cores, espaçamentos
                ├── helpers.js           # Funções auxiliares
                └── theme.js             # Configuração de tema
                    </pre>
                </section>

                <footer>
                    <p>&copy; 2025 Personal Finance Manager (Project Management)</p>
                </footer>
            </body>
            </html>
            """;
    }
}
