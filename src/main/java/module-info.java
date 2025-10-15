module org.example.tarea2_estdatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tarea2_estdatos to javafx.fxml;
    exports org.example.tarea2_estdatos;
}