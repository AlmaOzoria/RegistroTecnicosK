package edu.ucne.registrotecnicos.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTecnicoDb(
        @ApplicationContext appContext: Context
    ): TecnicoDb {
        return Room.databaseBuilder(
            appContext,
            TecnicoDb::class.java,
            "TecnicoDb"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTicketDao(db: TecnicoDb) = db.ticketDao()

    @Provides
    fun provideTecnicoDao(db: TecnicoDb) = db.tecnicoDao()

    @Provides
    fun provideMensajeDao(db: TecnicoDb) = db.mensajeDao()

    @Provides
    fun providePrioridadDao(db: TecnicoDb) = db.prioridadDao()
}