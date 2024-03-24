import model.*;
import repository.ICleanerRepository;
import repository.ICleaningRepository;
import repository.IClientRespository;
import repository.IRoomRepository;
import repository.databaseRepo.databaseCleanerRepo;
import repository.databaseRepo.databaseCleaningRepo;
import repository.databaseRepo.databaseClientRepo;
import repository.databaseRepo.databaseRoomRepo;
import repository.inMemoryRepo.*;
import service.CleanerController;
import service.ClientController;
import service.ManagerController;
import views.CleanerView;
import views.ClientView;
import views.ManagerView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Ui {
    // nu ar trebui  sa initializam repo cu aceste liste si sa facem constructoarele de la ele goale si pe moment sa le popoulam cu functiile de populate


    ICleanerRepository cleanerRepo;
    IClientRespository clientRepo;
    IRoomRepository roomRepo;
    ICleaningRepository cleaningRepo;

    ClientController clientController;
    CleanerController cleanerController;
    ManagerController managerController;

    ClientView clientView;
    CleanerView cleanerView;
    ManagerView managerView;

    public Ui(boolean memory) {

        if(memory){
            // Cleaner Controller
            this.cleanerRepo = new InMemoryCleanerRepo();
            this.cleaningRepo = new InMemoryCleaningRepo();
            // Client Controller
            this.clientRepo = new InMemoryClientRepo();
            this.roomRepo = new InMemoryRoomRepo();
        }
        else {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
            EntityManager manager = factory.createEntityManager();

            // Cleaner Controller
            this.cleanerRepo = new databaseCleanerRepo(manager);
            this.cleaningRepo = new databaseCleaningRepo(manager);
            // Client Controller
            this.clientRepo = new databaseClientRepo(manager);
            this.roomRepo = new databaseRoomRepo(manager);
        }
        // Cleaner Controller
        this.cleanerController = new CleanerController(cleanerRepo, roomRepo, cleaningRepo);

        // Client Controller
        this.clientController = new ClientController(clientRepo, roomRepo, cleanerRepo);

        // Manager Controller
        this.managerController = new ManagerController(roomRepo, clientRepo, cleanerRepo, cleaningRepo,"parola1");
    }


    public void createView(int option){
        switch (option) {
            case 1 -> {
                this.clientView = new ClientView(clientController);
                System.out.println("You are redirected to the Client menu..");
                showOptionsClient();
            }
            case 2 -> {
                this.managerView = new ManagerView(managerController);
                System.out.println("You are redirected to the Manager menu..");
                showOptionsManager();
            }
            case 3 -> {
                this.cleanerView = new CleanerView(cleanerController);
                System.out.println("You are redirected to the Cleaner menu..");
                 showOptionsCleaner();
            }
            default -> System.out.println("Invalid option!");
        }
    }
    public void showMenu(){
        System.out.println("""
                Hello! Please choose your connect mode!
                1. Client
                2. Manager
                3. Cleaner
                0. Exit
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        }
        catch (Exception exception){
            System.out.println("Invalid input type!");
            showMenu();
            return;
        }
        if (option == 0) {
            System.exit(0);
        }else {
            if (option < 4 && option > 0) {
                createView(option);
            }
            else {
                System.out.println("Invalid option!");
                showMenu();
            }
        }
    }

    public void showOptionsManager(){
        System.out.println("""
                You are in Manager mode!
                0. Back
                1. Login
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception)
        {
            System.out.println("Invalid input type!");
            showOptionsManager();
            return;
        }
        if(option == 0){
            showMenu();
        } else if (option == 1) {
            if (managerView.loginStatus()) {
                managerMenu();
            }
            else{
                showOptionsManager();
            }
        }
        else
        {
            System.out.println("Invalid option!");
            showOptionsManager();
        }
    }
    public void showOptionsClient(){
        System.out.println("""
                You are in Client mode!
                0. Back
                1. Login
                2. Register
                3. Exit
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception) {
            System.out.println("Invalid input type!");
            showOptionsClient();
            return;
        }
        if (option == 3) {
            System.exit(0);
        }
        if (option == 0) {
            showMenu();
        } else if (option == 1) {
            Client client = clientView.loginStatus();
            if (client != null) {
                clientMenu(client.getId());
            } else {
                showOptionsClient();
            }
        } else if (option == 2) {
            Client client = clientView.registerStatus();
            if (client != null) {
                clientMenu(client.getId());
            } else {
                showOptionsClient();
            }
        } else {
            showOptionsClient();
        }
    }
    public void showOptionsCleaner(){
        System.out.println("""
                You are in Cleaner mode!
                0. Back
                1. Login
                2. Register
                3. Exit
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception) {
            System.out.println("Invalid input type!");
            showOptionsCleaner();
            return;
        }
        if(option == 3){
            System.exit(0);
        }
        if(option == 0){
            showMenu();
        } else if (option == 1) {
            Cleaner cleaner = cleanerView.loginStatus();
            if (cleaner != null) {
                cleanerMenu(cleaner.getUsername());
            }
            else{
                showOptionsCleaner();
            }
        }
        else if (option == 2) {
            Cleaner cleaner = cleanerView.registerStatus();
            if (cleaner != null) {
                cleanerMenu(cleaner.getUsername());
            }
            else{
                showOptionsCleaner();
            }
        }
        else {
            System.out.println("Invalid option!");
            showOptionsCleaner();
        }
    }
    private void clientMenu(int id){
        Client client = clientController.findUserById(id);
        System.out.println("""
                Hello Client!
                Choose an option!
                RESERVATION
                1. Make a reservation
                2. Show all reservations
                3. Delete a reservation
                4. Show Coupons
                PERSONAL INFO
                5. Change FirstName and Last Name
                6. Change Password
                7. See your details
                EXIT
                0.  Logout
                -1. Exit
                
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception) {
                System.out.println("Invalid input type!");
                clientMenu(client.getId());
                return;
        }
        if (option == -1) {
            System.exit(0);
        } else if (option == 0) {
            showMenu();
        } else if (option == 1) {
            clientView.optionPart(client.getId());
            clientMenu(client.getId());
        } else if (option == 2) {
            clientView.printAllReservations(client.getId());
            clientMenu(client.getId());
        } else if (option == 3) {
            clientView.deleteReservationStatus(client.getId());
            clientMenu(client.getId());
        } else if (option == 4) {
            clientView.showCoupons(client.getId());
            clientMenu(client.getId());
        } else if (option == 5) {
            clientView.changeDetailsStatus(client.getId());
            clientMenu(client.getId());
        } else if (option == 6) {
            clientView.changePasswordStatus(client.getId());
            clientMenu(client.getId());
        } else if (option == 7) {
            clientView.printDetails(client.getId());
            clientMenu(client.getId());
        } else {
            System.out.println("Invalid option!");
            clientMenu(client.getId());
        }
    }

    private void cleanerMenu(String username){
        Cleaner cleaner = cleanerView.findCleanerByUsername(username);
        System.out.println("""
                Hello Cleaner!
                Choose an option!
                RESERVATION
                1. Clean Room
                2. Show cleaned rooms
                3. Show all rooms
                PERSONAL INFO
                4. Change FirstName and Last Name
                5. Change Password
                6. See your details
                EXIT
                0.  Logout
                -1. Exit
               
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception){
            System.out.println("Invalid input type!");
            cleanerMenu(cleaner.getUsername());
            return;
        }
        if (option == -1) {
            System.exit(0);
        } else if (option == 0) {
            showMenu();
        } else if (option == 1) {
            cleanerView.cleanRoomStatus(cleaner.getId());
            cleanerMenu(cleaner.getUsername());
        } else if (option == 2) {
            cleanerView.printCleanedRooms(cleaner.getId());
            cleanerMenu(cleaner.getUsername());
        } else if (option == 3) {
            cleanerView.printAllRooms();
            cleanerMenu(cleaner.getUsername());
        } else if (option == 4) {
            cleanerView.changeDetailsStatus(cleaner.getId());
            cleanerMenu(cleaner.getUsername());
        } else if (option == 5) {
            cleanerView.changePasswordStatus(cleaner.getId());
            cleanerMenu(cleaner.getUsername());
        } else if (option == 6) {
            cleanerView.showUserDetails(cleaner.getUsername());
            cleanerMenu(cleaner.getUsername());
        }
        else {
            System.out.println("Invalid option!");
            cleanerMenu(cleaner.getUsername());
        }
    }
    private void managerMenu(){
        System.out.println("""
                Hello Manager!
                Choose an option!
                0.  Logout
                1.  Change password
                CLIENTS
                2.  See all clients
                3.  Find client by username
                4.  Delete client
                ROOMS
                5.  See all rooms
                6.  Add room
                7.  Delete room
                8.  Update room details
                9.  Find room by id
                CLEANERS
                10.  See all cleaners
                11. Find cleaner by username
                12. Modify salary for cleaner
                13. Delete cleaner
                CLEANINGS
                14. See all cleanings
                15. See all cleanings for cleaner
                16. See all cleanings for room
                RESERVATIONS
                17. See all available rooms
                18. See all reservations
                19. Exit
                
                Enter your option:""");
        Scanner myObj = new Scanner(System.in);
        int option;
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception exception) {
            System.out.println("Invalid input type! Esti naspa");
            managerMenu();
            return;
        }
        switch (option) {
            case 0 -> showMenu();
            case 1 -> managerView.changePassword();
            case 2 -> managerView.printAllClients();
            case 3 -> managerView.findClientByUsernameStatus();
            case 4 -> managerView.deleteClientStatus();
            case 5 -> managerView.printAllRooms();
            case 6 -> managerView.addRoomStatus();
            case 7 -> managerView.deleteRoomStatus();
            case 8 -> managerView.updateRoomStatus();
            case 9 -> managerView.findRoomByIdStatus();
            case 10 -> managerView.printAllCleaners();
            case 11 -> managerView.findCleanerByUsernameStatus();
            case 12 -> managerView.setSalarySatus();
            case 13 -> managerView.deleteCleanerStatus();
            case 14 -> managerView.printAllCleanings();
            case 15 -> managerView.printCleaningsForCleaner();
            case 16 -> managerView.printCleaningsForRoom();
            case 17 -> managerView.seeAllAvailableRooms();
            case 18 -> managerView.seeAllReservations();
            case 19 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
        managerMenu();
    }
}
