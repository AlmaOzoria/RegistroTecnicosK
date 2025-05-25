package edu.ucne.registrotecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "mensajes")
data class MensajeEntity(
    @PrimaryKey
    val mensajeId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val fecha: String?,
    val rol: String = "",
    val fechaHora: Long
)