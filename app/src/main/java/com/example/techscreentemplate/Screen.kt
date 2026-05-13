package com.example.techscreentemplate

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

private val CELL_SIZE = 24.dp

private fun CardinalDirection.toArrow() = when (this) {
    CardinalDirection.N -> "↑"
    CardinalDirection.E -> "→"
    CardinalDirection.S -> "↓"
    CardinalDirection.W -> "←"
}

@Composable
fun Screen(
    viewModel: ScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Grid(state)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = state.onMoveClick) { Text("Move") }
            Button(onClick = state.onTurnClick) { Text("Turn") }
        }
    }
}

@Composable
private fun Grid(state: ScreenUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        for (row in 0 until 10) {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                for (col in 0 until 10) {
                    val isActive = col == state.x && row == state.y
                    val isObstacle = state.obstacles.contains(Coordinate(col, row))
                    Cell(isActive = isActive, isObstacle = isObstacle, label = if (isActive) state.direction.toArrow() else "")
                }
            }
        }
    }
}

@Composable
private fun Cell(isActive: Boolean, isObstacle: Boolean, label: String) {
    val borderColor = when {
        isActive -> Color.Blue
        isObstacle -> Color.DarkGray
        else -> Color.LightGray
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(CELL_SIZE)
            .border(1.dp, borderColor)
            .padding(2.dp)
    ) {
        when {
            isActive -> Text(text = label, fontSize = 18.sp, textAlign = TextAlign.Center, color = Color.Blue)
            isObstacle -> Text(text = "■", fontSize = 14.sp, textAlign = TextAlign.Center, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Grid(state = ScreenUiState(x = 3, y = 4, direction = CardinalDirection.E, obstacles = ScreenViewModel.OBSTACLES))
}
