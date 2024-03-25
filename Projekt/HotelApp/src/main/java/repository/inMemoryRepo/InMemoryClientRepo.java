package repository.inMemoryRepo;

import model.Client;
import model.Coupon;
import model.Reservation;
import model.Room;
import repository.IClientRespository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryClientRepo implements IClientRespository {

    private List<Client> clients;
    private int clientId;
    private int coupon_id;
    private int reservation_id;
    public InMemoryClientRepo( ) {
        this.clients = new ArrayList<>();
        clientId = 0;
        coupon_id = 0;
        reservation_id = 0;
        populate_clients();
    }

    private void populate_clients(){
        Client client1 = new Client("Bob", "Pop","bobpop","00bob");
        Client client2 = new Client("Laura", "Georgescu","laurgeor","22laura");
        Client client3 = new Client("Catalin", "Olariu","cataola","24catalin");
        this.add(client1);
        this.add(client2);
        this.add(client3);
    }

    @Override
    public void add(Client client) {
        clientId++;
        client.setId(clientId);
        clients.add(client);
    }

    @Override
    public void delete(Integer id) {
        clients.remove(findById(id));
    }

    @Override
    public void update(Integer id, Client client) {
        findById(id).setFirstName(client.getFirstName());
        findById(id).setLastName(client.getLastName());
    }

    @Override
    public Client findByUsername(String username) {
        for(Client c : clients){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }
    @Override
    public Client findById(Integer id){
        for(Client c : clients){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        return clients;
    }

    // RESERVATION

    public void addReservation(Reservation r, int clientId)
    {
        reservation_id++;
        r.setId(reservation_id);
        Client c = findById(clientId);
        if(c != null){
            c.addReservation(r);
        }
    }
    public Reservation findReservationById(int id){
        Reservation res = null;
        for(Reservation r : getAllReservations()){
            if(r.getId() == id){
                res=r;
            }
        }
        return res;
    }
    public void removeReservation(int resIid, int clientId)
    {
        Reservation res = findReservationById(resIid);
        Client c = findById(clientId);
        c.removeReservation(res);

    }
    public List<Reservation> getReservationsForClient(int clientId){
        Client c = findById(clientId);
        return c.getReservationList();
    }
    public List<Reservation> getAllReservations(){
        List<Reservation> reservations = new ArrayList<>();
        for(Client c : clients){
            reservations.addAll(c.getReservationList());
        }
        return reservations;
    }

    public List<Room> returnAllUnAvailableRooms(LocalDate start, LocalDate end) {
        List<Room> rooms = new ArrayList<>();
        for (Reservation reservation : getAllReservations())
        {
            if ((reservation.getStart().isAfter(start) && reservation.getStart().isBefore(end)) ||
                    (reservation.getStart().isEqual(start) ) ||
                    (reservation.getEnd().isAfter(start) && reservation.getEnd().isBefore(end)) ||
                    (reservation.getEnd().isEqual(end) ) ||
                    (reservation.getStart().isBefore(start) && reservation.getEnd().isAfter(end))
                    || (reservation.getStart().isEqual(start) && reservation.getEnd().isEqual(end)))
            {
                rooms.addAll(reservation.getRooms());

            }
        }
        return rooms;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public void addCoupon(Coupon c, int clientId)
    {
        coupon_id++;
        c.setCode(coupon_id);
        findById(clientId).addCoupon(c);
    }
    public void removeCoupon(int couponid, int clientId)
    {
        findById(clientId).removeCoupon(findCouponById(couponid,clientId));
    }


    public Coupon findCouponById(int couponId, int clientId)
    {
        for (Client client : clients)
        {
            if (client.getId() == clientId)
            {
                List<Coupon> coupons = client.getCouponList();
                for (Coupon coupon : coupons)
                {
                    if (coupon.getCode() == couponId)
                    {
                       return coupon;
                    }
                }
            }
        }
      return null;
    }

    @Override
    public Reservation findReservationById(int reservationId, int clientid) {
        Reservation res = null;
        for(Reservation r : getAllReservations()){
            if(r.getId() == reservationId){
                res=r;
            }
        }
        return res;
    }

    @Override
    public Reservation findReferenceReservation(int reservationId) {
        return null;
    }
}
