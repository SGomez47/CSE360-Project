package com.cse360.Stages;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.text.DecimalFormat;
import com.cse360.Users.Student;
import com.cse360.Pizza;
import com.cse360.Order;
import javafx.application.Platform;

import com.cse360.App;

// To-Dos: Functionality for ordering a pizza. Loading the pizzas from the FileHandler

public class StudentScreen extends GridPane{
    private Student student;
    private HBox pizza_type_hbox;
    private VBox pizza_toppings_vbox, order_panel, ordered_panel;
    private Label price_label, name_label;
    private Button order_pizza_btn;
    private RadioButton cheese_type, pepperoni_type, veggie_type;
    private CheckBox extra_cheese, olives, bacon, mushrooms;
    private Pizza pizza;
    private TableView<Order> pizza_table;
    private ObservableList<Order> data;

    // Creates the StudentScreen given a Student
    public StudentScreen(Student student){
        this.student = student;
        pizza = new Pizza("Cheese", new String[0], "Not Ordered");
        String price = formatPrice(pizza.getPrice());

        //Creates label that has the employee's name
        name_label = new Label(student.getName());
        // Creates an HBox object that has a toggle group of each pizza type
        pizza_type_hbox = create_pizza_types();
        // Creates a VBox object that allows the user to select toppings
        pizza_toppings_vbox = create_pizza_toppings();
        // Creates a label to display the price of the pizza
        price_label = new Label(price);
        // Creates a button the user will hit to order the pizza
        order_pizza_btn = new Button("Place Order");
        //Creates a VBox for the left-hand side order panel
        order_panel = new VBox(name_label, pizza_type_hbox, pizza_toppings_vbox, price_label, order_pizza_btn);
        order_panel.setSpacing(20);
        order_panel.setAlignment(Pos.CENTER);
        order_panel.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        // Creates the ScrollPane that will contain the table of ordered pizzas on the Customer's screen
        pizza_table = new TableView<>();
        createPizzaTable();

        // Creates the panel for the right-hand side of the screen
        Label my_orders = new Label("My Orders");
        Button logout = new Button("Logout");
        ordered_panel = new VBox(my_orders, pizza_table, logout);
        ordered_panel.setPadding(new Insets(10, 10, 10, 10));
        ordered_panel.setSpacing(20);


        setPricesToChange();
        order_pizza_btn.setOnAction(new OrderHandler());
        logout.setOnAction(new LogoutHandler());

        // Sets up the GridPane
        this.add(order_panel, 0, 0, 1, 1);
        this.add(ordered_panel, 1, 0, 1, 1);
    }


    private void createPizzaTable(){
        TableColumn pizza_desc = new TableColumn("Pizza");
        TableColumn price = new TableColumn("Price");
        TableColumn status = new TableColumn("Status");

        pizza_desc.setCellValueFactory(new PropertyValueFactory<Order,String>("pizzaDesc"));
        price.setCellValueFactory(new PropertyValueFactory<Order,String>("priceStr"));
        status.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
        pizza_table.getColumns().addAll(pizza_desc, price, status);

        updateData();
        pizza_table.setMinWidth(440);
    }


    protected void updateData(){
        ArrayList<Order> studentOrders = student.getOrders();
        data = FXCollections.observableArrayList(studentOrders);
        pizza_table.setItems(data);
    }


    // Creates a HBox object that contains a toggle group for which type of pizza (cheese, pepperoni, veggie) the 
    // customer would like. Helper method for constructor (private method)
    private HBox create_pizza_types(){
        // Creates buttons for the type of pizza the customer wants
        cheese_type = new RadioButton("Cheese");
        pepperoni_type = new RadioButton("Pepperoni");
        veggie_type = new RadioButton("Veggie");

        // Creates the toggle group and an HBox for the pizza types
        ToggleGroup pizza_types = new ToggleGroup();
        cheese_type.setToggleGroup(pizza_types);
        pepperoni_type.setToggleGroup(pizza_types);
        veggie_type.setToggleGroup(pizza_types);
        cheese_type.setSelected(true);
        HBox pizza_type_hbox = new HBox(cheese_type, pepperoni_type, veggie_type);
        pizza_type_hbox.setSpacing(10);
        return pizza_type_hbox;
    }


    // Creates a VBox that holds checkbox labels for each of the toppings. Helper method for constructor
    // and hence is private
    private VBox create_pizza_toppings(){
        extra_cheese = new CheckBox("Extra cheese");
        mushrooms = new CheckBox("Mushroom");
        bacon = new CheckBox("Bacon");
        olives = new CheckBox("Olives");
        VBox toppings_vbox = new VBox(extra_cheese, bacon, mushrooms, olives);
        return toppings_vbox;
    }

    
    // Creates an HBox to describe an ordered pizza.
    private HBox create_ordered_pizza(String pizza, String price, String status){
        Label pizza_desc_label = new Label(pizza);
        pizza_desc_label.setMinWidth(250.0);
        Label price_label = new Label(price);
        price_label.setMinWidth(50.0);
        Label status_label = new Label(status);
        status_label.setMinWidth(100.0);
        HBox ordered_pizza_info = new HBox(pizza_desc_label, price_label, status_label);
        ordered_pizza_info.setSpacing(10);
        return ordered_pizza_info;
    }


    // Sets the checkboxes on action so that the price displayed will change when clicked/selected
    private void setPricesToChange(){
        cheese_type.setOnAction(new PriceHandler());
        pepperoni_type.setOnAction(new PriceHandler());
        veggie_type.setOnAction(new PriceHandler());
        extra_cheese.setOnAction(new PriceHandler());
        bacon.setOnAction(new PriceHandler());
        olives.setOnAction(new PriceHandler());
        mushrooms.setOnAction(new PriceHandler());
    }

    public String formatPrice(double price){
        DecimalFormat dec = new DecimalFormat("$##.00");
        String price_string = dec.format(price);
        return price_string;
    }

    // EventHandler class changes the price displayed when one of the toppings or pizza type menus is pressed
    private class PriceHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            pizza = createPizza();
            double price_num = pizza.getPrice();
            String price = formatPrice(price_num);
            price_label.setText(price);
        }

        public Pizza createPizza(){
            String type;
            if(cheese_type.isSelected()){
                type = "Cheese";
            }
            else if(pepperoni_type.isSelected()){
                type = "Pepperoni";
            }
            else if(veggie_type.isSelected()){
                type = "Veggie";
            }
            else{
                type = "None";
            }
            
            int length = countNumToppings();
            String[] toppings = new String[length];
            int index = 0;
            if(extra_cheese.isSelected()){
                toppings[index] = "Extra Cheese";
                index += 1;
            }
            if(bacon.isSelected()){
                toppings[index] = "Bacon";
                index += 1;
            }
            if(mushrooms.isSelected()){
                toppings[index] = "Mushrooms";
                index += 1;
            }
            if(olives.isSelected()){
                toppings[index] = "Olives";
                index += 1;
            }         

            return new Pizza(type, toppings, "Not Ordered");
        }

        public int countNumToppings(){
            int length = 0;
            if(extra_cheese.isSelected())
                length += 1;
            if(bacon.isSelected())
                length += 1;
            if(mushrooms.isSelected())
                length += 1;
            if(olives.isSelected())
                length += 1;
            return length;
        }
    }

    
    private class OrderHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            student.placeOrder(pizza);
            updateData();
        }
    }

    private class LogoutHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            App.logout();
        }
    }
}
