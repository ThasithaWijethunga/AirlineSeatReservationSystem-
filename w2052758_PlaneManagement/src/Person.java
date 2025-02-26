/**
 * This class represents a person associated with a ticket.
 * It stores the person's name, surname, and email address.
 */
public class Person {

    // Private variables to store person information
    private String name;
    private String surname;
    private String email;


    //Constructor to create a Person object with the provided name, surname, and email.
    public Person(String userName , String userSurname , String userEmail){
        this.name = userName;
        this.surname = userSurname;
        this.email = userEmail;
    }


    //Setter method to update the person's name.
    public void setName(String name){
        this.name = name;
    }

    //Getter method to retrieve the person's name.
    public String getName() {
        return name;
    }

    //Setter method to update the person's surname.
    public void setSurname(String surname){
        this.surname = surname;
    }

    //Getter method to retrieve the person's surname.
    public String getSurname() {
        return surname;
    }

    //Setter method to update the person's email.
    public void setEmail(String email){
        this.email = email;
    }

    //Getter method to retrieve the person's email.
    public String getEmail() {
        return email;
    }



    //static method to Display person information
    public static String print_person_information(Person person){
        return ("Person Information :"+ "\n") +
                ("              Name    : " + person.getName()+ "\n") +
                ("              Surname : " + person.getSurname()+ "\n") +
                ("              Email   : " + person.getEmail());
    }
}