---
name: safe-commit
description: Revisa, valida build e prepara commit seguro. Invoke manualmente.
disable-model-invocation: true
context: fork
agent: senior-reviewer
argument-hint: [tipo-ou-escopo]
---

Prepare commit com escopo: $ARGUMENTS

1. Rode `git status` e `git diff --staged`
2. Confirme que NAO entram: local.properties, *.keystore, *.jks,
   .gradle/, build/, .idea/, *.iml
3. Confirme que o build passa: `.\gradlew.bat assembleDebug`
4. Sugira mensagem (feat/fix/refactor/chore)
5. Execute git commit SOMENTE se aprovado explicitamente
6. NUNCA git push

Saida:
- Arquivos que entrarao no commit
- ALERTA se arquivo sensivel ou gerado estiver staged
- Resultado do build
- Mensagem de commit sugerida
- Comando exato para executar
