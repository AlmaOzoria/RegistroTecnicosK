package edu.ucne.registrotecnicos.data.repository

import Home
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrotecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity
import edu.ucne.registrotecnicos.presentacion.enfermedad.EnfermedadViewModel
import edu.ucne.registrotecnicos.presentacion.enfermedad.EnfermedadListScreen
import edu.ucne.registrotecnicos.presentacion.enfermedad.EnfermedadScreen
import edu.ucne.registrotecnicos.presentacion.mensaje.MensajeScreen
import edu.ucne.registrotecnicos.presentacion.mensaje.MensajeViewModel
import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoListScreen
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoScreen
import edu.ucne.registrotecnicos.presentacion.tecnicos.TecnicoViewModel
import edu.ucne.registrotecnicos.presentacion.ticket.TicketListScreen
import edu.ucne.registrotecnicos.presentacion.ticket.TicketScreen
import edu.ucne.registrotecnicos.presentacion.ticket.TicketViewModel
import edu.ucne.registrotecnicos.presentacion.usuario.UsuarioListScreen
import edu.ucne.registrotecnicos.presentacion.usuario.UsuarioScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import edu.ucne.registrotecnicos.presentacion.usuario.UsuarioViewModel
import kotlinx.coroutines.launch



@Composable
fun TecnicosNavHost(
    navHostController: NavHostController,
    tecnicoViewModel: TecnicoViewModel,
    ticketViewModel: TicketViewModel,
    enfermedadViewModel : EnfermedadViewModel = hiltViewModel(),
    mensajeViewModel: MensajeViewModel = hiltViewModel(),
    usuarioViewModel: UsuarioViewModel = hiltViewModel()
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
                },
                goToEnfermedad = {
                    navHostController.navigate("EnfermedadList")
                },
                goToUsuario = {
                    navHostController.navigate("UsuarioList")
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

        composable("EnfermedadList") {
            val uiState by enfermedadViewModel.uiState.collectAsState()

            EnfermedadListScreen(
                viewModel = enfermedadViewModel,
                goToEnfermedad = { id ->
                    navHostController.navigate("Enfermedad/$id")
                },
                onDrawer = {
                    navHostController.popBackStack()
                },
                enfermedadList = uiState.enfermedades,
                onEdit = { enfermedad ->
                    navHostController.navigate("Enfermedad/${enfermedad.enfermedadId}")
                },
                onCreate = {
                    navHostController.navigate("Enfermedad/0")
                },
                onDelete = { enfermedad ->
                    enfermedad.enfermedadId?.let { enfermedadViewModel.deleteEnfermedad(it) }
                },
                onMessage = {

                }
            )
        }

        composable("Enfermedad/{enfermedadId}") { backStackEntry ->
            val enfermedadIdParam = backStackEntry.arguments?.getString("enfermedadId")
            val enfermedadId = enfermedadIdParam?.toIntOrNull() ?: 0
            val enfermedad = enfermedadViewModel.getEnfermedadById(enfermedadId)

            EnfermedadScreen(
                enfermedad = enfermedad,
                onSave = { descripcion, monto ->
                    val nuevaEnfermedad = EnfermedadDto(
                        enfermedadId = enfermedad?.enfermedadId,
                        descripcion = descripcion,
                        monto = monto
                    )
                    enfermedadViewModel.saveEnfermedad(nuevaEnfermedad)
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }


        composable("UsuarioList") {
            val usuarioViewModel: UsuarioViewModel = hiltViewModel()
            val uiState by usuarioViewModel.uiState.collectAsStateWithLifecycle()

            UsuarioListScreen(
                viewModel = usuarioViewModel,
//                usuario = uiState.usuarios,
                goToUsuario = { id ->
                    navHostController.navigate("Usuario/$id")
                },
                onCreate = {
                    navHostController.navigate("Usuario/0")
                },
                onDelete = { usuario ->
                    usuarioViewModel.deleteUsuario(usuario)
                },
                onRefresh = {
                    usuarioViewModel.getUsuarios()
                }
            )
        }


        composable("Usuario/{usuarioId}") { backStackEntry ->
            val usuarioViewModel: UsuarioViewModel = hiltViewModel()
            val usuarioIdParam = backStackEntry.arguments?.getString("usuarioId")
            val usuarioId = usuarioIdParam?.toIntOrNull() ?: 0
            val usuario = usuarioViewModel.getUsuarioById(usuarioId)

            UsuarioScreen(
                usuario = usuario,
                onSave = { nombre, apellido, email ->
                    val usuarioEntity = UsuarioEntity(
                        id = usuario?.id ?: 0,
                        nombre = nombre,
                        apellido = apellido,
                        email = email
                    )
                    usuarioViewModel.viewModelScope.launch {
                        usuarioViewModel.saveUsuario(usuarioEntity)
                        navHostController.popBackStack()
                    }
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }

    }
}

