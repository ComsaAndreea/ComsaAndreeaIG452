package tests;

import model.Cleaner;
import model.Client;
import model.Room;
import model.Type;
import org.junit.jupiter.api.Test;
import repository.inMemoryRepo.InMemoryCleanerRepo;
import repository.inMemoryRepo.InMemoryCleaningRepo;
import repository.inMemoryRepo.InMemoryClientRepo;
import repository.inMemoryRepo.InMemoryRoomRepo;
import service.CleanerController;
import service.ClientController;
import utils.CustomIllegalArgument;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CleanerControllerTest {
    InMemoryRoomRepo roomRepo = new InMemoryRoomRepo();
    InMemoryCleanerRepo cleanerRepo = new InMemoryCleanerRepo();

    InMemoryCleaningRepo cleaningRepo = new InMemoryCleaningRepo();
    CleanerController cleanerController = new CleanerController(cleanerRepo, roomRepo,cleaningRepo);


    @Test
    void register() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerController.register("a","a","a","a");
        assert Objects.equals(cleanerRepo.getAll().get(3).getFirstName(), "a");
        assert Objects.equals(cleanerRepo.getAll().get(3).getLastName(), "a");
        assert Objects.equals(cleanerRepo.getAll().get(3).getUsername(), "a");
        assert Objects.equals(cleanerRepo.getAll().get(3).getPassword(), "a");
        System.out.println("Register test works good");
    }

    @Test
    void login() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerRepo.add(cleaner);
        assert cleanerController.login("a","a").equals(cleaner);
        System.out.println("Login test works good");
    }

    @Test
    void changePassword() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerRepo.add(cleaner);
        cleanerController.changePassword(cleaner.getId(),"b");
        assert cleanerRepo.getAll().get(3).getPassword().equals("b");
        assert cleanerRepo.getAll().get(3).getUsername().equals("a");
        System.out.println("ChangePassword test works good");

    }

    @Test
    void changeDetails() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerRepo.add(cleaner);
        cleanerController.changeDetails("b","c",cleaner.getId());
        assert cleanerRepo.getAll().get(3).getFirstName().equals("b");
        assert cleanerRepo.getAll().get(3).getLastName().equals("c");
        System.out.println("ChangeDetails test works good");
    }

    @Test
    void cleanRoom() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerRepo.add(cleaner);
        Room r = new Room(Type.SINGLE,200,1);
        roomRepo.add(r);
        LocalDate date  = LocalDate.of(2002,1,1);
        cleanerController.cleanRoom(cleaner.getId(), r.getId(),date);
        assert cleaningRepo.getCleanings().get(14).getCleanDate().equals(date);
        System.out.println("cleanRoom test works good");

    }

    @Test
    void findUserByUsername() {
        Cleaner cleaner = new Cleaner("a","a","a","a");
        cleanerRepo.add(cleaner);
        assert cleanerController.findUserByUsername("a").getUsername().equals("a");
        System.out.println("findUserByUsername test works good");

    }


}