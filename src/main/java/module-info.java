module com.example.simple2048game {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.simple2048game to javafx.fxml;
    exports com.example.simple2048game;
}