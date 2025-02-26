// Import statements for used functionalities
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class represents a ticket for a seat, storing its details and associated passenger information.
 */
public class Ticket {

    // Private variables for ticket details
    private  String row;
    private  int seat ;
    private   int price;
    private  Person person;  // Represents the passenger associated with the ticket

    //Constructor to create a Ticket object with the provided details.
    public Ticket(String row , int seat , int price , Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Setter methods for ticket details
    public void setRow(String row){
        this.row = row;
    }
    public void setSeat(int seat ){
        this.seat = seat;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setPerson( Person person){
        this.person = person;
    }


    // Getter methods for ticket details
    public String getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public int getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }


    //Static method to Display ticket information
    public static String print_ticket_information(Ticket ticket , Person person){
        return  ("Ticket Information :"+ "\n") +
                ("              Row     : " + ticket.getRow() + "\n")  +
                ("              Seat    : " + ticket.getSeat()+ "\n")  +
                ("              Price   : £ " + ticket.getPrice()+ "\n")   +
                Person.print_person_information(person);
    }


    //static method to save the details of a ticket to a text file.
    public  static void save(Ticket ticket , Person person){

        try{
            FileWriter myWriter = new FileWriter(ticket.getRow() + ticket.getSeat() +".txt");
            myWriter.write(print_ticket_information(ticket , person));
            myWriter.close();

        }
        catch(IOException e){
            System.out.println("File creation failed!");
        }
    }
}