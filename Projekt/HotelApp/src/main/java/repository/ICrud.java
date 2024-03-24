package repository;

import java.util.List;

public interface ICrud <T, E>{
    void add(E e); //fuge ein Element von Typ E hinzu
    void delete(T id); //losche nach username
    void update(T id, E e); //erstelle Update des Elementes mit einem neuen Element e
    E findById(T id); //suche nach username
    List<E> getAll(); //gibt die Liste

}



