package edu.ucne.registrotecnicos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ucne.registrotecnicos.data.local.dao.MensajeDao
import edu.ucne.registrotecnicos.data.local.dao.PrioridadDao
import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao
import edu.ucne.registrotecnicos.data.local.dao.TicketDao
import edu.ucne.registrotecnicos.data.local.entities.MensajeEntity
import edu.ucne.registrotecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
        entities = [
            TecnicoEntity::class,
            TicketEntity::class,
            PrioridadEntity::class,
            MensajeEntity::class
        ],
        version = 15,
        exportSchema = false
    )
    abstract class TecnicoDb : RoomDatabase() {
        abstract fun tecnicoDao(): TecnicoDao
        abstract fun ticketDao(): TicketDao
    abstract fun prioridadDao(): PrioridadDao
    abstract fun mensajeDao(): MensajeDao

    companion object {
        @Volatile
        private var INSTANCE: TecnicoDb? = null

        fun getDatabase(context: Context): TecnicoDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TecnicoDb::class.java,
                    "tecnicos_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Lógica para insertar prioridades predeterminadas
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.prioridadDao()?.insertarPrioridades(
                                    listOf(
                                        PrioridadEntity(1, "Baja"),
                                        PrioridadEntity(2, "Media"),
                                        PrioridadEntity(3, "Alta")
                                    )
                                )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    }