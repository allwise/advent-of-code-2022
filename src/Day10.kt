import kotlin.properties.Delegates

fun main() {
    fun part1(input: List<String>): Int {
        val processor = Signal(input)
        val res =  processor.process()
        println("res1: $res")
        return res

    }

    fun part2(input: List<String>): String {
        val processor = Signal(input)
        val res =  processor.process2()
        println("res2: $res")
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    //check(part2(testInput) == "MCD")


    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

class Signal(val input:List<String>){
    var x = 1
    var currentCommand=""
    var row = ""
    //The simple way to Listen to variable in KOTLIN
    var totalSignal=0
    private var ticker by Delegates.observable(0) { property, oldValue, newValue ->
        var position = newValue%40-1
        if(x in position-1 .. position+1){
            row +="#"
            print("#")
        }else {
            row +="."
            print(".")
        }
        if(newValue%40==20){
            totalSignal+=signalStrength()

        }
        if(newValue%40==0){
            row =""
        println()
        }
        if(newValue%240==0) {
            println()
        }
        val debug = """Start cycle   $newValue: begin executing $currentCommand
        During cycle  $newValue: CRT draws pixel in position $position
        Current CRT row: $row
        """
        //println (debug)
        //println("New Value $newValue")
        //println("Old Value $oldValue")
    }

    fun process():Int{
        input.forEach{
            currentCommand=it
            if(it.startsWith("addx"))
                addX(it.drop(5).toInt() )
            if(it.startsWith("noop")){
                noop()
            }
        }

        return totalSignal
    }

    fun signalStrength():Int{
        return ticker*x
    }

    fun addX(add:Int){
        noop()
        noop()
        x+=add
    }

    fun noop(){
        ticker+=1
    }

    fun process2():String{
        return ""
    }
}

