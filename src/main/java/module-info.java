module com.example.oopfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.oopfinal to javafx.fxml;
    exports com.example.oopfinal;
}