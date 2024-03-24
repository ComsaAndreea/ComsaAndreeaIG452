package service;

import model.*;
import repository.ICleanerRepository;
import repository.ICleaningRepository;
import repository.IClientRespository;
import repository.IRoomRepository;
import repository.inMemoryRepo.InMemoryRoomRepo;
import utils.CustomIllegalArgument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManagerController {
    private String password;
    private IRoomRepository roomRepo;
    private IClientRespository clientRepo;
    private ICleanerRepository cleanerRepo;
    private ICleaningRepository cleaningRepo;


    public ManagerController(IRoomRepository roomRepo, IClientRespository clientRepo, ICleanerRepository cleanerRepo,ICleaningRepository cleaningRepo, String password) {
        this.password = password;
        this.roomRepo = roomRepo;
        this.clientRepo = clientRepo;
        this.cleanerRepo = cleanerRepo;
        this.cleaningRepo = cleaningRepo;
    }

    // PASSWORD

    public String getPassword() {
        return password;
    }

    /**
     * @param password
     * @return a boolean value which is set to true if the password is equal to the one in the parameter
     */
    public boolean login(String password) {
        return password.equals(this.password);
    }

    /**
     * @param password
     * It changes the previous password with the one given in the parameter
     */
    public void changePassword(String password){
        this.password=password;
    }

    // CLIENT

    /**
     * @return
     * returns all clients from clientrepo
     */
    public List<Client> seeAllClients(){
        return clientRepo.getAll();
    }

    /**
     * @param username
     * @return a Client which is found by Username
     */
    public Client findClientByUsername(String username) {
        return clientRepo.findByUsername(username);
    }

    /**
     * @param id of Client
     * @return the Client which has been deleted. If the Client is not found the returnvalue will be null.
     * We must delete all the reservations and the cupons of a client before deleting a client
     */
    public Client deleteClient(Integer id) {
        Client c = clientRepo.findById(id);
        if(c != null){
            for(Coupon cou : c.getCouponList())
            {
                clientRepo.removeCoupon(cou.getCode(),id);
            }
            for (Reservation res : c.getReservationList())
            {
                clientRepo.removeReservation(res.getId(), id);

            }
            clientRepo.delete(id);
        }
        return c;
    }


    // ROOM


    /**
     * @param checkIn Date of checkin
     * @param checkOut Date of checkout
     * @return a List of Rooms which are available between the given dates
     */
    public List<Room> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = new ArrayList<>(roomRepo.getAll());
        List<Room> unavailableRooms = clientRepo.returnAllUnAvailableRooms(checkIn, checkOut);
        for (Room r : unavailableRooms )
        {
            rooms.remove(r);
        }
        return rooms;
    }

    /**
     * @return a list of all rooms in roomRepo
     */
    public List<Room> seeAllRooms(){
        return roomRepo.getAll();
    }

    /**
     * @param type (Single,Double,Triple,Apartment)
     * @param nrPers the capacity of a room
     * @return checks if the type and capacity match
     */
    private boolean checkTypeRoom (Type type, int nrPers)
    {

        if (type.equals(Type.SINGLE) && nrPers == 1)
        {
            return  true;
        }
        else if (type.equals(Type.DOUBLE) && nrPers == 2)
        {
            return  true;
        }
        else if (type.equals(Type.TRIPLE) && nrPers == 3)
        {
            return  true;
        }
        else if (type.equals(Type.APARTMENT) && nrPers == 4)
        {
            return  true;
        }
        return false;
    }

    /**
     * @param type (Single,Double,Triple,Apartment)
     * @param price price of the room
     * @param nrPers capacity of a room
     */
    public void addRoom(Type type, double price, int nrPers) {
        if (checkTypeRoom(type,nrPers)) {
            roomRepo.add(new Room(type,price,nrPers));
        }
    }


    /**
     * @param id of the room that needs to be deleted
     * @return the Room that has been deleted. Null if Room not found
     * Before deletig a room we must remove all the reservations which include this room and also all the cleanings which include this room
     */
    public Room deleteRoom(Integer id){
        Room r = roomRepo.findById(id);
        if(r != null ) {
            for (Cleaning cleaning : getRoomCleanings(id)) {
                cleaningRepo.deleteCleaning(cleaning);
            }
            if(roomRepo instanceof InMemoryRoomRepo){
                for(Reservation res : this.findReservationsForRoom(id)){
                    for(Client client : clientRepo.getAll()){
                        if(clientRepo.getReservationsForClient(client.getId()).contains(res)){
                            clientRepo.removeReservation(res.getId(), client.getId());
                        }
                    }
                }
            }
            roomRepo.delete(id);
            return r;
        }
        return null;
    }

    /**
     * @param id of the room that will be updated
     * @param room contains the new informations of the room
     * @return returns the updated room
     */
    public Room updateRoom(int id, Room room){
        Room r = roomRepo.findById(id);
        if(r != null )
        {
            roomRepo.update(id,room);
            return r;
        }

        return null;
    }

    /**
     * @param id of a room
     * @return the room that has been found
     */
    public Room findRoomById(int id){
        return roomRepo.findById(id);
    }


    // CLEANER

    /**
     * @return the list of all the cleaners in cleanerRepo
     */
    public List<Cleaner> seeAllCleaners(){
        return cleanerRepo.getAll();
    }

    /**
     * @param username of a cleaner
     * @return the cleaner with the given username
     */
    public Cleaner findCleanerByUsername(String username){
        return cleanerRepo.findByUsername(username);
    }

    /**
     * @param id of a cleaner
     * @return the cleaner with the given id
     */
    public Cleaner findCleanerById(int id){
        return cleanerRepo.findById(id);
    }

    /**
     * @param id of the cleaner
     * @param salary new
     * @return the cleaner with salary value equal to the one given in the parameter
     */
    public Cleaner setSalaryCleaner(int id, int salary)
    {
        Cleaner c = cleanerRepo.findById(id);
        if (c != null)
        {
            c.setSalary(salary);
            return c;
        }
        return null;
    }

    /**
     * @param id of a cleaner
     * @return the cleaner thas been deleted
     */
    public Cleaner deleteCleaner(int id){
        Cleaner c = cleanerRepo.findById(id);
        if(c != null){
            for (Cleaning cleaning : getCleanerCleanings(id))
            {
                cleaningRepo.deleteCleaning(cleaning);
            }
            cleanerRepo.delete(id);
            return c;
        }
        return null;
    }

    // CLEANING

    /**
     * @param roomId id of a room
     * @return a list of all cleanings made in the room with roomid
     */
    public List<Cleaning> getRoomCleanings(int roomId){
        return cleaningRepo.getCleaningsForRoom(roomId);
    }

    /**
     * @param cleanerId id of a cleaner
     * @return a list if all the cleanings made by a cleaner
     */
    public List<Cleaning> getCleanerCleanings(int cleanerId){
        return cleaningRepo.getCleaningsForCleaner(cleanerId);
    }

    /**
     * @return list of all Cleanings from cleaningRepo
     */
    public List<Cleaning> getCleanings(){
        return cleaningRepo.getCleanings();
    }

    // RESERVATION

    /**
     * @return all Reservations from clientRepository
     */
    public List<Reservation> seeAllReservations(){
        return clientRepo.getAllReservations();
    }

    /**
     * @param rid Id of a room
     * @return a list of all the reservations made on a room
     */
    public List<Reservation> findReservationsForRoom(int rid){
        List<Reservation> reservations = new ArrayList<>();
        Room r = roomRepo.findById(rid);
        for(Reservation res : this.seeAllReservations()){
            if(res.getRooms().contains(r)){
                reservations.add(res);
            }
        }
        return reservations;
    }

    /**
     * @param client
     * @return a list of allthe reservations made by a client
     */
    public List<Reservation> findReservationsForClient(Client client) {
        return clientRepo.getReservationsForClient(client.getId());
    }
}
