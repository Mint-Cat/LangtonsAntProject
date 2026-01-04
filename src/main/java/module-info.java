module at.ac.hcw.langtonsantproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.ac.hcw.langtonsantproject to javafx.fxml;
    exports at.ac.hcw.langtonsantproject;
}