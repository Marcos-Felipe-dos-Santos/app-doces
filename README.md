# App Gestão Confeitaria Artesanal 🍰

Aplicativo Android nativo desenvolvido para gestão completa de uma confeitaria artesanal, focado em uso pessoal e produtividade offline.

## 🚀 Visão Geral

O projeto foi construído utilizando as tecnologias mais modernas do ecossistema Android, seguindo os princípios de **Clean Architecture** e **MVVM**. O objetivo é oferecer uma ferramenta robusta para controle de clientes, produtos, pedidos e finanças, funcionando 100% localmente.

## 🛠️ Stack Técnica

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose com Material Design 3
- **Arquitetura:** Clean Architecture + MVVM
- **Injeção de Dependência:** Hilt
- **Banco de Dados:** Room (SQLite)
- **Preferências:** DataStore
- **Assincronismo:** Coroutines + StateFlow
- **Rede:** Retrofit + OkHttp (ViaCEP, Nominatim, OSRM)
- **Imagens:** Coil
- **Gráficos:** MPAndroidChart
- **PDF:** iText7
- **Background Tasks:** WorkManager

## 📂 Estrutura do Projeto

O projeto segue uma estrutura de pacotes canônica para Clean Architecture:

```text
com.confeitaria.gestao/
├── data/               # Implementações de dados (Local e Remoto)
│   ├── local/          # Room Database, DAOs, Entities, DataStore
│   ├── remote/         # APIs Retrofit e DTOs
│   └── repository/     # Implementações dos repositórios
├── domain/             # Regras de Negócio
│   ├── model/          # Modelos de dados puros e Enums
│   ├── repository/     # Interfaces dos repositórios
│   └── usecase/        # Casos de uso específicos por domínio
├── presentation/       # Camada de UI
│   ├── components/     # Componentes Compose reutilizáveis
│   ├── navigation/     # NavHost e definições de rotas
│   ├── theme/          # Definições de cores, tipos e tema Material3
│   ├── ui/             # Telas e ViewModels por funcionalidade
│   └── util/           # Formatadores e utilitários de UI
└── worker/             # Tarefas em background (Notificações)
```

## ✨ Funcionalidades Principais

1.  **Dashboard:** Resumo diário de entregas, pedidos em produção e métricas financeiras.
2.  **Gestão de Clientes:** Cadastro completo com múltiplos endereços e histórico de pedidos.
3.  **Catálogo de Produtos:** Controle de produtos com variações de preços e cálculo de margem de lucro.
4.  **Fluxo de Pedidos:** Sistema multi-step para criação de pedidos, incluindo cálculo automático de frete.
5.  **Integrações Inteligentes:** 
    *   Auto-preenchimento de endereço via CEP (**ViaCEP**).
    *   Geocodificação e cálculo de distância para frete (**Nominatim** e **OSRM**).
6.  **Financeiro:** Acompanhamento de receitas e pagamentos pendentes.
7.  **Relatórios:** Geração de relatórios em PDF para exportação.
8.  **Notificações:** Lembretes automáticos de produção e entrega via WorkManager.

## ⚙️ Configuração do Ambiente

1.  **Android Studio:** Jellyfish (ou superior).
2.  **JDK:** Version 17.
3.  **Gradle:** 8.3.0.
4.  **Permissões:** O app solicita acesso à Internet (APIs de frete), Câmera (fotos de produtos) e Notificações.

---

## 📝 Regras de Desenvolvimento (Padrão do Projeto)

- **Offline First:** Todos os dados são persistidos localmente.
- **PT-BR:** Toda a interface e mensagens são em Português Brasileiro.
- **Clean Code:** Uso rigoroso de injeção de dependência e separação de responsabilidades.
- **Segurança:** Sem autenticação complexa, dados armazenados no dispositivo da usuária.

---
*Desenvolvido como solução de gestão para confeiteiras artesanais.*
