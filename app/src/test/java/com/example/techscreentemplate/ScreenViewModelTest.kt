package com.example.techscreentemplate

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ScreenViewModelTest {

    private lateinit var viewModel: ScreenViewModel

    private val state get() = viewModel.state.value
    private fun move() = state.onMoveClick()
    private fun turn() = state.onTurnClick()

    @Before
    fun setup() {
        viewModel = ScreenViewModel()
    }

    // region Turn

    @Test
    fun `turn right cycles through all directions and returns to start`() {
        // default starts at E
        assertEquals(CardinalDirection.E, state.direction)
        turn(); assertEquals(CardinalDirection.S, state.direction)
        turn(); assertEquals(CardinalDirection.W, state.direction)
        turn(); assertEquals(CardinalDirection.N, state.direction)
        turn(); assertEquals(CardinalDirection.E, state.direction)
    }

    // endregion

    // region Move

    @Test
    fun `move east increments x`() {
        // default direction is E
        move()
        assertEquals(1, state.x)
        assertEquals(0, state.y)
    }

    @Test
    fun `move south increments y`() {
        turn() // E -> S
        move()
        assertEquals(0, state.x)
        assertEquals(1, state.y)
    }

    @Test
    fun `move west decrements x`() {
        setDirection(CardinalDirection.E); move() // (1,0,E)
        setDirection(CardinalDirection.W)
        move()
        assertEquals(0, state.x)
        assertEquals(0, state.y)
        assertEquals(CardinalDirection.W, state.direction)
    }

    @Test
    fun `move north decrements y`() {
        setDirection(CardinalDirection.S); move() // (0,1,S)
        setDirection(CardinalDirection.N)
        move()
        assertEquals(0, state.x)
        assertEquals(0, state.y)
        assertEquals(CardinalDirection.N, state.direction)
    }

    // endregion

    // region Boundary — turns instead of moving off-grid

    @Test
    fun `hitting east wall turns instead of moving`() {
        repeat(9) { move() } // move to x=9
        assertEquals(9, state.x)
        move() // would go to x=10 — should turn
        assertEquals(9, state.x)
        assertEquals(CardinalDirection.S, state.direction)
    }

    @Test
    fun `hitting south wall turns instead of moving`() {
        setDirection(CardinalDirection.S)
        repeat(9) { move() } // move to y=9
        assertEquals(9, state.y)
        move() // would go to y=10 — should turn
        assertEquals(9, state.y)
        assertEquals(CardinalDirection.W, state.direction)
    }

    @Test
    fun `hitting west wall turns instead of moving`() {
        setDirection(CardinalDirection.W)
        move() // at (0,0,W) — already at boundary, should turn
        assertEquals(0, state.x)
        assertEquals(CardinalDirection.N, state.direction)
    }

    @Test
    fun `hitting north wall turns instead of moving`() {
        setDirection(CardinalDirection.N)
        move() // at (0,0,N) — already at boundary, should turn
        assertEquals(0, state.y)
        assertEquals(CardinalDirection.E, state.direction)
    }

    // endregion

    // region Helpers

    private fun setDirection(target: CardinalDirection) {
        while (state.direction != target) turn()
    }

    // endregion
}
