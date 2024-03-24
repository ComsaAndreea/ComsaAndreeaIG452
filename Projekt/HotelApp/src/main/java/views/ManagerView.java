package views;

import model.*;
import service.ManagerController;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ManagerView {
    private ManagerController managercontroller;
    private Scanner myObj;

    public ManagerView(ManagerController managercontroller) {
        this.managercontroller = managercontroller;
        this.myObj = new Scanner(System.in);
    }

    public boolean loginStatus() {
        System.out.println("Please enter your password:");
        try {
            String password = myObj.nextLine();
            if (managercontroller.login(password)) {
                System.out.println("Welcome to your designated space\n");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Invalid password!");
        }
        return false;
    }

    // CLIENT

    public void printAllClients() {
        List<Client> clients = managercontroller.seeAllClients();
        System.out.println("These clients are in our system\n");
        for (Client c : clients) {
            System.out.println(c.toString());
        }
    }

    public void findClientByUsernameStatus() {
        try {
            System.out.println("Enter the username of the client you want to find:");
            String username = myObj.nextLine();

            Client client = managercontroller.findClientByUsername(username);

            if (client == null) {
                System.out.println("Client not found! Check the username and try again!\n");
            } else {
                System.out.println("The client is:\n" + client + "\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }

    }

    public void deleteClientStatus() {
        System.out.println("Enter the id of the client you want to delete:");
        try {
            int id = Integer.parseInt(myObj.nextLine());
            managercontroller.deleteClient(id);
            System.out.println("Client successfully deleted!\n");
        } catch (Exception exception) {
            System.out.println("Invalid input type!");
        }
    }

    // ROOM

    public void printAllRooms() {
        List<Room> rooms = managercontroller.seeAllRooms();
        if (rooms.size() == 0) {
            System.out.println("There is no room yet!");
        } else {
            System.out.println("These rooms are in our system:\n");
            for (Room r : rooms) {
                System.out.println(r.toString());
            }
        }
    }

    public void addRoomStatus() {
        System.out.println("Enter the data of the room you want to create:");
        System.out.println("Enter type (1-SINGLE, 2-DOUBLE, 3-TRIPLE, 4-APARTMENT:");
        try {
            int typeint = Integer.parseInt(myObj.nextLine());
            Type type;
            if (typeint == 1) {
                type = Type.SINGLE;
            } else if (typeint == 2) {
                type = Type.DOUBLE;
            } else if (typeint == 3) {
                type = Type.TRIPLE;
            } else {
                type = Type.APARTMENT;
            }

            System.out.println("Enter price:");
            double price = Double.parseDouble(myObj.nextLine());
            System.out.println("Enter capacity (persons):");
            int nrPers = Integer.parseInt(myObj.nextLine());
            managercontroller.addRoom(type, price, nrPers);
            System.out.println("Room successfully added!\n");

        } catch (Exception exception) {
            System.out.println("Invalid input type!");
        }
    }

    public void deleteRoomStatus() {
        System.out.println("Enter the id of the room you want to delete:");
        try {
            int id = Integer.parseInt(myObj.nextLine());
            if (managercontroller.deleteRoom(id) != null) {
                System.out.println("Room deleted successfully!\n");
            } else {
                System.out.println("Room not found! Check the id and try again!\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void updateRoomStatus() {
        System.out.println("Enter the id of the room you want to update:");
        try {
            int id = Integer.parseInt(myObj.nextLine());

            System.out.println("Enter new type (1-SINGLE, 2-DOUBLE, 3-TRIPLE, 4-APARTMENT:");
            int typeint = Integer.parseInt(myObj.nextLine());
            Type type;
            if (typeint == 1) {
                type = Type.SINGLE;
            } else if (typeint == 2) {
                type = Type.DOUBLE;
            } else if (typeint == 3) {
                type = Type.TRIPLE;
            } else {
                type = Type.APARTMENT;
            }

            System.out.println("Enter new price:");
            int price = Integer.parseInt(myObj.nextLine());
            System.out.println("Enter new capacity (persons):");
            int nrPers = Integer.parseInt(myObj.nextLine());
            Room room = managercontroller.findRoomById(id);
            if (room != null) {

                Room status = managercontroller.updateRoom(id, new Room(type, price, nrPers));
                System.out.println(status);
            } else {
                System.out.println("Room not found! Check the id and try again!\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void findRoomByIdStatus() {
        System.out.println("Enter the id of the room you want to find:");
        try {
            int id = Integer.parseInt(myObj.nextLine());
            Room room = managercontroller.findRoomById(id);

            if (room == null) {
                System.out.println("Room not found! Check the id and try again!\n");
            } else {
                System.out.println("The room is:\n" + room + "\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    // CLEANER

    public void printAllCleaners() {
        List<Cleaner> cleaners = managercontroller.seeAllCleaners();
        if (cleaners.size() == 0) {
            System.out.println("There is no cleaner in our system yet!");
        } else {
            System.out.println("These cleaners are in our system\n");
            for (Cleaner c : cleaners) {
                System.out.println(c.toString());
            }
        }
    }

    public void findCleanerByUsernameStatus() {
        System.out.println("Enter the username of the cleaner you want to find:");
        try {
            String username = myObj.nextLine();

            Cleaner cleaner = managercontroller.findCleanerByUsername(username);

            if (cleaner == null) {
                System.out.println("Cleaner not found! Please check the username and try again!\n");
            } else {
                System.out.println("The cleaner is:\n" + cleaner + "\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void setSalarySatus() {
        try {
            System.out.println("Enter the id of the cleaner you want to change salary:");
            int id = Integer.parseInt(myObj.nextLine());
            System.out.println("Enter the new salary for cleaner " + id);
            int salary = Integer.parseInt(myObj.nextLine());

            Cleaner cleaner = managercontroller.findCleanerById(id);
            if (cleaner == null) {
                System.out.println("Cleaner was not found. Check the username and try again!\n");
                return;
            }

            if (managercontroller.setSalaryCleaner(id, salary) != null) {
                System.out.println("Salary was successfully changed!\n");
            } else {
                System.out.println("Cleaner was not found. Check the username and try again!\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void deleteCleanerStatus() {
        System.out.println("Enter the id of the cleaner you want to delete");
        try {
            int id = Integer.parseInt(myObj.nextLine());

            Cleaner cleaner = managercontroller.findCleanerById(id);
            if (cleaner == null) {
                System.out.println("Cleaner was not found. Check the username and try again!\n");
                return;
            }
            managercontroller.deleteCleaner(id);
            System.out.println("Cleaner successfully deleted!\n");
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    // CLEANING

    public void printCleaningsForCleaner() {
        System.out.println("The cleaner id to show cleanings for:");
        try {
            int cleanerId = Integer.parseInt(myObj.nextLine());
            if(managercontroller.findCleanerById(cleanerId) == null){
                System.out.println("Cleaner not found. Check the id and try again!");
            }
            else {
                List<Cleaning> cleanings = managercontroller.getCleanerCleanings(cleanerId);
                for (Cleaning cleaning : cleanings) {
                    System.out.println(cleaning.toString());
                }
                if (cleanings.size() == 0) {
                    System.out.println("There is no cleaned rooms by this cleaner.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void printCleaningsForRoom() {
        System.out.println("The room id to show cleanings for:");
        try {
            int roomId = Integer.parseInt(myObj.nextLine());

            List<Cleaning> cleanings = managercontroller.getRoomCleanings(roomId);
            for (Cleaning cleaning : cleanings) {
                System.out.println(cleaning.toString());
            }
            if (cleanings.size() == 0) {
                System.out.println("There is no cleaning for this room.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void printAllCleanings() {
        System.out.println("These are the cleanings:");
        for (Cleaning c : managercontroller.getCleanings()) {

            System.out.println("Cleaner " + c.getCleaner().getUsername() + " cleaned room" + c.getRoom().getId() + " on " + c.getCleanDate());
        }
    }

    public void seeAllReservations() {
        for (Client client : managercontroller.seeAllClients()) {
            if (managercontroller.findReservationsForClient(client).size() != 0) {
                System.out.println("\n");
                System.out.println(client.getUsername() + ":");
                for (Reservation r : managercontroller.findReservationsForClient(client)) {
                    System.out.println("\nReservation(" + r.getId() + "):  checkIn " + r.getStart() + " checkout " + r.getEnd() + " Total Price " + r.getPrice() + ", ");
                    System.out.println("Rooms: ");
                    for (Room room : r.getRooms()) {
                        System.out.print("Room(" + room.getId() + "," + room.getType() + "), ");
                    }
                }
            }
        }
        System.out.println('\n');
    }

    public void seeAllAvailableRooms() {

        try {

            System.out.println("When will you be staying with us ?");
            System.out.print("From year = ");
            int year = Integer.parseInt(myObj.nextLine());

            System.out.print("month = ");
            int month = Integer.parseInt(myObj.nextLine());

            System.out.print("day = ");
            int day = Integer.parseInt(myObj.nextLine());

            LocalDate from = LocalDate.of(year, month, day);
            System.out.print("To year = ");
            int year2 = Integer.parseInt(myObj.nextLine());

            System.out.print("month = ");
            int month2 = Integer.parseInt(myObj.nextLine());

            System.out.print("day = ");
            int day2 = Integer.parseInt(myObj.nextLine());

            LocalDate to = LocalDate.of(year2, month2, day2);
            List<Room> rooms = managercontroller.searchAvailableRooms(from, to);
            for (Room r : rooms) {
                System.out.println(r.toString());
            }
        } catch (Exception e) {
            System.out.println("Invalid input type!");
        }
    }

    public void changePassword() {
        System.out.println("Enter the new password:");
        try {
            String password = myObj.nextLine();
            managercontroller.changePassword(password);
            System.out.println("Password changed successfully!");
        } catch (Exception e){
            System.out.println("Invalid input type!");
        }
    }
}
