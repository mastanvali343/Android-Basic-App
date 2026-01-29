package com.example.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value: String){
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChange(value: String){
        uiState = uiState.copy(password = value)
    }

    fun login(){
        if(uiState.email.isBlank() || uiState.password.isBlank()){
            uiState = uiState.copy(error = "Fields cannot be empty...")
            return
        }

        uiState = uiState.copy(isLoading = true)

    }
}