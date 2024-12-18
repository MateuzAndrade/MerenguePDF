package org.pegamtita.merenguepdf.controller;

import com.gluonhq.attach.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.pegamtita.merenguepdf.service.PdfService;
import org.pegamtita.merenguepdf.util.Mensage;

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
    private Button bt_separar;

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

    private final Mensage mensage = new Mensage();

    private final PdfService pdfService = new PdfService();

    private Stage getStage(Button button) {
        return (Stage) button.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_selecionar.setOnAction(event -> selecionarArquivo());
        bt_salvar.setOnAction(event -> localSalvar());
        bt_separar.setOnAction(event -> separarArquivo());
    }

    private void selecionarArquivo() {
        arquivoSelecionado = pdfService.selecionarArquivo(getStage(bt_selecionar), "*.pdf");
        if (arquivoSelecionado != null) {
            lbArquivoSelecionado.setText(arquivoSelecionado.getName());
            int totalPaginas = pdfService.ObterTotalPagina(arquivoSelecionado);
            if (totalPaginas > 0) {
                lbTotalPaginasDoc.setText(String.valueOf(totalPaginas));
            }else {
                lbTotalPaginasDoc.setText("Erro ao Carregar Arquivo");
            }
        } else {
            lbArquivoSelecionado.setText("Nenhum Aquivo foi Selecionado");
        }
    }

    private void localSalvar(){
       arquivoSaida = pdfService.selecionarLocalSalvar(getStage(bt_salvar),"Separado");
        if (arquivoSaida != null){
            lbLocalSalvarArquivo.setText(arquivoSaida.getAbsolutePath());
        }else {
            lbLocalSalvarArquivo.setText("Nenhum local foi selecionado para salvar");
        }
    }
    
    private void separarArquivo(){
        if (arquivoSelecionado == null || arquivoSaida == null){
            lbLocalSalvarArquivo.setText("Nenhum local foi selecionado para salvar");
            return;
        }


            int paginaInicial = Integer.parseInt(txPgInicial.getText());
            int paginaFinal = Integer.parseInt(txPgFinal.getText());
            int totalPaginas = Integer.parseInt(lbTotalPaginasDoc.getText());

            if ( paginaInicial < 1 || paginaFinal > totalPaginas || paginaInicial > paginaFinal){

                mensage.exibir("Atenção","Intervalo de Páginas Invalido", Alert.AlertType.WARNING);
                return;
            }

            try(PDDocument document = PDDocument.load(arquivoSelecionado);
                PDDocument novoDocumento = new PDDocument()){

                for (int i = paginaInicial - 1; i < paginaFinal; i++){
                    novoDocumento.addPage(document.getPage(i));
                }

                novoDocumento.save(arquivoSaida);
                mensage.exibir("Sucesso","Arquivo Gerado", Alert.AlertType.INFORMATION);

            }catch (NumberFormatException e) {
                lbLocalSalvarArquivo.setText("Erro: Insira números válidos.");
                e.printStackTrace();
            } catch (Exception e) {
                lbLocalSalvarArquivo.setText("Erro ao separar o arquivo.");
                e.printStackTrace();
            }

    }


}
