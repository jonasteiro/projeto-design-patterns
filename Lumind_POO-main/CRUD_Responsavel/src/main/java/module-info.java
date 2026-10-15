module com.example.crud_responsavel {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.crud_responsavel to javafx.fxml;
    exports com.example.crud_responsavel;
}