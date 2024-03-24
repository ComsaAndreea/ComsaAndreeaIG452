package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int code;
    private int percentage;

    public Coupon( int percentage) {

        this.percentage = percentage;
    }

    public Coupon() {

    }

    public int getCode() {
        return code;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Coupon( " + code + " mit " + percentage + "%)";
    }
}
