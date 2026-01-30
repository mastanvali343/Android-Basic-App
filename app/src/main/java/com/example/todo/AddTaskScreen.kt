package com.example.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, taskViewModel: TaskViewModel, taskId: Int?) {
    val isEditing = taskId != null
    val task by if (isEditing) {
        taskViewModel.getTaskById(taskId!!).collectAsState(initial = null)
    } else {
        remember { mutableStateOf(null) }
    }
    val isLoading = isEditing && task == null

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDate by remember { mutableStateOf("") }
    var taskStatus by remember { mutableStateOf("") }

    LaunchedEffect(task) {
        if (task != null) {
            taskName = task!!.name
            taskDescription = task!!.description
            taskDate = task!!.date
            taskStatus = task!!.status
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (isEditing) "Edit Task" else "Add Task") })
        },
        content = { padding ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Task Name") },
                        enabled = !isLoading
                    )
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Task Description") },
                        enabled = !isLoading
                    )
                    OutlinedTextField(
                        value = taskDate,
                        onValueChange = { taskDate = it },
                        label = { Text("Task Date") },
                        enabled = !isLoading
                    )
                    OutlinedTextField(
                        value = taskStatus,
                        onValueChange = { taskStatus = it },
                        label = { Text("Task Status") },
                        enabled = !isLoading
                    )
                    Button(onClick = {
                        if (isEditing) {
                            if (task != null) {
                                val updatedTask = task!!.copy(
                                    name = taskName,
                                    description = taskDescription,
                                    date = taskDate,
                                    status = taskStatus
                                )
                                taskViewModel.updateTask(updatedTask)
                            }
                        } else {
                            val newTask = Task(
                                name = taskName,
                                description = taskDescription,
                                date = taskDate,
                                status = taskStatus
                            )
                            taskViewModel.addTask(newTask)
                        }
                        navController.popBackStack()
                    }) {
                        Text(if (isEditing) "Update Task" else "Add Task")
                    }
                }
            }
        }
    )
}