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
    requires javax.inject;
    requires dagger;
    requires java.compiler;
    requires com.google.gson;

    exports es.diadam.diadam;
    exports es.diadam.diadam.controllers;
    opens es.diadam.diadam.controllers to javafx.fxml;
    exports es.diadam.diadam.managers;
    opens es.diadam.diadam.managers to javafx.fxml;
    exports es.diadam.diadam.views;
    opens es.diadam.diadam.views to javafx.fxml;
    exports es.diadam.diadam.dto;
    opens es.diadam.diadam.dto to javafx.fxml;
    exports es.diadam.diadam.utils;
    opens es.diadam.diadam.utils to javafx.fxml;
}