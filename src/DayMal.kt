
fun main() {
    fun part1(input: List<String>): String {
        val stacks = Stacking(input)
        return stacks.process()

    }

    fun part2(input: List<String>): String {
        val dayMal = DayMal(input)
        return dayMal.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")


    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

class DayMal(val input:List<String>){
    fun process():String{
        return ""
    }

    fun process2():String{
        return ""
    }
}

