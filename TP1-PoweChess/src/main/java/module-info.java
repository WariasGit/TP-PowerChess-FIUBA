module org.fiuba.algoritmos3.tp1powechess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;

    opens org.fiuba.algoritmos3.tp1powechess.Controlador to javafx.fxml;
    exports org.fiuba.algoritmos3.tp1powechess;
    opens org.fiuba.algoritmos3.tp1powechess.Controlador.Eventos to javafx.fxml;
    opens org.fiuba.algoritmos3.tp1powechess.Modelo.General to javafx.fxml;
}