package org.pegamtita.merenguepdf.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.pegamtita.merenguepdf.service.PdfService;
import org.pegamtita.merenguepdf.util.Mensage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConverterController implements Initializable {

    @FXML
    private Button bt_converter;

    @FXML
    private Button bt_salvar;

    @FXML
    private Button bt_selecionar;

    @FXML
    private Label lbLocalSalvarArquivo;

    @FXML
    private Label lbSelecionarArquivo;

    private File arquivoSelecionado;

    private File arquivoSaida;


    private Stage getStage(Button button) {
        return (Stage) button.getScene().getWindow();
    }

    private final PdfService pdfService = new PdfService();

    private final Mensage mensage = new Mensage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_selecionar.setOnAction(event -> selecionarArquivo());
        bt_salvar.setOnAction(event -> localSalvar());
        bt_converter.setOnAction(event -> converter());
    }

    private void converter() {

    }

    private void localSalvar() {
        arquivoSaida = pdfService.selecionarLocalSalvar(getStage(bt_salvar),"Convertido");
        if (arquivoSaida != null){
            lbLocalSalvarArquivo.setText(arquivoSaida.getAbsolutePath());
        }else {
            lbLocalSalvarArquivo.setText("Nenhum local foi selecionado para salvar");
        }
    }

    private void selecionarArquivo() {
        arquivoSelecionado = pdfService.selecionarArquivo(getStage(bt_selecionar),"*.all");
        if (arquivoSelecionado != null){
            lbSelecionarArquivo.setText(arquivoSelecionado.getName());
        }else {
            lbSelecionarArquivo.setText("Nenhum Arquivo foi Selecionado");
        }
    }


}
