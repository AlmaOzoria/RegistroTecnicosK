package edu.ucne.registrotecnicos.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrotecnicos.presentacion.remote.EnfermedadApi
import edu.ucne.registrotecnicos.presentacion.remote.UsuarioApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    private const val ENFERMEDAD_BASE_URL = "https://apienfermedad.azurewebsites.net/"
    private const val USUARIOS_BASE_URL = "https://usuarioapp.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    @Named("EnfermedadRetrofit")
    fun providesEnfermedadRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENFERMEDAD_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesEnfermedadApi(
        @Named("EnfermedadRetrofit") retrofit: Retrofit
    ): EnfermedadApi = retrofit.create(EnfermedadApi::class.java)

    @Provides
    @Singleton
    @Named("UsuarioRetrofit")
    fun providesUsuarioRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USUARIOS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesUsuarioApi(
        @Named("UsuarioRetrofit") retrofit: Retrofit
    ): UsuarioApi = retrofit.create(UsuarioApi::class.java)

    val okHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(USUARIOS_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

}
