package edu.ucne.registrotecnicos.presentacion.usuario

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.data.local.entities.UsuarioEntity

@Composable
fun UsuarioListScreen(
    viewModel: UsuarioViewModel = hiltViewModel(),
    goToUsuario: (Int) -> Unit,
    onCreate: () -> Unit,
    onDelete: (UsuarioEntity) -> Unit,
    onRefresh: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UsuarioListBodyScreen(
        uiState = uiState,
        goToUsuario = goToUsuario,
        onCreate = onCreate,
        onDelete = onDelete,
        onRefresh = onRefresh
    )
}

@Composable
fun UsuarioListBodyScreen(
    uiState: UsuarioUiState,
    goToUsuario: (Int) -> Unit,
    onCreate: () -> Unit,
    onDelete: (UsuarioEntity) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lista de Usuarios",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color(0xFF311B92),
                        fontWeight = FontWeight.Bold
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
                    onClick = onCreate,
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar usuario")
                }
            }
        },
        containerColor = Color(0xFFF5F5F5),
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.usuarios) { usuario ->
                    UsuarioCard(
                        item = usuario,
                        goToUsuario = goToUsuario,
                        onDelete = onDelete
                    )
                }
            }
        }
    )
}

@Composable
fun UsuarioCard(
    item: UsuarioEntity,
    goToUsuario: (Int) -> Unit,
    onDelete: (UsuarioEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToUsuario(item.id) },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Usuario #${item.id}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF311B92)
                    )
                )
                Text(
                    text = "${item.nombre} ${item.apellido}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.email,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
            Row {
                IconButton(onClick = { goToUsuario(item.id) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF4CAF50))
                }
                IconButton(onClick = { onDelete(item) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}
