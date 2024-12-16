package org.pegamtita.merenguepdf.util;

import javafx.scene.control.Alert;

public class Mensage {

    public void exibir(String titulo, String mensagem, Alert.AlertType tipo ){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
