package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Client extends Person {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<Reservation> reservationList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<Coupon> couponList;


    public Client(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.couponList = new ArrayList<>();

        this.reservationList = new ArrayList<>();

    }

    public Client() {

    }


    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public void addCoupon(Coupon coupon) {
        this.couponList.add(coupon);
    }

    public void removeCoupon(Coupon coupon) {
        this.couponList.remove(coupon);
    }
    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservationList.remove(reservation);
    }

    private String Coupons() {
        String couponString = "";
        for (Coupon c : this.getCouponList()) {
            couponString = couponString.concat("\n");
            couponString = couponString.concat(c.toString());
        }

        return couponString;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public String toString() {
        return "Client (" + super.toString() + ", \nCoupons: " + Coupons() + " )";
    }

}
