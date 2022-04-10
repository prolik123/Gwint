module gwint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;

    opens gwint to javafx.fxml;
    exports gwint;
}
