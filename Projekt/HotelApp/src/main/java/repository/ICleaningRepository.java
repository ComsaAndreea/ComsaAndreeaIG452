package repository;

import model.Cleaner;
import model.Cleaning;
import model.Room;

import java.util.List;

public interface ICleaningRepository {
    List<Cleaning> getCleaningsForRoom(int roomId);
    List<Cleaning> getCleaningsForCleaner(int cleanerId);
    void addCleaning(Cleaning cleaning);
    void deleteCleaning(Cleaning cleaning);

    List<Cleaning> getCleanings();
}
