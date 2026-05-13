package com.example.techscreentemplate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

enum class CardinalDirection { N, E, S, W }

data class Coordinate(val x: Int, val y: Int)

data class ScreenUiState(
    val x: Int = 0,
    val y: Int = 0,
    val direction: CardinalDirection = CardinalDirection.E,
    val obstacles: List<Coordinate> = emptyList(),
    val onMoveClick: () -> Unit = {},
    val onTurnClick: () -> Unit = {},
)

@HiltViewModel
class ScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ScreenUiState())
    val state: StateFlow<ScreenUiState> = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            obstacles = OBSTACLES,
            onMoveClick = ::onMoveClick,
            onTurnClick = ::onTurnClick,
        )
    }

    private fun onMoveClick() {
        val current = _state.value
        val newX = current.x + when (current.direction) {
            CardinalDirection.E -> 1
            CardinalDirection.W -> -1
            else -> 0
        }
        val newY = current.y + when (current.direction) {
            CardinalDirection.S -> 1
            CardinalDirection.N -> -1
            else -> 0
        }
        val blocked = newX !in 0 until GRID_SIZE
                || newY !in 0 until GRID_SIZE
                || OBSTACLES.contains(Coordinate(newX, newY))
        if (blocked) {
            onTurnClick()
        } else {
            _state.value = current.copy(x = newX, y = newY)
        }
    }

    private fun onTurnClick() {
        _state.value = _state.value.copy(
            direction = when (_state.value.direction) {
                CardinalDirection.N -> CardinalDirection.E
                CardinalDirection.E -> CardinalDirection.S
                CardinalDirection.S -> CardinalDirection.W
                CardinalDirection.W -> CardinalDirection.N
            }
        )
    }

    companion object {
        private const val GRID_SIZE = 10
        val OBSTACLES = listOf(
            Coordinate(2, 3),
            Coordinate(5, 1),
            Coordinate(7, 6),
            Coordinate(4, 8),
            Coordinate(8, 4),
        )
    }
}
