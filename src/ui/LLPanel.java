package ui;


import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Prototipo.Prototipo;
import analisis.AnalisisLL1;
import analisis.analisisSintactico.LL1;
import analisis.tabla.Tabla;
import analisis.tabla.TablaDescendente;
import gramatica.Gramatica;
import gramatica.Nulo;
import gramatica.Terminal;
import gramatica.VectorSimbolos;
import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;

public class LLPanel extends ProblemaPanel<LL1> {

	private static final long serialVersionUID = -8899275410326830826L;
	public static String fichero;
	private JPanel gramPanel;
	public static JTextArea expresionText;
	private JPanel botonesPanel;

	private JButton guardarButton;
	private JButton borrarButton;
	private JPanel opcionesPanel;

	protected Problema<LL1> problemaActual = null;

	private JButton cargarButton;
	public static JTextArea cadenaText;

	private Label cadenalabel;

	@SuppressWarnings("static-access")
	public LLPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("LL");

		this.gramPanel = new JPanel();
		this.mainPanel.add(this.gramPanel);
		this.gramPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.borrarButton = new JButton("-");
		this.borrarButton.addActionListener(new BotonBorrarActionListener());
		this.gramPanel.add(this.borrarButton);

		this.expresionText = new JTextArea(10, 20);
		this.gramPanel.add(this.expresionText);

		this.cadenalabel = new Label("Cadena");
		this.mainPanel.add(this.cadenalabel);

		this.cadenaText = new JTextArea(1, 0);
		this.mainPanel.add(this.cadenaText);

		this.botonesPanel = new JPanel();
		this.mainPanel.add(this.botonesPanel);

		this.cargarButton = new JButton("Cargar");
		this.cargarButton.addActionListener(new BotoncargarActionListener());
		this.botonesPanel.add(this.cargarButton);

		this.guardarButton = new JButton("Mostrar");
		this.guardarButton.addActionListener(new BotonGuardarActionListener());
		this.botonesPanel.add(this.guardarButton);

