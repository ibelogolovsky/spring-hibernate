package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }
      System.out.println("Add users with cars\n");
      userService.add(new User(
              "Michael",
              "Schumacher",
              "msch@f1.com",
              new Car("Mercedes-Benz", 1)
      ));
      userService.add(new User(
              "Rubens Gonçalves",
              "Barrichello",
              "rbarrichello@f1.com",
              new Car("Williams", 1)
      ));

      System.out.println("Get users with cars\n");
      List<User> usersWithCar = new ArrayList<>();
      usersWithCar.add(userService.get(5));
      usersWithCar.add(userService.get(6));

      for (User user : usersWithCar) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         Car car = user.getCar();
         if (car != null) {
            System.out.println("Car = " + car.getModel() +
                    ", series: " + car.getSeries());
         }
         System.out.println();
      }

      User williamsDriver = userService.get("Williams", 1);
      if (williamsDriver != null) {
         System.out.println("User who drives Williams series 1 is " +
                 williamsDriver.getFirstName() + " " +
                 williamsDriver.getLastName());
      } else {
         System.out.println("No one drives Williams series 1 :-(");
      }

      context.close();
   }
}
