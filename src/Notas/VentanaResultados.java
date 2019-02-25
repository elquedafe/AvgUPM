package Notas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaResultados extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Float media;
	private ArrayList<Asignatura> listaAsignaturas;
	private JTextField textMedia;
	private JButton buttBack;
	private JPanel panelBack;
	private JScrollPane jScroll;
	private JTable table;
	private DefaultTableModel model;
	private String path;
	
	public VentanaResultados(ArrayList<Asignatura> listaAsignaturas, float media, String path){
		super("Ventana de resultados");
		this.listaAsignaturas = listaAsignaturas;
		this.media =  media;
		this.path = path;
		run();
	}

	public void run(){
		//CREAR COMPONENTES
		panelBack = new JPanel();
		buttBack = new JButton("<< Volver");
		new JTextArea();
		textMedia = new JTextField();

		//AÑADIR LISTENERS
		buttBack.addActionListener(new Volver());
		//AÑADIR ASIGNATURAS A LA TABLA
		textMedia.setText("***NOTA MEDIA PONDERADA*** = " + media.toString());
		table = new JTable();
		model = new DefaultTableModel();
		model.addColumn("Curso");
		model.addColumn("Asignatura");
		model.addColumn("Calificacion");
		model.addColumn("Creditos");
		for(Asignatura a : listaAsignaturas){
			Float f = a.getCalificacion();
			Integer c = a.getCurso();
			Float cr = a.getCreditos();
			ArrayList<String> s = new ArrayList<String>();
			s.add(c.toString());  s.add(a.getNombre()); s.add(f.toString());s.add(cr.toString()+" creditos");
			Object[] r = s.toArray();
			model.addRow(r);
		}

		table.setColumnSelectionAllowed(false);
		table.setModel(model);
		jScroll = new JScrollPane(table);
		//AÑADIR COMPONENTES
		panelBack.add(buttBack);
		add(panelBack, BorderLayout.NORTH);
		add(jScroll);
		add(textMedia, BorderLayout.SOUTH);

		pack();
	}

	class Volver implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			dispose();
			GUI gui = new GUI(path);
			gui.pack();
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.setVisible(true);
		}
	}
}
