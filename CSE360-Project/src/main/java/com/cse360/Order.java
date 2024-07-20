package com.cse360;

import java.text.DecimalFormat;
import com.cse360.Users.Student;

// Class implements an Order. It contains a Pizza and a Student, along with a price (calculated via the Pizza) and 
// an order status
public class Order {
    private Pizza pizza;
    private Student student;
    private String orderID;
    private String status;

    public Order(Pizza pizza, Student student, String status, String orderID){
        this.pizza = pizza;
        this.student = student;
        this.status = status;   
        this.orderID = orderID;     
    }

    public Student getStudent(){
        return student;
    }

    public double getPrice(){
        return this.pizza.getPrice();
    }

    public String getPriceStr(){
        double price = getPrice();
        DecimalFormat dec = new DecimalFormat("$##.00");
        String price_string = dec.format(price);
        return price_string;
    }

    public String getPizzaDesc(){
        return this.pizza.toString();
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String getStatus(){
        return status;
    }

    public String getOrderId(){
        return orderID;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setOrderID(String id){
        this.orderID = id;
    }
}