package service;

import model.*;
import repository.ICleanerRepository;
import repository.IClientRespository;
import repository.IRoomRepository;
import utils.CustomIllegalArgument;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

public class ClientController {

    private final IClientRespository clientRepo;
    private final IRoomRepository roomRepo;
    private ICleanerRepository cleanerRepo;


    public ClientController(IClientRespository clientRepo, IRoomRepository roomRepo, ICleanerRepository cleanerRepo) {
        this.clientRepo = clientRepo;
        this.roomRepo = roomRepo;
        this.cleanerRepo = cleanerRepo;
        populateReservation();
    }

    /**
     * Populates the reservation list
     */
    private void populateReservation(){
        Option option1 = new Option(600,new ArrayList<>(Arrays.asList(roomRepo.findById(1),roomRepo.findById(2))));
        makeReservation(option1,1,LocalDate.of(2022,12,12), LocalDate.of(2022,12,14));
        Option option2 = new Option(1000,new ArrayList<>(Collections.singletonList(roomRepo.findById(9))));
        makeReservation(option2,1,LocalDate.of(2023,2,1), LocalDate.of(2023,2,15));
        Option option3 = new Option(700,new ArrayList<>(Arrays.asList(roomRepo.findById(3),roomRepo.findById(4))));
        makeReservation(option3,2,LocalDate.of(2020,1,1), LocalDate.of(2020,1,5));
        Option option4 = new Option(700,new ArrayList<>(Arrays.asList(roomRepo.findById(3),roomRepo.findById(4))));
        makeReservation(option4,3,LocalDate.of(2019,1,1), LocalDate.of(2019,1,5));
        Option option5 = new Option(1500,new ArrayList<>(Arrays.asList(roomRepo.findById(4),roomRepo.findById(5),roomRepo.findById(6))));
        makeReservation(option5,3,LocalDate.of(2018,1,1), LocalDate.of(2018,1,5));
        Option option6 = new Option(1500,new ArrayList<>(Arrays.asList(roomRepo.findById(4),roomRepo.findById(5),roomRepo.findById(6))));
        makeReservation(option6,4,LocalDate.of(2018,5,1), LocalDate.of(2018,5,5));
        Option option7 = new Option(2000,new ArrayList<>(Arrays.asList(roomRepo.findById(9),roomRepo.findById(10))));
        makeReservation(option7,5,LocalDate.of(2018,5,1), LocalDate.of(2018,5,5));

    }

    /**
     * Finds all available rooms on a specific date
     * @param checkIn the check-in date of the reservation
     * @param checkOut the check-out date of the reservation
     * @return a list of available rooms
     */
    public List<Room> searchAvailableRoom(LocalDate checkIn, LocalDate checkOut)  {
        List<Room> rooms = new ArrayList<>(roomRepo.getAll());
        List<Room> unavailableRooms = clientRepo.returnAllUnAvailableRooms(checkIn, checkOut);
        for (Room r : unavailableRooms )
        {
          rooms.remove(r);
        }
        return rooms;
    }

    /**
     * Finds the coupons of a client
     * @param id the id of the client
     * @return a list of coupons
     */
    public List<Coupon> showCoupons(Integer id) {
        return clientRepo.findById(id).getCouponList();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ALGORITM GENERARE OPTIUNI

    /**
     * Generates all combinations of a number in a range
     * @param n number
     * @param r range
     * @return a list of arrays with the combinations
     */
    // functie generare combinatii de n cate r
    // returneaza o lista de array-uri = combinatii
    private List<int[]> generateCombinations(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }
        while (combination[r - 1] < n) {
            combinations.add(combination.clone());
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }
        return combinations;
    }

    /**
     * Transforms the combinations of numbers in combinations of rooms from a list
     * @param n number
     * @param k range
     * @param nrPersons list of number of persons -> rooms
     * @return a list of combinations of rooms
     */
    // scrie combinatiile de numere ca si combinatii ale unor elemente dintr-o lista de nrPersons
    private List<int[]> transform_combinations(int n, int k, List<Integer> nrPersons){
        List<int[]> list = generateCombinations(n,k);
        int[] room_comb = new int[k];
        List<int[]> room_combs = new ArrayList<>();
        for(int[] i : list){
            int ct = 0;
            for(int j : i){
                room_comb[ct] = nrPersons.get(j);
                ct++;
            }
            room_combs.add(room_comb.clone());
        }
        return room_combs;
    }

