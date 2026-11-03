package com.example.taskmanager.ui

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

// O DatePicker do Material3 trabalha com milissegundos em UTC (meia-noite).
// Por isso formatamos e comparamos sempre em UTC para evitar erro de um dia.

private val utc = TimeZone.getTimeZone("UTC")

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).apply { timeZone = utc }.format(millis)

/** Meia-noite UTC do dia atual (data local do aparelho). */
fun todayUtcMillis(): Long {
    val local = Calendar.getInstance()
    return Calendar.getInstance(utc).apply {
        clear()
        set(local.get(Calendar.YEAR), local.get(Calendar.MONTH), local.get(Calendar.DAY_OF_MONTH))
    }.timeInMillis
}
