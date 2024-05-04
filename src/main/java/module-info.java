module org.example.sm {
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
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.desktop;

    opens org.example.sm to javafx.fxml;
    exports org.example.sm;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Controller.CourseLec;
    opens Controller.CourseLec to javafx.fxml;
    exports Controller.Tuition;
    opens Controller.Tuition to javafx.fxml;
    exports Controller.ExamStu;
    opens Controller.ExamStu to javafx.fxml;
    exports Controller.TimeTable;
    opens Controller.TimeTable to javafx.fxml;
}