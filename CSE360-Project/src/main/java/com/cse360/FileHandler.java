package com.cse360;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import com.cse360.Users.Student;
import com.cse360.Users.User;
import com.cse360.Users.User.USERTYPE;

public class FileHandler {

    /**
     * Reads from "users.csv" file to return user info for SunDevil Pizza
     * @param ID the id of the the user to find
     * @return a User object with all the information, returns null if no user is found
     */
    public static User getUserFromID(String ID){
        File usersFile;
        Scanner usersScanner;
        try {
            //First we open the "users.csv" file under the project resources
            usersFile = new File(App.class.getResource("users.csv").toURI());
            //then we make a scanner in order to read the file line by line
            usersScanner = new Scanner(usersFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        while(usersScanner.hasNextLine()){
            String str = usersScanner.nextLine();

            //this is where the first comma is, then we make a substring to get the user id
            int id_index = str.indexOf(",");
            String id = str.substring(0, id_index);

            //if our id is equal to the one we are looking for
            if(id.compareTo(ID) == 0){
                //then we can use a string split to break up the rest of the elements into an array of strings
                String[] values = str.substring(id_index + 1).split(",");
                //using enums we can determine the type just by using valueOf
                USERTYPE type = USERTYPE.valueOf(values[0]);
                //otherwise, we can just give an id, name, and type
                usersScanner.close();
                return new User(id, values[1], type);
            }
        }

        usersScanner.close();
        return null;
    }

    public static void updateOrder(Order order, String status){
        order.setStatus(status);
        //Read entire order.csv into 2d array
        File ordersFile;
        Scanner orderScanner;
        try {
            ordersFile = new File(App.class.getResource("orders.csv").toURI());
            orderScanner = new Scanner(ordersFile);
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
        ArrayList<String> lines = new ArrayList<String>();
        orderScanner.nextLine();
        while(orderScanner.hasNextLine()){
            lines.add(orderScanner.nextLine());
        }

        orderScanner.close();
        //find the element we need to change
        for(int i = 0; i < lines.size(); i++){
            String str = lines.get(i);
            String order_id = str.substring(1, str.indexOf(","));
            if(order_id.compareTo(order.getOrderId()) == 0){
                //change the element
                String[] values = str.split(",");
                String top = "";
                for(int j = 5; j < values.length; j++){
                    top += "," + values[j]; 
                }
                String result = String.format("%s,%s,%s,%s,%s%s", 
                values[0],
                values[1],
                values[2],
                status,
                values[4],
                top);
                System.out.println(result);
                lines.set(i, result);
                break;
            }
        }

        //write array to "orders.csv"
        FileWriter orderWriter;
        try {
            orderWriter = new FileWriter(ordersFile);
            orderWriter.write("-1,0,1,FAKE,Cheese,Bacon\n");
            ListIterator<String> it = lines.listIterator();
            while(it.hasNext()){
                orderWriter.append(it.next());
            }
            orderWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * PlaceOrder saves a new order object into "orders.csv"
     * It assigns an order id in the function and updates the passed order
     * Order ID's are determined by the last order in "orders.csv" +1
     * @param order an order object with student and pizza
     */
    public static void placeOrder(Order order){
        //open "orders.csv" for reading last line
        File ordersFile;
        Scanner orderFileScanner;
        try{
            ordersFile = new File(App.class.getResource("orders.csv").toURI());
            orderFileScanner = new Scanner(ordersFile);
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }

        //Read file to find latest order id
        String lastLine = "";
        while(orderFileScanner.hasNextLine()){
            lastLine = orderFileScanner.nextLine();
        }
        String lastID = lastLine.substring(0, lastLine.indexOf(","));

        //the new order id is the last one + 1
        String newID = "-1";
        try{
            newID = Integer.toString(Integer.parseInt(lastID) + 1);
        }
        catch(Exception e){
            newID = "00000";
        }
        orderFileScanner.close();

        
        //set new order object with order_id+1
        order.setOrderID(newID);

        //append order to end of order file
        FileWriter orderFileWriter;
        try{
            orderFileWriter = new FileWriter(ordersFile, true);
            orderFileWriter.append(String.format("%s,%s,%.2f,%s,%s%s", 
                order.getOrderId(), 
                order.getStudent().getUserID(),
                order.getPrice(), 
                order.getStatus(), 
                order.getPizza().getType(), 
                order.getPizza().getToppingsAsString()));
            orderFileWriter.append("\n");
            orderFileWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * getStudentOrders builds an ArrayList of orders that match with the given Student
     * @param student Student object where we compare the student id to the orders we are looking for
     * @return ArrayList<Order> with all given orders from "orders.csv"
     */
    public static ArrayList<Order> getStudentOrders(Student student) {
        ArrayList<Order> orders = new ArrayList<Order>();

        //open orders.csv for reading
        File ordersFile;
        Scanner ordersScanner;
        try {
            ordersFile = new File(App.class.getResource("orders.csv").toURI());
            ordersScanner = new Scanner(ordersFile);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

        //go throught every line of orders.csv
        boolean first = true;
        while(ordersScanner.hasNextLine()){
            if(first == true){
                ordersScanner.nextLine();
                first = false;
            }
            else{
                String str = ordersScanner.nextLine();
                System.out.println(str);
                //first we grab the order id
                int id_index = str.indexOf(",");
                System.out.println(id_index);
                String id = str.substring(1, id_index);

                //then the student id
                int studentID_index = str.indexOf(",", id_index+1);
                String studentID = str.substring(id_index+1, studentID_index);

                //if the fetched student id is equal to the one we are looking for
                if(studentID.compareTo(student.getUserID()) == 0){
                    //split the rest of the string by commas
                    String[] values = str.substring(studentID_index+1).split(",");
                    //make a new pizza object for our order object
                    Pizza pizza = new Pizza(values[2], Arrays.copyOfRange(values, 3, values.length));
                    //add the new order to our ArrayList
                    orders.add(new Order(pizza, student, values[1], id));
                }
            }
        }
        ordersScanner.close();
        return orders;
    }

    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();

        File ordersFile;
        Scanner ordersScanner;
        try {
            ordersFile = new File(App.class.getResource("orders.csv").toURI());
            ordersScanner = new Scanner(ordersFile);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        ordersScanner.nextLine();
        while(ordersScanner.hasNextLine()){
            String[] values = ordersScanner.nextLine().substring(1).split(",");

            Pizza pizza = new Pizza(values[4], Arrays.copyOfRange(values, 5, values.length));
            orders.add(new Order(pizza, 
                new Student(getUserFromID(values[1])), 
                values[3], 
                values[0]));
        }
        ordersScanner.close();
        return orders;
    }
}
