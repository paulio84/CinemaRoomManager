package cinema

fun displayMenu(): Int {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")

    return readLine()!!.toInt()
}

fun printSeatingPlan(numRows: Int, numSeatsPerRow: Int, seatingPlan: Array<Array<String>>) {
    // print seating layout
    println("Cinema: ")
    for (i in 1..numSeatsPerRow) {
        if (i - 1 == 0) print(" ")

        print(" $i")
    }
    println()
    for (i in 1..numRows) {
        print("$i ")
        println(seatingPlan[i - 1].joinToString( " "))
    }
}

fun bookTicket(numRows: Int, numSeatsPerRow: Int, seatingPlan: Array<Array<String>>) {
    var isTicketPurchased = false
    while (!isTicketPurchased) {
        // get the row and seat to be booked
        println("Enter a row number: ")
        val bookingRow = readLine()!!.toInt()
        println("Enter a seat number in that row: ")
        val bookingSeat = readLine()!!.toInt()

        // check if the requested booking is within the numRows and numSeatsPerRow
        // and if the ticket has already been purchased
        if (bookingRow in 1..numRows && bookingSeat in 1..numSeatsPerRow) {
            if (seatingPlan[bookingRow - 1][bookingSeat - 1] == "B") {
                println("That ticket has already been purchased!")
            } else {
                isTicketPurchased = true

                // calculate the ticket price
                val ticketPrice = calculateSeatPrice(numRows, numSeatsPerRow, bookingRow - 1)
                println("Ticket price: $$ticketPrice")

                // update the seating plan with the booked seat
                seatingPlan[bookingRow - 1][bookingSeat - 1] = "B"
            }
        } else println("Wrong input!")
    }
}

fun statistics(numRows: Int, numSeatsPerRow: Int, seatingPlan: Array<Array<String>>) {
    // check number of purchased tickets
    // and current income
    var ticketsPurchased = 0
    var currentIncome = 0
    for (i in seatingPlan.indices) {
        for (j in seatingPlan[i].indices) {
            if (seatingPlan[i][j] == "B") {
                ++ticketsPurchased
                currentIncome += calculateSeatPrice(numRows, numSeatsPerRow, i)
            }
        }
    }

    val totalIncome = when {
        numRows * numSeatsPerRow <= 60 -> numRows * numSeatsPerRow * 10
        else -> {
            val frontRows = numRows / 2
            val backRows = numRows - frontRows

            (frontRows * 10 + backRows * 8) * numSeatsPerRow
        }
    }

    val percentageSeatsSold = ticketsPurchased / (numRows * numSeatsPerRow).toDouble() * 100

    println("Number of purchased tickets: $ticketsPurchased")
    println("Percentage: ${String.format("%.2f", percentageSeatsSold)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun calculateSeatPrice(numRows: Int, numSeatsPerRow: Int, rowNumber: Int): Int {
    return when {
        numRows * numSeatsPerRow <= 60 || rowNumber < numRows / 2 -> 10
        else -> 8
    }
}

fun main() {
    var menuInput = -1

    println("Enter the number of rows: ")
    val numRows = readLine()!!.toInt()
    println("Enter the number of seats in each row: ")
    val numSeatsPerRow = readLine()!!.toInt()

    // create a 2D array of the seating plan
    val seatingPlan = Array(numRows) { Array(numSeatsPerRow) { "S" } }

    while (menuInput != 0) {
        menuInput = displayMenu()
        when (menuInput) {
            1 -> printSeatingPlan(numRows, numSeatsPerRow, seatingPlan)
            2 -> bookTicket(numRows, numSeatsPerRow, seatingPlan)
            3 -> statistics(numRows, numSeatsPerRow, seatingPlan)
        }
    }
}