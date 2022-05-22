module gwint {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires transitive org.json;

    opens gwint to javafx.fxml;
    exports gwint;
}
