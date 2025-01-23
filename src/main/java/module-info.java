module by.poltavetsav.frpomodorotimer {
    requires javafx.controls;
    requires javafx.fxml;


    opens by.poltavetsav.frpomodorotimer to javafx.fxml;
    exports by.poltavetsav.frpomodorotimer;
}