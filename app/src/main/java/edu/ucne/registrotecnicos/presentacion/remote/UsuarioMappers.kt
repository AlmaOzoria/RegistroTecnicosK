package edu.ucne.registrotecnicos.presentacion.remote

import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity
import edu.ucne.registrotecnicos.presentacion.remote.dto.UsuarioDto

fun UsuarioEntity.toDto(): UsuarioDto {
    return UsuarioDto(
        usuarioId = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        email = this.email
    )
}