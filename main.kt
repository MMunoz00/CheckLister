import java.util.Scanner

fun main() {

    var run: Boolean = true
    while (run != false) {
        run = menu()
    }

}

fun menu(): Boolean {

    println("\nPlease make a selection:")
    println("\t1. Test")
    println("\t2. Quit")

    var input = Scanner(System.`in`)
    print("\nSelect Option: ")
    
    while (true)
        try {
            var num: Int = input.nextInt()
            if (num == 1) {
                println("\nThis is a test")
                return true
            }
            else if (num == 2) {
                println("\nGoodbye")
                return false
            }
            else {
                println("\nBad input")
                input = Scanner(System.`in`)
                print("\nSelect Option: ")
                continue;
            }
        }
        catch (e: java.util.InputMismatchException) {
            println("\nBad input")
            input = Scanner(System.`in`)
            print("\nSelect Option: ")
        }
}