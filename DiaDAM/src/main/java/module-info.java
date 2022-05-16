module es.diadam.diadam {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.mybatis;
    requires lombok;
    requires org.apache.logging.log4j;
    requires javafx.base;
    requires javax.inject;

    opens es.diadam.diadam to javafx.fxml;
    exports es.diadam.diadam;
}