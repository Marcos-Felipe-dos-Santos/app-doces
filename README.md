# App Confeitaria Gestao

Aplicativo Android nativo offline-first para gestao de confeitaria artesanal.
Bolos, doces, ovos de Pascoa, panetones — sem login, sem nuvem, dados no celular.

## Status

- MVP funcional, 17 testes passando, BUILD SUCCESSFUL
- 5 PRs mergeados (Fases 1 a 5)
- Pronto para uso real controlado

## Funcionalidades

### Cadastros

- Clientes: nome, telefone, email, observacoes, multiplos enderecos
- Produtos: nome, categoria, preco base, descricao

### Pedidos

- Vincula cliente + lista de itens (produto + quantidade)
- Tipo: entrega ou retirada
- Calcula automaticamente: subtotal, frete, desconto, total
- Status do pedido com chip visual
- Detalhe completo com cliente, itens e total

### Financeiro

- Dinheiro armazenado em Long (centavos) — sem erro de precisao
- Regras testadas: subtotal de item, total do pedido, total com juros,
  valor da parcela, valor pendente, frete com taxa minima

### Relatorio mensal

- Total de pedidos no mes vigente
- Faturamento bruto
- Recebido vs pendente
- Ticket medio

### Recursos nativos do celular

- Abrir WhatsApp do cliente (Intent nativa, sem API paga)
- Abrir endereco no Maps/Waze (Intent nativa)
- Mensagem pre-formatada para avisar sobre pedido pronto

## Offline-first

- Dados em SQLite (Room) — fonte de verdade local
- ViaCEP, Nominatim e OSRM (geocodificacao) sao opcionais e protegidos
  por try/catch — falha de rede nao impede salvar
- App funciona 100% sem internet

## Stack

Kotlin, Jetpack Compose, Material3, Navigation Compose, Room 2.6.1,
Hilt 2.51, DataStore, KSP. compileSdk 34, minSdk 26.

## Arquitetura (MVVM)

- `data/local` — Room (entity, dao, AppDatabase) e DataStore
- `data/remote` — Retrofit (ViaCEP, Nominatim, OSRM — opcionais)
- `data/repository` — Impl dos repositories
- `di` — Hilt (DatabaseModule, NetworkModule, RepositoryModule)
- `domain/model` — Models e enums
- `domain/repository` — interfaces
- `domain/usecase` — use cases por feature (cliente, pedido, produto, frete)
- `presentation/ui` — telas Compose por feature
- `presentation/components` — Card, TopBar, EmptyState, Chip
- `presentation/navigation` — AppNavGraph, Screen
- `presentation/util` — CurrencyFormatter (Long centavos), IntentUtils

## Como rodar

1. Instale o Android Studio (https://developer.android.com/studio)
2. File -> Open -> selecione a pasta do projeto
3. Aguarde o Gradle Sync (~5-15 min na primeira vez)
4. Build -> Make Project (ou Ctrl+F9)
5. Run no celular fisico (com depuracao USB) ou no emulador

## Comandos

```
# Build debug
.\gradlew.bat assembleDebug

# Testes JVM (17 testes)
.\gradlew.bat testDebugUnitTest

# Limpar cache
.\gradlew.bat clean
```

## Regras de negocio

- subtotal item = quantidade x precoUnitario
- total pedido = subtotalItens + frete - desconto
- total com juros = total x (100 + juros) / 100
- valor parcela = totalComJuros / numeroParcelas
- valor pendente = total - pago
- frete = distancia x precoPorKm, respeitando taxaMinima
- Todos os valores em Long centavos para precisao monetaria

## Roadmap (pos-MVP, baseado em uso real)

- Polish baseado no feedback do uso real
- Notificacoes locais para lembretes de entrega
- Backup/export em CSV
- Filtros e busca avancada
- Fotos do pedido (opcional)

## Licenca

Uso pessoal / familiar.
