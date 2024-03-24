package tests;

import model.*;
import repository.inMemoryRepo.InMemoryCleanerRepo;
import repository.inMemoryRepo.InMemoryClientRepo;
import repository.inMemoryRepo.InMemoryRoomRepo;
import service.ClientController;
import utils.CustomIllegalArgument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ClientControllerTest {

    InMemoryRoomRepo roomRepo = new InMemoryRoomRepo();

    InMemoryClientRepo clientRepo = new InMemoryClientRepo();
    ClientController clientController = new ClientController(clientRepo,roomRepo,new InMemoryCleanerRepo());

    @org.junit.jupiter.api.Test
    void searchAvailableRoom() {
        Room room1 = new Room(Type.SINGLE,100,1);
        Room room2 = new Room(Type.DOUBLE,200,2);
        Room room3 = new Room(Type.TRIPLE,300,3);
        List<Room>rooms = new ArrayList<>();
        List<Room>availableRooms1;
        roomRepo.add(room1);
        roomRepo.add(room2);
        roomRepo.add(room3);

        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        rooms.add(room1);
        Option option = new Option(100,rooms);
        clientController.makeReservation(option,client.getId(),LocalDate.of(2003,2,1), LocalDate.of(2003,2,3));

        availableRooms1 = clientController.searchAvailableRoom(LocalDate.of(2003,2,1), LocalDate.of(2003,2,3));

        assert(!availableRooms1.contains(room1));
        assert(availableRooms1.contains(room2));
        assert(availableRooms1.contains(room3));

        List<Room>availableRooms2;
        availableRooms2 = clientController.searchAvailableRoom(LocalDate.of(2003,2,1), LocalDate.of(2003,2,2));
        assert(!availableRooms2.contains(room1));
        assert(availableRooms2.contains(room2));
        assert(availableRooms2.contains(room3));

        List<Room>availableRooms3;
        availableRooms3 = clientController.searchAvailableRoom(LocalDate.of(2003,1,1), LocalDate.of(2003,2,10));
        assert(!availableRooms3.contains(room1));
        assert(availableRooms3.contains(room2));
        assert(availableRooms3.contains(room3));

        System.out.println("searchAvailableRoom test works good");
    }



    @org.junit.jupiter.api.Test
    void makeReservationWithCoupon() {
        Room room1 = new Room(Type.SINGLE,100,1);
        Room room2 = new Room(Type.DOUBLE,200,2);
        Room room3 = new Room(Type.TRIPLE,300,3);
        List<Room>rooms = new ArrayList<>();
        List<Room>availableRooms1;
        roomRepo.add(room1);
        roomRepo.add(room2);
        roomRepo.add(room3);

        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        rooms.add(room1);
        Option option = new Option(100,rooms);
        Coupon c = new Coupon(50);

        //c.setCode(90000);
        client.addCoupon(c);
        clientController.makeReservationWithCoupon(option,c.getCode(),client.getId(),LocalDate.of(2003,2,1), LocalDate.of(2003,2,3));
        int resId = client.getReservationList().size() - 1;
        Reservation res = client.getReservationList().get(resId);

        assert res.getStart().equals(LocalDate.of(2003,2,1));
        assert res.getEnd().equals(LocalDate.of(2003,2,3));
        assert res.getPrice() == 50;

    }

    @org.junit.jupiter.api.Test
    void makeReservation() {
        Room room1 = new Room(Type.SINGLE,100,1);
        Room room2 = new Room(Type.DOUBLE,200,2);
        Room room3 = new Room(Type.TRIPLE,300,3);
        List<Room>rooms = new ArrayList<>();
        List<Room>availableRooms1;
        roomRepo.add(room1);
        roomRepo.add(room2);
        roomRepo.add(room3);

        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        rooms.add(room1);
        Option option = new Option(100,rooms);
        clientController.makeReservation(option,client.getId(),LocalDate.of(2003,2,1), LocalDate.of(2003,2,3));
        int resId = client.getReservationList().size() - 1;
        Reservation res = client.getReservationList().get(resId);

        assert res.getStart().equals(LocalDate.of(2003,2,1));
        assert res.getEnd().equals(LocalDate.of(2003,2,3));
        assert res.getPrice() == 100;
    }

    @org.junit.jupiter.api.Test
    void deleteReservation() {
        Room room1 = new Room(Type.SINGLE,100,1);
        Room room2 = new Room(Type.DOUBLE,200,2);
        Room room3 = new Room(Type.TRIPLE,300,3);
        List<Room>rooms = new ArrayList<>();
        List<Room>availableRooms1;
        roomRepo.add(room1);
        roomRepo.add(room2);
        roomRepo.add(room3);

        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        rooms.add(room1);
        Option option = new Option(100,rooms);
        clientController.makeReservation(option,client.getId(),LocalDate.of(2003,2,1), LocalDate.of(2003,2,3));

        Reservation r = client.getReservationList().get(0);
        assertEquals(clientController.deleteReservation(r.getId(), client.getId()), r);
        System.out.println("Delete Reservation works!");

    }

    @org.junit.jupiter.api.Test
    void addCoupon() {
        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        List<Coupon> coupons = new ArrayList<>();
        Coupon c = new Coupon(40);
        Coupon c2 = new Coupon(50);
        Coupon c3 = new Coupon(60);
        coupons.add(c);
        coupons.add(c2);
        coupons.add(c3);
        clientController.findUserById(client.getId()).addCoupon(c);
        clientController.findUserById(client.getId()).addCoupon(c2);
        clientController.findUserById(client.getId()).addCoupon(c3);
        assert  clientController.findUserById(client.getId()).getCouponList().equals(coupons);
    }

    @org.junit.jupiter.api.Test
    void removeCoupon() {
        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        List<Coupon> coupons = new ArrayList<>();
        Coupon c = new Coupon(40);
        Coupon c2 = new Coupon(50);
        Coupon c3 = new Coupon(60);
        coupons.add(c);
        coupons.add(c2);
        coupons.add(c3);
        clientController.findUserById(client.getId()).addCoupon(c);
        clientController.findUserById(client.getId()).addCoupon(c2);
        clientController.findUserById(client.getId()).addCoupon(c3);
        clientController.findUserById(client.getId()).removeCoupon(c2);
        coupons.remove(c2);
        assert  clientController.findUserById(client.getId()).getCouponList().equals(coupons);
    }

    @org.junit.jupiter.api.Test
    void findCouponById() {
        Client client = new Client("a","a","a","a");
        clientRepo.add(client);
        List<Coupon> coupons = new ArrayList<>();
        Coupon c = new Coupon(40);
        Coupon c2 = new Coupon(50);
        Coupon c3 = new Coupon(60);
        coupons.add(c);
        coupons.add(c2);
        coupons.add(c3);
        clientController.findUserById(client.getId()).addCoupon(c);
        clientController.findUserById(client.getId()).addCoupon(c2);
        clientController.findUserById(client.getId()).addCoupon(c3);
        assert clientController.findCouponById(c.getCode(),client.getId()).equals(c);
    }

    @org.junit.jupiter.api.Test
    void login() {
        Client client = new Client("a","a","a","a");
        clientRepo.add(client);

        CustomIllegalArgument exception = assertThrows(CustomIllegalArgument.class, () -> clientController.login("a","b"));
        assertEquals("Invalid credentials!", exception.getMessage());

        System.out.println("Login test works good");
    }
}