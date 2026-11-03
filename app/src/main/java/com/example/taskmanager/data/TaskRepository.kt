package com.example.taskmanager.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val tasks: Flow<List<Task>> = dao.observeAll()

    suspend fun get(id: Int): Task? = dao.getById(id)

    suspend fun save(task: Task) = dao.upsert(task)

    suspend fun delete(task: Task) = dao.delete(task)

    suspend fun clearDone() = dao.clearDone()

    /** Cria (id == -1) ou atualiza uma tarefa a partir dos campos do formulário. */
    suspend fun saveForm(
        id: Int,
        title: String,
        description: String,
        priority: Priority,
        dueDate: Long?
    ) {
        val base = if (id == -1) null else dao.getById(id)
        val task = base?.copy(
            title = title,
            description = description,
            priority = priority,
            dueDate = dueDate
        ) ?: Task(
            title = title,
            description = description,
            priority = priority,
            dueDate = dueDate
        )
        dao.upsert(task)
    }
}
