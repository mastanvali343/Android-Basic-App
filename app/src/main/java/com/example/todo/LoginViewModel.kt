package com.example.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.ui.LoginEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()
    private val _uiState = MutableStateFlow(LoginUiState())
        val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String){
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String){
        _uiState.update{it.copy(password = value)}
    }

    fun login(){
        val state = _uiState.value
        if(state.email.isBlank() || state.password.isBlank()){
            _uiState.update { it.copy(error = "Fields cannot be empty...")}
            return
        }
        viewModelScope.launch {
        _uiState.update{it.copy(isLoading = true)}
        delay(timeMillis = 1500)
//        _uiState.update{it.copy(isLoading = false)}
            _event.emit(LoginEvent.Success)
        }
    }
}