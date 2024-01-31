module com.fitness.animatedfitnesstracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.fitness.animatedfitnesstracker to javafx.fxml;
    exports com.fitness.animatedfitnesstracker;
}