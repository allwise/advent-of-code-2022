
fun main() {
    fun part1(input: List<String>): Int {
        val treeHouse = Trees(input)
        return treeHouse.process()

    }

    fun part2(input: List<String>): Int {
        val dayMal = Trees(input)
        return dayMal.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)


    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

class Trees(val input:List<String>){
    val treeMap = buildMap(input)

    fun process():Int{
        var visible = 0
        treeMap.mapIndexed { rowIdx, row -> row
            .mapIndexed{colIdx, col ->
                val isVisible =isVisible(rowIdx,colIdx)
                if(isVisible)
                    visible++
                isVisible} }
       // s.forEach { println(it) }


        return visible
    }

    fun process2():Int{
        var biggestScenicScore=0
        treeMap.mapIndexed { rowIdx, row -> row
            .mapIndexed{colIdx, col ->
                val scenicScore =scenicScore(rowIdx,colIdx)
                if(scenicScore>biggestScenicScore)
                    biggestScenicScore=scenicScore
                scenicScore} }
       // s.forEach { println(it) }
        return biggestScenicScore
    }

    fun buildMap( input:List<String>): List<List<Int>> {
        val treemap = input.map {
                row -> row.chunked(1).map {it.toInt() }}
       // treemap.viewGrid()

        return treemap

    }

     fun  List<List<Int>>.viewGrid(){
         this.forEachIndexed{rowIdx,row -> row.forEachIndexed{colIdx,col -> print("[$col($rowIdx,$colIdx)] ") };println() }
     }

    //   1  2  3
    //   4  5  6
    //   7  8  9
    //

    fun isVisible(x:Int, y:Int): Boolean {
        val height = treeMap[x][y]
        var hiddenOver = false
        var hiddenBelow = false
        var hiddenLeft = false
        var hiddenRight = false

     for (i:Int in x-1 downTo 0 ){
         if(treeMap[i][y]>=height) {
             hiddenOver=true
             break
         }
        }
        for (i:Int in y-1 downTo 0 ){
            if(treeMap[x][i]>=height){
                hiddenLeft=true
                break
            }
        }
        for (i:Int in x+1 until  treeMap[x].size ){
            if(treeMap[i][y]>=height){
                hiddenBelow=true
                break
            }
        }
        for (i:Int in y+1 until treeMap.size ){
            if(treeMap[x][i]>=height){
                hiddenRight=true
                break
            }
        }
        if(hiddenOver && hiddenBelow && hiddenLeft && hiddenRight)
            return false
        return true


    }

    fun scenicScore(x:Int, y:Int): Int {
        val height = treeMap[x][y]
        var visibleOver = 0
        var visibleBelow = 0
        var visibleLeft = 0
        var visibleRight = 0

        for (i:Int in x-1 downTo 0 ){
            visibleOver++
            if(treeMap[i][y]>=height) {
                break
            }
        }
        for (i:Int in y-1 downTo 0 ){
            visibleLeft++
            if(treeMap[x][i]>=height){
                break
            }
        }
        for (i:Int in x+1 until  treeMap[x].size ){
            visibleBelow++
            if(treeMap[i][y]>=height){
                break
            }
        }
        for (i:Int in y+1 until treeMap.size ){
            visibleRight++
            if(treeMap[x][i]>=height){
                break
            }
        }
        return visibleOver*visibleBelow*visibleRight*visibleLeft


    }


}

