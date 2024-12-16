package org.pegamtita.merenguepdf.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;

public class PdfService {

    public File selecionarArquivo(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione um arquivo PDF para Separar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        return fileChooser.showOpenDialog(stage);
    }

    public File selecionarLocalSalvar (Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha onde salvar o arquivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos","*.all"));
        fileChooser.setInitialFileName("Separado.pdf");
        return fileChooser.showSaveDialog(stage);
    }

    public int ObterTotalPagina(File arquivo){
        try(PDDocument document = PDDocument.load(arquivo)) {
            return document.getNumberOfPages();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public boolean separarPdf(File arquivoOriginal, File arquivoSaida, int paginaInicial, int paginalFinal){
        try (PDDocument document = PDDocument.load(arquivoOriginal);
            PDDocument novoDocumento = new PDDocument()){

            for (int i = paginaInicial -1; i < paginalFinal; i++){
                novoDocumento.addPage(document.getPage(i));
            }
            novoDocumento.save(arquivoSaida);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
