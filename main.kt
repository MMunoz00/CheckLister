import java.io.File
import kotlin.random.Random

fun main() {
    //set up initial game environment
    var run: Boolean = true
    var guesses = mutableListOf<Char>()     //create empty list to store all guessed characters
    var wrong_guesses = 0                   //initialize number of incorrect guesses to use as index for hangman
    var word = getRandomWord("words.txt")   //generate word to use in the game from premade list

    clearConsole()                          //clean up the console for easy viewing
    drawHangMan(wrong_guesses)              //displays gallows with no parts of the hangman to start
    drawWord(word)                          //displays the number of letters in the generated word for the game

    while (run != false) {
        var guess = getGuess()              //prompt user for character
        guesses += guess                    //add user input to list of guesses
        var correct: Boolean = checkGuess(guess, word)  //check if guessed character is in word

        if (correct == false) {
            ++wrong_guesses                 //adds part of hangman for wrong guess
        }

        clearConsole()                      //clean up the console for easy viewing

        drawHangMan(wrong_guesses)          //display current hangman status based on number of wrong guesses
        run = drawWord(word, guesses)       //check if user has won the game
        println("\n" + guesses + "\n")      //display all guessed characters

        if (wrong_guesses == 6) {
            run = false                     //if user completed the hangman, end the game and display lose text
            println("\nThe word was: " + word)
            println("\n=========")
            println("GAME OVER")
            println("=========")
        }
    }

}

//prints 100 empty lines to make console reading easier
fun clearConsole() {
    repeat(100) { println() }
}

//read file with premade words to generate a random word for the game
//Parameters: fileName (String)
//Output: word (String)
//optional output to use a default word if words file is empty
fun getRandomWord(fileName: String): String {
    val file = File(fileName)
    val lines = file.readLines()
    if (lines.isNotEmpty()) {
        val randomIndex = Random.nextInt(lines.size)
        val word = lines[randomIndex]
        return word
    }
    return "nowords"
}

//prompt user for character input
//Output: letter (Char)
fun getGuess(): Char {
    print("\nEnter a letter: ")
    var letter = getInput()
    return letter
}

//check user input is not null and is only a single character, and converts letter to lowercase
//Output: lowerLetter (Char)
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

//checks if users guess is in the word
//Output: (Boolean)
fun checkGuess(letter: Char, word: String): Boolean {
    if (word.contains(letter)) {
        return true
    }
    else {
        return false
    }
}

//used to initially display a "_" for each letter in the generated word
fun drawWord(word: String) {
    for (letter in word) {
        print("_")
    }
    println()
}

//displays a "_" for each letter in word, but replaces all the letters with their respective correct guess
//determines if player has won the game and displays win text
//Output: (Boolean)
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

//creates array of strings to display the gallows and hangman for the game in the console
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