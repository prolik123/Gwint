module gwint {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens gwint to javafx.fxml;
    exports gwint;
}
