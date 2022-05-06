module es.diadam.diadam {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires lombok;
    requires org.mybatis;

    opens es.diadam.diadam to javafx.fxml;
    exports es.diadam.diadam;
}