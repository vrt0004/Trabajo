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
import analisis.AnalisisSLR1;
import analisis.analisisSintactico.SLR1;
import analisis.analisisSintactico.ascendente.Automata;
import analisis.tabla.Tabla;
import analisis.tabla.TablaAscendente;
import gramatica.Gramatica;
import gramatica.Nulo;
import gramatica.Terminal;
import gramatica.VectorSimbolos;
import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;



public class SLRPanel extends ProblemaPanel<SLR1> {


	private static final long serialVersionUID = -8899275410326830826L;
	public static String fichero;
	

	private JPanel gramPanel;
	static JTextArea expresionText;
	private JPanel botonesPanel;

	private JButton guardarButton;
	private JButton borrarButton;
	private JPanel opcionesPanel;
	private JButton cargarButton;
	protected Problema<SLR1> problemaActual = null;
	private Label cadenalabel;
	
	public static JTextArea cadenaText;


	@SuppressWarnings("static-access")
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

	
		this.expresionText = new JTextArea(10, 20);
		this.gramPanel.add(this.expresionText);

		this.cadenalabel = new Label("Cadena");
		this.mainPanel.add(this.cadenalabel);

		this.cadenaText = new JTextArea(1, 0);
		this.mainPanel.add(this.cadenaText);
		this.gramPanel.add(this.expresionText);
		
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
		this.opcionesPanel.setLayout(new BoxLayout(this.opcionesPanel,
				BoxLayout.Y_AXIS));

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
				JOptionPane.showMessageDialog(null,"Error al mostrar la gramática");
			}
		}
	}
	private class BotonGuardarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			List<Object> salida = new ArrayList<Object>();
			ArrayList<Object> itemmarcado = new ArrayList<Object>();
			List<Object> producciones = new ArrayList<Object>();
			List<Object> noterminales = new ArrayList<Object>();
			List<Object> terminales = new ArrayList<Object>();
			List<Object> TodosSimbolos = new ArrayList<Object>();
			ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
			Gramatica g = null;
			
			try {
				g = pg.parsearGramaticaCadena(expresionText.getText());
			} catch (ParseException e) {

				JOptionPane.showMessageDialog(null,"Error al parsear la gramática");
			}
			g.obtenerFirst();
			String cadena2 = null;
			String nombregramatica = fichero;

			
			for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
				producciones.add(g.obtenerProduccionGramatica(i));
			}
			cadena2 = "<h1>" + nombregramatica +" &#9 &#9 Análisis SLR</h1>"+ "<br><h2>Producciones</h2>" + g.producciones.toString().replace("\n", "<br>")
					+ "<br>";
			
			
			cadena2 += "<br><h1>Conjuntos de items marcados</h1>";
			AnalisisSLR1 analisis = new AnalisisSLR1(g);
			Automata ana = analisis.obtenerAutomataAnalisis();
			
			for (int i = 0; i < ana.numeroNodosAutomata(); i++) {

				for (String j : ana.obtenerNodoAutomata(i).toString().trim().split("\n")) {
					itemmarcado.add(j);
				}
				
				cadena2 += "<table border=\"1\"><th colspan=\"2\">conjunto "+i+"</th> ";
				for(int k=0;k<itemmarcado.size();k++){
					cadena2 +="<tr><td>"+itemmarcado.get(k)+"</td></tr>";
				}
				
				itemmarcado.clear();
				cadena2 += "</table><br>";
			}

			for (int i = 0; i < g.obtenerTerminales().simbolosIntroducidos(); i++) {
				terminales.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
				TodosSimbolos.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
			}
			
			TodosSimbolos.add("$");
			for (int i = 0; i < g.obtenerNoTerminales().simbolosIntroducidos(); i++) {
				noterminales.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
				TodosSimbolos.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
			}
			
			
			Tabla tabla = analisis.obtenerTablaAnalisis();
			TablaAscendente tabla_principal1 = (TablaAscendente) tabla;
			Object elemento;


			int num = analisis.obtenerAutomataAnalisis().numeroNodosAutomata();
			cadena2 += "<br><h1>Tabla de ACCION e IR A</h1>";
			int numter = g.obtenerTerminales().simbolosIntroducidos() + 1;
			int numter2=g.obtenerNoTerminales().simbolosIntroducidos();
			cadena2 += "<table border=\"1\"><tr><th></th><th colspan="+numter+">Tabla de accion </th><th colspan="+numter2+">Tabla de ir a</th></tr><tr><td>estado</td>";
			for (Object j : TodosSimbolos) {
				cadena2 +="<td>"+j+"</td>";
			}
			cadena2 +="</tr>";
			for (int i = 0; i < num; i++) {
				cadena2 +="<tr><td>"+i+"</td>";
				for (Object j : TodosSimbolos) {
					elemento = tabla_principal1.tabla_principal.get(i).get(j);
					if (elemento == null) {
							cadena2 +="<td>-</td>";
					} else {
							cadena2 +="<td>"+Prototipo.eliminarparentesis(elemento)+"</td>";
					}
				}
			}
			
			cadena2 += "</table><br>";
			String cadenaintr = cadenaText.getText();
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
					JOptionPane.showMessageDialog(null, "Error al analizar la traza");
				}
			}
			main.vistaPreviaText.setText(cadena2);
		}
	}


	public void problema(Problema<SLR1> problema) {
		problema.setFichero(fichero);
		problema.setNumero(numero);
		problemaActual = problema;
		expresionText.setText(problema.getProblema().toString());

	}




	

	
}

