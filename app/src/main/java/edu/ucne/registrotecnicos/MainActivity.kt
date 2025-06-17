package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.repository.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repository.TicketRepository
import edu.ucne.registrotecnicos.data.repository.TecnicosNavHost
import edu.ucne.registrotecnicos.presentacion.enfermedad.EnfermedadViewModel
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoViewModel
import edu.ucne.registrotecnicos.presentacion.ticket.TicketViewModel
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroTecnicosTheme {
                val navController = rememberNavController()
                val tecnicoViewModel: TecnicoViewModel = hiltViewModel()
                val ticketViewModel: TicketViewModel = hiltViewModel()
                val enfermedadViewModel: EnfermedadViewModel = hiltViewModel()

                TecnicosNavHost(
                    navHostController = navController,
                    tecnicoViewModel = tecnicoViewModel,
                    ticketViewModel = ticketViewModel,
                    enfermedadViewModel = enfermedadViewModel
                )
            }
        }
    }
}
