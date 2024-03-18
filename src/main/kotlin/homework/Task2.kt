package homework

sealed interface Command {
    fun isValid(): Boolean
}

data class AddCommand(val name: String?, val phone: String?, val email: String?) : Command {
    override fun isValid(): Boolean {
        return (phone == null || phone.matches(Regex("""[0-9]+"""))) &&
                (email == null || email.matches(Regex("""\w+@\w+\.\w+""")))
    }
}

object HelpCommand : Command {
    override fun isValid() = true
}

object ExitCommand : Command {
    override fun isValid() = true
}

object ShowCommand : Command {
    override fun isValid() = true
}

data class Person(val name: String?, val phone: String?, val email: String?)

var currentPerson: Person? = null

fun readCommand(): Command {
    print("Введите команду (exit, help, add, show): ")
    val input = readlnOrNull() ?: return HelpCommand

    return when (input) {
        "exit" -> ExitCommand
        "help" -> HelpCommand
        "add" -> {
            print("Добавьте имя: ")
            val name = readlnOrNull()
            print("Добавьте телефон: ")
            val phone = readlnOrNull()
            print("Добавьте email: ")
            val email = readlnOrNull()
            AddCommand(name, phone, email)
        }
        "show" -> ShowCommand
        else -> HelpCommand
    }
}

fun main() {
    while (true) {
        val command = readCommand()
        println(command)

        if (!command.isValid()) {
            println("Помощь:")
            println("exit - выход из приложения")
            println("help - вызов помощи")
            println("add - добавить имя, телефон и email")
            println("show - показать последнее добавленное значение")
        } else {
            when (command) {
                is AddCommand -> {
                    currentPerson = Person(command.name, command.phone, command.email)
                    println("Добавлено: $currentPerson")
                }
                ExitCommand -> {
                    println("Выход из приложения")
                    return
                }
                ShowCommand -> {
                    if (currentPerson == null) {
                        println("Not initialized")
                    } else {
                        println("Последнее добавленное значение: $currentPerson")
                    }
                }
                else -> {}
            }
        }
    }
}