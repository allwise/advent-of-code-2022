
fun main() {

        fun part1(input: List<String>): Int {
            val mal = DayMal(input)
            val res= mal.process()
            println("res=$res")
            return res
        }

        fun part2(input: List<String>): Int {

            val mal = DayMal(input)
            val res= mal.process2()
            println("res=$res")
            return res

        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)


    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

class DayMal(val input:List<String>){

    fun process():Int{
        return 0
    }

    fun process2():Int{
        return 0
    }
}

