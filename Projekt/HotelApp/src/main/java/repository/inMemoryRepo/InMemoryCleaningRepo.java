package repository.inMemoryRepo;

import model.Cleaner;
import model.Cleaning;
import model.Room;
import repository.ICleaningRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCleaningRepo implements ICleaningRepository {
    public List<Cleaning> cleanings;
    private int idCleaning;
    public InMemoryCleaningRepo() {
        this.cleanings = new ArrayList<>();
         idCleaning = 0;
    }

    public List<Cleaning> getCleanings() {
        return cleanings;
    }

    @Override
    public String toString() {
        return "InMemoryCleaningRepo{" +
                "cleanings=" + cleanings +
                '}';
    }

    @Override
    public List<Cleaning> getCleaningsForRoom(int roomId) {
        List<Cleaning> roomCleanings = new ArrayList<>();
        for(Cleaning c : cleanings){
            if(c.getRoom().getId() == roomId){
                roomCleanings.add(c);
            }
        }
        return roomCleanings;
    }

    @Override
    public List<Cleaning> getCleaningsForCleaner(int cleanerId) {
        List<Cleaning> roomCleanings = new ArrayList<>();
        for(Cleaning c : cleanings){
            if(c.getCleaner().getId() == cleanerId){
                roomCleanings.add(c);
            }
        }
        return roomCleanings;
    }
    public void addCleaning(Cleaning cleaning){
        idCleaning++;
        cleaning.setId(idCleaning);
        cleanings.add(cleaning);

    }

    @Override
    public void deleteCleaning(Cleaning cleaning) {
        cleanings.remove(cleaning);
    }
}
