package edu.ucne.registrotecnicos.presentacion.enfermedad

import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto

data class EnfermedadUiState(
    val enfermedadId: Int? = null,
    val descripcion: String? = null,
    val monto: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val enfermedades: List<EnfermedadDto> = emptyList(),
    val inputError: String? = null
)