---
name: review-diff
description: Revisa o diff atual antes de commit. Foco em build, dinheiro Long, offline-first e regras financeiras.
context: fork
agent: senior-reviewer
---

Revise o diff com foco em: $ARGUMENTS

1. Rode `git diff` e `git diff --staged`
2. Para cada arquivo alterado avalie:
   - Quebra o build? (import faltando, tipo incompativel)
   - Dinheiro em Double em vez de Long centavos?
   - Funcionalidade principal depende de internet?
   - API externa sem try/catch ou fallback manual?
   - Regra financeira correta?
   - Schema Room mudou sem migration?
3. NAO altere arquivos

Saida:
- Veredito: PODE COMMITAR / NAO PODE COMMITAR
- Problemas criticos (quebram build ou regra financeira)
- Problemas medios
- Mensagem de commit sugerida (Conventional Commits)
