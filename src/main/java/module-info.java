module se.albert.personalfinanceguidb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens se.albert.personalfinanceguidb to javafx.fxml;
    exports se.albert.personalfinanceguidb;
}