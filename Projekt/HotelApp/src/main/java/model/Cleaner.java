package model;

import javax.persistence.*;
@Entity
public class Cleaner extends Person {
    private int salary;

    public Cleaner(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.salary = 0;
    }

    public Cleaner() {

    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Cleaner(" + getId() + ", " + getUsername() + ", " + getFirstName() + ", " + getLastName() + ", salary: " + getSalary() + " lei)";
    }
}
