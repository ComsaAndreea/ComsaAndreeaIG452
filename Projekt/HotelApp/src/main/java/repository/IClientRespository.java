package repository;

import model.*;

import java.time.LocalDate;
import java.util.List;

public interface IClientRespository extends ICrud<Integer,Client>{
    Client findByUsername(String username);

    List<Room> returnAllUnAvailableRooms(LocalDate checkIn, LocalDate checkOut);

    void addReservation(Reservation reservation, int clientId);

    void removeReservation(int resId, int clientId);

    List<Reservation> getReservationsForClient(int id);

    void addCoupon(Coupon c, int client_id);

    void removeCoupon(int couponid, int client_id);

    Coupon findCouponById(int couponId, int clientid);
    Reservation findReservationById(int reservationId, int clientid);
    Reservation findReferenceReservation(int reservationId);


    List<Reservation> getAllReservations();
}
