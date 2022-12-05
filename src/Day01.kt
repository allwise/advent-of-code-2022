import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val calories = Calories(input).countCalories()
        return calories
    }

    fun part2(input: List<String>): Int {
        val calories = Calories(input).countTop3Calories()
        return calories

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println("""Biggest Calorie value  ${part1(input)} C. """)
    println("Sum of 3 biggest: $${part2(input)}")
}


class Calories(private val input: List<String>) {
    fun countTop3Calories(): Int {
        var calories: MutableMap<Int, Int> = mutableMapOf()
        var count = 1
        var currentSum = 0
        input.forEach{
            if (it.trim() == "") {
                calories.put(count, currentSum)
                count++
                currentSum = 0
            } else {
                currentSum += it.toInt()
            }

        }
        calories.put(count, currentSum)
        return  calories.toList().sortedByDescending { it.second }.take(3)
            .sumOf { it.second }
    }

    fun countCalories(): Int {
        var count = 1
        var biggestElf = 0
        var biggestValue = 0
        var currentSum = 0
        input.forEach {
            if(it.trim()=="" ){
                if(currentSum>biggestValue) {
                    biggestElf = count
                    biggestValue = currentSum
                }
                count++
                currentSum=0
            }else{
                val value = it.toInt()
                currentSum+=value

            }
        }
        return  biggestValue



    }
}
