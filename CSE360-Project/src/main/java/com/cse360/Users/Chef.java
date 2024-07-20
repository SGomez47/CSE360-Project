package com.cse360.Users;

import com.cse360.FileHandler;
import com.cse360.Order;

public class Chef extends Employee {

    public Chef(String name, String id) {
        super(name, id);
    }
    public Chef(User user) {
        super(user.NAME, user.ID);
    }

    public void setOrderCook(Order order){
        FileHandler.updateOrder(order, "COOKING");
    }

    public void setOrderPickUp(Order order){
        FileHandler.updateOrder(order, "COMPLETED");
    }
    
}
