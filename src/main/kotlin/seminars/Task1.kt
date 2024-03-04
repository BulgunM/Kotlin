package seminars

    fun main() {
        println("sumAll = ${sumAll(1, 5, 20)}")
        println("sumAll = ${sumAll()}")
        println("sumAll = ${sumAll(2, 3, 4, 5, 6, 7)}")

        println(createOutputString("Alice"))
        println(createOutputString("Bob", 23))
        println(createOutputString(isStudent = true, name = "Carol", age = 19))
        println(createOutputString("Daniel", 32, isStudent = null))

        println(multiplyBy(null, 4))
        println(multiplyBy(3, 4))
    }


    fun sumAll(vararg args: Int): Int {
        var sum = 0
        if (args.isNotEmpty()) {
            for (i in args) {
                sum += i
            }
            return sum
        } else {
            return 0
        }
    }

    fun createOutputString(name: String, age: Int = 42, isStudent: Boolean? = null): String {
        val studentPrefix = if (isStudent == true) "student " else ""
        return "$studentPrefix$name has age of $age"
    }

    fun multiplyBy(numberOne: Int?, numberTwo: Int) {
        when (numberOne) {
            null -> null
            else -> numberOne * numberTwo // return if (a != null) a * b else null
        }
    }