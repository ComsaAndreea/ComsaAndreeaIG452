package repository;

import model.Cleaner;

import java.util.List;

public interface ICleanerRepository extends ICrud<Integer, Cleaner> {
    Cleaner findByUsername(String username);

}
