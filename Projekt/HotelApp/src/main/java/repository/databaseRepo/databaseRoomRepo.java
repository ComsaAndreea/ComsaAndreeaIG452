package repository.databaseRepo;

import model.*;
import repository.IRoomRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class databaseRoomRepo implements IRoomRepository {
    EntityManager manager;

    public databaseRoomRepo(EntityManager manager) {
        this.manager = manager;
        populate_rooms();

    }

    private void populate_rooms()
    {
        manager.getTransaction().begin();
        Room rooms1 = new Room(Type.SINGLE,300,1);
        Room rooms2 = new Room(Type.SINGLE,300,1);
        Room rooms3 = new Room(Type.SINGLE,300,1);
        Room roomd1 = new Room(Type.DOUBLE,400,2);
        Room roomd2 = new Room(Type.DOUBLE,400,2);
        Room roomt1 = new Room(Type.TRIPLE,700,3);
        Room roomt2 = new Room(Type.TRIPLE,700,3);
        Room roomt3 = new Room(Type.TRIPLE,700,3);
        Room rooma1 = new Room(Type.APARTMENT,1000,4);
        Room rooma2 = new Room(Type.APARTMENT,1000,4);
        manager.persist(rooms1);
        manager.persist(rooms2);
        manager.persist(rooms3);
        manager.persist(roomd1);
        manager.persist(roomd2);
        manager.persist(roomt1);
        manager.persist(roomt2);
        manager.persist(roomt3);
        manager.persist(rooma1);
        manager.persist(rooma2);
        manager.getTransaction().commit();
    }
    @Override
    public void add(Room room) {
        manager.getTransaction().begin();
        manager.persist(room);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Integer roomId) {
        manager.getTransaction().begin();
        Room room = manager.find(Room.class,roomId);

       // Reservation reservationDatabase = manager.find(Reservation.class,resIid);
       // Client clientDatabase = manager.find(Client.class,clientId);
        Query query = manager.createNativeQuery("SELECT reservation_id FROM reservation_room where room_id=:idc");
        query.setParameter("idc", Integer.toString(roomId));
        List<Integer>reservations = (List<Integer>) query.getResultList();
        for (int reserv : reservations)
        {
            Reservation r = manager.find(Reservation.class,reserv);
            Query query2 = manager.createNativeQuery("DELETE FROM reservation_room WHERE reservation_id=:idRes ");
            query2.setParameter("idRes", Integer.toString(reserv));
            query2.executeUpdate();

            Query query3 = manager.createNativeQuery("DELETE FROM reservation WHERE id=:idRes ", Reservation.class);
            query3.setParameter("idRes", Integer.toString(reserv));
            query3.executeUpdate();
            r.setRooms(new ArrayList<>());

        }

        manager.remove(room);
        manager.getTransaction().commit();
        /*
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery("DELETE FROM Room WHERE id=:idRoom",Room.class);
        query.setParameter("idRoom", Integer.toString(roomId));
        query.executeUpdate();
        Room room =  manager.find(Room.class,roomId);
        manager.remove(room);
        manager.getTransaction().commit();
        /*
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery("DELETE FROM Room WHERE id=:idRoom",Room.class);
        query.setParameter("idRoom", Integer.toString(roomId));
        query.executeUpdate();
        manager.getTransaction().commit();
        */
    }

    @Override
    public void update(Integer id, Room room) {
        manager.getTransaction().begin();
        Room r =  manager.find(Room.class,id);
        manager.detach(r);
        r.setType(room.getType());
        r.setPrice(room.getPrice());
        r.setNrPers(room.getNrPers());
        manager.merge(r);
        manager.getTransaction().commit();
        /*
        manager.getTransaction().begin();
        Query query = manager.createNativeQuery("UPDATE Room SET nrPers=:roomNrP, price=:roomPr, type=:roomT", Room.class);
        query.setParameter("roomNrP", room.getNrPers());
        query.setParameter("roomPr", room.getPrice());
        query.setParameter("roomT", room.getType());
        query.executeUpdate();
        manager.getTransaction().commit();

         */
    }
    @Override
    public Room findById(Integer id){
        manager.getTransaction().begin();
        Room room = manager.find(Room.class,id);
        manager.getTransaction().commit();
        return room;
        /*
        for(Room r : getAll()){
            if(r.getId() == id){
                return r;
            }
        }
        return null;

         */
    }

    @Override
    public List<Room> getAll() {
        List<Room> rooms ;
        try {
            Query query = manager.createNativeQuery("SELECT * FROM Room", Room.class);
            rooms = (List<Room>) query.getResultList();

        }catch (Exception exception)
        {
            return new ArrayList<>();
        }
        return  rooms;
    }


    @Override
    public List<Room> returnRoomsOfType(Type t) {
        List<Room>rooms =getAll();
        List<Room>typeRooms = new ArrayList<>();
        for (Room room : rooms)
        {
            if (room.getType() == t)
            {
                typeRooms.add(room);
            }
        }
        return typeRooms;
    }
}
