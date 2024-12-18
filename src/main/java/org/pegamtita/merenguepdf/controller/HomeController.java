package org.pegamtita.merenguepdf.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button bt_converter;

    @FXML
    private Button bt_juntar;

    @FXML
    private Button bt_separar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_separar.setOnAction(event -> abirTela("/org/pegamtita/merenguepdf/telas/separador.fxml","Separar PDF"));
        bt_juntar.setOnAction(event -> abirTela("/org/pegamtita/merenguepdf/telas/juntar.fxml","Juntar PDF"));
        bt_converter.setOnAction(event -> abirTela("/org/pegamtita/merenguepdf/telas/converter.fxml", "Converter em PDF"));
    }

    public void abirTela (String tela, String titulo){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
