import kotlin.coroutines.ContinuationInterceptor

/*Задача 3.

Имеется массив из символов русского алфавита (все 33 символа, могут быть не по порядку). Символы алфавита нумеруются от 1 до 33.
Каждое число используется только один раз.  Сообщение шифруется с помощью ключевого слова, задаваемого пользователем.
Номер символа ключевого слова показывает, на сколько нужно сдвинуться по массиву из символов русского алфавита.
Составить программу шифровки и дешифровки строкового выражения (то есть программа спрашивает -
зашифровать или расшифровать текст и ключевое слово). Первый массив считать закольцованным. Регистр букв не имеет значения. Например:
А	Б	В	Г	Д	Е	Ё	Ж	З	И	Й	К	Л	М	Н	О	П	Р	С	Т	У	Ф	Х	Ц	Ч	Ш	Щ	Ь	Ы	Ъ	Э	Ю	Я
21	13	4	20	22	1	25	12	24	14	2	28	9	23	3	29	6	16	15	11	26	5	30	27	8	18	10	33	31	32	19	7	17
Ключевое слово - ПОЛЕ
Исходный текст - СООБЩЕНИЕ
Зашифрованный текст - АЁФИРХЖСЮ*/

fun main() {
    println("Использовать\n1-стандартную таблицу шифрования\n2-создать новую")
    var ans = readln().toIntOrNull()
    var map = mutableMapOf("А" to 21,"Б" to 13,"В" to 4 ,"Г" to 20,"Д" to 22,"Е" to 1,"Ё" to 25,"Ж" to 12,"З" to 24,"И" to 14,
        "Й" to 2,"К" to 28,"Л" to 9,"М" to 23,"Н" to 3, "О" to 29,"П" to 6,"Р" to 16,"С" to 15,"Т" to 11,"У" to 26,
        "Ф" to 5,"Х" to 30,"Ц" to 27,"Ч" to 8,"Ш" to 18,"Щ" to 10,"Ь" to 33,"Ы" to 31,"Ъ" to 32,"Э" to 19,"Ю" to 7,
        "Я" to 17)
    if (ans == null) return
    else if (ans == 2) {
        val arr = Array(33) { 33 }
        for (i in arr.indices) arr[i] = i + 1
        arr.shuffle()
        for (i in 'А'..'Я') map.put(i.toString(), arr[i.code - 'А'.code])
        println(map)
    }
    while (true) {
        var key = mutableListOf(' ')
        var text = mutableListOf(' ')
        try {
            println("Введите ключевое слово")
            key = readln().toMutableList()
            println("Введите сообщение")
            text = readln().toMutableList()
        } catch (e: Exception) {
            println("Ошибка")
            return
        }
        key = sizeKey(text, key)
        println("Выберите операцию\n1-Зашифровать\n2-Расшифровать")
        ans = readln().toIntOrNull()
        ans?.let {
            var choiceInt = ans!!
            if (ans !in 1..2) {
                println("Ошибка")
                return
            }
            crypt(text, key, map, choiceInt)
        }
        println("Продолжить работу? 1-да\n2-нет")
        ans = readln().toIntOrNull()
        ans?.let {
            if (ans == 2) return
            else println("Ошибка")
        }

    }
}

fun sizeKey(text: MutableList<Char>, key: MutableList<Char>): MutableList<Char> {
    var count = 0
    while (text.size != key.size) {
        key.add(key[count])
        count++
        if (count == key.size) {
            count = 0
        }
    }
    return key
}

fun crypt(text: MutableList<Char>, key: MutableList<Char>, map: Map<String, Int>, ans: Int) {
    println(ans)
    var str = ""
    var value = 0
    var minus = 1
    if (ans == 2) minus = -1
    for (i in key.indices) {
        value = map[key[i].toString()]!! * minus + map[text[i].toString()]!!

        if (ans == 1) if (value >= map.size - 1) value -= map.size
        if (ans == 2) if (value <= 0) value += map.size
        println(value)
        str += map.filterValues { it == value }.keys.joinToString(separator = "[]")

    }
    println(str)
}