package edu.ucne.registrotecnicos.data.repository

import Home
import android.os.Build
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrotecnicos.data.local.entities.MensajeEntity
import edu.ucne.registrotecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registrotecnicos.presentacion.mensaje.MensajeScreen
import edu.ucne.registrotecnicos.presentacion.mensaje.MensajeViewModel
import edu.ucne.registrotecnicos.presentacion.mensaje.UiState
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoListScreen
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoScreen
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoViewModel
import edu.ucne.registrotecnicos.presentacion.navigation.Screen
import edu.ucne.registrotecnicos.presentacion.ticket.TicketListScreen
import edu.ucne.registrotecnicos.presentacion.ticket.TicketScreen
import edu.ucne.registrotecnicos.presentacion.ticket.TicketViewModel

@Composable
fun TecnicosNavHost(
    navHostController: NavHostController,
    tecnicoViewModel: TecnicoViewModel,
    ticketViewModel: TicketViewModel,
    mensajeViewModel: MensajeViewModel = hiltViewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = "Home"
    ) {
        composable("Home") {
            Home(
                goToTecnico = {
                    navHostController.navigate("TecnicoList")
                },
                goToTicket = {
                    navHostController.navigate("TicketList")
                }
            )
        }

        // Lista de técnicos
        composable("TecnicoList") {
            val tecnicoList = tecnicoViewModel.tecnicoList.collectAsState().value

            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onEdit = { tecnico ->
                    navHostController.navigate("Tecnico/${tecnico.tecnicoId}")
                },
                onCreate = {
                    navHostController.navigate("Tecnico/null")
                },
                onDelete = { tecnico ->
                    tecnicoViewModel.delete(tecnico)
                }
            )
        }

        composable("TicketList") {
            val tecnicoList = tecnicoViewModel.tecnicoList.collectAsState().value
            val ticketList = ticketViewModel.ticketList.collectAsState().value
            val context = LocalContext.current

            TicketListScreen(
                ticketList = ticketList,
                tecnicos = tecnicoList,
                onEdit = { ticket ->
                    navHostController.navigate("Ticket/${ticket.ticketId}")
                },
                onCreate = {
                    navHostController.navigate("Ticket/null")
                },
                onDelete = { ticket ->
                    ticketViewModel.delete(ticket)
                },
                onMessage = {
                    navHostController.navigate("mensaje")
                }
            )
        }

        // Crear o editar técnico
        composable("Tecnico/{tecnicoId}") { backStackEntry ->
            val tecnicoIdParam = backStackEntry.arguments?.getString("tecnicoId")
            val tecnicoId = if (tecnicoIdParam == "null") null else tecnicoIdParam?.toIntOrNull()
            val tecnico = tecnicoViewModel.getTecnicoById(tecnicoId)

            TecnicoScreen(
                tecnico = tecnico,
                agregarTecnico = { nombre, sueldo ->
                    if (tecnico == null) {
                        tecnicoViewModel.agregarTecnico(nombre, sueldo)
                    } else {
                        tecnicoViewModel.update(tecnico.copy(nombre = nombre, sueldo = sueldo))
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }

        // Crear o editar ticket
        composable("Ticket/{ticketId}") { backStackEntry ->
            val ticketIdParam = backStackEntry.arguments?.getString("ticketId")
            val ticketId = if (ticketIdParam == "null") null else ticketIdParam?.toIntOrNull()
            val ticket = ticketViewModel.getTicketById(ticketId)
            val tecnicos = tecnicoViewModel.tecnicoList.collectAsState().value

            val prioridades = listOf(
                PrioridadEntity(1, "Baja"),
                PrioridadEntity(2, "Media"),
                PrioridadEntity(3, "Alta")
            )

            val context = LocalContext.current

            TicketScreen(
                ticket = ticket,
                prioridades = prioridades,
                tecnicos = tecnicos,
                agregarTicket = { fecha, prioridadId, cliente, asunto, descripcion, tecnicoId ->
                    if (ticket == null) {
                        ticketViewModel.agregarTicket(fecha, prioridadId, cliente, asunto, descripcion, tecnicoId)
                    } else {
                        ticketViewModel.update(
                            ticket.copy(
                                fecha = fecha,
                                prioridadId = prioridadId,
                                cliente = cliente,
                                asunto = asunto,
                                descripcion = descripcion,
                                tecnicoId = tecnicoId
                            )
                        )
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                },
                onSendMessage = { ticket ->
                    Toast.makeText(context, "Enviando mensaje para el ticket: ${ticket.asunto}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        composable("mensaje") {
            val uiState by mensajeViewModel.uiState.collectAsState()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MensajeScreen(
                    uiState = uiState,
                    onDescripcionChange = mensajeViewModel::onDescripcionChange,
                    onNombreChange = mensajeViewModel::onNombreChange,
                    onRolChange = mensajeViewModel::onRolChange,
                    onSave = {
                        mensajeViewModel.saveMensaje()
                    },
                    onBack = {
                        navHostController.popBackStack()
                        mensajeViewModel.nuevoMensaje()
                    }
                )
            }
        }

    }
}

