package com.cse360.Stages;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane; 

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.cse360.FileHandler;
import com.cse360.Users.User;
import com.cse360.Users.Chef;
import com.cse360.Users.OrderProcessingAgent;
import com.cse360.Users.Student;




public class LoginScreen extends Parent {
	
	Button login;
	TextArea textAr;
	Label error;
	Stage window;
	
	int id;


	public LoginScreen(Stage arg0) {		
		window = arg0;
		StackPane layout = new StackPane();
		
		error = new Label();
		VBox vbox = new VBox();
		login = new Button ("Login");
		login.setPrefSize(50, 30);
		
		Text title = new Text("Sun Devil Pizza Login");
		Text AsuId = new Text("ASUID:");
		textAr = new TextArea();
		
		
		GridPane gridPane2 = new GridPane(); 

		//----------------------------------------------
		//JavaFx code for GUI of Login screen
		title.setStyle("-fx-font: 17 arial;");
		title.setTextAlignment(TextAlignment.CENTER);
		layout.getChildren().addAll(title);
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		

		textAr.setEditable(true);
		textAr.setPrefHeight(0.05);
		textAr.setPrefWidth(300);
		textAr.setPromptText("ex.1234567890");
		AsuId.setStyle("-fx-font: 15 arial;");
		login.setStyle("-fx-background-color: #2f81d9; ");
		
		gridPane2.setMinSize(600, 600);
		gridPane2.setPadding(new Insets(10, 10, 10, 10)); 
		gridPane2.setVgap(5); 
	    gridPane2.setHgap(5); 
	    gridPane2.setAlignment(Pos.CENTER); 
	    

	    gridPane2.add(AsuId, 1, 1); 
	    gridPane2.add(textAr, 2, 1);       
	    gridPane2.add(login, 1, 3); 
	      
		//----------------------------------------------
		vbox.getChildren().addAll(layout,error,gridPane2);
		error.setAlignment(Pos.CENTER_RIGHT);
		this.getChildren().add(vbox);
		login.setOnAction(new ButtonHandler());

	}
		
		//-----------------------------------------------
		//Button Handler for when login button is pressed
	private class ButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			
			try {
			String str = textAr.getText();
		
			//Once button is pressed, we check to see if ID is 10 digits long and place it into int id;
			if(str.length() == 10) {
				User user = FileHandler.getUserFromID(str);
				if(user == null){
					error.setText("ID NOT FOUND");
					error.setTextFill(Color.RED);
					return;
				}
				switch(user.TYPE) {
					case STUDENT:
						//student screen
						window.setScene(new Scene(new StudentScreen(new Student(user))));
						break;
					case CHEF:
						window.setScene(new Scene(new ChefScreen(new Chef(user))));
						//Chef screen
						break;
					case OPA:
						window.setScene(new Scene(new OrderProcessingAgentScreen(new OrderProcessingAgent(user))));
						//opa screen
						break;
				}

			}
			//if shorter or longer than 10 digits we display an error message
			else {
				error.setText("ID must be 10 digits");
				error.setTextFill(Color.RED);
				}
			}
			//However if the input is a string we set another error to enter an ID
			catch(NumberFormatException e) {
				error.setText("Enter an ID");
				error.setTextFill(Color.RED);
			}

		}
	}
}
