package com.cse360;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import com.cse360.Users.User;
import com.cse360.Stages.LoginScreen;
import com.cse360.Users.Student;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage window;
    public static void main(String[] args) {
        User user = FileHandler.getUserFromID("1219379798");

        //Student stu = new Student(user);

        /*for(Order o: FileHandler.getAllOrders()){
            System.out.printf("Order: %s, Pizza Type: %s\n", o.getOrderID(), o.getPizza().getType());
           FileHandler.updateOrder(o, "LETSGO");
        }*/

        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        window = arg0;
        LoginScreen login = new LoginScreen(arg0);

        arg0.setScene(new Scene(login));
        arg0.show();
    }

    public static void logout() {
        window.setScene(new Scene(new LoginScreen(window)));
    }

}