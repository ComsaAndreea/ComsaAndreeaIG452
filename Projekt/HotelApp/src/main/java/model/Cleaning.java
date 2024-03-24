package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Cleaning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cleanerId")
    private Cleaner cleaner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @Column(name = "cleanDate")
    private LocalDate cleanDate;

    public Cleaning() {
    }

    public Cleaning( Cleaner cleaner, Room room, LocalDate cleanDate) {
        this.cleaner = cleaner;
        this.room = room;
        this.cleanDate = cleanDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCleaner(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCleanDate(LocalDate cleanDate) {
        this.cleanDate = cleanDate;
    }

    public int getId() {
        return id;
    }

    public Cleaner getCleaner() {
        return cleaner;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCleanDate() {
        return cleanDate;
    }

    @Override
    public String toString() {

        return "Cleaning{" +
                "id=" + id +
                ", cleaner=" + cleaner.getUsername() +
                ", room=" + room.getId() + " " + room.getType()+
                ", cleanDate=" + cleanDate +
                '}';
    }
}
