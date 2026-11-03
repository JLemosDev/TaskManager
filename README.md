# Gerenciador de Tarefas (Android)

Kotlin + Jetpack Compose (Material 3) + Room + MVVM + Navigation Compose.

## Como rodar
1. Android Studio (Koala ou mais novo) → **File > Open** → selecione a pasta `TaskManager`.
2. Aguarde o **Gradle Sync** (precisa de internet na primeira vez; JDK 17).
3. Crie/escolha um emulador (API 24+) ou conecte um celular e clique em **Run ▶**.

## Funcionalidades
- Criar, editar, excluir (deslizar para a esquerda, com **Desfazer**) e concluir tarefas
- Prioridade (baixa/média/alta) e data de vencimento (destaque para atrasadas)
- Busca por texto, filtros (todas/pendentes/concluídas) e barra de progresso
- Persistência local com Room; cores dinâmicas (Android 12+) e modo escuro

## Estrutura
- `data/` — entidade `Task`, `TaskDao`, `AppDatabase`, `TaskRepository`
- `ui/` — `TaskViewModel`, `TaskListScreen`, `TaskEditScreen`, `AppNav`, tema
