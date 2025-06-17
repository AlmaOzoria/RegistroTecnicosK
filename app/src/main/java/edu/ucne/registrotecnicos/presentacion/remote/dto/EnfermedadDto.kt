package edu.ucne.registrotecnicos.presentacion.remote.dto

data class EnfermedadDto (
    val enfermedadId: Int? = null,
    val descripcion: String,
    val monto: Double
    )