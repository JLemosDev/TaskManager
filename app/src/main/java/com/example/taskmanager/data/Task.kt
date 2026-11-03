package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Priority(val label: String) {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta")
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val done: Boolean = false,
    val dueDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
