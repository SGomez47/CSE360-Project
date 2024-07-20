package com.cse360.Users;

import com.cse360.FileHandler;
import com.cse360.Order;

import com.cse360.Order;

public class OrderProcessingAgent extends Employee {
    
    public OrderProcessingAgent(String name, String id){
        super(name, id);
    }

    public OrderProcessingAgent(User user){
        super(user.NAME, user.ID);
    }

    public void approveOrder(Order order){
        FileHandler.updateOrder(order, "APPROVED");
    }
    public void declineOrder(Order order){
        FileHandler.updateOrder(order, "DECLINED");
    }
}
