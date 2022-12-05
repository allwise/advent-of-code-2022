import RockPaperScissors.Play.*
import RockPaperScissors.Result.*
import java.io.File

fun main() {

    fun part1(input: List<String>): Int {
        val rpc = RockPaperScissors(input)
        return rpc.quick1()
        println(rpc.quick1())
    }

    fun part2(input: List<String>): Int {
        val rpc = RockPaperScissors(input)
        return rpc.quick2()
        println(rpc.quick2())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
class RockPaperScissors(val input:List<String>) {
    var count = 0
    var sum = 0


    fun quick1(): Int {
        val mapit = mapOf(
            "A X" to 4,
            "A Y" to 8,
            "A Z" to 3,
            "B X" to 1,
            "B Y" to 5,
            "B Z" to 9,
            "C X" to 7,
            "C Y" to 2,
            "C Z" to 6
        )
        val sum = input.map { mapit[it] ?: 1 }.sum()
        return sum
    }

    fun quick2(): Int {
        val mapit = mapOf(
            "A X" to 3,
            "A Y" to 4,
            "A Z" to 8,
            "B X" to 1,
            "B Y" to 5,
            "B Z" to 9,
            "C X" to 2,
            "C Y" to 6,
            "C Z" to 7
        )

        val sum = input.map { mapit[it] ?: 1 }.sum()
        return sum
    }


    fun sumrounds() {
        val res = input.map { getGame(it) } .map { (calculateRound(it)) }.sum()
        val res2 = input.map { getGame2(it) } .map { (calculateRound(it)) }.sum()


        println("res is " + res)
        println("res 2 is " + res2)


    }

    fun calculateRound(round: Pair<Play, Play>): Int {
        count++
        val shapeValue = shapeValue(round.second)
        val winnings = gameResult(round)
        //println("""$count [opponent: ${round.first}, mine:${round.second}]($shapeValue,$winnings)""")
        return shapeValue + winnings

    }

    fun gameResult(round: Pair<Play, Play>): Int {
        if (round.first == round.second)
            return 3
        if (round.first == ROCK) {
            if (round.second == PAPER)
                return 6
            else
                return 0
        } else if (round.first == PAPER) {
            if (round.second == SCISSORS)
                return 6
            else
                return 0
        } else if (round.first == SCISSORS) {
            if (round.second == ROCK)
                return 6
            else
                return 0
        }

        return 0

    }

    fun convert(action: String): Play {
        return when (action) {
            "A" -> ROCK
            "B" -> PAPER
            "C" -> SCISSORS
            "X" -> ROCK
            "Y" -> PAPER
            "Z" -> SCISSORS

            else -> ROCK


        }
    }

    fun convertRes(action: String): Result {
        return when (action) {
            "X" -> LOSE
            "Y" -> DRAW
            "Z" -> WIN
            else -> LOSE
        }


    }

    fun getGame(game: String): Pair<Play, Play> {
        //println("game " + game)
        val opponenet: Play = convert(game.split(" ").first())
        val mine: Play = convert(game.split(" ").last())
        //println("""[opponent: $opponenet, mine:$mine]""")
        return opponenet to mine
    }

    fun getGame2(game: String): Pair<Play, Play> {
        //println("game " + game)
        val opponenet: Play = convert(game.split(" ").first())
        val result: Result = convertRes(game.split(" ").last())
        val mine = getPlay(opponenet, result)
        //println("""[opponent: $opponenet, mine:$mine]""")
        return opponenet to mine
    }


    fun getPlay(opponent: Play, result: Result): Play {
        return when (result) {
            DRAW -> opponent
            WIN -> {
                when (opponent) {
                    SCISSORS -> ROCK
                    ROCK -> PAPER
                    PAPER -> SCISSORS
                }
            }
            LOSE -> when (opponent) {
                SCISSORS -> PAPER
                ROCK -> SCISSORS
                PAPER -> ROCK
            }
        }


    }


    fun shapeValue(mine: Play): Int {
        // X - Rock 1
        // Y - Paper 2
        // Z - Scissors 3
        return when (mine) {
            ROCK -> 1
            PAPER -> 2
            SCISSORS -> 3
            else -> 0
        }
    }


    enum class Play{
        ROCK,
        PAPER,
        SCISSORS

    }

    enum class Result{
        WIN,
        DRAW,
        LOSE
    }
}