import model.*;

import org.hibernate.Session;
import repository.ICleanerRepository;
import repository.IRoomRepository;
import repository.databaseRepo.databaseCleanerRepo;
import repository.databaseRepo.databaseRoomRepo;
import repository.inMemoryRepo.*;
import service.CleanerController;
import service.ClientController;
import service.ManagerController;
import views.ClientView;
import views.ManagerView;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean memory = false;
        //niste cod care se executa de fiecare data cand programul se termina sau il oprim
        //se sterge baza de date si se creeaza alta goala

        if(!memory){
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    System.out.println("Se reseteaza baza de date...");
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
                    EntityManager manager = factory.createEntityManager();
                    manager.getTransaction().begin();
                    Query query1 = manager.createNativeQuery(" use master\n" +
                            " alter database Hotel2 set single_user with rollback immediate\n" +
                            "DROP DATABASE Hotel2");
                    query1.executeUpdate();
                    Query query2 = manager.createNativeQuery("CREATE DATABASE Hotel2");
                    query2.executeUpdate();
                }
            }, "Shutdown-thread"));
        }

         Ui ui = new Ui(memory);
         ui.showMenu();

    }


}