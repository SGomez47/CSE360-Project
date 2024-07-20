module com.cse360 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cse360 to javafx.fxml;
    exports com.cse360;
}
