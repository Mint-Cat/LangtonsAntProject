module at.ac.hcw.langtonsantproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    opens at.ac.hcw.langtonsantproject to javafx.fxml;
    exports at.ac.hcw.langtonsantproject;
    exports at.ac.hcw.langtonsantproject.Controller;
    opens at.ac.hcw.langtonsantproject.Controller to javafx.fxml;
    exports at.ac.hcw.langtonsantproject.Misc;
    opens at.ac.hcw.langtonsantproject.Misc to javafx.fxml;
}