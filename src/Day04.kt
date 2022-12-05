import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        val areas= Areas(input)
        return areas.process()

    }

    fun part2(input: List<String>): Int {
        val areas= Areas(input)
        return areas.processContains()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}


class Areas(val input:List<String>) {
    //Lines 4-6,5-9

    fun process():Int{
        val rows= input.map { line -> line.split(",").map{ range -> range.split("-").toPair()}}
        //[(2, 4), (6, 8)]
        val number =  rows.map { row-> ((row.first().toInt() within row.last().toInt()) || (row.last().toInt() within row.first().toInt()))   }.count {it}
        println ("within $number")
        return number


    }
    fun processContains():Int{
        var count = 0
        val rows= input.map { line -> line.split(",").map{ range -> range.split("-").toPair()}}
        //[(2, 4), (6, 8)]
        val nrContains = rows.map { row-> ((row.first().toInt() contains row.last().toInt()) || (row.last().toInt() contains row.first().toInt()))   }.count {it}

        println ("contains $nrContains")
    return nrContains

    }

    fun List<String>.toPair(): Pair<String, String> {
        return Pair(this.first(),this.last())
    }

    fun Pair<String,String>.toInt():Pair<Int,Int>{
        return this.first.toInt() to this.second.toInt()
    }

    infix fun Pair<Int,Int>.within(range1:Pair<Int,Int>):Boolean{
        if(this.first.isBetween(range1.first,range1.second) && this.second.isBetween(range1.first,range1.second))
            return true
        return false
    }

    infix fun Pair<Int,Int>.contains(range1:Pair<Int,Int>):Boolean{
        if(this.first.isBetween(range1.first,range1.second) ||this.second.isBetween(range1.first,range1.second))
            return true
        return false
    }
    fun Int.isBetween(start:Int, finish:Int):Boolean{
        return this>=start && this<=finish
    }
}
