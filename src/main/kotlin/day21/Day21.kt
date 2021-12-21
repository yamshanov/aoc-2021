package day21

class Player(start: Int) {
    private var position = start
    var score = 0
        private set
    val won: Boolean
        get() = score >= 1000

    fun rollAndMove(die: DeterministicDie) {
        val moves = die.take(3).sum()
        position = (position + moves - 1) % 10 + 1
        score += position
    }
}

class DeterministicDie : Sequence<Int> {
    var rolledTimes = 0
        private set
    private var nextValue = 1

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {
        override fun hasNext(): Boolean = true

        override fun next(): Int {
            val result = nextValue
            nextValue = if (nextValue == 100) 1 else nextValue + 1
            rolledTimes++

            return result
        }
    }
}

fun main() {
    val player1 = Player(8)
    val player2 = Player(6)
    val die = DeterministicDie()

    println(part1(player1, player2, die))
}

fun part1(player1: Player, player2: Player, die: DeterministicDie): Int {
    while (true) {
        player1.rollAndMove(die)
        if (player1.won) break
        player2.rollAndMove(die)
        if (player2.won) break
    }
    val loser = if (player1.won) player2 else player1

    return loser.score * die.rolledTimes
}
