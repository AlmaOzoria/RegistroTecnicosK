package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repository.TicketRepository
import edu.ucne.registrotecnicos.data.repository.TecnicosNavHost
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoViewModel
import edu.ucne.registrotecnicos.presentacion.ticket.TicketViewModel
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicoDb
    private lateinit var tecnicosRepository: TecnicoRepository
    private lateinit var tecnicosViewModel: TecnicoViewModel

    private lateinit var ticketRepository: TicketRepository
    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización de la base de datos
        tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnico.db"
        ).fallbackToDestructiveMigration()
            .build()

        // Repositorios y ViewModels
        tecnicosRepository = TecnicoRepository(tecnicoDb.tecnicoDao())
        tecnicosViewModel = TecnicoViewModel(tecnicosRepository)

        ticketRepository = TicketRepository(tecnicoDb)
        ticketViewModel = TicketViewModel(ticketRepository)

        setContent {
            RegistroTecnicosTheme {
                val navController = rememberNavController()
                TecnicosNavHost(
                    navHostController = navController,
                    tecnicoViewModel = tecnicosViewModel,
                    ticketViewModel = ticketViewModel
                )
            }
        }
    }
}
