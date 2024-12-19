package org.pegamtita.merenguepdf.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        if (arquivoSelecionado == null || arquivoSaida == null) {
            mensage.exibir( "Erro", "Por favor, selecione um arquivo e um local para salvar.", Alert.AlertType.WARNING);
            return;
        }

        String extensao = obterExtensaoArquivo(arquivoSelecionado);

        boolean sucesso = false;

        switch (extensao.toLowerCase()) {
            case "png":
            case "jpg":
            case "jpeg":
                sucesso = pdfService.converterImagemParaPdf(arquivoSelecionado, arquivoSaida);
                break;

            case "txt":
                sucesso = pdfService.converterTextoParaPdf(arquivoSelecionado, arquivoSaida);
                break;

            case "docx":
                sucesso = pdfService.converterWordParaPdf(arquivoSelecionado, arquivoSaida);
                break;

            default:
                mensage.exibir("Erro", "Formato de arquivo não suportado.", Alert.AlertType.ERROR);
                return;
        }

        if (sucesso) {
            mensage.exibir( "Sucesso", "Arquivo convertido com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            mensage.exibir("Erro", "Erro ao converter o arquivo.", Alert.AlertType.ERROR);
        }

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
        arquivoSelecionado = pdfService.selecionarArquivo(getStage(bt_selecionar),"*.*");
        if (arquivoSelecionado != null){
            lbSelecionarArquivo.setText(arquivoSelecionado.getName());
        }else {
            lbSelecionarArquivo.setText("Nenhum Arquivo foi Selecionado");
        }
    }


    private String obterExtensaoArquivo(File arquivo) {
        String nome = arquivo.getName();
        int index = nome.lastIndexOf('.');
        return (index > 0) ? nome.substring(index + 1) : "";
    }

}
