package service;

import model.Cleaner;
import model.Cleaning;
import model.Room;
import model.Type;
import repository.ICleanerRepository;
import repository.ICleaningRepository;
import repository.IRoomRepository;

import java.time.LocalDate;
import java.util.List;

public class CleanerController {
    private ICleanerRepository cleanerRepo;
    private IRoomRepository roomRepo;
    private ICleaningRepository cleaningRepo;

    public CleanerController(ICleanerRepository cleanerRepo, IRoomRepository roomRepo, ICleaningRepository cleaningRepo) {
        this.cleanerRepo = cleanerRepo;
        this.roomRepo = roomRepo;
        this.cleaningRepo = cleaningRepo;
        populate_cleanings();
    }

    /**
     * Populates the cleanings repo
     */
    private void populate_cleanings(){
        Room rooms1 = roomRepo.findById(1);
        Room rooms2 =  roomRepo.findById(2);

        cleanRoom(1,rooms1.getId(),LocalDate.of(2022,12,12));
        cleanRoom(1,rooms2.getId(),LocalDate.of(2022,12,12));
        cleanRoom(2,9,LocalDate.of(2022,12,12));
        cleanRoom(2,4,LocalDate.of(2020,1,1));
        cleanRoom(2,4,LocalDate.of(2019,1,1));
        cleanRoom(2,3,LocalDate.of(2019,1,1));
        cleanRoom(3,4,LocalDate.of(2018,1,1));
        cleanRoom(4,5,LocalDate.of(2018,1,1));
        cleanRoom(5,6,LocalDate.of(2018,1,1));
        cleanRoom(5,6,LocalDate.of(2018,5,1));
        cleanRoom(4,5,LocalDate.of(2018,5,1));
        cleanRoom(3,4,LocalDate.of(2018,5,1));
        cleanRoom(3,9, LocalDate.of(2018,5,1));
        cleanRoom(3,10, LocalDate.of(2018,5,1));
    }

    /**
     * Registers the new client
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param username the username of the client
     * @param password the password of the client
     * @return the cleaner registered or null if the cleaner username already exists
     */
    public Cleaner register(String firstName, String lastName, String username, String password){
        if (cleanerRepo.findByUsername(username) == null){
            Cleaner c = new Cleaner(firstName,lastName,username,password);
            cleanerRepo.add(c);
            return c;
        }
        return null;
    }

    /**
     * Logs the client in
     * @param username the username of the client
     * @param password the password of the client
     * @return the client or null if the client doesn't exist
     */
    public Cleaner login(String username, String password){
        for( Cleaner c : cleanerRepo.getAll()){
            if(c.getUsername().equals(username) && c.getPassword().equals(password)){
                return c;
            }
        }
        return null;
    }

    /**
     * Changes the password of a client
     * @param id the id of the client
     * @param newPassword the new password
     */
    public void changePassword(Integer id, String newPassword){
        if (cleanerRepo.findById(id)!= null)
        {
            cleanerRepo.findById(id).setPassword(newPassword);
        }
    }

    /**
     * Changes first name and last name of a client
     * @param firstName the new first name of the client
     * @param lastName the new last name of the client
     * @param id the id of the client
     */
    public void changeDetails(String firstName, String lastName, Integer id)
    {
        if (cleanerRepo.findById(id)!=null) {
            cleanerRepo.findById(id).setFirstName(firstName);
            cleanerRepo.findById(id).setLastName(lastName);
        }
    }

    /**
     * Adds a cleaning to a cleaner
     * @param cleanerId the id of the cleaner
     * @param roomId the id of the room to clean
     * @param date the date of the cleaning
     */
    public void cleanRoom(int cleanerId, int roomId, LocalDate date)
    {
        Cleaner c  = cleanerRepo.findById(cleanerId);
        Room r  = roomRepo.findById(roomId);
        Cleaning cleaning = new Cleaning(c,r,date);
        cleaningRepo.addCleaning(cleaning);
    }

    /**
     * Finds a cleaner by his username
     * @param username the username of the cleaner
     * @return the cleaner or null if not found
     */
    public Cleaner findUserByUsername(String username)
    {
        for(Cleaner c : cleanerRepo.getAll())
        {
            if(c.getUsername().equals(username))
            {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds all cleanings for cleaner
     * @param cleanerId the id of the cleaner
     * @return the cleanings for a cleaner
     */
    public List<Cleaning> getPersonalCleanings(int cleanerId){
        return cleaningRepo.getCleaningsForCleaner(cleanerId);
    }

    /**
     * @return a list of all rooms
     */
    public List<Room> getRooms(){
        return roomRepo.getAll();
    }
}
