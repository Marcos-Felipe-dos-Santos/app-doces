---
name: project-audit
description: Audita o estado atual do app Android antes de qualquer trabalho.
context: fork
---

Audite o projeto com foco em: $ARGUMENTS

1. Rode `git status` e `git log --oneline -5`
2. Leia CLAUDE.md e app/build.gradle.kts
3. Verifique se ha Gradle Wrapper (gradlew.bat, gradle/wrapper/)
4. Liste os modulos/telas relevantes ao foco
5. NAO leia local.properties, *.keystore nem *.jks
6. NAO altere nenhum arquivo

Saida:
- Resumo executivo (3 linhas)
- O projeto compila? (ha wrapper? ha icones em res/mipmap?)
- Gargalos relevantes ao foco confirmados no codigo
- Arquivos que precisariam mudar
- Proxima acao mais segura
