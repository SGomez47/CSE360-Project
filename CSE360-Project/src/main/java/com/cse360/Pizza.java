package com.cse360;

// Class describes a pizza. Contains a type, toppings, price, and status
public class Pizza {
    final public static double TOPPING_PRICE = 1.50;

    private String toppings[];
    private String type;
    private double price;

    // Constructors
    public Pizza(String type, String toppings[]){
        this.type = type;
        this.toppings = toppings;
        calcPrice();
    }

    public Pizza(String type, String toppings[], String status){
        this.type = type;
        this.toppings = toppings;
        calcPrice();
    }

    // Accessors
    public double getPrice(){
        return this.price;
    }

    public String getType(){
        return type;
    }
    // Modifiers
    public void setToppings(String toppings[]){
        this.toppings = toppings;
    }

    public void setType(String type){
        this.type = type;
    }

    //Gets a list of toppings as a string
    public String getToppingsAsString(){
        StringBuilder result = new StringBuilder();
        for(String topping : toppings) {
            result.append(String.format(",%s", topping));
        }

        return result.toString();
    }

    // Calculates the price of the pizza - called with the constructor
    private void calcPrice(){
        this.price = 0.00;
        if(this.type.equals("Cheese")){
            this.price = 10.00;
        }
        else if(this.type.equals("Pepperoni")){
            this.price = 12.00;
        }
        else if(this.type.equals("Veggie")){
            this.price = 15.00;
        }
        // Invalid pizza type
        else{
            System.out.println("Invalid pizza type: " + type);
            this.price = 9999;
        }

        // Adds 1.50 for each topping that has been selected
        for(String topping: this.toppings){
            if(topping.isEmpty() == false && topping != null){
                this.price += TOPPING_PRICE;
            }
        }
    }

    // To-string argument gives a basic description of the pizza ordered. Can be used to print out the pizza info
    public String toString(){
        String result = this.type;
        if(toppings.length > 0){
            result = result + ": ";
            boolean first = true;
            for(String topping: this.toppings){
                if(topping.isEmpty() == false && topping != null){
                    if(first == true){
                        result = result + topping;
                        first = false;
                    }
                    else{
                        result = result + ", " + topping;
                    }
                }
            }
        }
        return result;
    }
}