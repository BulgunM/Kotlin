package seminars


fun main(args: Array<String>) {
    if (args.size < 3) return

    val a = args[0].toInt()
    val b = args[1].toInt()
    val c = args[2].toInt()

    for (i in 0 until b) {
        printStarsLine(a, c, i)
    }
    for (i in b downTo 0) {
        printStarsLine(a, c, i)
    }
}

fun printStarsLine(a: Int, c: Int, i: Int) {
    for (j in 1 until i * c + a) {
        print('*')
    }
    println()
}