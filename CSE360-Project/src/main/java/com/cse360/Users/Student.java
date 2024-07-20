package com.cse360.Users;

import java.util.ArrayList;
import java.util.Arrays;

import com.cse360.FileHandler;
import com.cse360.Order;
import com.cse360.Pizza;

public class Student {
    private String name, id;
    private ArrayList<Order> orders;

    public Student(String name, String id, String orders[]){
        this.name = name;
        this.id = id;
    }
    
    public Student(User user){
        this.name = user.NAME;
        this.id = user.ID;
        this.orders = FileHandler.getStudentOrders(this);
    }

    public ArrayList<Order> getOrders(){
        return orders;
    }

    public String getName(){
        return name;
    }

    //Adds the order to the filesystem and then updates the student's arraylist
    public void placeOrder(Order order){
        FileHandler.placeOrder(order);
        updateOrderList();
    }

    public void placeOrder(Pizza pizza){
        Order newOrder = new Order(pizza, this, "APPROVED", "-1");
        placeOrder(newOrder);
    }

    // Updates the ArrayList of the student's orders
    public void updateOrderList(){
        this.orders = FileHandler.getStudentOrders(this);
    }

    public void pickUpOrder(Order order){
        //TO DO
    }

    public String getUserID(){
        return this.id;
    }
}
