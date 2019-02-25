package Notas;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String informacion = "<html><u>Descargar desde la Politécnica Virtual el expediente de <b>última situación</b></u></html>";
	private JPanel panelInfo, panelLoad, panelSearch;
	private JTextField textPath;
	private JLabel infoPath, etiqueta;
	private JButton buttLoad, buttSearch;
	private String path;

	public GUI(String path){
		super("Media Universidad");
		this.path = path;
		this.run();
	}

	public void run(){
		BorderLayout layout = new BorderLayout(0,0);
		setLayout(layout);

		//CREAR PANELES
		panelInfo = new JPanel();
		panelSearch = new JPanel();
		panelLoad = new JPanel();

		//CREAR COMPONENTES
		infoPath = new JLabel(informacion);
		textPath = new JTextField(path);
		textPath.setToolTipText("Path");
		buttLoad = new JButton("Cargar expediente");
		buttSearch = new JButton(". . .");
		etiqueta = new JLabel("Path del expediente:");

		//AÑADIR COMPONENTES A PANELES
		panelInfo.add(infoPath);
		panelLoad.add(buttLoad);
		panelSearch.add(buttSearch);

		//CREAR LISTENERS
		buttSearch.setAlignmentY(textPath.getAlignmentY());
		buttLoad.addActionListener(new ObtenerPath());	
		buttSearch.addActionListener(new SearchFile());
		infoPath.setCursor(new Cursor(Cursor.HAND_CURSOR));
		infoPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.upm.es/politecnica_virtual/login.upm"));
				} catch (URISyntaxException | IOException ex) {
					//It looks like there's a problem
				}
			}
		});
		//AÑADIR PANELES
		add(panelInfo, BorderLayout.NORTH);
		add(textPath, BorderLayout.CENTER);
		add(panelLoad, BorderLayout.SOUTH);
		add(panelSearch, BorderLayout.EAST);
		add(etiqueta, BorderLayout.WEST);
	}

	class ObtenerPath implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			PDFManager pdfManager = new PDFManager();
			boolean abrirResultados = true;
			boolean comprobarExpediente = true;
			//ARCHIVO ES EL EXPEDIENTE EN ULTIMA SITUACION
			path = textPath.getText();
			ArrayList<Asignatura> listaAsignaturas = null;
			float media=0;

			try {
				pdfManager.setFilePath(path); //path=/Users/alvaro/Documents/ProyectoNotasUniversidad/Pruebas_PDF/expediente06312548656231.pdf
				listaAsignaturas =pdfManager.getAsignaturas();
				media = pdfManager.getMedia();
			} catch (FileNotFoundException p){
				JOptionPane.showMessageDialog(null, "Archivo no encontrado", "Error", 
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon( getClass().getResource("icons/cancel.png") ));
				abrirResultados = false;
				comprobarExpediente = false;
			}catch (IOException r) {
				JOptionPane.showMessageDialog(null, "Error al cargar archivo", "Error", 
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon( getClass().getResource("icons/cancel.png") ));
				abrirResultados = false;
				comprobarExpediente = false;
			}
			if(comprobarExpediente){
				if(!pdfManager.getIsExpediente()){
					JOptionPane.showMessageDialog(null, "El archivo no es detectado como expediente", "Error", 
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon( getClass().getResource("icons/cancel.png") ));
					abrirResultados = false;
				}
			}
			if(abrirResultados){
				dispose();
				//SIGUIENTE VENTANA
				JFrame ventanaResultados = new VentanaResultados(listaAsignaturas, media, path);
				ventanaResultados.pack();
				ventanaResultados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ventanaResultados.setVisible(true);
			}
		}

	}

	class SearchFile implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				textPath.setText(selectedFile.getPath());
			}
		}
	}
}
