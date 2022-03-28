module gwint {
    requires javafx.controls;
    requires javafx.fxml;

    opens gwint to javafx.fxml;
    exports gwint;
}
