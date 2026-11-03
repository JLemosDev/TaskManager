package com.example.taskmanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNav(vm: TaskViewModel) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "list") {
        composable("list") {
            TaskListScreen(
                vm = vm,
                onAdd = { nav.navigate("edit/-1") },
                onEdit = { id -> nav.navigate("edit/$id") }
            )
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            TaskEditScreen(
                taskId = entry.arguments?.getInt("id") ?: -1,
                vm = vm,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
