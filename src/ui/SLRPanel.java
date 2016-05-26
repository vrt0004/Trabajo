package ui;



import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Prototipo.Prototipo;
import analisis.analisisSintactico.SLR1;



public class SLRPanel extends ProblemaPanel<SLR1> {


	private static final long serialVersionUID = -8899275410326830826L;
	public String fichero;
	

	private JPanel gramPanel;
	private JTextArea expresionText;
	private JPanel botonesPanel;

	private JButton guardarButton;
	private JButton borrarButton;
	private JPanel opcionesPanel;
	private JButton cargarButton;
	protected Problema<SLR1> problemaActual = null;
	
	


	@SuppressWarnings("rawtypes")
	public SLRPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("SLR");


		this.gramPanel = new JPanel();
		this.mainPanel.add(this.gramPanel);
		this.gramPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.borrarButton = new JButton("-");
		this.borrarButton.addActionListener(new BotonBorrarActionListener());
		this.gramPanel.add(this.borrarButton);

		this.expresionText = new JTextArea(10,20);

		this.gramPanel.add(this.expresionText);
		
		this.botonesPanel = new JPanel();
		this.mainPanel.add(this.botonesPanel);

		this.cargarButton = new JButton("Cargar");
		this.cargarButton.addActionListener(new BotoncargarActionListener());
		this.botonesPanel.add(this.cargarButton);

		this.guardarButton = new JButton("Guardar");
		this.guardarButton.addActionListener(new BotonGuardarActionListener());
		this.botonesPanel.add(this.guardarButton);

		this.opcionesPanel = new JPanel();
		this.mainPanel.add(this.opcionesPanel);
		this.opcionesPanel.setLayout(new BoxLayout(this.opcionesPanel,
				BoxLayout.Y_AXIS));

	}
	
	private class BotoncargarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			FileDialog dialogoArchivo;

			Frame f = null;
			dialogoArchivo = new FileDialog(f, "Abrir gram�tica", FileDialog.LOAD);
			dialogoArchivo.setVisible(true);
			String cadena = "";

			FileReader fr;
			Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "gramaticas" + System.getProperty("file.separator"));
			try {
				fr = new FileReader(path + System.getProperty("file.separator") + dialogoArchivo.getFile());
				BufferedReader b = new BufferedReader(fr);
				while ((b.readLine()) != null) {
					cadena = cadena + b.readLine();
					cadena = cadena + "\n";
				}
				expresionText.setText(cadena);
				b.close();
				
				fichero = dialogoArchivo.getFile();
				String a = "-g " + fichero + " -t SLR ";
				String[] args = a.split(" ");
				Prototipo.main(args);
				FileReader fr2 = new FileReader(Prototipo.ruta);
				BufferedReader b2 = new BufferedReader(fr2);

				String cadena2 = "";
				String sCadena;
				while ((sCadena = b2.readLine())!=null) {
					   cadena2 = cadena2 + sCadena + "\n";
					}
				b2.close();
				main.vistaPreviaText.setText(cadena2);

			} catch (IOException e) {
				
			
			}
		}
	}
	private class BotonGuardarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Frame f = null;
			FileDialog dialogoArchivo = new FileDialog(f, "Guardar gram�tica", FileDialog.SAVE);
			dialogoArchivo.setVisible(true);
			System.out.println(dialogoArchivo.getDirectory()+ dialogoArchivo.getName());
			File fichero = new File(dialogoArchivo.getDirectory(), dialogoArchivo.getFile());
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
				bw.write(expresionText.getText());
				bw.close();
			} catch (IOException e) {
				
				
			}
			

		}

	}


	public void problema(Problema<SLR1> problema) {
		problema.setFichero(fichero);
		problema.setNumero(numero);
		problemaActual = problema;
		expresionText.setText(problema.getProblema().toString());

	}




	

	
}

