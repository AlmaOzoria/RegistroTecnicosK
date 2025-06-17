package edu.ucne.registrotecnicos.presentacion.enfermedad

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.presentacion.remote.dto.EnfermedadDto


@Composable
fun EnfermedadListScreen(
    viewModel: EnfermedadViewModel = hiltViewModel(),
    goToEnfermedad: (Int) -> Unit,
    onDrawer: () -> Unit,
    enfermedadList: List<EnfermedadDto>,
    onEdit: (EnfermedadDto) -> Unit,
    onCreate: () -> Unit,
    onDelete: (EnfermedadDto) -> Unit,
    onMessage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getEnfermedades()
    }

    EnfermedadListBodyScreen(
        uiState = uiState,
        goToEnfermedad = goToEnfermedad,
        onDrawer = onDrawer,
        onRefresh = viewModel::getEnfermedades
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnfermedadListBodyScreen(
    uiState: EnfermedadUiState,
    goToEnfermedad: (Int) -> Unit,
    onDrawer: () -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, bottom = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lista de Enfermedades",
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF311B92),
                        textAlign = TextAlign.Center
                    )
                )
            }
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = onRefresh,
                    containerColor = Color(0xFF03DAC5),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                }
                FloatingActionButton(
                    onClick = { goToEnfermedad(0) },
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar enfermedad")
                }
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF5F5F5), Color(0xFF00BCD4))
                    )
                )
        ) {
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.enfermedades) { enfermedad ->
                    EnfermedadCard(enfermedad, goToEnfermedad)
                }
            }
        }
    }
}

@Composable
private fun EnfermedadCard(
    item: EnfermedadDto,
    goToEnfermedad: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                item.enfermedadId?.let { id ->
                    goToEnfermedad(id)
                }
            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Enfermedad #${item.enfermedadId}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF311B92)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.descripcion ?: "Sin descripción",
                        style = TextStyle(fontSize = 16.sp, color = Color.White)
                    )

                    Text(
                        text = "RD$${item.monto}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                    ))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { item.enfermedadId?.let { id -> goToEnfermedad(id) } },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF43A047)
                    )
                }

                IconButton(
                    onClick = { item.enfermedadId?.let { id -> goToEnfermedad(id) } },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFEA0000)
                    )
                }
            }
        }
    }
    }
}