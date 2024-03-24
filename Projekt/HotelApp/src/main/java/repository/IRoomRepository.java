package repository;

import model.Room;
import model.Type;

import java.util.List;

public interface IRoomRepository extends ICrud<Integer, Room>{
      List<Room> returnRoomsOfType(Type t);
}
