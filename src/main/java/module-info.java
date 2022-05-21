module gwint {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive org.json;

    opens gwint to javafx.fxml;
    exports gwint;
}
