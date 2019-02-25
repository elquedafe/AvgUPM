package Notas;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.text.PDFTextStripper;
public class PDFManager {
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private String text;
	private float media;
	private ArrayList<Asignatura> listaAsignturas;
	private String filePath;
	private File file;
	private boolean isExpediente;
	
	public PDFManager(){}
	
	public boolean getIsExpediente(){
		return this.isExpediente;
	}
	
	public ArrayList<Asignatura> getAsignaturas() throws IOException{
		this.pdfStripper = null;
		this.pdDoc = null;
		this.listaAsignturas = new ArrayList<Asignatura>();
		file = new File(filePath);
		//pdDoc = new PDDocument().load(file);
		pdDoc = PDDocument.load(file);
		pdfStripper = new PDFTextStripper();
		int pages = pdDoc.getNumberOfPages();
		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(pages);
		text = pdfStripper.getText(pdDoc);
		GestorPDF gestor = new GestorPDF();
		listaAsignturas= gestor.getAsignaturas(text);
		isExpediente = gestor.getIsExpediente();
		pdDoc.close();

		this.media = gestor.mediaPonderada(listaAsignturas);
		return listaAsignturas;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	public float getMedia(){
		return this.media;
	}
	
}
