package newPackage

import java.io.File
import java.io.FileNotFoundException

data class Person(val name: String,
                  val phones: MutableList<String> =mutableListOf(),
                  val emails: MutableList<String> = mutableListOf()) {

    override fun toString(): String {
        return "name='$name', phones='$phones', emails='$emails'"
    }
}

sealed interface Command {
    fun isValid(): Boolean
}
class Exit(val value: String) : Command {
    override fun isValid(): Boolean {
        return (value == "exit")
    }
}

class Help(val value: String) : Command{
    override fun isValid(): Boolean {
        return (value == "help")
    }
    companion object {
        fun showHelp() {
            println("""Commands:
            exit 
            help
            show
            showAll
            addPhone <Имя> <Номер телефона>
            addEmail <Имя>  <Адрес электронной почты>
            find <email or phone>
            export </Users/user/myfile.json>
            """.trimMargin())
        }
    }
}

class AddPhone(val value: List<String>, var persons : MutableMap<String, Person>) : Command {
    var name = value[1]
    var phone = value[2]
    override fun isValid(): Boolean {
        return name.matches(Regex("""[A-Z][a-z]+""")) && phone.matches(Regex("\\+?[0-9]+"))
    }

    fun savePersonPhone() {
        if (persons.containsKey(name)) {
            persons[name]?.phones?.add(phone)
        } else persons.put(name, Person(name, mutableListOf(phone), mutableListOf()))
        println("Save ${name}, ${phone}")
    }
}
class AddEmail(val value: List<String>, var persons : MutableMap<String, Person>) : Command {
    var name = value[1]
    var email = value[2]
    override fun isValid(): Boolean {
        return (name.matches(Regex("""[A-Z][a-z]+"""))
                && email.matches(Regex("\\w+\\@\\w+\\.\\w+")))
    }

    fun savePersonEmail() {
        if (persons[name] != null) {
            persons[name]?.emails?.add(email)
        } else persons.put(name, Person(name, mutableListOf(), mutableListOf(email)))
        println("Save ${name}, ${email}")
    }
}

class ShowAll(val value: String,  var persons : MutableMap<String, Person>) : Command{
    override fun isValid(): Boolean {
        return (value == "showAll")
    }
    fun showAll(){
        println(persons)
    }
}
class Find(val value: List<String>,  var persons : MutableMap<String, Person> ) : Command{
    override fun isValid(): Boolean {
        return (value[1].matches(Regex("\\w+\\@\\w+\\.\\w+")) ||
                value[1].matches(Regex("\\+?[0-9]+")))
    }

    fun search(){
        val searchValue = value[1]
        val foundPeople = mutableListOf<Person>()

        when{
            searchValue.matches(Regex("\\w+\\@\\w+\\.\\w+")) ->
                foundPeople.addAll(persons.values.filter { it.emails.contains(searchValue) })
            searchValue.matches(Regex("""\+?[0-9]+""")) ->
                foundPeople.addAll(persons.values.filter { it.phones.contains(searchValue) })
        }
        println(foundPeople)
    }
}
class Export(val value: List<String>,  var persons : MutableMap<String, Person>) : Command{
    val path = value[1]
    val text = ""
    override fun isValid(): Boolean {
        return File(path).exists() || File(path).createNewFile()
    }

    fun save(text: String){
        try{
            File(path).writeText(text)
            println("written to file")
        }catch (e: FileNotFoundException){
            println(e)
        }
    }
}
open class DSLJson () {
    companion object {
        fun toJson(persons : MutableMap<String, Person>): String {
            var res: String = "{"
            val pers = persons.values
            for (person in pers) {
                res += "\"name\": " + Quotes(person.name).toString() + ","
                res += "\"phones\": ["
                for (phone in person.phones){
                    res += Quotes(phone).toString() + ","
                }
                res = res.dropLast(1)
                res+= "], "
                res += "\"emails\": ["
                for (email in person.emails){
                    res += Quotes(email).toString() + ","
                }
                res = res.trimEnd(',')
                res+= "]"

            }
            res += "}"
            return res
        }
    }
}
class Quotes(var a: String) : DSLJson() {
    override fun  toString(): String{
        return """ "$a""""
    }
}
