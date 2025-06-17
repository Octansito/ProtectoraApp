package com.example.protectora.ui.MainScreen

import androidx.lifecycle.ViewModel
import com.example.protectora.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class PrincipalViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiStatePrincipal(usuario = Repository.getCurrentUser()))
    val uiState: StateFlow<UiStatePrincipal> = _uiState.asStateFlow()

    fun cerrarSesion() {
        Repository.logout()
    }
    /*fun getUserEmail(): String  {
        val usuario= Repository.getCurrentUser()
        return usuario.email
    }
    fun getUserName(): String  {
        val usuario= Repository.getCurrentUser()
        return usuario.nombre
    }*/
}