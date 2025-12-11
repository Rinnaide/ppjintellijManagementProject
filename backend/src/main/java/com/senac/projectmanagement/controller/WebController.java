package com.senac.projectmanagement.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class WebController {
    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Apresentação - Personal Finance Manager</title>\n" +
                "    <style>\n" +
                "        /* Reset básico */\n" +
                "        * {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            color: #333;\n" +
                "            background-color: #f4f4f4;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "\n" +
                "        header {\n" +
                "            text-align: center;\n" +
                "            background-color: #2c3e50;\n" +
                "            color: white;\n" +
                "            padding: 40px 20px;\n" +
                "            margin-bottom: 30px;\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "\n" +
                "        header h1 {\n" +
                "            font-size: 2.5em;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        header p {\n" +
                "            font-size: 1.2em;\n" +
                "            opacity: 0.9;\n" +
                "        }\n" +
                "\n" +
                "        section {\n" +
                "            background-color: white;\n" +
                "            margin-bottom: 30px;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        section h2 {\n" +
                "            color: #2c3e50;\n" +
                "            margin-bottom: 20px;\n" +
                "            font-size: 1.8em;\n" +
                "            border-bottom: 2px solid #3498db;\n" +
                "            padding-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .download-btn {\n" +
                "            display: inline-block;\n" +
                "            background-color: #27ae60;\n" +
                "            color: white;\n" +
                "            padding: 15px 30px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            font-size: 1.2em;\n" +
                "            font-weight: bold;\n" +
                "            transition: background-color 0.3s ease;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .download-btn:hover {\n" +
                "            background-color: #229954;\n" +
                "        }\n" +
                "\n" +
                "        .feature-grid {\n" +
                "            display: grid;\n" +
                "            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));\n" +
                "            gap: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .feature {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            background-color: #f9f9f9;\n" +
                "        }\n" +
                "\n" +
                "        .feature h3 {\n" +
                "            color: #3498db;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .feature ul {\n" +
                "            list-style-type: none;\n" +
                "            padding-left: 0;\n" +
                "        }\n" +
                "\n" +
                "        .feature li {\n" +
                "            margin-bottom: 5px;\n" +
                "            padding-left: 20px;\n" +
                "            position: relative;\n" +
                "        }\n" +
                "\n" +
                "        .feature li:before {\n" +
                "            content: \"✓\";\n" +
                "            color: #27ae60;\n" +
                "            font-weight: bold;\n" +
                "            position: absolute;\n" +
                "            left: 0;\n" +
                "        }\n" +
                "\n" +
                "        ul {\n" +
                "            padding-left: 20px;\n" +
                "        }\n" +
                "\n" +
                "        li {\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        pre {\n" +
                "            background-color: #f8f8f8;\n" +
                "            border: 1px solid #ddd;\n" +
                "            border-radius: 5px;\n" +
                "            padding: 15px;\n" +
                "            overflow-x: auto;\n" +
                "            font-family: 'Courier New', monospace;\n" +
                "            font-size: 0.9em;\n" +
                "        }\n" +
                "\n" +
                "        footer {\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "            background-color: #2c3e50;\n" +
                "            color: white;\n" +
                "            border-radius: 8px;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "\n" +
                "        /* Responsividade */\n" +
                "        @media (max-width: 768px) {\n" +
                "            body {\n" +
                "                padding: 10px;\n" +
                "            }\n" +
                "\n" +
                "            header {\n" +
                "                padding: 20px 10px;\n" +
                "            }\n" +
                "\n" +
                "            header h1 {\n" +
                "                font-size: 2em;\n" +
                "            }\n" +
                "\n" +
                "            section {\n" +
                "                padding: 20px;\n" +
                "            }\n" +
                "\n" +
                "            .feature-grid {\n" +
                "                grid-template-columns: 1fr;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <h1>Personal Finance Manager</h1>\n" +
                "        <p>Um aplicativo móvel de gerenciamento financeiro pessoal</p>\n" +
                "    </header>\n" +
                "\n" +
                "    <section id=\"introducao\">\n" +
                "        <h2>Introdução</h2>\n" +
                "        <p>Desenvolvido com React Native e Expo, este aplicativo permite aos usuários controlar suas finanças pessoais, gerenciar transações de receitas e despesas, organizar categorias e acompanhar estatísticas financeiras.</p>\n" +
                "    </section>\n" +
                "\n" +
                "    <section id=\"download\">\n" +
                "        <h2>Baixe o Aplicativo</h2>\n" +
                "        <p>Clique no botão abaixo para baixar a versão Android do Personal Finance Manager.</p>\n" +
                "        <a href=\"/download/app\" class=\"download-btn\" target=\"_blank\">Baixar APK</a>\n" +
                "        <br><br>\n" +
                "        <p>Ou escaneie o QR Code:</p>\n" +
                "        <img src=\"https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://academico3.rj.senac.br/projectmanagement/download/app\" alt=\"QR Code para download do APK\" style=\"border: 2px solid #007bff; border-radius: 5px; margin-top: 10px;\">\n" +
                "    </section>\n" +
                "\n" +
                "    <section id=\"api-status\">\n" +
                "        <h2>Status da API</h2>\n" +
                "        <p>Verificando conexão com o servidor backend...</p>\n" +
                "        <div id=\"api-status-message\" style=\"margin-top: 10px; padding: 10px; border-radius: 5px; background-color: #f0f0f0;\"></div>\n" +
                "    </section>\n" +
                "\n" +
                "    <section id=\"funcionalidades\">\n" +
                "        <h2>Funcionalidades Principais</h2>\n" +
                "        <div class=\"feature-grid\">\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>📊 Dashboard Principal</h3>\n" +
                "                <ul>\n" +
                "                    <li>Visualização de saldo total atual</li>\n" +
                "                    <li>Totais de receitas e despesas separadas</li>\n" +
                "                    <li>Lista das últimas transações recentes</li>\n" +
                "                    <li>Estatísticas rápidas e resumo financeiro</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>💰 Gerenciamento de Transações</h3>\n" +
                "                <ul>\n" +
                "                    <li>Adicionar, visualizar, editar e excluir transações</li>\n" +
                "                    <li>Filtragem avançada por tipo, categoria e período</li>\n" +
                "                    <li>Pesquisa em tempo real</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>📂 Sistema de Categorias</h3>\n" +
                "                <ul>\n" +
                "                    <li>Criar, editar e excluir categorias personalizadas</li>\n" +
                "                    <li>Organização por tipo (receitas/despesas)</li>\n" +
                "                    <li>Seleção intuitiva com cores distintas</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>👤 Perfil do Usuário</h3>\n" +
                "                <ul>\n" +
                "                    <li>Informações pessoais e estatísticas detalhadas</li>\n" +
                "                    <li>Editar perfil e senha</li>\n" +
                "                    <li>Logout seguro</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>🔍 Sistema de Filtros e Pesquisa</h3>\n" +
                "                <ul>\n" +
                "                    <li>Pesquisa inteligente e filtros avançados</li>\n" +
                "                    <li>Estado compartilhado entre navegações</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <div class=\"feature\">\n" +
                "                <h3>🔐 Autenticação e Segurança</h3>\n" +
                "                <ul>\n" +
                "                    <li>Cadastro e login seguros</li>\n" +
                "                    <li>Isolamento de dados por usuário</li>\n" +
                "                    <li>Migração automática de dados</li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </section>\n" +
                "\n" +
                "    <section id=\"tecnologias\">\n" +
                "        <h2>Tecnologias Utilizadas</h2>\n" +
                "        <ul>\n" +
                "            <li><strong>React Native 0.81.5:</strong> Framework principal para desenvolvimento mobile</li>\n" +
                "            <li><strong>Expo 54.0.23:</strong> Plataforma para desenvolvimento e build</li>\n" +
                "            <li><strong>React 19.1.0:</strong> Biblioteca base do React</li>\n" +
                "            <li><strong>React Navigation:</strong> Sistema completo de navegação</li>\n" +
                "            <li><strong>AsyncStorage:</strong> Armazenamento local persistente</li>\n" +
                "            <li><strong>@expo/vector-icons/Ionicons:</strong> Biblioteca de ícones vetoriais</li>\n" +
                "            <li><strong>date-fns:</strong> Manipulação avançada de datas</li>\n" +
                "            <li><strong>@react-native-community/datetimepicker:</strong> Seletores nativos de data/hora</li>\n" +
                "            <li><strong>React Native Paper:</strong> Componentes de UI adicionais</li>\n" +
                "            <li><strong>Axios:</strong> Cliente HTTP para integrações</li>\n" +
                "        </ul>\n" +
                "    </section>\n" +
                "\n" +
                "    <section id=\"estrutura\">\n" +
                "        <h2>Estrutura do Projeto</h2>\n" +
                "        <pre>\n" +
                "src/\n" +
                "├── components/          # Componentes reutilizáveis\n" +
                "│   ├── CustomButton.js      # Botão personalizado\n" +
                "│   ├── CustomInput.js       # Campo de entrada\n" +
                "│   └── TransactionItem.js   # Item de lista de transação\n" +
                "├── contexts/            # Contextos React para estado global\n" +
                "│   ├── FilterContext.js     # Gerenciamento de filtros\n" +
                "│   └── TransactionContext.js # Estado de transações\n" +
                "├── navigation/          # Configuração de navegação\n" +
                "│   └── AppNavigator.js      # Navegação principal\n" +
                "├── screens/             # Telas do aplicativo\n" +
                "│   ├── LoginScreen.js       # Tela de login\n" +
                "│   ├── HomeScreen.js        # Dashboard principal\n" +
                "│   ├── TransactionsListScreen.js # Lista de transações\n" +
                "│   └── ... (outras telas)\n" +
                "├── services/            # Serviços e APIs\n" +
                "│   ├── api.js               # Configuração base da API\n" +
                "│   ├── authService.js       # Serviço de autenticação\n" +
                "│   └── ... (outros serviços)\n" +
                "└── utils/               # Utilitários e constantes\n" +
                "    ├── constants.js         # Cores, espaçamentos\n" +
                "    ├── helpers.js           # Funções auxiliares\n" +
                "    └── theme.js             # Configuração de tema\n" +
                "        </pre>\n" +
                "    </section>\n" +
                "\n" +
                "    <footer>\n" +
                "        <p>&copy; 2025 Personal Finance Manager (Project Management)</p>\n" +
                "    </footer>\n" +
                "\n" +
                "    <script>\n" +
                "        // Função para verificar o status da API\n" +
                "        async function checkApiStatus() {\n" +
                "            const statusMessage = document.getElementById('api-status-message');\n" +
                "            try {\n" +
                "                const response = await fetch('http://academico3.rj.senac.br/projectmanagement/api/users', {\n" +
                "                    method: 'GET',\n" +
                "                    headers: {\n" +
                "                        'Content-Type': 'application/json'\n" +
                "                    }\n" +
                "                });\n" +
                "\n" +
                "                if (response.ok) {\n" +
                "                    const users = await response.json();\n" +
                "                    const userCount = Array.isArray(users) ? users.length : 0;\n" +
                "                    statusMessage.innerHTML = `<strong style=\"color: green;\">✅ API Conectada com Sucesso!</strong><br>Total de usuários registrados: ${userCount}`;\n" +
                "                    statusMessage.style.backgroundColor = '#d4edda';\n" +
                "                    statusMessage.style.color = '#155724';\n" +
                "                    statusMessage.style.border = '1px solid #c3e6cb';\n" +
                "                } else {\n" +
                "                    throw new Error(`HTTP ${response.status}: ${response.statusText}`);\n" +
                "                }\n" +
                "            } catch (error) {\n" +
                "                console.error('Erro ao conectar com a API:', error);\n" +
                "                statusMessage.innerHTML = `<strong style=\"color: red;\">❌ Erro na Conexão com a API</strong><br>Detalhes: ${error.message}<br>Verifique se o servidor backend está rodando e acessível.`;\n" +
                "                statusMessage.style.backgroundColor = '#f8d7da';\n" +
                "                statusMessage.style.color = '#721c24';\n" +
                "                statusMessage.style.border = '1px solid #f5c6cb';\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        // Executar verificação quando a página carregar\n" +
                "        window.addEventListener('DOMContentLoaded', function() {\n" +
                "            checkApiStatus();\n" +
                "        });\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        return ResponseEntity.ok(htmlContent);
    }
}
