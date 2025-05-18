package edu.ucne.registrotecnicos.data.local.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prioridades")
data class PrioridadEntity(
    @PrimaryKey val prioridadId: Int,
    val descripcion: String
)
