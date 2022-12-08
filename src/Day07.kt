fun main() {
    fun part1(input:String): Int {
        val processFiles = ProcessFiles(input)
        return processFiles.process()

    }

    fun part2(input:String): Int {
        val processFiles = ProcessFiles(input)
        return processFiles.process2()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readWholeFile("Day07_test")
    println("testinput 1 :" +part1(testInput))
    check(part1(testInput) == 95437)
    println("testinput 2 :" +part2(testInput))
    check(part2(testInput) == 24933642)


    val input = readWholeFile("Day07")
    println("real 1: " + part1(input))
    println("real 2: " + part2(input))

   // println(part2(input))
}

class ProcessFiles(val input:String) {
    private val commands = input.replace("\r".toRegex(),"").split("\$ ")
        .map { command ->
            command.split("\n")
                .let { it.first() to it.drop(1)}
        }
    var root = Dir("/")
    var currentDir = root
    val totalSpace =70000000
    val neededSpace = 30000000
    init{
        createFileSystem()
    }


    fun process(): Int {
        root.visualize("")

        val dirs = root.getAllDirs()
        return dirs.map { it.getSize() }.filter{ it<=100000 }.sum()

    }

    private fun createFileSystem() {
        commands.forEach { (command, rows) ->
            if (command.startsWith("ls")) {
                rows.forEach { currentDir.addContent(it) }
            }
            if (command.startsWith("cd ")) {
                cd(command.drop(3))
            }
        }
    }

    fun cd(to: String) {
        //println(" cd \"$to\"")
        currentDir = when (to) {
            "/" -> root
            ".." -> currentDir.parent!!
            else -> currentDir.dirs.filter { it.name==to }.single()

        }
    }




    fun process2():Int{
       // root.visualize("")
        val spaceToFree = neededSpace -(totalSpace -root.getSize())
        val smallesUsableDir =root.getAllDirs().filter { it.getSize()>spaceToFree }.sortedBy { it.getSize() }.first()
        return smallesUsableDir.getSize()
    }
}






data class Dir(val name:String,val parent:Dir?=null){

     var dirs:MutableList<Dir> = mutableListOf()
     var files:MutableMap<String,Int> = mutableMapOf()


    fun getSize():Int{
        val fileSize =files.map { it.value }.sum()
        val dirSize = this.dirs.sumOf { it.getSize() }
       // println("size of $name:  ${fileSize + dirSize}")
        return fileSize+dirSize

    }

    fun addContent(row:String){
       // println("row \"$row\"")
        if(row.trim().isEmpty()){
            return
        }
        row.split(" ").let{
            if(it.first()=="dir"){
                dirs.add(Dir(it[1],this))
                return
            }
            if(it[0].matches("\\d*".toRegex())){
                files.put(it[1], it[0].toInt())
            }

        }


    }

    fun getAllDirs(): List<Dir> {
        val subList = listOf(this) + dirs.map { it.getAllDirs() }.flatten()
        return subList
    }

    fun visualize(pre:String){
        println("$pre+$name")
        dirs.forEach{
            it.visualize("$pre  ")
        }
        files.forEach { (name, size) -> println("  $pre$name $size") }
    }


}

