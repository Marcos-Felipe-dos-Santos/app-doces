---
name: build-check
description: Compila o projeto e roda testes unitarios JVM. Use depois de qualquer mudanca de codigo.
context: fork
agent: android-dev
---

Verifique o build com foco em: $ARGUMENTS

1. Rode `.\gradlew.bat assembleDebug`
2. Se compilar, rode `.\gradlew.bat testDebugUnitTest`
3. Se falhar:
   - copie o primeiro erro real (nao o stacktrace inteiro)
   - identifique arquivo e linha
   - explique a causa provavel
   - proponha a menor correcao possivel
4. NAO altere arquivos nesta skill — so reporte

Saida:
- assembleDebug: sucesso / falha
- testDebugUnitTest: sucesso / falha / N passaram
- Erros com arquivo:linha e causa
- Correcao minima sugerida
