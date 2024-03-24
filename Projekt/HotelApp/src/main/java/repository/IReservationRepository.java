package repository;

import model.Reservation;
import model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IReservationRepository  {
      List<Room> returnAllUnAvailableRooms(LocalDate start, LocalDate end);
      List<Room>  GetAllReservedRoomsForAUser(int id);
      List<Reservation>  GetAllReservationsForAUser(int id);
      void addReservation(Reservation reservation);
      boolean deleteReservation (int id);
     // returntype la getall
     // void getAll();

      Reservation findReservationById(int id);

}
