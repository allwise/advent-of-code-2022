import java.io.File

fun main() {
    fun part1(input: List<String>): String {
        val stacks = Stacking(input)
        return stacks.process()

    }

    fun part2(input: List<String>): String {
        val stacks = Stacking(input)
        return stacks.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")


    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}


class Stacking(val input: List<String>) {

    //     [D]
    // [N] [C]
    // [Z] [M] [P]
    //  1   2   3
    //
    fun process():String{
        val s =  input.indexOf("")
        println(s)
        val rows = ArrayDeque<String>(500)
        rows.addAll(input.subList(0,s))
        val commands = input.subList(s,input.size)
        val stacks =createStacks(rows)
        stacks.forEach{println(it)}
        commands.forEach { command ->stacks.handleCommand(command)}
        stacks.forEach{println(it)}
        println("solution 1:" + stacks.getTop())
        return stacks.getTop()

    }
    fun process2():String{
        val s =  input.indexOf("")
        println(s)
        val rows = ArrayDeque<String>(500)
        rows.addAll(input.subList(0,s))
        val commands = input.subList(s,input.size)
        val stacks =createStacks(rows)
        commands.forEach { command ->stacks.handleCommand2(command);stacks.forEach{println(it)}}
        stacks.forEach{println(it)}
        println("solution 2:" + stacks.getTop())
        return stacks.getTop()

    }


    fun createStacks(lines:ArrayDeque<String>): List<ArrayDeque<String>> {
        val stackline = lines.last().stackline()
        val stacks: List<ArrayDeque<String>> = stackline.map {  ArrayDeque<String>(50) }
        lines.reversed().map {  stacks.addList(it.stackline()) }
        return stacks

    }
    fun String.stackline():List<String>{
        return  this.chunked(4).map { println("chars of ($it)");it[1].toString().trim() }

    }

    fun  List<ArrayDeque<String>>.addList(strings:List<String>){
        strings.forEachIndexed{inx, string ->if(string.isNotEmpty())this[inx].addFirst(string)}
    }
    fun  List<ArrayDeque<String>>.handleCommand(command:String){
        if(command.isEmpty()){
            return
        }
        val (count,from,to) = ("(\\d+)".toRegex()).findAll(command).map{ it.value.toInt()}.toList()
        println(command)
        println("""moving $count from $from to $to """)
        repeat(count){move(this[from-1],this[to-1])}

    }
    fun  List<ArrayDeque<String>>.handleCommand2(command:String){
        if(command.isEmpty()){
            return
        }
        val (count,from,to) = ("(\\d+)".toRegex()).findAll(command).map{ it.value.toInt()}.toList()
        println(command)
        println("""moving $count from $from to $to """)
        val temp = ArrayDeque<String>(20)
        repeat(count){move(this[from-1],temp)}
        repeat(count){move(temp,this[to-1])}

    }

    fun move(from:ArrayDeque<String>,to:ArrayDeque<String>){
        to.addFirst(from.removeFirst())
    }

    fun List<ArrayDeque<String>>.getTop():String{
        return this.map{it.first()}.joinToString("" )
    }



}