		this.opcionesPanel = new JPanel();
		this.mainPanel.add(this.opcionesPanel);
		this.opcionesPanel.setLayout(new BoxLayout(this.opcionesPanel, BoxLayout.Y_AXIS));

	}

	private class BotoncargarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			Frame f = null;
			FileDialog dialogoArchivo = new FileDialog(f, "Abrir gramática", FileDialog.LOAD);
			dialogoArchivo.setVisible(true);
			String cadena = "";
			FileReader fr;
			Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "gramaticas" + System.getProperty("file.separator"));
			fichero = dialogoArchivo.getFile();
			try {
				fr = new FileReader(path + System.getProperty("file.separator") + dialogoArchivo.getFile());
				BufferedReader b = new BufferedReader(fr);
				String sCadena;
				while ((sCadena = b.readLine()) != null) {
					cadena = cadena + sCadena + "\n";
				}
				expresionText.setText(cadena);
				b.close();

			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null,"Error al cargar la gramática");
			}
		}
	}

	private class BotonGuardarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			List<Object> salida = new ArrayList<Object>();
			ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
			Gramatica g = null;

			try {
				g = pg.parsearGramaticaCadena(LLPanel.expresionText.getText());
			} catch (ParseException e) {

				JOptionPane.showMessageDialog(null,"Error al parsear la gramática");
			}
			g.obtenerFirst();
			String cadena2 = null;
			String nombregramatica = fichero;

			List<Object> producciones = new ArrayList<Object>();
			List<Object> noterminales = new ArrayList<Object>();
			List<Object> terminales = new ArrayList<Object>();
			for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
				producciones.add(g.obtenerProduccionGramatica(i));
			}
			cadena2 = "<h1>" + nombregramatica + " &#9 &#9 Análisis LL</h1>" + "<br><h2>Producciones</h2>"
					+ g.producciones.toString().replace("\n", "<br>") + "<br>";

			for (int i = 0; i < g.obtenerNoTerminales().simbolosIntroducidos(); i++) {
				noterminales.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
			}
			for (int i = 0; i < g.obtenerTerminales().simbolosIntroducidos(); i++) {
				terminales.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
			}
			cadena2 += "<h1>First</h1> <br>";
			cadena2 += g.obtenerFirst().toString().replace("\n", "<br>").replace("{", ":}").replace(" ", ",")
					.replace("}", " ");
			cadena2 += "<br><h1>Follow </h1><br>";
			cadena2 += g.obtenerFollow().toString().replace("\n", "<br>").replace("{", ":}").replace(" ", ",")
					.replace("}", " ");

			cadena2 += "<br><h1>Tabla de Analisis sintactico predictivo</h1>";
			String cadenaintr = cadenaText.getText();
			AnalisisLL1 analisis = new AnalisisLL1(g);
			Tabla tabla = analisis.obtenerTablaAnalisis();
			TablaDescendente tabla_principal = (TablaDescendente) tabla;
			cadena2 += "<table border=\"1\"><thead><tr><th></th>";
			for (Object i : terminales) {
					cadena2 += "<th>"+i+"</th>"; 
				}
			for (Object j2 : noterminales) {
				cadena2 += "</tr> <tr>";
				cadena2 +="<th> "+j2+" </th>";
				for (Object i : terminales) {
					if (tabla_principal.tabla_principal.get(i).get(j2).toString().equals("")
							|| tabla_principal.tabla_principal.get(i).get(j2).toString().trim().equals(0)) {				 
						 cadena2 +="<th> - </th>";
					} else {
						cadena2 +="<th>"+ tabla_principal.tabla_principal.get(i).get(j2).toString()+"</th>";	
					}
				}cadena2 += "</tr>";
				cadena2 += "</tr>";
			}
			
			cadena2 += "</table>";
			VectorSimbolos ent = new VectorSimbolos();
			for (String j : cadenaintr.split(" ")) {
				Terminal a = new Terminal(j);
				ent.insertarSimbolo(a);

			}
			Nulo nulo = new Nulo();
			ent.insertarSimbolo(nulo);
			analisis.iniciarAnalisis(ent);

			try {

				int i = 0;
				while (true) {
					analisis.realizarIteracion(i, i + 1);
					if (i == 0) {
						cadena2 += "<h1>Cadena de la traza</h1>";
						cadena2 += "<table border=\"1\"><thead>  <tr> <th>Pila</th><th> Estado</th><th> Salida</th></tr> <tr>";
						cadena2 += "<th>"
								+ Prototipo.eliminarparentesis(analisis.obtenerEstadoPilaAnalisis().toString())
								+ "</th>";
						cadena2 += "<th>" + analisis.obtenerEstadoEntradaAnalisis().toString() + "</th>";
						cadena2 += "<th>" + analisis.obtenerProduccionSalida() + "</th>";
						cadena2 += "</tr>";
					} else {
						String pila = Prototipo.eliminarparentesis(analisis.obtenerEstadoPilaAnalisis().toString());
						String entrada = analisis.obtenerEstadoEntradaAnalisis().toString();
						salida.add(analisis.obtenerProduccionSalida());
						cadena2 += "<tr>";
						cadena2 += "<th>" + pila + "</th>";
						cadena2 += "<th>" + entrada + "</th>";
						cadena2 += "<th>" + Prototipo.eliminarparentesis(salida) + "</th>";
						cadena2 += "</tr>";
						salida.remove(analisis.obtenerProduccionSalida());
					}
					i++;
				}
			} catch (Exception e) {
				try {
					String pila = Prototipo.eliminarparentesis(analisis.obtenerEstadoPilaAnalisis().toString());
					String entrada = analisis.obtenerEstadoEntradaAnalisis().toString();
					cadena2 += "<tr>";
					cadena2 += "<th>" + pila;
					cadena2 += "<th>" + entrada;
					cadena2 += "<th>" + Prototipo.eliminarparentesis(salida);
					cadena2 += "</tr>";
					cadena2 += "</table>";
				} catch (NullPointerException s) {
				}
			}
			main.vistaPreviaText.setText(cadena2);

		}
	}
	public void problema(Problema<LL1> problema) {

		problema.setFichero(fichero);
		problema.setNumero(numero);
		problemaActual = problema;
		expresionText.setText(problema.getProblema().toString());

	}
}
