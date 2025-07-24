package edu.ucne.registrotecnicos.presentacion.usuario

import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity

data class UsuarioUiState (
    val usuarioId: Int? = null,
    val descripcion: String? = null,
    val email: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val inputError: String? = null,
    val usuarios: List<UsuarioEntity> = emptyList()
    )