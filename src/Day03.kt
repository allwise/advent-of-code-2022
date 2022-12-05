import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
     val rucksacks = Rocksacks(input)
       return rucksacks.process()
    }

    fun part2(input: List<String>): Int {
        val rucksacks = Rocksacks(input)
        return rucksacks.processGroups()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}



class Rocksacks(val input:List<String>) {
    //val input_test = File("C:\\Users\\kimha\\Downloads\\rucksack_test.txt").readLines()

    fun process(): Int {
        val s = input.map{ stringy ->
            val splitLine = stringy.chunked(stringy.length/2)
            findAllSame(splitLine[0],splitLine[1]).first().getValue()}.sum()

        println(s)
        return s
    }

    fun processGroups(): Int {
        val s = input.chunked(3)
        val g = s.map{findAllSame(findAllSame(it[0],it[1]), it[2]).first().getValue()}.sum()
        println("sum " +g)
        return g
    }

    fun findSame(str1:String,str2:String): Char {
        //println("str1 "+str1)
        for(cha in str1.toCharArray()){
            if (str2.contains(cha))
                return cha
        }
        return ' '


    }

    fun findAllSame(str1:String,str2:String): String {
        //  println("str1 "+str1.toCharArray().filter { str2.contains(it) }.distinct().joinToString())
        return str1.toCharArray().filter { str2.contains(it) }.distinct().joinToString()



    }

    fun Char.getValue():Int{
        if(this in ('a'..'z')){
            // println("small " +this + " " + this.code + " " + (this - 'a'+1))
            return (this -'a'+1)

        }
        if(this in ('A'..'Z')){
            // println("big " +this + " " + this.code + " "+ (this -'A'+27))
            return (this -'A'+27)
        }
        return -1
    }


}