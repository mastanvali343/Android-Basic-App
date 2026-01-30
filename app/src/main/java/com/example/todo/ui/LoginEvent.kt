package com.example.todo.ui

sealed interface LoginEvent {
    object Success : LoginEvent
}