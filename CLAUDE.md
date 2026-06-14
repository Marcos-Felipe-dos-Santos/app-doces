# App Confeitaria — Gestao local para confeitaria artesanal

App Android nativo offline-first para gestao de clientes, produtos,
pedidos, pagamentos e entregas. Bolos, doces, ovos de Pascoa, panetones.
Dados 100% locais via Room. Sem login, sem backend, sem nuvem.

## Stack
Kotlin, Jetpack Compose, Material3, Navigation Compose, Room 2.6.1,
Hilt 2.51, DataStore, KSP. compileSdk 34, minSdk 26.
namespace: com.confeitaria.gestao

## Comandos (NAO ha pytest nem ruff)
- Build debug:   `.\gradlew.bat assembleDebug`
- Testes JVM:    `.\gradlew.bat testDebugUnitTest`
- Clean:         `.\gradlew.bat clean`
- Primeiro build baixa deps e demora — normal.

## Arquitetura (MVVM)
data/local/database/  — Room: entity, dao, AppDatabase
data/local/preferences/ — DataStore: AppPreferences
data/remote/          — Retrofit: ViaCEP, Nominatim, OSRM (A TORNAR OPCIONAIS)
data/repository/      — Impl dos repositories
di/                   — Hilt: DatabaseModule, NetworkModule, RepositoryModule
domain/model/         — Models, enums (StatusPedido, TipoEntrega, FormaPagamento)
domain/repository/    — interfaces
domain/usecase/       — cliente, pedido, produto
presentation/ui/      — telas Compose por feature
presentation/components/ — ClienteCard, PedidoCard, EmptyState, StatusChip
presentation/navigation/ — AppNavGraph, Screen
worker/               — NotificacaoWorker (DESATIVADO na Fase 1)

## Principios inegociaveis
- offline-first: NADA principal depende de internet
- dinheiro em Long centavos, NUNCA Double/Float
- APIs externas opcionais com try/catch e fallback manual
- usabilidade: novo pedido em menos de 1 min, campos avancados opcionais
- Room e a fonte de verdade local
- pedido sempre vinculado a cliente
- pedido confirmado precisa de pelo menos 1 item

## Regras de negocio
- subtotal item = quantidade x precoUnitario
- total pedido = subtotalItens + frete - desconto
- total com juros = total x (100 + juros) / 100
- valor parcela = totalComJuros / numeroParcelas
- valor pendente = total - pago
- frete = distancia x precoPorKm, respeitando taxaMinima

## Gargalos conhecidos (prioridade)
1. SEM Gradle Wrapper — nao compila. FIX: adicionar wrapper (Fase 1)
2. SEM icones launcher — res/ sem mipmap. FIX: gerar icones minimos (Fase 1)
3. Dinheiro em Double — todas entidades. FIX: migrar Long centavos (Fase 3)
4. APIs externas ativas no Hilt — ViaCEP/Nominatim/OSRM. FIX: opcionais (Fase 4)
5. MPAndroidChart morto — dep sem uso. FIX: remover (Fase 4)
6. ConfiguracaoRepository sem impl — tela pode crashar. FIX: DataStore (Fase 2)
7. WorkManager+Hilt sem initializer — pode quebrar. FIX: desativar (Fase 1)

## Dados sensiveis — NUNCA tocar
- local.properties, *.keystore, *.jks (SDK path e assinatura)

## Git
- Conventional commits: feat/fix/refactor/chore
- Um commit por mudanca logica
- Build deve passar antes de qualquer commit
- Nunca git push sem aprovacao explicita
