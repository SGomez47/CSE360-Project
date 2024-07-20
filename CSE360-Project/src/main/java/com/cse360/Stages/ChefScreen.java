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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.text.DecimalFormat;

import com.cse360.Users.Chef;
import com.cse360.Users.Student;
import com.cse360.Pizza;
import com.cse360.Order;
import javafx.application.Platform;

import com.cse360.App;
import com.cse360.FileHandler;

// To-Dos: Functionality for ordering a pizza. Loading the pizzas from the FileHandler

public class ChefScreen extends GridPane{
    private Chef chef;
    private TableView<Order> pizza_table;
    private ObservableList<Order> data;
    private Label name_label;

    // Creates the StudentScreen given a Student
    public ChefScreen(Chef chef){
        this.chef = chef;

        //Creates label that has the employee's name
        name_label = new Label(chef.getName());

        // Creates the ScrollPane that will contain the table of ordered pizzas on the Customer's screen
        pizza_table = new TableView<>();
        createPizzaTable();

        // Creates the panel for the right-hand side of the screen
        Label my_orders = new Label("Orders");
        Button logout = new Button("Logout");
        VBox ordered_panel = new VBox(name_label,my_orders, pizza_table, logout);
        ordered_panel.setPadding(new Insets(10, 10, 10, 10));
        ordered_panel.setSpacing(20);

        logout.setOnAction(new LogoutHandler());

        // Sets up the GridPane
        this.add(ordered_panel, 1, 0, 1, 1);
    }


    private void createPizzaTable(){
        TableColumn<Order, String> pizza_desc = new TableColumn<Order, String>("Pizza");
        TableColumn<Order, String> price = new TableColumn<Order, String>("Price");
        TableColumn<Order, String> status = new TableColumn<Order, String>("Status");
        TableColumn<Order, Void> actions = new TableColumn<Order, Void>("Actions");

        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){

            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
                TableCell<Order, Void> cell = new TableCell<Order, Void>(){
                    private Button button1 = new Button("COOKING");
                    {
                        button1.setOnAction(e-> {
                            chef.setOrderCook(getTableView().getItems().get(getIndex()));
                            getTableView().refresh();
                        });
                    }

                    private Button button2 = new Button("COMPLETED");
                    {
                        button2.setOnAction(e-> {
                            chef.setOrderPickUp(getTableView().getItems().get(getIndex()));
                            getTableView().refresh();
                        });
                    }
                    
                    @Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(new HBox(button1, button2));
						}
					}
                };
                return cell;
            }
        };
        pizza_desc.setCellValueFactory(new PropertyValueFactory<Order,String>("pizzaDesc"));
        price.setCellValueFactory(new PropertyValueFactory<Order,String>("priceStr"));
        status.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
        actions.setCellFactory(cellFactory);


        pizza_table.getColumns().addAll(pizza_desc, price, status, actions);

        updateData();
        pizza_table.setMinWidth(440);
    }


    protected void updateData(){
        ArrayList<Order> studentOrders = FileHandler.getAllOrders();
        data = FXCollections.observableArrayList(studentOrders);
        pizza_table.setItems(data);
    }

    public String formatPrice(double price){
        DecimalFormat dec = new DecimalFormat("$##.00");
        String price_string = dec.format(price);
        return price_string;
    }

    
    private class LogoutHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            App.logout();
        }
    }
}
