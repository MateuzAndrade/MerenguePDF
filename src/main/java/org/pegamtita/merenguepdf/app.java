package org.pegamtita.merenguepdf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class app extends Application {

    Stage janela;

    @Override
    public void start(Stage stage) throws Exception {
            janela = stage;
            Parent tela = FXMLLoader.load(getClass().getResource("telas/home.fxml"));
            Scene scene = new Scene(tela);
            janela.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/pegamtita/merenguepdf/img/icone.png")));
            stage.setResizable(false);
            janela.show();

    }

}