import Direction.*
import kotlin.math.abs

fun main()  {
    fun part1(input: List<String>): Int {
        val hillClimb = HillClimb(input)
        val res =  hillClimb.process()
        println("res1: $res")
        return res

    }

    fun part2(input: List<String>): Int {
        val hillClimb = HillClimb(input)
        val res =  hillClimb.process2()
        println("res2: $res")
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)


    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

class HillClimb(val input:List<String>){
    var startpos=(0 to 0)
    var endPos =(0 to 0)
    var endFound=false

    var smallest =1000
    var permutations =0
   var heightMap :List<List<Int>> = input.mapIndexed { x, line -> line.toCharArray()
       .mapIndexed{y,cha ->
           if(cha=='S') {
               startpos=(x to y)
               0
           }else if(cha=='E'){
               endPos=(x to y)
               ('z'-'a')
           }else {
               cha-'a'
           }

       }

   }
    var distanceMap:List<MutableList<Int>> =resetMovement()





    fun process():Int{
        distanceMap[endPos.first][endPos.second]=1000
        //heightMap.forEach(::println)
     //   distanceMap.forEach(::println)
        println( "$startpos -> $endPos")

        var e: List<Movement>? = (walk(startpos, 0))

        println("minmoves for $startpos -> $endPos  ${e?.size}")
        println(smallest)
        distanceMap.forEach(::println)
        println()
        println("permutatons $permutations")

        return smallest

    }

    fun resetMovement(): List<MutableList<Int>> {
        val distanceMap = heightMap.map{ it.map{-1}.toMutableList() }.toList()
        distanceMap[endPos.first][endPos.second]=1000
        return distanceMap
    }


    fun process2():Int{
        distanceMap[endPos.first][endPos.second]=1000
        heightMap.forEachIndexed{x, row ->
            row.forEachIndexed{y, height ->
            if(height==0){
                distanceMap = resetMovement()
                startpos=(x to y)
                var e: List<Movement>? = (walk(startpos, 0))
                distanceMap.forEach(::println)
                println()
                println("minmoves for $startpos -> $endPos  ${e?.size}")
                if(e != null && smallest > e.size ){
                   smallest=e.size
                }

            }
            }

        }

        var e: List<Movement>? = (walk(startpos, 0))
        println("minmoves for $startpos -> $endPos  ${e?.size}")
        println("smallest: $smallest")

        println("permutatons $permutations")

        return smallest
    }

      fun walk(currentPos:Pair<Int,Int>,moves:Int):List<Movement>?{
          permutations++
          /*if(permutations%1000000==0){
              distanceMap.forEach(::println)
              println()
          }*/
       /* if(moves>=smallest || distanceMap[currentPos.first][currentPos.second]<moves){
           // println("canceling at size ${moves.size} ${moves.map { it.direction }.joinToString()}")
            return null
        }*/
          if(isShorter(currentPos, moves)){
              distanceMap[currentPos.first][currentPos.second]=moves
          }else{
              return null
          }

        val validMoves = listOf(currentPos.move(RIGHT),currentPos.move(UP),currentPos.move(DOWN), currentPos.move(LEFT))
            .filterNotNull()
            .filter{isShorter(it.toPos, moves+1) }
            //.filterNot { moves.map{it.fromPos }.contains(it.toPos) }
            .sortedBy {  abs(endPos.first-it.toPos.first) + abs(endPos.second-it.toPos.second) }

          if(isEnd(currentPos)){
              println("found an end after ${moves} moves ")
              if(moves<smallest){
                  println("found smaller! $smallest -> $moves")
                  smallest=moves
              }
              endFound=true
              return emptyList<Movement>()

          } //Cannot continue from this point
            if(validMoves.isEmpty()){
                distanceMap[currentPos.first][currentPos.second]=moves
           // println("no more valid moves after ${moves.size} moves ")
            return null
        }else{
           // launch{
            val best = validMoves.map{
                walk(it.toPos, moves + 1)?.plus(it)
            }
                .filterNotNull()
                .sortedBy { it.size }
                .firstOrNull()
         //
            return best

        }

    }

    fun isEnd(currentPos:Pair<Int,Int>):Boolean{
        if(currentPos.first==endPos.first && currentPos.second==endPos.second ){
            println("found End $currentPos")
            return true
        }

        return false
    }

    fun  Pair<Int,Int>.move(direction:Direction):Movement?{
        val currentPos= this
        val toPos =
            when(direction){
                UP -> currentPos.first-1 to currentPos.second
                DOWN -> currentPos.first+1 to currentPos.second
                LEFT -> currentPos.first to currentPos.second-1
                RIGHT -> currentPos.first to currentPos.second+1

            }
        if(checkDirection(currentPos,toPos)){
            return Movement(currentPos,toPos,direction)
        }

        return null

    }
    fun isShorter(currentPos:Pair<Int,Int>, distance:Int): Boolean {
       val shortestDistance =distanceMap[currentPos.first][currentPos.second]
        if(shortestDistance==-1 || distance<shortestDistance){
           return true
       }
        return false
    }

    fun checkDirection(currentPos:Pair<Int,Int>, toPos:Pair<Int,Int>):Boolean{
        //if outside map
        if( toPos.first<0 || toPos.first>=heightMap.size || toPos.second<0 || toPos.second>=heightMap.first().size){
            return false
        }
        //if dist>1
        if( abs(currentPos.first-toPos.first)>1 || abs(currentPos.second-toPos.second)>1){
            return false
        }
        val from = heightMap[currentPos.first][currentPos.second]
        val to = heightMap[toPos.first][toPos.second]

        if(to>from+1 ){
            return false
        }
        return true
    }

}

data class Movement(val fromPos:Pair<Int,Int>,val toPos:Pair<Int,Int>, val direction:Direction )

enum class Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT,
    //END

}

