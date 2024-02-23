import java.io.File
import kotlin.random.Random

fun main() {
    var run: Boolean = true
    var guesses = mutableListOf<Char>()
    var wrong_guesses = 0
    var word = getRandomWord("words.txt")
    clearConsole()
    drawHangMan(wrong_guesses)
    drawWord(word)
    while (run != false) {
        var guess = getGuess()
        guesses += guess
        var correct: Boolean = checkGuess(guess, word)
        if (correct == false) {
            ++wrong_guesses
        }
        clearConsole()
        drawHangMan(wrong_guesses)
        run = drawWord(word, guesses)
        println("\n" + guesses + "\n")
        if (wrong_guesses == 6) {
            run = false
            println("\nThe word was: " + word)
            println("\n=========")
            println("GAME OVER")
            println("=========")
        }
    }

}

fun clearConsole() {
    repeat(100) { println() }
}

fun getRandomWord(fileName: String): String {
    val file = File(fileName)
    val lines = file.readLines()
    if (lines.isNotEmpty()) {
        val randomIndex = Random.nextInt(lines.size)
        return lines[randomIndex]
    }
    return "nowords"
}

fun getGuess(): Char {
    print("\nEnter a letter: ")
    var letter = getInput()
    return letter
}

fun getInput(): Char {
    var input = readLine()
    while(true) {
        try {
            val letter = input?.let {
                if (it.isBlank()) {
                    throw IllegalArgumentException("Input is blank")
                }
                if (it.length != 1) {
                    throw IllegalArgumentException("Input must be a single character")
                }
                it.single()
            } ?: throw IllegalArgumentException("Input is null")
            
            val lowerLetter = letter.lowercaseChar()
            return lowerLetter
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
            input = readLine()
        }
    }
}

fun checkGuess(letter: Char, word: String): Boolean {
    if (word.contains(letter)) {
        return true
    }
    else {
        return false
    }
}

fun drawWord(word: String) {
    for (letter in word) {
        print("_")
    }
    println()
}

fun drawWord(word: String, guesses: MutableList<Char>): Boolean {
    var count = word.length
    for (letter in word) {
        if (guesses.contains(letter)) {
            print(letter)
            --count
        }
        else {
            print("_")
        }
    }
    if (count == 0) {
        println("\n========")
        println("YOU WIN!")
        println("========")
        return false
    }
    println()
    return true
}

fun drawHangMan(wrong_guesses: Int) {
    val hanged_man = arrayOf(
        """
  -----
  |   |
      |
      |
      |
      |
      |
      |
      |
      |
-------
""",
        """
  -----
  |   |
  O   |
      |
      |
      |
      |
      |
      |
      |
-------
""",
        """
  -----
  |   |
  O   |
 ---  |
  |   |
  |   |
      |
      |
      |
      |
-------
""",
        """
  -----
  |   |
  O   |
 ---  |
/ |   |
  |   |
      |
      |
      |
      |
-------
""",
        """
  -----
  |   |
  O   |
 ---  |
/ | \ |
  |   |
      |
      |
      |
      |
-------
""",
        """
  -----
  |   |
  O   |
 ---  |
/ | \ |
  |   |
 ---  |
/     |
|     |
      |
-------
""",
        """
  -----
  |   |
  O   |
 ---  |
/ | \ |
  |   |
 ---  |
/   \ |
|   | |
      |
-------
"""
    )

    println(hanged_man[wrong_guesses])
}