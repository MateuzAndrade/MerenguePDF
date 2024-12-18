package org.pegamtita.merenguepdf.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pegamtita.merenguepdf.service.PdfService;
import org.pegamtita.merenguepdf.util.Mensage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JuntarController implements Initializable {

    @FXML
    private Button bt_juntar;

    @FXML
    private Button bt_salvar;

    @FXML
    private Button bt_selecionar;

    @FXML
    private VBox lbArquivoSelecionado;

    @FXML
    private Label lbLocalSalvarArquivo;

    private List<File> arquivosSelecionados;

    private File arquivoSaida;

    private final PdfService pdfService = new PdfService();

    private final Mensage mensage = new Mensage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_selecionar.setOnAction(event -> selecionarArquivos());
        bt_salvar.setOnAction(event -> localSalvar());
        bt_juntar.setOnAction(event -> juntarArquivos());
    }

    private void juntarArquivos() {
        if (arquivosSelecionados == null || arquivoSaida == null) {
            mensage.exibir("Atenção","Selecione arquivos e o local para salvar", Alert.AlertType.WARNING);
            return;
        }

        boolean sucesso = pdfService.juntarPDF(arquivosSelecionados,arquivoSaida);

        if (sucesso){
            mensage.exibir("Sucesso","PDFs combinados com sucesso! Salvo em: " + arquivoSaida.getAbsolutePath(), Alert.AlertType.INFORMATION);
        }else {
            mensage.exibir("ERRO","Erro ao juntar os arquivos.", Alert.AlertType.ERROR);
        }

    }

    private void localSalvar() {
        arquivoSaida = pdfService.selecionarLocalSalvar(getStage(bt_salvar),"Mesclado");
        if (arquivoSaida != null){
            lbLocalSalvarArquivo.setText("Salvar em:" + arquivoSaida.getPath());
        }
        
    }

    private void selecionarArquivos() {
        arquivosSelecionados = pdfService.selecionarVariosArquivos(getStage(bt_selecionar));

        if (arquivosSelecionados != null && !arquivosSelecionados.isEmpty()) {
            arquivosSelecionados.forEach(arquivo -> {
                // Cria um Label para cada arquivo selecionado
                Label labelArquivo = new Label(arquivo.getName());
                lbArquivoSelecionado.getChildren().add(labelArquivo);
            });
        } else {
            // Adiciona uma mensagem padrão se nenhum arquivo foi selecionado
            Label vazio = new Label("Nenhum arquivo selecionado.");
            lbArquivoSelecionado.getChildren().add(vazio);
        }
    }
    

    private Stage getStage(Button button) {
        return (Stage) button.getScene().getWindow();
    }


}
