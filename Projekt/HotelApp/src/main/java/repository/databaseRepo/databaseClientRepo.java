package repository.databaseRepo;

import model.*;
import repository.IClientRespository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class databaseClientRepo implements IClientRespository {
    EntityManager manager;

    public databaseClientRepo(EntityManager manager) {
        this.manager = manager;
        populate_clients();
    }

    private void populate_clients(){
        manager.getTransaction().begin();
        Client client1 = new Client("Bob", "Pop","bobpop","00bob");
        Client client2 = new Client("Laura", "Georgescu","laurgeor","22laura");
        Client client3 = new Client("Catalin", "Olariu","cataola","24catalin");
        Client client4 = new Client("Catalina", "Vasiu","catasiu","cata777");
        Client client5 = new Client("Crina", "Timotei","criti","crin880");
        Client client6 = new Client("Mira", "Muresan","miramure","mure1009");
        Client client7 = new Client("Dan", "Morariu","dandan","dan100dd");
        Client client8 = new Client("Darius", "Ciubaru","darciu","24darciu");
        Client client9 = new Client("Alin", "Trambulin","alintra","aliintram7");
        Client client10 = new Client("Iasmina", "Hondaru","iasmahondaru","iasmi699");
        Client client11 = new Client("Ana", "Maria","ana","1234");

        manager.persist(client1);
        manager.persist(client2);
        manager.persist(client3);
        manager.persist(client4);
        manager.persist(client5);
        manager.persist(client6);
        manager.persist(client7);
        manager.persist(client8);
        manager.persist(client9);
        manager.persist(client10);
        manager.persist(client11);
        manager.getTransaction().commit();

    }

    @Override
    public void add(Client client) {
        manager.getTransaction().begin();
        manager.persist(client);
        manager.getTransaction().commit();

    }
    @Override
    public void delete(Integer clientId) {
        manager.getTransaction().begin();
        Client cl =  manager.find(Client.class,clientId);
        manager.remove(cl);
        manager.getTransaction().commit();

      //  Query query = manager.createNativeQuery("DELETE FROM Client WHERE id=:idClient", Client.class);
       // query.setParameter("idClient", Integer.toString(clientId));
       // query.executeUpdate();


    }

    @Override
    public void update(Integer clientId, Client client) {
        manager.getTransaction().begin();
        Client cl =  manager.find(Client.class,clientId);
        manager.detach(cl);
        cl.setFirstName(client.getFirstName());
        cl.setLastName(client.getLastName());
        cl.setUsername(client.getUsername());
        cl.setPassword(client.getPassword());
        cl.setCouponList(cl.getCouponList());
        cl.setReservationList(cl.getReservationList());
        manager.merge(cl);
        manager.getTransaction().commit();
        /*
        Query query = manager.createNativeQuery("UPDATE Client SET firstname=:clFN, lastname=:clLN, username=:clU, password=:clP WHERE id=:clId",Client.class);
        query.setParameter("clFN", client.getFirstName());
        query.setParameter("clLN", client.getLastName());
        query.setParameter("clU", client.getUsername());
        query.setParameter("clP", client.getPassword());
        query.setParameter("clId", Integer.toString(clientId));
        query.executeUpdate();

*/

    }

    @Override
    public Client findByUsername(String username) {

        Client c ;

        try {

            Query query = manager.createNativeQuery("SELECT * FROM Client WHERE username=:clU", Client.class);
            query.setParameter("clU", username);
            c = (Client) query.getSingleResult();

        }catch (Exception e)
        {
            return null;
        }
       return  c;

    }
    @Override
    public Client findById(Integer clientId){
        //manager.getTransaction().begin();


        Client cl =  manager.find(Client.class,clientId);

        //manager.getTransaction().commit();
        return cl;
       /*

        Client c = null;
        try {
            Query query = manager.createNativeQuery("SELECT * FROM Client WHERE id=:clId",Client.class);
            query.setParameter("clId", Integer.toString(clientId));
            c = (Client) query.getSingleResult();
        }catch (Exception exception)
        {
            return c;
        }


        return c ;

        */
    }

    @Override
    public List<Client> getAll() {
        List<Client>clients ;
        try {
            Query query = manager.createNativeQuery("SELECT * FROM Client",Client.class);
            clients =  (List<Client>) query.getResultList();
        }catch (Exception exception)
        {
            return new ArrayList<>();
        }


        return clients;
    }
    public void addCoupon(Coupon c, int client_id)
    {
        manager.getTransaction().begin();
        findById(client_id).addCoupon(c);
        manager.persist(c);
        manager.getTransaction().commit();

    }
    public void removeCoupon(int couponid, int clientId)
    {
        manager.getTransaction().begin();
        Coupon couponDatabase  = manager.find(Coupon.class,couponid);
        Client clientDatabase = manager.find(Client.class,clientId);
        manager.remove(couponDatabase);
        clientDatabase.removeCoupon(couponDatabase);
        manager.getTransaction().commit();
        /*
       // Client cl =  manager.find(Client.class,clientId);
       // Coupon coupon = manager.find(Coupon.class,coupon.getCode());
        manager.remove(cl);
        manager.getTransaction().commit();
        //manager.getTransaction().begin();
        Query query = manager.createNativeQuery("DELETE FROM Coupon WHERE code=:idCoupon AND client_id=:idCl", Coupon.class);
        query.setParameter("idCoupon", Integer.toString(coupon.getCode()));
        query.setParameter("idCl", Integer.toString(clientId));
        query.executeUpdate();

         */


    }


    // RESERVATION

    public void addReservation(Reservation r, int clientId)
    {
        manager.getTransaction().begin();
        findById(clientId).addReservation(r);
        manager.persist(r);
        manager.getTransaction().commit();

    }
    public Reservation findReferenceReservation(int id)
    {
        manager.getTransaction().begin();
        Reservation reservation = manager.getReference(Reservation.class,id);
        manager.getTransaction().commit();
        return reservation;

    }
    public Reservation findReservationById(int reservationId,int clientid){
        manager.getTransaction().begin();
        Reservation reservation = manager.find(Reservation.class,reservationId);
       manager.detach(reservation);
        System.out.println(reservation);
        manager.getTransaction().commit();
        return reservation;
        /*
        //manager.getTransaction().begin();
        //Reservations
        Query query1 = manager.createNativeQuery("SELECT * FROM Reservation WHERE id=:resId",Reservation.class);
        query1.setParameter("resId", Integer.toString(reservationId));
        //Reservation Rooms
        Query query2 = manager.createNativeQuery("SELECT room_id FROM reservation_room WHERE reservation_id=:resId",Reservation.class);
        query2.setParameter("resId", Integer.toString(reservationId));
        //Rooms
        Query query3 = manager.createNativeQuery("SELECT * FROM Room",Room.class);
        List<Room> allRooms = (List<Room>) query3.getResultList();
        //set Rooms for Reservation
        List<Integer> roomIds = (List<Integer>) query2.getResultList();
        List<Room> rooms = new ArrayList<>();
        for(int roomId : roomIds) {
            for (Room room : allRooms) {
                if (room.getId() == roomId) {
                    rooms.add(room);
                    break;
                }
            }
        }
        Reservation reservation = (Reservation) query1.getSingleResult();
        reservation.setRooms(rooms);
        //manager.getTransaction().commit();
        return reservation;

         */

    }
    public void removeReservation(int resIid, int clientId)
    {
        manager.getTransaction().begin();
        Reservation reservationDatabase  = manager.find(Reservation.class,resIid);
        Client clientDatabase = manager.find(Client.class,clientId);

        Query query = manager.createNativeQuery("DELETE FROM reservation_room WHERE reservation_id=:idRes ", Reservation.class);
        query.setParameter("idRes", Integer.toString(resIid));
        query.executeUpdate();
        Query query2 = manager.createNativeQuery("DELETE FROM reservation WHERE id=:idRes ", Reservation.class);
        query2.setParameter("idRes", Integer.toString(resIid));
        query2.executeUpdate();
        clientDatabase.removeReservation(reservationDatabase);
        manager.getTransaction().commit();
/*
        for (Room r : reservationDatabase.getRooms()) {
            if (r.getReservations().size() == 1) {
                manager.remove(r);
            } else {
                r.getReservations().remove(reservationDatabase);
            }
        }

 */
       // manager.remove(reservationDatabase);


       // manager.remove(manager.contains(reservationDatabase) ? reservationDatabase : manager.merge(reservationDatabase));
        //manager.remove(reservationDatabase);


        /*
       // manager.getTransaction().begin();
        Reservation reservation = findReservationById(resIid);
        Query query = manager.createNativeQuery("DELETE FROM Reservation WHERE id=:idRes AND client_id=:idCl", Reservation.class);
        query.setParameter("idRes", Integer.toString(resIid));
        query.setParameter("idCl", Integer.toString(clientId));
        query.executeUpdate();
       // manager.getTransaction().commit();

         */
    }
    public List<Reservation> getReservationsForClient(int clientId){
        List<Reservation> reservations = new ArrayList<>();
        Query query = manager.createNativeQuery("SELECT id FROM Reservation WHERE client_id=:clId");
        query.setParameter("clId", clientId);
        List<Integer> res_ids = (List<Integer>) query.getResultList();
        for (int res : res_ids) {
            Query query1 = manager.createNativeQuery("SELECT startDate FROM Reservation WHERE id=:resId");
            query1.setParameter("resId", res);
            Timestamp TStart = (Timestamp) query1.getSingleResult();
            LocalDate finalStart = TStart.toLocalDateTime().toLocalDate();

            Query query2 = manager.createNativeQuery("SELECT endDate FROM Reservation WHERE id=:resId");
            query2.setParameter("resId", res);
            Timestamp TEnd = (Timestamp) query2.getSingleResult();
            LocalDate finalEnd = TEnd.toLocalDateTime().toLocalDate();

            Query query3 = manager.createNativeQuery("SELECT price FROM Reservation WHERE id=:resId");
            query3.setParameter("resId", res);
            int finalPrice = (int) query3.getSingleResult();

            Query query4 = manager.createNativeQuery("SELECT room_id FROM reservation_room WHERE reservation_id=:resId");
            query4.setParameter("resId", res);
            List<Integer> roomIds = (List<Integer>) query4.getResultList();

            List<Room> finalRooms = new ArrayList<>();
            for (int room : roomIds) {
                Query query5 = manager.createNativeQuery("SELECT * FROM Room WHERE id=:roomId", Room.class);
                query5.setParameter("roomId", room);
                Room finalRoom = (Room) query5.getSingleResult();
                finalRooms.add(finalRoom);
            }
            Reservation finalReservation = new Reservation();
            finalReservation.setId(res);
            finalReservation.setStart(finalStart);
            finalReservation.setEnd(finalEnd);
            finalReservation.setPrice(finalPrice);
            finalReservation.setRooms(finalRooms);
            reservations.add(finalReservation);
        }
        return reservations;

//        List<Reservation>reservations = new ArrayList<>();
//       // manager.getTransaction().begin();
//        Client clientDatabase = manager.find(Client.class,clientId);
//        reservations =  clientDatabase.getReservationList();
//
//        //manager.getTransaction().commit();
//        return reservations;
        /*
       // manager.getTransaction().begin();
        try{
            Query query = manager.createNativeQuery("SELECT id FROM Reservation WHERE client_id=:idCl", Reservation.class);
            query.setParameter("idCl", Integer.toString(clientId));
            List<Integer> resIds = (List<Integer>) query.getResultList();
            List<Reservation> reservationsForClient = new ArrayList<>();
            for(int resId : resIds) {
                reservationsForClient.add(findReservationById(resId));
            }
            return reservationsForClient;
        } catch (Exception exception)
        {
            return new ArrayList<>();
        }

        //manager.getTransaction().commit();
       */
    }

    public List<Reservation> getAllReservations(){
       // manager.getTransaction().begin();
        List<Reservation> reservations = new ArrayList<>();
        for(Client c : getAll()) {
            Query query = manager.createNativeQuery("SELECT id FROM Reservation WHERE client_id=:clId");
            query.setParameter("clId", c.getId());
            List<Integer> res_ids = (List<Integer>) query.getResultList();

            for (int res : res_ids) {
                Query query1 = manager.createNativeQuery("SELECT startDate FROM Reservation WHERE id=:resId");
                query1.setParameter("resId", res);
                Timestamp TStart = (Timestamp) query1.getSingleResult();
                LocalDate finalStart = TStart.toLocalDateTime().toLocalDate();

                Query query2 = manager.createNativeQuery("SELECT endDate FROM Reservation WHERE id=:resId");
                query2.setParameter("resId", res);
                Timestamp TEnd = (Timestamp) query2.getSingleResult();
                LocalDate finalEnd = TEnd.toLocalDateTime().toLocalDate();

                Query query3 = manager.createNativeQuery("SELECT price FROM Reservation WHERE id=:resId");
                query3.setParameter("resId", res);
                int finalPrice = (int) query3.getSingleResult();

                Query query4 = manager.createNativeQuery("SELECT room_id FROM reservation_room WHERE reservation_id=:resId");
                query4.setParameter("resId", res);
                List<Integer> roomIds = (List<Integer>) query4.getResultList();

                List<Room> finalRooms = new ArrayList<>();
                for (int room : roomIds) {
                    Query query5 = manager.createNativeQuery("SELECT * FROM Room WHERE id=:roomId", Room.class);
                    query5.setParameter("roomId", room);
                    Room finalRoom = (Room) query5.getSingleResult();
                    finalRooms.add(finalRoom);
                }
                Reservation finalReservation = new Reservation();
                finalReservation.setId(res);
                finalReservation.setStart(finalStart);
                finalReservation.setEnd(finalEnd);
                finalReservation.setPrice(finalPrice);
                finalReservation.setRooms(finalRooms);
                reservations.add(finalReservation);
            }
        }
        return reservations;

//        List<Client> clients = getAll();
//        List<Reservation> reservations = new ArrayList<>();
//        for (Client client : clients)
//        {
//            reservations.addAll(client.getReservationList());
//        }
//        return  reservations;
        /*
        List<Integer> clientIds = getAll().stream().map(Client::getId).toList();
        List<Reservation> reservations = new ArrayList<>();
        for(int clientId : clientIds){
            reservations.addAll(getReservationsForClient(clientId));
        }
        //manager.getTransaction().commit();
        return reservations;

         */
    }

    public List<Room> returnAllUnAvailableRooms(LocalDate start, LocalDate end) {
        //manager.getTransaction().begin();
        List<Room> rooms = new ArrayList<>();
        List<Reservation>reservations = getAllReservations();
        for (Reservation reservation : reservations)
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
      //  manager.getTransaction().commit();
        return rooms;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Coupon findCouponById(int couponId, int clientId)
    {
       // manager.getTransaction().begin();
        Coupon coupon = manager.find(Coupon.class,couponId);
        System.out.println(coupon);
       // manager.getTransaction().commit();
        return coupon;
        /*
        Coupon c ;
       // manager.getTransaction().begin();
        Query query = manager.createNativeQuery("SELECT * FROM Coupon WHERE code=:coId AND client_id=:clId",Coupon.class);
        query.setParameter("coId", Integer.toString(couponId));
        query.setParameter("clId", Integer.toString(clientId));
        c = (Coupon) query.getSingleResult();
        //manager.getTransaction().commit();
        return  c;

         */
    }
}

