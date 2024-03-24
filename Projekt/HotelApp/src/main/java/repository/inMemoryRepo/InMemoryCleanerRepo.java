package repository.inMemoryRepo;

import model.Cleaner;
import model.Client;
import repository.ICleanerRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCleanerRepo implements ICleanerRepository {
    private List<Cleaner> cleaners;
    private int id_cleaner;

    public InMemoryCleanerRepo() {
        this.cleaners =  new ArrayList<>();
        id_cleaner = 0;
        populate_cleaners();
    }
    private void populate_cleaners(){

        Cleaner cleaner1 = new Cleaner("Andu", "Andreescu","anduandre","1234");
        Cleaner cleaner2 = new Cleaner("Laura", "Halmaciu","lauramaciu","2222");
        Cleaner cleaner3 = new Cleaner("Catalina", "Vasiu","catasiu","24siu");
        this.add(cleaner1);
        this.add(cleaner2);
        this.add(cleaner3);

    }
    @Override
    public void add(Cleaner cleaner) {
        id_cleaner++;
        cleaner.setId(id_cleaner);
        this.cleaners.add(cleaner);
    }

    @Override
    public void delete(Integer id) {
        cleaners.remove(findById(id));
    }

    @Override
    public void update(Integer id, Cleaner cleaner) {
        Cleaner oldCleaner = findById(id);
        oldCleaner.setFirstName(cleaner.getFirstName());
        oldCleaner.setLastName(cleaner.getLastName());
        oldCleaner.setUsername(cleaner.getUsername());
        oldCleaner.setPassword(cleaner.getPassword());
        //oldCleaner.setSalary(cleaner.getSalary());

    }

    @Override
    public Cleaner findByUsername(String username) {
        for(Cleaner c : cleaners){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }
    public Cleaner findById(Integer id){
        for(Cleaner c : cleaners){
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cleaner> getAll() {
        return cleaners;
    }


}
