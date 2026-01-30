package com.example.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.ui.LoginEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()

    fun onEmailChange(email: String){
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String){
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            if(uiState.value.email == "mastan343" && uiState.value.password == "mitbp"){
                _event.send(LoginEvent.Success)
            } else {
                _uiState.update { it.copy(error = "Invalid credentials") }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}