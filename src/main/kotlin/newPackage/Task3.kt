package newPackage

sealed interface Command {
    fun isValid(): Boolean
}

data class AddPersonCommand(val name: String) : Command {
    override fun isValid() = name.isNotBlank()
}

data class AddPhoneCommand(val name: String, val phone: String) : Command {
    override fun isValid() = name.isNotBlank() && phone.matches(Regex("""[0-9]+"""))
}

data class AddEmailCommand(val name: String, val email: String) : Command {
    override fun isValid() = name.isNotBlank() && email.matches(Regex("""\w+@\w+\.\w+"""))
}

object ShowCommand : Command {
    override fun isValid() = true
}

object FindCommand : Command {
    override fun isValid() = true
}

object HelpCommand : Command {
    override fun isValid() = true
}

object ExitCommand : Command {
    override fun isValid() = true
}

data class Person(val name: String, val phones: MutableList<String> = mutableListOf(), val emails: MutableList<String> = mutableListOf())

val phonebook = mutableMapOf<String, Person>()

fun readCommand(): Command {
    print("Введите команду (exit, help, add person, add phone, add email, show, find): ")
    val input = readlnOrNull() ?: return HelpCommand

    return when {
        input == "exit" -> ExitCommand
        input == "help" -> HelpCommand
        input == "show" -> ShowCommand
        input == "find" -> FindCommand
        input.startsWith("add person") -> {
            val name = input.substringAfter("add person ").trim()
            AddPersonCommand(name)
        }
        input.startsWith("add phone") -> {
            val (name, phone) = input.substringAfter("add phone ").trim().split(" ", limit = 2)
            AddPhoneCommand(name, phone)
        }
        input.startsWith("add email") -> {
            val (name, email) = input.substringAfter("add email ").trim().split(" ", limit = 2)
            AddEmailCommand(name, email)
        }
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
            println("add person <name> - добавить нового человека")
            println("add phone <name> <phone> - добавить телефон человеку")
            println("add email <name> <email> - добавить email человеку")
            println("show <name> - показать телефоны и email человека")
            println("find <phone|email> - найти человека по телефону или email")
        } else {
            when (command) {
                is AddPersonCommand -> {
                    val person = Person(command.name)
                    phonebook[command.name] = person
                    println("Добавлен новый человек: ${person.name}")
                }
                is AddPhoneCommand -> {
                    val person = phonebook[command.name]
                    if (person != null) {
                        person.phones.add(command.phone)
                        println("Телефон ${command.phone} добавлен для ${command.name}")
                    } else {
                        println("Человек ${command.name} не найден")
                    }
                }
                is AddEmailCommand -> {
                    val person = phonebook[command.name]
                    if (person != null) {
                        person.emails.add(command.email)
                        println("Email ${command.email} добавлен для ${command.name}")
                    } else {
                        println("Человек ${command.name} не найден")
                    }
                }
                ShowCommand -> {
                    print("Введите имя человека: ")
                    val name = readlnOrNull()?.trim() ?: return@main
                    val person = phonebook[name]
                    if (person != null) {
                        println("Телефоны для ${person.name}: ${person.phones.joinToString(", ")}")
                        println("Email для ${person.name}: ${person.emails.joinToString(", ")}")
                    } else {
                        println("Человек $name не найден")
                    }
                }
                FindCommand -> {
                    print("Введите телефон или email: ")
                    val value = readlnOrNull()?.trim() ?: return@main
                    val foundPeople = phonebook.values.filter { person ->
                        person.phones.contains(value) || person.emails.contains(value)
                    }
                    if (foundPeople.isNotEmpty()) {
                        println("Найдены следующие люди:")
                        foundPeople.forEach { println("${it.name}: телефоны - ${it.phones.joinToString(", ")}, email - ${it.emails.joinToString(", ")}") }
                    } else {
                        println("Никто не найден с таким телефоном или email")
                    }
                }
                ExitCommand -> {
                    println("Выход из приложения")
                    return
                }
                else -> {}
            }
        }
    }
}