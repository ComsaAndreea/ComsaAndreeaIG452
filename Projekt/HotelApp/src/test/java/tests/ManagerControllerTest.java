package tests;

import model.*;
import org.junit.jupiter.api.Test;
import repository.inMemoryRepo.InMemoryCleanerRepo;
import repository.inMemoryRepo.InMemoryCleaningRepo;
import repository.inMemoryRepo.InMemoryClientRepo;
import repository.inMemoryRepo.InMemoryRoomRepo;
import service.ClientController;
import service.ManagerController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerControllerTest {
    private String password;

    InMemoryRoomRepo roomRepo = new InMemoryRoomRepo();
    InMemoryCleanerRepo cleanerRepo = new InMemoryCleanerRepo();

    InMemoryCleaningRepo cleaningRepo = new InMemoryCleaningRepo();
    InMemoryClientRepo clientRepo = new InMemoryClientRepo();

    ManagerController managerController = new ManagerController(roomRepo,clientRepo,cleanerRepo,cleaningRepo,"112");


    @Test
    void login() {
        this.password = "112";
        assert  managerController.login("112");
        assert password.equals("112");
    }

    @Test
    void changePassword() {

        managerController.changePassword("113");
        assert managerController.getPassword().equals("113");

    }

    @Test
    void seeAllClients() {
        Client a  = new Client("a","a","a","a");
        Client a2 = new Client("a","b","e","a");
        Client a3  = new Client("a","c","y","a");

        clientRepo.add(a);
        clientRepo.add(a2);
        clientRepo.add(a3);
        assert managerController.seeAllClients().contains(a);
        assert managerController.seeAllClients().contains(a2);
        assert managerController.seeAllClients().contains(a3);
        assert managerController.seeAllClients().size() == 6;
    }

    @Test
    void findClientByUsername() {
        Client a  = new Client("a","a","a","a");
        clientRepo.add(a);
        assert managerController.findClientByUsername("a").equals(a);
    }

    @Test
    void deleteClient() {
        Client a  = new Client("a","a","a","a");
        clientRepo.add(a);
        assert managerController.findClientByUsername("a").equals(a);
        managerController.deleteClient(4);
        assert managerController.findClientByUsername("a") == (null);
    }

    @Test
    void searchAvailableRooms() {
        //System.out.println();
        LocalDate date1 = LocalDate.of(2002,1,2);
        LocalDate date2 = LocalDate.of(2002,1,2);
        List<Room> rooms = new ArrayList<>();
        rooms.add(roomRepo.findById(1));
        Reservation res = new Reservation(LocalDate.of(2002,1,1),LocalDate.of(2002,1,9),200);
        res.setRooms(rooms);
        clientRepo.addReservation(res,1);
         rooms = managerController.searchAvailableRooms(date1,date2);
        assert rooms.size() == roomRepo.getAll().size()-1;

    }

    @Test
    void addRoom(){
        managerController.addRoom(Type.SINGLE,2500,8);
        assert roomRepo.getAll().size() == 10;
        managerController.addRoom(Type.SINGLE,2500,1);
        assert roomRepo.getAll().size() == 11;

    }

    @Test
    void updateRoom(){
        Room r = new Room(Type.TRIPLE,400,3);
        managerController.updateRoom(1,r);
        assert roomRepo.findById(1).getType().equals(r.getType()) &&
                roomRepo.findById(1).getPrice() == r.getPrice() &&
                roomRepo.findById(1).getNrPers() == r.getNrPers();
    }

    @Test
    void setSalaryCleaner(){
        assert cleanerRepo.findById(1).getSalary() != 2925;
        managerController.setSalaryCleaner(1,2925);
        assert cleanerRepo.findById(1).getSalary() == 2925;
    }

}