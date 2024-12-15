module org.example.quuiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;


    opens org.example.quuiz to javafx.fxml;
    exports org.example.quuiz;
}