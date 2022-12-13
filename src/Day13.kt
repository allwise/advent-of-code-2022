
fun main() {
    fun part1(input: List<String>): Int {
        val distress = Distress(input)
        val res= distress.process()
        println("res=$res")
        return res
    }

    fun part2(input: List<String>): Int {

        val distress = Distress(input)
        val res= distress.process2()
        println("res=$res")
        return res

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)


    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

class Distress(val input:List<String>){
    val packets:List<Pair<String,String>> = input.chunked(3).map{ it[0] to it[1]}
    fun process():Int{
        return packets.mapIndexed { idx,it ->
            println("== Pair ${idx+1} ==")

           if(outOfOrder(it)){
            println("Pair ${idx+1} is out of order")

              0
           } else{
               println("Pair ${idx+1} is in order")
               idx+1
           }

        }.sum()
            return 0

    }

    fun process2():Int{
        val key1 = listOf(listOf(2))
        val key2 = listOf(listOf(6))
        val signalList = listOf(key1,key2)+input.filterNot { it.trim().isEmpty() }.map(::processString).unzip().first
        println("SignnalList: ")
        val listComparator = Comparator{left: List<Any>, right:List<Any> -> compare(left,right)}
        val sortedList = signalList.sortedWith( listComparator)
        val index1= sortedList.indexOf(key1)+1.also (::println)
        val index2= sortedList.indexOf(key2)+1.also (::println)

        sortedList.forEach(::println)
       /* return packets.mapIndexed { idx,it ->
            println("== Pair ${idx+1} ==")

            if(outOfOrder(it)){
                println("Pair ${idx+1} is out of order")

                0
            } else{
                println("Pair ${idx+1} is in order")
                idx+1
            }

        }.sum()*/
        return index1*index2

    }

    fun compare(first:List<Any>,second:List<Any>):Int{

        val res = findDiff(first,second)
        if(res.contains("not"))
            return 1
        return -1




    }

    fun outOfOrder(signals:Pair<String,String>):Boolean{
        val first = processString(signals.first).first
        val second = processString(signals.second).first
        val res = findDiff(first,second)
        if(res.contains("not"))
            return true
        return false
        //println("first: $first")
        //println("second: $second")




    }

    fun findDiff(left: Any?, right: Any?): String {
       println("Compare $left vs $right")
        if(left is Int && right is Int){
            if(left<right)
                return "Left side is smaller, so inputs are in the right order"
            if(left>right)
                return "Right side is smaller, so inputs are not in the right order"
            return "next"
        }
        if(left is List<*> && right is List<*>){
            for( i in 0 until left.size) {
                if(right.size<=i){
                   return  "Right side ran out of items, so inputs are not in the right order"

                }
                val res = findDiff(left[i],right[i])
                if(res!="next"){
                    return res
                }

            }
            if(left.size==right.size){
                return "next"
            }

            return  "Left side ran out of items, so inputs are in the right order"

        //  return left.forEach{ idx, it -> findDiff(it,right.indexOf(idx)) }.toString()
        }
        if(left is List<*> && right is Int) {
            val rightList = listOf(right)
            println("Mixed types; convert right to $rightList and retry comparison")
          return findDiff(left,rightList)
        }
        if(left is Int && right is List<*>) {
            val leftList = listOf(left)
            println("Mixed types; convert left to $leftList and retry comparison")
            return findDiff(leftList,right)
        }
    return ""
    }

    fun processString(line:String): Pair<List<Any>,String> {
        var s = line.trim().let {it.substring(1)  }
        val output= mutableListOf<Any>()
       while(s.length>0) {
           when (s.first()) {
               '[' -> {
                   val o = processString(s)
                   output.add(o.first)
                   s = o.second

               }
               ',' -> s = s.drop(1)
               ']' -> {
                   s = s.drop(1)
                   return (output to s)
               }
               else ->{
                   var integer = ""
                   while (s.first().isDigit()) {
                       integer += s.first()
                       s = s.drop(1)
                   }
                   output.add(integer.toInt())
               }
           }
       }
       return (output to s)
    }

    fun handleList(listSignal:String):List<Any>{
        //[1,[2],[3,4]]

        return TODO()

    }

}

