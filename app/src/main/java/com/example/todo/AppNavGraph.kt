package com.example.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(navController: NavHostController) {
    val application = LocalContext.current.applicationContext as TodoApplication
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(application.database.taskDao())
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController, taskViewModel)
        }
        composable("addTask") {
            AddTaskScreen(navController, taskViewModel, null)
        }
        composable(
            "editTask/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            AddTaskScreen(navController, taskViewModel, taskId)
        }
    }
}