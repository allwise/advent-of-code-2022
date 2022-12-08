
fun main() {
    fun part1(input: List<String>): Int {
        val signals = Signals(input)
        return signals.process()

    }

    fun part2(input: List<String>): Int {
        val signals = Signals(input)
        return signals.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)


    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

class Signals(val input:List<String>){
    fun process():Int{
       val a:Int = input.map { str: String ->
           findUnique(str,4)
        }.single()

        return a

    }


    fun process2():Int{
        val a:Int = input.map { str: String ->
            findUnique(str,14)
        }.single()

        return a
    }

    private fun findUnique(str: String, size:Int):Int {
        str.forEachIndexed { idx: Int, _ ->
            if (str.subSequence(idx, idx + size).toSet().size == size) {
                println("""${str.subSequence(idx, idx + size)} (${idx + size})""")
                return (idx + size)
            }
        }
        return -1
    }

}

