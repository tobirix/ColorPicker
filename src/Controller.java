import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

class Controller implements Initializable {

    //---------------------------------------------
    // Membervariablen für Steuerelemente
    //---------------------------------------------        
    @FXML
    private Slider sliderRot, sliderGruen, sliderBlau;

    @FXML
    private Pane paneFarbe;

    @FXML
    private TextField textFieldHex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //---------------------------------------------
        // Eventhandler
        //---------------------------------------------    
        sliderRot.valueProperty().addListener(
                (observable, alterWert, neuerWert) -> {
                    berechnenUndAusgeben();
                });

        sliderGruen.valueProperty().addListener(
                (observable, alterWert, neuerWert) -> {
                    berechnenUndAusgeben();
                });

        sliderBlau.valueProperty().addListener(
                (observable, alterWert, neuerWert) -> {
                    berechnenUndAusgeben();
                });

        textFieldHex.setOnAction((event) -> {
            //Text aus TextField auslesen
            String hexString = textFieldHex.getText();

            //String -> 3 Zahlen
            //1. Zeichen (#) entfernen
            String textOhneHash = hexString.substring(1);

            try {
                //hexString -> Zahl
                int gesamtZahl = Integer.parseInt(textOhneHash, 16);

                //Umwandlung OK
                if (gesamtZahl >= 0 && gesamtZahl <= 0xFFFFFF) {
                    //Einzelwerte aus Gesamtzahl berechnen
                    int rot = (gesamtZahl / 256) / 256;
                    int gruen = (gesamtZahl / 256) % 256;
                    int blau = gesamtZahl % 256;

                    //Zahlen den Slidern zuweisen
                    sliderRot.setValue(rot);
                    sliderGruen.setValue(gruen);
                    sliderBlau.setValue(blau);
                } else {
                    fehlerAusgeben();
                }
            } catch (NumberFormatException e) {
                fehlerAusgeben();
            }
        });

        //---------------------------------------------
        // Start
        //---------------------------------------------
        berechnenUndAusgeben();
    }

    private void berechnenUndAusgeben() {
        //Zahlenwerte von den Slidern holen
        int rot = (int) sliderRot.getValue();
        int gruen = (int) sliderGruen.getValue();
        int blau = (int) sliderBlau.getValue();

        //Hexwert berechnen  
        String hexString = String.format("#%02x%02x%02x", rot, gruen, blau);

        //Pane einfaerben
        paneFarbe.setStyle("-fx-background-color: " + hexString + ";");

        //Hexwert im TextField ausgeben
        textFieldHex.setText(hexString);

        //Textfeld weiss
        textFieldHex.setStyle("-fx-control-inner-background: white");
    }

    private void fehlerAusgeben() {
        Alert alert = new Alert(AlertType.ERROR, "Nur max. 6 Ziffern und keine negativen Ziffern!", ButtonType.OK);
        textFieldHex.setStyle("-fx-control-inner-background: red");
        alert.showAndWait();
    }
}