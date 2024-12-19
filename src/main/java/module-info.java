module org.pegamtita.merenguepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.pdfbox;
    requires com.gluonhq.attach.util;
    requires org.docx4j.core;


    opens org.pegamtita.merenguepdf to javafx.fxml;
    opens org.pegamtita.merenguepdf.telas to javafx.fxml;
    opens org.pegamtita.merenguepdf.controller to javafx.fxml;

    exports org.pegamtita.merenguepdf;
}