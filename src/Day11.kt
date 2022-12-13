
fun main() {
    fun part1(input: List<String>): Int {
        val monkeys = Monkeys(input)
        val res =  monkeys.process()
        println("res1: $res")
        return res

    }

    fun part2(input: List<String>): Long {
        val monkeys = Monkeys(input)
        val res =  monkeys.process2()
        println("res2: $res")
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)


    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

class Monkeys(val input:List<String>){
    var monkeys = input.chunked(7).map { Monkey(it) }
    val lcm = lcm(monkeys.map{it.testDiv})

    fun process():Int{

        monkeys.forEach(::println)
        println()
        println("runround 20")
        repeat(20){
            runRound()

        }
        println(monkeys)
        return monkeys.map{it.inspections}.sorted().takeLast(2).let{it[0]*it[1]}

    }

    fun process2():Long{
        println("part 2: ")
        val lcm = lcm(monkeys.map{it.testDiv}.toSet().toList())
        monkeys.forEach{it.lcm=lcm}
        monkeys.forEach{it.part2=true}
     //   runRound()
        monkeys.forEach(::println)
        println("20")
        repeat(20){
            runRound()
            monkeys.forEach(::println)
            println()

        }
//        monkeys.forEach(::println)
        println("1000")
        repeat(980){
            runRound()
        }
        monkeys.forEach(::println)
        println()
        monkeys.forEach(::println)
        println("10000")
        repeat(9000){
            runRound()
        }
        monkeys.forEach(::println)
        println()
        return monkeys.map{it.inspections}.sorted().takeLast(2).let{it[0].toLong()*it[1]}
    }

    fun lcm(divers:List<Int>):Int{
        var res = 1
        divers.forEach{res*=it}
        println("lcm $res")
        println(divers)
       return res
    }

    private fun runRound() {
        monkeys.forEach { monkey ->
            while (monkey.items.size > 0) {
                val cast = monkey.handleItem()
                monkeys[cast.second].items.add(cast.first)

            }

        }
    }
}

class Monkey(var description:List<String>){
    val testDiv:Int
    var items:MutableList<Long>
    var operation:List<String>
    val throwToTrue:Int
    val throwToFalse:Int
    var inspections=0
    var part2=false
    var lcm = 1

    init {
        /**
         * Monkey 0:
        Starting items: 79, 98
        Operation: new = old * 19
        Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3
         */
        items = description[1].split(":")[1].split(",").map { it.trim().toLong() }.toMutableList()
        operation = description[2].split(" ").takeLast(3)
        testDiv = description[3].split(" ").last().toInt()
        throwToTrue = description[4].split(" ").last().toInt()
        throwToFalse = description[5].split(" ").last().toInt()
    }
        //
     fun runOperation(oldValue: Long): Long {
        val input1 = getValue(oldValue, operation[0])
        val input2 = getValue(oldValue, operation[2])
            if (operation[1].trim() == "*") {
                val res:Long = (input1 * input2)
                if(res>Int.MAX_VALUE) {
                    return (res % lcm)
                }
                return res
            }
            if (operation[1].trim() == "+") {
                return input1 + input2
            }
        return oldValue
        }


            fun getValue(oldValue: Long, input:String):Long =
                if(input == "old"){
                    oldValue
                }else {
                    input.trim().toLong()
                }


        fun test(value:Long):Int{
            return if(value % testDiv>0)
                    throwToTrue
                    else
                        throwToFalse
        }

    fun handleItem(): Pair<Long, Int> {
        var item = items.first()
        inspections++
        items.remove(item)
        item = runOperation(item)
        if(!part2)
            item /= 3
        else {

                val before= item.mod(testDiv)
                item= item%lcm
                val after = item.mod(testDiv)
                if(before!=after){
                    println("item mod before $before after $after")

                }


        }
        return if((item.mod(testDiv)) != 0){
            (item to throwToFalse)
            }else{
            (item to throwToTrue)
        }
    }


    override fun toString():String{
        return """Monkey: checks: $inspections, items: $items  operation = $operation, check: div $testDiv, true: $throwToTrue false: $throwToFalse part2: $part2"""
    }


}
