package day21

data class Player(val position: Int, val rolling: Boolean, val score: Int = 0, val rolls: List<Int> = emptyList()) {
    fun roll(value: Int): Player = if (rolls.size < 2) {
        Player(position, true, score, rolls + value)
    } else {
        val moves = rolls.sum() + value
        val newPosition = (position + moves - 1) % 10 + 1

        Player(newPosition, false, score + newPosition, emptyList())
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

class Game1(player1: Player, player2: Player) {
    private val scoreToWin = 1000
    val die = DeterministicDie()
    var player1 = player1
        private set
    var player2 = player2
        private set

    fun play(): Int {
        while (true) {
            repeat(3) { player1 = player1.roll(die.first()) }
            if (player1.score >= scoreToWin) break
            repeat(3) { player2 = player2.roll(die.first()) }
            if (player2.score >= scoreToWin) break
        }
        val loser = if (player1.score < scoreToWin) player1 else player2

        return loser.score * die.rolledTimes
    }
}

class Game2(private val player1: Player, private val player2: Player) {
    private val scoreToWin = 21
    private val cache = HashMap<Pair<Player, Player>, Pair<Long, Long>>()

    fun play(): Long {
        val result = splitUniverse(player1, player2)
        return if (result.first > result.second) result.first else result.second
    }

    private fun splitUniverse(player1: Player, player2: Player): Pair<Long, Long> {
        val players = player1 to player2
        if (players in cache) return cache.getValue(players)

        if (player1.score >= scoreToWin) {
            val result = 1L to 0L
            cache[players] = result
            return result
        }
        if (player2.score >= scoreToWin) {
            val result = 0L to 1L
            cache[players] = result
            return result
        }

        val results = if (player1.rolling) {
            val cases = (1..3).map { player1.roll(it) }
            val p2 = if (cases.first().rolling) player2 else player2.copy(rolling = true)
            cases.map { splitUniverse(it, p2) }
        } else {
            val cases = (1..3).map { player2.roll(it) }
            val p1 = if (cases.first().rolling) player1 else player1.copy(rolling = true)
            cases.map { splitUniverse(p1, it) }
        }
        val result = results.reduce { acc, pair -> acc.first + pair.first to acc.second + pair.second }
        cache[players] = result

        return result
    }
}

fun main() {
    val player1 = Player(8, true)
    val player2 = Player(6, false)

    println(Game1(player1, player2).play())
    println(Game2(player1, player2).play())
}
