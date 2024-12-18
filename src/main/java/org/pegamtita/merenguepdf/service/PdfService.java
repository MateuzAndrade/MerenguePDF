package org.pegamtita.merenguepdf.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfService {

    public File selecionarArquivo(Stage stage, String typeArquivo){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione um arquivo PDF para Separar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos", typeArquivo));
        return fileChooser.showOpenDialog(stage);
    }

    public List<File> selecionarVariosArquivos(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione os Arquivos PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        return fileChooser.showOpenMultipleDialog(stage);
    }

    public File selecionarLocalSalvar (Stage stage, String nomeArquivoSaida){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha onde salvar o arquivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos","*.all"));
        fileChooser.setInitialFileName(nomeArquivoSaida);
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

    public boolean juntarPDF(List<File> arquivosSelecionados, File arquivoSaida){
        PDFMergerUtility merger = new PDFMergerUtility();
        merger.setDestinationFileName(arquivoSaida.getAbsolutePath());

        try {
            for (File arquivo : arquivosSelecionados){
                merger.addSource(arquivo);
            }
            merger.mergeDocuments(null);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

}
