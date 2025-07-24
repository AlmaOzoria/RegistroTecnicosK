package edu.ucne.registrotecnicos.presentacion.remote

import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import edu.ucne.registrotecnicos.presentacion.remote.dto.UsuarioDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val api: EnfermedadApi,
    private val usuarioApi: UsuarioApi
){
    suspend fun getEnfermedades(): List<EnfermedadDto> = api.getEnfermedades()

    suspend fun createEnfermedad(enfermedad: EnfermedadDto): EnfermedadDto =
        api.createEnfermedad(enfermedad)

    suspend fun getEnfermedad(id: Int): EnfermedadDto =api.getEnfermedad(id)

    suspend fun updateEnfermedad(id: Int, enfermedad: EnfermedadDto): EnfermedadDto =
        api.updateEnfermedad(id, enfermedad)

    suspend fun deleteEnfermedad(id: Int) = api.deleteEnfermedad(id)

    suspend fun getUsuarios(): List<UsuarioDto> =
        usuarioApi.getUsuarios()

    suspend fun createUsuario(usuario: UsuarioDto): UsuarioDto =
        usuarioApi.createUsuario(usuario)

    suspend fun getUsuario(id: Int): UsuarioDto =
        usuarioApi.getUsuario(id)

    suspend fun updateUsuario(id: Int, usuario: UsuarioDto): UsuarioDto =
        usuarioApi.updateUsuario(id, usuario)

    suspend fun deleteUsuario(id: Int) =
        usuarioApi.deleteUsuario(id)
}
