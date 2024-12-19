package org.pegamtita.merenguepdf.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public boolean converterImagemParaPdf(File imagem, File arquivoSaida){
        try (PDDocument document = new PDDocument()){
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(imagem.getAbsolutePath(),document);
            PDPageContentStream contentStream = new PDPageContentStream(document,page);
            contentStream.drawImage(pdImage, 50, 50, page.getMediaBox().getWidth() - 100, page.getMediaBox().getHeight() - 100);
            contentStream.close();
            document.save(arquivoSaida);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean converterTextoParaPdf(File texto, File arquivoSaida) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ler o texto do arquivo e adicionar ao PDF
            String conteudo = new String(Files.readAllBytes(texto.toPath()));
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);

            for (String linha : conteudo.split("\n")) {
                contentStream.showText(linha);
                contentStream.newLineAtOffset(0, -15);
            }

            contentStream.endText();
            contentStream.close();

            // Salvar o PDF
            document.save(arquivoSaida);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean converterWordParaPdf(File arquivoWord, File arquivoSaida) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(arquivoWord);
            Docx4J.toPDF(wordMLPackage, new java.io.FileOutputStream(arquivoSaida));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
