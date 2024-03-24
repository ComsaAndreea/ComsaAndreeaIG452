package views;

import model.*;
import service.ClientController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientView {
     private  ClientController clientcontroller;
     private Scanner myObj;

    public ClientView(ClientController clientcontroller) {
        this.clientcontroller = clientcontroller;
        this.myObj = new Scanner(System.in);
    }
    public List<Coupon> showCoupons (int id)
    {
        if(clientcontroller.showCoupons(id).size() == 0){
            System.out.println("You don't have any coupon yet. Make more reservations and you'll get some!");
            return null;
        }
        System.out.println("These are your coupons:");

        for (Coupon c : clientcontroller.showCoupons(id))
        {
            System.out.println(c.toString());
        }
        return clientcontroller.showCoupons(id);
    }

    public void  printAllReservations (int id)
    {
        List<Reservation> reservations =  clientcontroller.seeAllReservations(id);
        System.out.println("You have following reservations:\n");
        for (Reservation res :reservations)
        {
            System.out.println(res.toString());
        }
        System.out.println('\n');
    }
    public void makeReservationStatus(Option option, int id, LocalDate start, LocalDate end)
    {
        if(clientcontroller.makeReservation(option,id, start,end)!= null){
            System.out.println("Reservation created successfully!");
            return;
        }
        System.out.println("Reservation not created!");
    }
    public void makeReservationWithCouponStatus(Option option, int couponid, int id, LocalDate start, LocalDate end)
    {
        if(clientcontroller.makeReservationWithCoupon(option,couponid,id, start,end)!= null){
            System.out.println("Reservation created successfully!");
            return;
        }
        System.out.println("Reservation not created!");
    }
    public void deleteReservationStatus(int clientId)  {
        System.out.println("Which reservation would you like to delete? Ans = ");
        try
        {
            int resId = Integer.parseInt(myObj.nextLine());
            if(clientcontroller.deleteReservation(resId,clientId) != null){
                System.out.println("Reservation deleted successfully!");
            }
            else {
                System.out.println("Reservation not found!");
            }
        }catch (Exception exception)
        {
            System.out.println("Invalid input type!");
        }
    }
    public Client registerStatus()
    {
        System.out.println("Please enter your first Name:");
        String firstName = myObj.nextLine();
        System.out.println("Please enter your last Name:");
        String lastName = myObj.nextLine();
        String username;
        do{
            System.out.println("Please enter your username:");
            username = myObj.nextLine();
            if(clientcontroller.findClientByUsername(username)!= null){
                System.out.println("This username is already Taken.");
            }
        } while(clientcontroller.findClientByUsername(username)!= null);

        System.out.println("Please enter your password:");
        String password = myObj.nextLine();
        Client c = clientcontroller.register(firstName,lastName,username,password);
        if(c != null){
            System.out.println("Client registered successfully!");
        }
        return c;
    }
    public void printDetails(int id)
    {
        Client client = clientcontroller.findUserById(id);
        System.out.println("Your Personal information:");
        System.out.println("Name: " + client.getLastName() + " " + client.getFirstName());
        System.out.println("Username: " + client.getUsername());
        System.out.println("Password: " + client.getPassword());
        System.out.println("Coupons " + client.getCouponList());
    }
    public void changeDetailsStatus (int id)
    {
        System.out.println("Enter your new First Name:");
        String newFirstName = myObj.nextLine();
        System.out.println("Enter your new Last Name:");
        String newLastName = myObj.nextLine();
        clientcontroller.changeDetails(newFirstName, newLastName, id);
    }

    public Client loginStatus()
    {
        System.out.println("Please enter your username:");
        String username = myObj.nextLine();
        System.out.println("\n");
        System.out.println("Please enter your password:");
        String password = myObj.nextLine();
        System.out.println("\n");
        try
        {
            clientcontroller.login(username,password);
            System.out.println("User " + username + " is logged in \n" );
            return clientcontroller.findClientByUsername(username);

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }



    }
    public void changePasswordStatus(int id)
    {
        System.out.println("Enter your new Password:");
        String password = myObj.nextLine();
        clientcontroller.changePassword(id,password);
        System.out.println("Password changed successfully!");
    }
    private void printOptions(List<Option> options)
    {
        for (Option option :options)
        {
            System.out.println(option.toString());
        }
    }
    public void optionPart (Integer clientId)
    {
        System.out.println("When will you be staying with us ?");
        //le declaram aici ca sa le cunoasca si dupa try
        int year=0,month=0,day=0,year2=0,month2=0,day2=0,people=0,couponans=0;
        List<Coupon> couplist = new ArrayList<>();
        LocalDate to,from=to=LocalDate.now();

        try {
            System.out.println("From year = ");
            year = Integer.parseInt(myObj.nextLine());

            System.out.println("month = ");
            month = Integer.parseInt(myObj.nextLine());

            System.out.println("day = ");
            day = Integer.parseInt(myObj.nextLine());

            from = LocalDate.of(year,month,day);
            System.out.println("To year = ");
            year2 = Integer.parseInt(myObj.nextLine());

            System.out.println("month = ");
            month2 = Integer.parseInt(myObj.nextLine());

            System.out.println("day = ");
            day2 = Integer.parseInt(myObj.nextLine());
            to = LocalDate.of(year2,month2,day2);
            System.out.println("How many will be staying with us ?");
            System.out.println("people = ");
            people = Integer.parseInt(myObj.nextLine());

            System.out.println("These are your coupons: ");
            this.showCoupons(clientId);
            System.out.println("Which coupon would you like to use? Type -1 if you won't like to use any \n");
            System.out.println("coupon = ");
            couponans = Integer.parseInt(myObj.nextLine());

        } catch (Exception exception)
        {
            System.out.println("Invalid input type!");
            return;
        }

        Scanner myObj = new Scanner(System.in);

        List<Option> options = clientcontroller.generateOptions(from,to,people);
        if(options == null){
            return;
        }
        System.out.println("Options:");
        this.printOptions(options);
        System.out.println("What option do you prefer:");
        try {
            int optionnr = Integer.parseInt(myObj.nextLine());
            if (couponans >-1 && options.size() > 0)
            {
                makeReservationWithCouponStatus(findOptionById(options,optionnr),couponans, clientId,from,to);
                clientcontroller.removeCoupon(couponans,clientId);
            }
            else if(couponans == -1 && options.size() > 0)
            {
                makeReservationStatus(findOptionById(options,optionnr), clientId,from,to);
                if (clientcontroller.seeAllReservations(clientId).size() %2 == 0)
                {
                    int random_int = (int)Math.floor(Math.random()*(90-10+1)+10)/10;
                    random_int = random_int * 10;

                    Coupon coupon = new Coupon(random_int);
                    clientcontroller.addCoupon(coupon,clientId);
                }
            }
        } catch (Exception exception) {
            System.out.println("Invalid input type!");
        }
    }

    private Option findOptionById(List<Option> options, int opId){
        for(Option o : options){
            if(o.getId() == opId){
                return o;
            }
        }
        return null;
    }


}
