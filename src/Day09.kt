import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val rope = Rope(input)
        return rope.process()

    }

    fun part2(input: List<String>): Int {
        val rope = Rope(input)
        return rope.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println( "part1 " +part1(testInput))
     println("part2 " + part2(testInput))
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)


    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

class Rope(val input:List<String>){

    fun process(): Int {
        var head = mutableListOf<Int>(0, 0)
        return handleChain(mutableListOf(head,head.copyTwo()))
    }

    fun process2():Int{
        var head = mutableListOf<Int>(0, 0)
        var chain = mutableListOf<MutableList<Int>>()
        repeat(10){chain.add(head.copyTwo())}
        //println(chain)
        return handleChain(chain)

    }


    fun handleChain( chain:MutableList<MutableList<Int>>):Int {
        var visitedPosistion = mutableListOf(chain.last().copyTwo())

        input.forEach { movement ->
        //    println(movement)
            val (move: String, count: String) = movement.split(" ")
            repeat(count.toInt()) {

                chain.forEachIndexed { idx, knot ->
                    if (idx == 0) {
                        move(move, knot)
                } else {
                    var head = chain[idx-1]
                    //println(movement)

                    //println("(${head[0]},${head[1]}) (${chain[idx + 1][0]},${chain[idx + 1][1]}) $move")
                    chain[idx] = follow(head, knot)


                    }
                }
               // visualize(chain)
                visitedPosistion.add(chain.last().copyTwo())
            }
          //  println(visitedPosistion.size)
           // println(visitedPosistion.toSet().size)
           // println(visitedPosistion)
        }
        return visitedPosistion.toSet().size
    }

    fun MutableList<Int>.copyTwo():MutableList<Int>{
        return  mutableListOf(this[0], this[1])

    }

    fun move(move:String, head:MutableList<Int>) {
        when(move) {
            "R" ->  head[0]++
            "L" ->  head[0]--
            "U" -> head[1]++
            "D" -> head[1]--


        }



    }

    fun visualize( chain:MutableList<MutableList<Int>>){
       //var plane = mutableListOf(MutableList<String>)
        for( i in 6 downTo  0) {
            for (j in 0 until 6) {
                var char="#"
                if(chain.contains(mutableListOf(j,i))) {
                    val idx=chain.indexOf(mutableListOf(j, i))
                    if(idx==0) {
                       char = "H"
                    }else {
                        char="$idx"
                    }
                }
                print(char)
                }
            println()
            }
        println()
        }


    fun follow(head:MutableList<Int>, tail:MutableList<Int>, ): MutableList<Int> {
        val diffX = head[0]-tail[0]
        val diffY = head[1]-tail[1]
        if(diffY==0 && abs(diffX)==2)
            tail[0]+=diffX/2
        else if(diffX==0 && abs(diffY)==2)
            tail[1]+=diffY/2
        else if(abs(diffX)+abs(diffY)>2){
            if(head[0]>tail[0]){
                tail[0]++
            }else{
                tail[0]--
            }
            if(head[1]>tail[1]){
                tail[1]++
            }else{
                tail[1]--
            }
}
       return tail
    }


}

