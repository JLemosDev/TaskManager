package com.example.taskmanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.Priority
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Filter(val label: String) {
    ALL("Todas"),
    PENDING("Pendentes"),
    DONE("Concluídas")
}

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = TaskRepository(AppDatabase.get(app).taskDao())

    val query = MutableStateFlow("")
    val filter = MutableStateFlow(Filter.ALL)

    /** Lista filtrada e ordenada: pendentes primeiro, maior prioridade, vencimento mais próximo. */
    val tasks: StateFlow<List<Task>> = combine(repo.tasks, query, filter) { list, q, f ->
        list
            .filter { t ->
                when (f) {
                    Filter.ALL -> true
                    Filter.PENDING -> !t.done
                    Filter.DONE -> t.done
                }
            }
            .filter { t ->
                q.isBlank() ||
                    t.title.contains(q, ignoreCase = true) ||
                    t.description.contains(q, ignoreCase = true)
            }
            .sortedWith(
                compareBy<Task> { it.done }
                    .thenByDescending { it.priority.ordinal }
                    .thenBy { it.dueDate ?: Long.MAX_VALUE }
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Par (concluídas, total) considerando todas as tarefas, sem filtros. */
    val progress: StateFlow<Pair<Int, Int>> = repo.tasks
        .map { list -> list.count { it.done } to list.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0 to 0)

    fun toggleDone(task: Task) = viewModelScope.launch {
        repo.save(task.copy(done = !task.done))
    }

    fun delete(task: Task) = viewModelScope.launch { repo.delete(task) }

    fun restore(task: Task) = viewModelScope.launch { repo.save(task) }

    fun clearDone() = viewModelScope.launch { repo.clearDone() }

    suspend fun getTask(id: Int): Task? = repo.get(id)

    fun saveForm(
        id: Int,
        title: String,
        description: String,
        priority: Priority,
        dueDate: Long?
    ) = viewModelScope.launch {
        repo.saveForm(id, title, description, priority, dueDate)
    }
}
