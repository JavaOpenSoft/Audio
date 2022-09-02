module juit.utils.Audio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.desktop;

    opens juit.utils.Audio to javafx.fxml;
    exports juit.utils.Audio;
    exports packageInfo;
    opens packageInfo to javafx.fxml;
}