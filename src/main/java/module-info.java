module gwint {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.media;
    requires transitive org.json;
    requires org.apache.commons.collections4;
    requires json.simple;
    requires javafx.graphics;

    opens gwint to javafx.fxml;
    exports gwint;
}
