module com.example.plottingapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.plottingapp to javafx.fxml;
    exports com.example.plottingapp;
}