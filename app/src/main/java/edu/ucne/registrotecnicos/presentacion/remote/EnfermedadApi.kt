package edu.ucne.registrotecnicos.presentacion.remote

import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import retrofit2.http.*

interface EnfermedadApi {
    @GET("api/Enfermedades")
    suspend fun getEnfermedades(): List<EnfermedadDto>

    @GET("api/Enfermedades/{id}")
    suspend fun getEnfermedad(@Path("id") id: Int): EnfermedadDto

    @POST("api/Enfermedades")
    suspend fun createEnfermedad(@Body enfermedadDto: EnfermedadDto): EnfermedadDto

    @PUT("api/Enfermedades/{id}")
    suspend fun updateEnfermedad(@Path("id") id: Int, @Body enfermedadDto: EnfermedadDto): EnfermedadDto

    @DELETE("api/Enfermedades/{id}")
    suspend fun deleteEnfermedad(@Path("id") id: Int)
}