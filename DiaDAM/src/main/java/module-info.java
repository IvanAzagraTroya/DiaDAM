module es.diadam.diadam {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.mybatis;
    requires lombok;
    requires org.apache.logging.log4j;
    requires javafx.base;
    requires javax.inject;
    requires dagger;

    opens es.diadam.diadam to javafx.fxml;
    exports es.diadam.diadam;
}