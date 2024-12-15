module org.example.quizzz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.quizzz to javafx.fxml;
    exports org.example.quizzz;
    exports org.example.quizsystem;
    opens org.example.quizsystem to javafx.fxml;
}