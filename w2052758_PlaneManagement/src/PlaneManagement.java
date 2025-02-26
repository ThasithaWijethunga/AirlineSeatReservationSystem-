// Import statements for used functionalities
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This class manages the airplane seating system.
 * It provides functionalities for buying seats, cancelling seats,
 * finding available seats, displaying the seating plan,
 * printing ticket information, and searching for specific tickets.
 */
public class PlaneManagement {

    /**
     * A 2D array representing the plane seats.
     * 0 indicates an available seat, 1 indicates a booked seat.
     * At the beginning of the program, all seats are marked as available (0) in the array.
     */
    static int[][] seats = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };


    /**
     * A 2D array to store sold ticket information.
     * Each row represents a ticket, with columns for:
     *  - row letter (A, B, C, or D)
     *  - seat number (1-14 for A/D rows, 1-12 for B/C rows)
     *  - ticket price
     *  - passenger name
     *  - passenger surname
     *  - passenger email
     */
    static String[][] soldTickets = new String[52][6];

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    // Variables to store user selections and seat information
    static int selectedOption ;
    static int ticketPosition ;
    static int seatRowNumber ;
    static String name;
    static String surname;
    static String email;
    static int seatPrice ;
    static String seatRowLetter;
    static int seatNumber;
    static int rowIndex;
    static int columnIndex;
    static int totalPriceTickets ;
    static int price ;

    // Ticket object to store ticket details
    static Ticket ticket;


    public static void main(String[] args) {

        System.out.println();
        System.out.println("   Welcome to the Plane Management application ");

        // Main loop for menu interaction
        do{
            System.out.println();
            System.out.println("*************************************************");
            System.out.println("*                   MENU OPTIONS                *");
            System.out.println("*************************************************");
            System.out.println("     1) Buy a seat");
            System.out.println("     2) Cancel a seat");
            System.out.println("     3) Find first available seat");
            System.out.println("     4) Show seating plan");
            System.out.println("     5) Print tickets information and total sales");
            System.out.println("     6) Search tickets");
            System.out.println("     0) Quit");
            System.out.println("*************************************************");

            // Loop for handling invalid input
            while(true) {
                try {
                    System.out.println();
                    System.out.print("Please select an option: ");
                    selectedOption = scanner.nextInt();

                    if (selectedOption == 0) {
                        break;  // Exit loop on option 0 (Quit)
                    } else if (selectedOption == 1) {
                        buy_seat();
                        break;
                    } else if (selectedOption == 2) {
                        cancel_seat();
                        break;
                    } else if (selectedOption == 3) {
                        find_first_available();
                        break;
                    } else if (selectedOption == 4) {
                        show_seating_plan();
                        break;
                    } else if (selectedOption == 5) {
                        print_tickets_info();
                        break;
                    } else if (selectedOption == 6) {
                        search_ticket();
                        break;
                    } else {
                        System.out.println("Invalid Input ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    scanner.next(); // Clear invalid input from scanner
                }
            }
        }while((selectedOption != 0));

        System.out.println();
        System.out.println("Thank you for choosing our airline services.");
        System.out.println("We hope you had a pleasant experience with us.");
        System.out.println("Safe travels, and we look forward to serving you again soon!!!");
    }


    /**
     * This method handles the process of buying a seat on the airplane.
     * It prompts the user for seat selection, determines the seat price,
     * checks seat availability, collects passenger information, creates a Ticket object,
     * updates the seat availability in the seats array, displays a booking confirmation message,
     * stores ticket information in the soldTickets array, and calls a method to save the ticket details.
     */
    public static void buy_seat() {

        // Get user input for seat selection
        inputs();

        // Determine seat price based on seat number
        if (seatNumber < 6) {
            seatPrice = 200;
        }
        else if (seatNumber < 10) {
            seatPrice = 150;
        }
        if (seatNumber > 9) {
            seatPrice = 180;
        }

        // Check if seat is available
        if (seats[seatRowNumber][seatNumber - 1] == 0) {

            // Get passenger information
            System.out.println();
            System.out.print("Enter your  name : ");
            name = scanner.next();

            System.out.print("Enter your surname : ");
            surname = scanner.next();

            System.out.print("Enter your email address : ");
            email = scanner.next();

            // Create Person object to store passenger information
            Person person = new Person(name, surname, email);

            // Create Ticket object with seat details and passenger information
            ticket = new Ticket(seatRowLetter, seatNumber, seatPrice, person);

            // Update seat availability in seat array
            seats[seatRowNumber][seatNumber - 1] = 1;


            System.out.println();
            System.out.println("Booking confirmed!!! ");
            System.out.println("Your seat has been successfully reserved.");

            // Store ticket information in soldTickets array
            soldTickets[ticketPosition][0] = ticket.getRow();
            soldTickets[ticketPosition][1] = String.valueOf(ticket.getSeat());
            soldTickets[ticketPosition][2] = String.valueOf(ticket.getPrice());
            soldTickets[ticketPosition][3] = String.valueOf(ticket.getPerson().getName());
            soldTickets[ticketPosition][4] = String.valueOf(ticket.getPerson().getSurname());
            soldTickets[ticketPosition][5] = String.valueOf(ticket.getPerson().getEmail());

            // Call a method to save the ticket details
            Ticket.save(ticket, person);

        } else {
            System.out.println();
            System.out.println("Sorry, this seat is already booked.");
            System.out.println("Please choose another seat.");
        }
    }

    /**
     * This method handles the process of canceling a seat reservation on the airplane.
     * It prompts the user for seat selection, checks if the seat is booked,
     * updates the seat availability in the seats array, displays a cancellation confirmation message,
     * deletes the corresponding ticket file, and clears the ticket information from the soldTickets array.
     */
    public static void cancel_seat() {

        // Get user input for seat selection
        inputs();

        // Check if seat is booked
        if (seats[seatRowNumber][seatNumber - 1] == 1) {

            // Update seat availability in seat array
            seats[seatRowNumber][seatNumber - 1] = 0;  // Mark seat as available

            System.out.println();
            System.out.println("Seat cancellation successful.");
            System.out.println("Your booking has been canceled.");

            // Delete the corresponding ticket file
            File file = new File(soldTickets[ticketPosition][0] + soldTickets[ticketPosition][1] +".txt");
            if (file.exists()){
                file.delete();
            }

            // Clear ticket information from soldTickets array
            for (int index = 0; index < 6; index++) {
                soldTickets[ticketPosition][index] = null;
            }
        }
        else {
            System.out.println("Cancellation Not Required");
            System.out.println("This seat is currently available and not reserved.");
        }
    }


    /**
     * This method searches for the first available seat on the airplane.
     * It iterates through all rows and columns of the seat array, checking if a seat is available (marked as 0).
     * If an available seat is found, the method prints a message indicating the row letter and seat number of the first available seat.
     * The method also keeps track of the total number of booked seats. If all seats are booked, it informs the user that no seats are available.
     */
    public static void find_first_available() {
        // Counter to keep track of the total number of booked seats
        int seatsBooked = 0;

        // Loop through all rows and columns of the seat array
        for (rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {

                // Check if the current seat is available
                if (seats[rowIndex][columnIndex] == 0) {

                    // Print the first available seat based on the row index
                    if (rowIndex == 0) {
                        System.out.println("       The first available seat is  A " + (columnIndex + 1));
                        break; // Exit the inner loop after finding the first available seat
                    }
                    else if (rowIndex == 1) {
                        System.out.println();
                        System.out.println("       The first available seat is  B " + (columnIndex + 1));
                        break;
                    }
                    else if (rowIndex == 2) {
                        System.out.println();
                        System.out.println("       The first available seat is  C " + (columnIndex + 1));
                        break;

                    }
                    else if (rowIndex == 3) {
                        System.out.println();
                        System.out.println("       The first available seat is  D " + (columnIndex + 1));
                        break;
                    }
                }else{
                    seatsBooked += 1 ;
                }
            }
            // Additional check to handle cases where the last seat in a row might be available
            if(columnIndex != seats[rowIndex].length){
                if (seats[rowIndex][columnIndex] == 0) {
                    break; // Exit the outer loop if the last seat in the current row is available
                }
            }else{
                if (seats[rowIndex][columnIndex - 1 ] == 0) {
                    break; // Exit the outer loop if the last seat (checked in previous iteration) is available
                }
            }
        }
        // Inform user if all seats are booked
        if(seatsBooked == 52){
            System.out.println();
            System.out.println("Sorry, there are no available seats at the moment.");
            System.out.println("Please try again later.");
        }
    }





    /**
     * This method displays a visual representation of the airplane's seating plan.
     * It iterates through the seats array and prints either "O" for available seats or "X" for booked seats.
     * The output provides a clear overview of the current seat availability on the plane.
     */
    public static void show_seating_plan() {
        System.out.println();

        // Loop through all rows and columns of the seat array
        for (rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (columnIndex = 0; columnIndex < seats[rowIndex].length; columnIndex++) {

                // Print a seat representation based on the seat availability in seats array
                switch (seats[rowIndex][columnIndex]) {
                    case 0:
                        System.out.print(" O  ");
                        break;

                    case 1:
                        System.out.print(" X  ");
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     * This method iterates through the soldTickets array and prints the information for each booked ticket.
     * It checks if the ticket information is not null (indicating an empty slot) and then displays details like row, seat, price, passenger name, surname, and email.
     * The method also calculates the total price for each ticket based on the stored price value and accumulates the total price of all tickets.
     * If no tickets are found in the soldTickets array, it informs the user.
     * Finally, the method prints the total price of all booked tickets and resets the totalPriceTickets variable for future calculations.
     */
    public static void print_tickets_info() {

        // Loop through all entries in the soldTickets array
        for(rowIndex = 0; rowIndex < soldTickets.length; rowIndex++){

            // Check if the current ticket information is not null
            if(soldTickets[rowIndex][0] != null){
                System.out.println();
                System.out.println("Ticket Information :");
                System.out.println("      Row     : " + soldTickets[rowIndex][0]);
                System.out.println("      Seat    : " + soldTickets[rowIndex][1]);
                System.out.println("      Price   : £ "  +soldTickets[rowIndex][2]);
                System.out.println("      Name    : " + soldTickets[rowIndex][3]);
                System.out.println("      Surname : " + soldTickets[rowIndex][4]);
                System.out.println("      Email   : " + soldTickets[rowIndex][5]);

                // Calculate total price based on the stored price in soldTickets
                if(soldTickets[rowIndex][2].equals("200") ){
                    price = 200;
                }
                else if(soldTickets[rowIndex][2].equals("180") ){
                    price = 180;
                }
                else if(soldTickets[rowIndex][2].equals("150") ){
                    price = 150;
                }

                // Accumulate the total price of all tickets
                totalPriceTickets = totalPriceTickets + price;
            }
        }
        // Inform user if no tickets are found
        if (totalPriceTickets == 0){
            System.out.println();
            System.out.print("Seats have not been booked yet !!");

        }
        // Print the total price of all tickets
        System.out.println();
        System.out.println("Total price of the tickets : £ " + totalPriceTickets);

        // Reset totalPriceTickets for future calculations
        totalPriceTickets = 0;

    }

    /**
     * This method allows users to search for a specific ticket by entering the seat row and number.
     * It retrieves user input for the seat selection and then searches the soldTickets array for a matching ticket.
     * If a ticket is found for the entered seat, the method displays the ticket information, including the row, seat, price, and passenger details (name, surname, and email).
     * If no ticket is found, the method informs the user that the seat is available.
     */
    public static void search_ticket(){

        // Get user input for seat selection
        inputs();

        // Check if a ticket exists for the entered seat using the soldTickets array
        if(soldTickets[ticketPosition][0] != null){
            System.out.println("Ticket Information :");
            System.out.println("              Row     : " + soldTickets[ticketPosition][0]);
            System.out.println("              Seat    : " + soldTickets[ticketPosition][1]);
            System.out.println("              Price   : £ " + soldTickets[ticketPosition][2]);
            System.out.println("Person Information :");
            System.out.println("              Name    : " + soldTickets[ticketPosition][3]);
            System.out.println("              Surname : " + soldTickets[ticketPosition][4]);
            System.out.println("              Email   : " + soldTickets[ticketPosition][5]);
        }
        else{
            System.out.println();
            System.out.println("This seat is available.");
        }
    }

    /**
     * This method handles user input for seat selection.
     * It uses a loop to ensure the user enters a valid seat row letter (A, B, C, or D).
     * Then, it uses another loop with input validation to handle seat number input. The valid seat number range depends on the chosen row letter (A/D: 1-14, B/C: 1-12).
     * The method also handles potential exceptions like `InputMismatchException` if the user enters non-numeric input for the seat number.
     * Finally, it calculates the ticket position in the soldTickets array based on the seat row letter and number.
     */
    public static void  inputs(){

        // Loop to handle invalid seat row letter input
        do {
            System.out.println();
            System.out.print("Enter seat row letter (A,B,C,D) : ");
            seatRowLetter = scanner.next();

            // Convert the input to uppercase for easier comparison
            seatRowLetter = seatRowLetter.toUpperCase();

            if (!"A".equals(seatRowLetter) && !"B".equals(seatRowLetter) && !"C".equals(seatRowLetter) && !"D".equals(seatRowLetter)) {
                System.out.println("Invalid Input");
            }
            else {
                break;
            }
        } while (true);

        // Loop to handle invalid seat number input and format based on row letter
        do{
            try{
                if ("A".equals(seatRowLetter) || "D".equals(seatRowLetter)) {
                    System.out.println();
                    System.out.print("Enter seat number (1-14) : ");

                } else {
                    System.out.println();
                    System.out.print("Enter seat number (1-12) : ");
                }


                seatNumber = scanner.nextInt();

                // Validate seat number based on row letter (A/D: 1-14, B/C: 1-12)
                if ("A".equals(seatRowLetter) || "D".equals(seatRowLetter)) {
                    if(seatNumber < 0 || seatNumber > 14){
                        System.out.println();
                        System.out.println("Invalid seat number.");
                        System.out.println("Please enter a seat number between 1 and 14.");
                    }else{
                        break;
                    }
                }else{
                    if(seatNumber < 0 || seatNumber > 12){
                        System.out.println();
                        System.out.println("Invalid seat number.");
                        System.out.println("Please enter a seat number between 1 and 12.");
                    }else{
                        break;
                    }
                }

            }catch (InputMismatchException e){
                System.out.println("Invalid Input");
                scanner.next();  // Clear invalid input from scanner
            }
        }
        while (true);

        // Calculate ticket position based on seat row letter and number
        if ("A".equals(seatRowLetter)) {
            seatRowNumber = 0;
            ticketPosition = seatNumber - 1;
        } else if ("B".equals(seatRowLetter)) {
            seatRowNumber = 1;
            ticketPosition = 14 + (seatNumber - 1);
        } else if ("C".equals(seatRowLetter)) {
            seatRowNumber = 2;
            ticketPosition = 26 + (seatNumber - 1);

        } else if ("D".equals(seatRowLetter)) {
            seatRowNumber = 3;
            ticketPosition = 38 + (seatNumber - 1);
        }
    }
}