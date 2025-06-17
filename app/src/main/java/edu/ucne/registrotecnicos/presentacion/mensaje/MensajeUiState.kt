package edu.ucne.registrotecnicos.presentacion.mensaje

import edu.ucne.registrotecnicos.data.local.entities.MensajeEntity

data class UiState(
    val mensajes: List<MensajeEntity> = emptyList(),
    val descripcion: String = "",
    val nombre: String = "",
    val rol: String = "",
    val successMessage: String? = null,
    val errorMessage: String? = null
)
