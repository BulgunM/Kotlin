package newPackage

fun main() {
    var persons : MutableMap<String, Person> = mutableMapOf()
    persons.put("Ivan", Person("Ivan", mutableListOf("0000","9999"), mutableListOf("rrr@gg.com")))

    var flag = true
    do{
        var command = readCommand(persons)
        if(command.isValid()){
            when(command){
                is Help     -> Help.showHelp()
                is Exit     -> flag = false
                is AddPhone -> if( command.isValid())command.savePersonPhone()
                is AddEmail -> if( command.isValid())command.savePersonEmail()
                is ShowAll  -> command.showAll()
                is Find     -> command.search()
                is Export   -> command.save(DSLJson.toJson(persons))
            }
        }else Help.showHelp()
    }while (flag)
    println("exit")
}

fun readCommand(persons : MutableMap<String, Person> ): Command {
    println("Введите команду: ")
    var responce = readlnOrNull()
    val parts = responce?.split(" ")
    if(parts!=null && parts[0] != null) {
        return when (parts?.get(0)) {
            "exit"     -> Exit(parts[0])
            "help"     -> Help(parts[0])
            "addPhone" -> AddPhone(parts, persons)
            "addEmail" -> AddEmail(parts,persons)
            "showAll"  -> ShowAll(parts[0], persons)
            "find"     -> Find(parts, persons)
            "export"   -> Export(parts, persons)
            else       -> Help("help")
        }
    }else return Help("help")
}