    /**
     * Checks if a list of arrays contains an array
     * @param list a list of arrays
     * @param array an array
     * @return true if list contains array or false else
     */
    //verifica daca o lista de array-uri de int-uri contine un array din parametru
    private static boolean contains_array(List<int[]> list, int[] array) {
        for (int[] i : list) {
            if (Arrays.equals(i, array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a room by type in a room list
     * @param rooms the list of rooms
     * @param type the type of rooms
     * @return the first room of the given type or null if it doesn't exist
     */
    private Room findARoomByType(List<Room> rooms,Type type) {
        for(Room r : rooms){
            if(r.getType().equals(type)){
                return r;
            }
        }
        return null;
    }

    /**
     * Finds type of room by the number of persons
     * @param nrPers the number of persons
     * @return type of room
     */
    private Type findTypeByNrPers(int nrPers){
        if(nrPers == 1){
            return Type.SINGLE;
        } else if (nrPers == 2) {
            return Type.DOUBLE;
        } else if (nrPers == 3) {
            return Type.TRIPLE;
        }
        return Type.APARTMENT;
    }

//    public Option cheapestOption(List<Option> options) {
//        Option optionMinPreis = options.get(0);
//        for (Option option : options) {
//            if (option.getTotalPrice() < optionMinPreis.getTotalPrice()) {
//                optionMinPreis.setId(option.getId());
//                optionMinPreis.setRooms(option.getRooms());
//                optionMinPreis.setTotalPrice(option.getTotalPrice());
//            }
//        }
//        return optionMinPreis;
//    }

    /**
     * Generates options for making a reservation
     * @param checkIn the start date of the reservation
     * @param checkOut the end date of the reservation
     * @param nrTotalPers the total number of persons of the reservation
     * @return a list of options sorted by the cheapest option
     */
    public List<Option> generateOptions(LocalDate checkIn, LocalDate checkOut, int nrTotalPers){
        List<Room> availableRooms = searchAvailableRoom(checkIn,checkOut);


        if (availableRooms.size()<1)
        {
            System.out.println("There is no available room, sorry!");
            return null;
        }

        //nrPersList va fi lista de nrPersoane din fiecare camera
        List<Integer> nrPersList = availableRooms.stream()
                .map(Room::getNrPers).toList();

        //calculam combinatiile de toate felurile pentru a obtine optiuni si le punem intr-o lista mare combinations
        List<int[]> combinations = new ArrayList<>();
        for(int i=1;i<nrPersList.size();i++){
            combinations = Stream.concat(combinations.stream(), transform_combinations(nrPersList.size(),i, nrPersList).stream()).toList();
        }

        int totalPrice = 0;
        List<Room> option_rooms = new ArrayList<>();
        List<int[]> options = new ArrayList<>();
        List<Option> final_options = new ArrayList<>();
        int optionId = 1;

        for(int[] i : combinations){
            int ok = 1;
            option_rooms.clear();
            totalPrice = 0;
            if(Arrays.stream(i).sum() == nrTotalPers){
                if(!contains_array(options,i)){
                    List<Room> availableRoomsAux = new ArrayList<>(availableRooms);
                    options.add(i);
                    for(int el : i){
                        Room r = findARoomByType(availableRooms,findTypeByNrPers(el));
                        while(option_rooms.contains(r)){
                            availableRoomsAux.remove(r);
                            r = findARoomByType(availableRoomsAux,findTypeByNrPers(el));
                        }

                    option_rooms.add(r);
                    assert r != null;
                    totalPrice += r.getPrice();
                    }

                    Option option = new Option(totalPrice,option_rooms.stream().toList());
                    option.setId(optionId);
                    final_options.add(option);

                    optionId++;
                }
            }
        }
        int daysBetween = (int) ChronoUnit.DAYS.between(checkIn, checkOut)  ;
        for(Option o : final_options){
            o.setTotalPrice(o.getTotalPrice() * daysBetween);
        }
        final_options.sort(Comparator.comparing(Option::getTotalPrice));
        return final_options;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Makes a reservation with coupon
     * @param option the option
     * @param couponid the id of the coupon
     * @param clientId the id of the client
     * @param start the start date
     * @param end the end date
     * @return the made reservation or null if the coupon not found
     */
    public Reservation makeReservationWithCoupon(Option option, int couponid, int clientId, LocalDate start, LocalDate end) {
        // work to do
       // System.out.println("Se creeaza rezervarea cu cupon.");
        Coupon coupon = findCouponById(couponid,clientId);
        if (coupon!= null)
        {

            Reservation reservation = new Reservation(start, end, applyCoupon(coupon, option.getTotalPrice()));
            // System.out.println("Am stetat pretul rezervarii actualizat.");
            reservation.setRooms(option.getRooms());
            clientRepo.addReservation(reservation,clientId);

            return reservation;
        }
        else return null;
    }

    /**
     * Makes a reservation
     * @param option the option
     * @param clientId the id of the client
     * @param start the start date
     * @param end the end date
     * @return the made reservation
     */
    public Reservation makeReservation(Option option, Integer clientId, LocalDate start, LocalDate end) {
        // work to do
        Reservation reservation = new Reservation(start, end, option.getTotalPrice());
        reservation.setRooms(option.getRooms());
        clientRepo.addReservation(reservation,clientId);
        return reservation;
    }

    /**
     * Applies a coupon on a price
     * @param coupon the coupon
     * @param price the price
     * @return the new price
     */
    private int applyCoupon(Coupon coupon, int price) {

        float per = (float)(100 - coupon.getPercentage()) / 100;
        float price2 =  price * per;
        price = (int) price2;
        return price;

    }

    /**
     * Finds a client by id
     * @param id the id of the client
     * @return the client or null if not found
     */
    public Client findUserById (int id)
    {
        List<Client>clients = clientRepo.getAll();
        for (Client c : clients)
        {
            if (c.getId() == id)
                return  c;
        }
        return null;
    }

    /**
     * Finds a reservation by id
     * @param clientId the id of the client
     * @param reservationId the id of the reservation
     * @return the reservation or null if not found
     */
    public Reservation findReservationById (int clientId, int reservationId)
    {
        List<Client>clients = clientRepo.getAll();
        Reservation reservation = null;
        for (Client c : clients)
        {
            if (c.getId() == clientId)
            {
                List<Reservation>reservations = c.getReservationList();
                for (Reservation res : reservations)
                {
                    if (res.getId() == reservationId)
                        return res;
                }
            }
        }
        return null;
    }

    /**
     * Deletes a reservation
     * @param resId the id of the reservation
     * @param clientId the id of the client
     * @return the deleted reservation or null if not found
     */
    public Reservation deleteReservation(int resId, int clientId) {
        Reservation r = findReservationById(clientId,resId);
        if (r != null) {
            clientRepo.removeReservation(resId, clientId);
            return r;
        }
        return null;
    }

    /**
     * @param id the id of the client
     * @return all reservations of a client
     */
    public List<Reservation> seeAllReservations(int id) {
        return clientRepo.getReservationsForClient(id);
    }

    /**
     * Registers a client
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param username the username of the client
     * @param password the password of the client
     * @return the created client or null if the username already exists
     */
    public Client register(String firstName, String lastName, String username, String password) {
        if (clientRepo.findByUsername(username) == null){
            Client c = new Client(firstName,lastName,username,password);
            clientRepo.add(c);
            return c;
        }
        return null;
    }

    /**
     * Changes data of a client
     * @param newFirstName the new first name
     * @param newLastName the new last name
     * @param id the id of the client
     */
    public void changeDetails(String newFirstName, String newLastName, int id) {
        if (clientRepo.findById(id)!=null) {
            clientRepo.findById(id).setFirstName(newFirstName);
            clientRepo.findById(id).setLastName(newLastName);
        }
    }

    /**
     * Logs a client in
     * @param username the username of the client
     * @param password the password of the client
     * @throws CustomIllegalArgument if the login credentials are not in the system
     */
    public void login(String username, String password)throws CustomIllegalArgument {
        int ok = 0;
        for (Client c : clientRepo.getAll()) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                ok = 1;
                break;
            }
        }
        if (ok == 0 )
            throw  new CustomIllegalArgument("Invalid credentials!");

    }

    /**
     * Changes the password of a client
     * @param id the id of the client
     * @param newPassword the new password of the client
     */
    public void changePassword(int id, String newPassword) {
        Client c = clientRepo.findById(id);
        c.setPassword(newPassword);
        clientRepo.update(id,c);
    }

    /**
     * Find a client by username
     * @param username the username of the client
     * @return the client or null if not found
     */
    public Client findClientByUsername(String username) {
        return clientRepo.findByUsername(username);
    }

    /**
     * Adds a coupon for client
     * @param c the coupon
     * @param clientId the id of the client
     */
    public void addCoupon(Coupon c, int clientId)
    {
        clientRepo.addCoupon(c,clientId);
    }

    /**
     * Removes a coupon
     * @param couponid the id of the coupon
     * @param client_id the id of the client
     */
    public void removeCoupon(int couponid, int client_id)
    {
        clientRepo.removeCoupon(couponid,client_id);
    }

    /**
     * Finds a coupon by id
     * @param couponId the id of the coupon
     * @param clientid the id of the client
     * @return the coupon or null if not found
     */
    public Coupon findCouponById(int couponId, int clientid)
    {
        return clientRepo.findCouponById(couponId,clientid);
    }
}
