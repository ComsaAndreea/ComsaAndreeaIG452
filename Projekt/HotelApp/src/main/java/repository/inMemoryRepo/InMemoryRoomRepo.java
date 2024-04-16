package repository.inMemoryRepo;

        import model.Room;
        import model.Type;
        import repository.IRoomRepository;

        import java.util.ArrayList;
        import java.util.List;

public class InMemoryRoomRepo implements IRoomRepository {
    private List<Room> rooms;
    private int room_id;


    public InMemoryRoomRepo() {
        this.rooms = new ArrayList<>();
        room_id = 0;
        populate_rooms();
    }

    private void populate_rooms()
    {

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
        this.add(rooms1);
        this.add(rooms2);
        this.add(rooms3);
        this.add(roomd1);
        this.add(roomd2);
        this.add(roomt1);
        this.add(roomt2);
        this.add(roomt3);
        this.add(rooma1);
        this.add(rooma2);
    }
    @Override
    public void add(Room room) {
        room_id++;
        room.setId(room_id);
        rooms.add(room);

    }

    @Override
    public void delete(Integer id) {
        if(findById(id)!=null){
            rooms.remove(findById(id));
        }
    }

    @Override
    public void update(Integer id, Room room) {
        findById(id).setPrice(room.getPrice());
        findById(id).setType(room.getType());
        findById(id).setNrPers(room.getNrPers());
    }
    @Override
    public Room findById(Integer id){
        for(Room r : rooms){
            if(r.getId() == id){
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Room> getAll() {
        return rooms;
    }

    @Override
    public List<Room> returnRoomsOfType(Type t) {
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

    public void emptyRepo(){
        rooms.clear();
    }

}
