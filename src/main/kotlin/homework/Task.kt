package homework

// Написать программу, которая обрабатывает введённые пользователем в консоль команды:
//exit
//help
//add <Имя> phone <Номер телефона>
//add <Имя> email <Адрес электронной почты>
//
//После выполнения команды, кроме команды exit, программа ждёт следующую команду.

fun main() {
    print("Введите команду:\n exit\n help\n add\n")
    val command = readlnOrNull()
    if (command == "exit") return println("выход из приложения")
    else println(
        "Введите:\n" +
                " 2 для вызова помощи,\n" +
                " 3 - добавить имя и номер телефона,\n" +
                " 4 - добавить имя и адрес email"
    )
    val newCommand = readlnOrNull()
    consoleInput(newCommand)
}

fun consoleInput(str: String?) {
    if (str == "2") return println("помощь")
    else if (str == "3") {
        println("Добавьте имя:")
        val name = readln()
        println("Добавьте телефон:")
        val phone = readln()
        if (phone.matches(Regex("""[0-9]+"""))) {
            println("Добавлен $name с номером телефона $phone")
        } else return println("Номер телефона указан неправильно")

    } else println("Добавьте имя:")
    val name = readlnOrNull()
    println("Добавьте email:")
    val email = readln()
    if (email.matches(Regex("""\w+@\w+\.\w+"""))) {
        println("Добавлен $name с адресом почты $email")
    } else return println("Адрес email указан неправильно")
}
