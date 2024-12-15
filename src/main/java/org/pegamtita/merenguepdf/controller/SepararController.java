package org.pegamtita.merenguepdf.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SepararController implements Initializable {

    @FXML
    private Button bt_salvar;

    @FXML
    private Button bt_selecionar;

    @FXML
    private Label lbArquivoSelecionado;

    @FXML
    private Label lbLocalSalvarArquivo;

    @FXML
    private Label lbTotalPaginasDoc;

    @FXML
    private TextField txPgFinal;

    @FXML
    private TextField txPgInicial;

    private File arquivoSelecionado;

    private File arquivoSaida;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_selecionar.setOnAction(event -> selecionarArquivo());
        bt_salvar.setOnAction(event -> localSalvar());
    }

    private void selecionarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione um arquivo PDF para Separar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        Stage stage = (Stage) bt_selecionar.getScene().getWindow();
        File arquivo = fileChooser.showOpenDialog(stage);

        if (arquivo != null) {
            arquivoSelecionado = arquivo;
            lbArquivoSelecionado.setText(arquivoSelecionado.getName());

            try(PDDocument document = PDDocument.load(arquivo)) {
                int totalPaginas = document.getNumberOfPages();
                lbTotalPaginasDoc.setText(String.valueOf(totalPaginas));
            }catch (Exception e){
                lbTotalPaginasDoc.setText("Erro ao ler o PDF");
                e.printStackTrace();
            }
        } else {
            lbArquivoSelecionado.setText("Nenhum Aquivo foi Selecionado");
        }
    }

    private void localSalvar(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha onde salvar o arquivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Aquivos PDF","*.pdf"));

        fileChooser.setInitialFileName("Separado");

        Stage stage = (Stage) bt_salvar.getScene().getWindow();
        File arquivo = fileChooser.showSaveDialog(stage);

        if (arquivo != null){
            arquivoSaida = arquivo;
            lbLocalSalvarArquivo.setText(arquivoSaida.getAbsolutePath());
        }else {
            lbLocalSalvarArquivo.setText("Nenhum local foi selecionado para salvar");
        }
    }
}
