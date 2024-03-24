package views;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Cleaner;
import model.Cleaning;
import model.Room;
import service.CleanerController;

public class CleanerView {
    private CleanerController cleanercontroller;
    private Scanner myObj;

    public CleanerView(CleanerController cleanercontroller) {
        this.cleanercontroller = cleanercontroller;
        this.myObj = new Scanner(System.in);
    }

   public Cleaner registerStatus()
    {
        System.out.println("Please enter your first Name:");
        String firstName = myObj.nextLine();
        System.out.println("Please enter your last Name:");
        String lastName = myObj.nextLine();
        String username;
        do{
            System.out.println("Please enter your username:");
            username = myObj.nextLine();
            if(cleanercontroller.findUserByUsername(username)!= null){
                System.out.println("This username is already Taken.");
            }
        } while(cleanercontroller.findUserByUsername(username)!= null);

        System.out.println("Please enter your password:");
        String password = myObj.nextLine();
        Cleaner c = cleanercontroller.register(firstName,lastName,username,password);
        if(c != null){
            System.out.println("Cleaner registered successfully!");
        }
        return c;
    }
    public Cleaner loginStatus()
    {
        System.out.println("Please enter your username:");
        String username = myObj.nextLine();
        System.out.println("\n");
        System.out.println("Please enter your password:");
        String password = myObj.nextLine();
        System.out.println("\n");
        Cleaner c = cleanercontroller.login(username,password);
        if(c != null){
            System.out.println("User " + username + " is logged in \n" );
        }
        return c;
    }
    public void changePasswordStatus(Integer id)
    {
        System.out.println("Enter your new Password:");
        String password = myObj.nextLine();
        cleanercontroller.changePassword(id, password);
    }
    public void changeDetailsStatus (Integer id)
    {
        System.out.println("Enter your new First Name:");
        String newFirstName = myObj.nextLine();
        System.out.println("Enter your new Last Name:");
        String newLastName = myObj.nextLine();
        cleanercontroller.changeDetails(newFirstName, newLastName, id);
    }
    public void cleanRoomStatus(int cleanerid)
    {
        try {
            System.out.println("What room would you like to clean?\n");
            int roomId = Integer.parseInt(myObj.nextLine());
            System.out.println("When ? ");
            System.out.print("year = ");
            int year = Integer.parseInt(myObj.nextLine());
            System.out.print("month = ");
            int month = Integer.parseInt(myObj.nextLine());
            System.out.print("day = ");
            int day = Integer.parseInt(myObj.nextLine());
            LocalDate date = LocalDate.of(year, month, day);
            cleanercontroller.cleanRoom(cleanerid, roomId, date);
            System.out.println("Room cleaned successfully!");
        }
        catch (Exception exception) {
            System.out.println("Invalid input type!");
        }
    }
    public void printCleanedRooms(int cleanerId)
    {
        List<Cleaning> cleanings = cleanercontroller.getPersonalCleanings(cleanerId);
        if(cleanings.size() == 0){
            System.out.println("You haven't cleaned any room yet!");
        }
        else {
            System.out.println("These rooms have been cleaned by you:");
            for (Cleaning c : cleanings)
            {
                System.out.println(c.toString()) ;
            }
        }
        System.out.println('\n');
    }

    public void showUserDetails(String username) {
        Cleaner cleaner = cleanercontroller.findUserByUsername(username);
        System.out.println("These are your personal details:");
        System.out.println("Name: " + cleaner.getLastName() + " " + cleaner.getFirstName());
        System.out.println("Username: " + cleaner.getUsername());
        System.out.println("Password: " + cleaner.getPassword());
        System.out.println("Salary: " + cleaner.getSalary());
    }

    public void printAllRooms() {
        System.out.println("These rooms are in our system:\n");
        for(Room r : cleanercontroller.getRooms()){
            System.out.println(r);
        }
        System.out.println('\n');
    }

    public Cleaner findCleanerByUsername(String username) {
        return cleanercontroller.findUserByUsername(username);
    }
}
