package Prototipo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.Maps;

import analisis.Analisis;
import analisis.AnalisisLALR1;
import analisis.AnalisisLL1;
import analisis.AnalisisLR1;
import analisis.AnalisisSLR1;

import analisis.analisisSintactico.ascendente.Automata;
import analisis.tabla.Tabla;
import analisis.tabla.TablaAscendente;
import analisis.tabla.TablaDescendente;

import gramatica.Gramatica;

import gramatica.Nulo;
import gramatica.Terminal;
import gramatica.VectorSimbolos;
import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;

/**
 *
 * <b>Descripción</b><br>
 * Prototipo de línea de comandos utilizando clases de Burgram
 * <p>
 * <b>Detalles</b><br>
 * La idea es tener una pequeña aplicación en modo texto que:<br>
 * 
 * 1. Toma como argumento el nombre de un archivo con la especificaci�n de una
 * gramática en el formato YACC<br>
 * 
 * 2. Utilice su contenido para utilizando las clases adecuadas de Burgram para
 * instanciar un objeto 'Gramatica'<br>
 * 
 * 3. Interrogue el objeto gramática para mostrar el first y follow de los
 * símbolos de la gramática.<br>
 * 
 * 4.Muestre la tabla de analisis sintactico predictivo
 * 
 * 5.Genere un informe de la extension deseada
 * </p>
 * 
 * @author Victor Renuncio
 * @version 1.0
 */
public class Prototipo {

	public static List<String> argumentos = null;
	public static String informe;
	public static String cadena;
	public static String ficherosalida = "";
	public static String ruta;

	public static void main(String[] args) throws IOException {

		final String DEF_INFORME = "ALL";
		CommandLineParser parser = null;
		CommandLine cmdLine = null;

		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("mustache.debug", "true");
		systemProperties.setProperty("mustache.profile", "true");
		///////////////////////////////////////////////////////////////////////
		// Fase 1: Configuramos las opciones de validación de entrada.
		///////////////////////////////////////////////////////////////////////

		Options options = new Options();
		options.addOption("g", true, "Nombre de la gramatica a analizar");
		options.addOption("t", true, "Tipo de analisis a realizar[LL,SLR,LARL,LR] ");
		options.addOption("i", true, "Extension del informe [TEX,XML],por defecto ALL");
		options.addOption("ca", true, "Cadena a analizar");
		options.addOption("o", true, "Nombre fichero de salida");
		options.addOption("h", "help", false, "Imprime el mensaje de ayuda");

		// No pueden aparecen las dos opciones simultaneamente.
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("err", "Salida estandar de errores"));
		group.addOption(new Option("console", "Salida estandar"));
		options.addOptionGroup(group);

		try {

			///////////////////////////////////////////////////////////////////////
			// Fase 2: Parseamos la entrada con la configuración establecida
			///////////////////////////////////////////////////////////////////////

			parser = new DefaultParser();
			cmdLine = parser.parse(options, args);

			///////////////////////////////////////////////////////////////////////
			// Fase 3: Analizar los resultados y realizar las tareas pertinentes
			///////////////////////////////////////////////////////////////////////

			// Si está la opcion de ayuda, la imprimimos y salimos.
			if (cmdLine.hasOption("h")) {
				new HelpFormatter().printHelp("java -jar PLGRAMlineComand.jar" + " -g -t [-i][-ca][-o] ", options);
			}

			// Leemos la gramatica y el tipo de analisis. Sino existen generamos
			// un error pues es un parámetro requerido.

			String gramatica = cmdLine.getOptionValue("g");
			if (gramatica == null) {
				throw new org.apache.commons.cli.ParseException("La gramatica es requerida");
			}
			String tipoanalisis = cmdLine.getOptionValue("t");
			if (tipoanalisis == null) {
				throw new org.apache.commons.cli.ParseException("El tipo de analisis a realizar es requerido");
			}

			// Si el usuario ha especificado el informe lo leemos

			if (cmdLine.hasOption("i")) {
				informe = cmdLine.getOptionValue("i");
			} else {
				informe = DEF_INFORME;
			}
			if (cmdLine.hasOption("ca")) {
				cadena = cmdLine.getOptionValue("ca");
			}
			if (cmdLine.hasOption("o")) {
				ficherosalida = cmdLine.getOptionValue("o");
			}
			// ..............................................................
			// Aquí van las tareas que tiene que realizar la aplicación
			// ..............................................................
			File directoriogram = new File(
					System.getProperty("user.dir") + System.getProperty("file.separator") + "gramaticas");
			directoriogram.mkdir();

			Gramatica g = null;
			Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "gramaticas" + System.getProperty("file.separator"));
			ParserGramatica pg = new ParserGramatica(false, new ParserYacc());

			try {
				g = pg.parsearGramaticaArchivo(path + System.getProperty("file.separator") + gramatica);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			}
			Analisis analisis = null;
			if (args.length > 0) {

				System.out.println("gramatica:   " + gramatica);
				System.out.println();
				System.out.println(g.toString());
				System.out.println();
				System.out.println("first");
				System.out.println(g.obtenerFirst());
				System.out.println();
				System.out.println("follow");
				System.out.println(g.obtenerFollow());
				System.out.println();

				switch (tipoanalisis) {

				case "LL":
					analisis = new AnalisisLL1(g);
					break;
				case "SLR":
					analisis = new AnalisisSLR1(g);
					break;
				case "LALR":
					analisis = new AnalisisLALR1(g);
					break;
				case "LR":
					analisis = new AnalisisLR1(g);
					break;
				}
			}
			File directorio = new File(
					System.getProperty("user.dir") + System.getProperty("file.separator") + "informes");
			directorio.mkdir();
			switch (informe) {
			case "TEX":
				creaTex(g, gramatica, analisis, tipoanalisis, cadena);
				break;
			case "XML":
				creaXML(g, gramatica, analisis, tipoanalisis, cadena);
				break;
			case "ALL":
				creaTex(g, gramatica, analisis, tipoanalisis, cadena);
				creaXML(g, gramatica, analisis, tipoanalisis, cadena);
				break;

			}

		} catch (org.apache.commons.cli.ParseException ex) {
			System.out.println(ex.getMessage());

			new HelpFormatter().printHelp("java -jar PLGRAMlineComand.jar" + " -g -t [-i][-ca][-o] ", options); // Error,
			// imprimimos la ayuda
		} catch (java.lang.NumberFormatException ex) {
			new HelpFormatter().printHelp("java -jar PLGRAMlineComand.jar" + " -g -t [-i][-ca][-o]", options); // Error,
			// imprimimos la ayuda
		}
	}

	/**
	 * Método que genera el informe en .XML
	 * 
	 * @param g
	 *            Gramática a anilizar
	 * @param gramatica
	 *            Nombre de la gramática
	 * @param analisis
	 *            Análisis de la gramática
	 * @param tipoanalisis
	 *            Tipo de análisis
	 * @throws IOException
	 */
	public static void creaXML(Gramatica g, String gramatica, Analisis analisis, String tipoanalisis, String cadena)
			throws IOException {

		List<Object> producciones = new ArrayList<>();

		List<Object> terminales = new ArrayList<>();
		List<Object> noterminales = new ArrayList<>();
		List<Object> TodosSimbolos = new ArrayList<>();
		List<Object> orden = new ArrayList<>();
		String gramaticasin = gramatica;
		MustacheFactory mf = new DefaultMustacheFactory();

		Map<String, Object> context = Maps.newHashMap();
		context.put("NombreGramatica", gramatica);
		context.put("TipoAnalisis", tipoanalisis);
		context.put("SimboloInicio", g.getSimboloInicio());

		for (int i = 0; i < g.obtenerTerminales().simbolosIntroducidos(); i++) {
			terminales.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
			TodosSimbolos.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
		}
		
		TodosSimbolos.add("$");
		for (int i = 0; i < g.obtenerNoTerminales().simbolosIntroducidos(); i++) {
			noterminales.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
			TodosSimbolos.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
		}
		orden.addAll(noterminales);
		orden.addAll(terminales);
		context.put("Orden", orden);
		context.put("Terminales", terminales);
		context.put("NumTerminales", g.obtenerTerminales().simbolosIntroducidos());
		context.put("NumTerminalesmas1", g.obtenerTerminales().simbolosIntroducidos() + 1);
		context.put("NoTerminales", noterminales);
		context.put("NumNoTerminales", g.obtenerNoTerminales().simbolosIntroducidos());
		context.put("TodosSimbolos", TodosSimbolos);

		for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
			producciones.add(g.obtenerProduccionGramatica(i));
		}
		context.put("Producciones", producciones);

		switch (tipoanalisis) {

		case "LL":
			context.put("LL", true);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", false);
			context.put("First", g.obtenerFirst());
			context.put("Follow", g.obtenerFollow());
			List<Object> FirstFollow = creacadenasFirtsFollow(g.first.tabla, g.follow.tabla, noterminales, terminales,
					true, false);
			List<Object> FirstFollowSin = creacadenasFirtsFollow(g.first.tabla, g.follow.tabla, noterminales,
					terminales, false, false);

			context.put("FirstFollow", FirstFollow);
			context.put("FirstFollowSin", FirstFollowSin);
			List<TASP> RowTASP = creacadenasTASP(g, analisis, noterminales, terminales, producciones, true, false);
			List<TASP> RowTASPSin = creacadenasTASP(g, analisis, noterminales, terminales, producciones, false, false);

			context.put("RowTASP", RowTASP);
			context.put("RowTASPSin", RowTASPSin);

			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTrazaXML = creacadenasTraza(analisis, cadena, producciones, true, false);
				context.put("RowTrazaXML", RowTrazaXML);
				List<Object> RowTrazaSin = creacadenasTraza(analisis, cadena, producciones, false, false);
				context.put("RowTrazaSin", RowTrazaSin);

			}
			context.put("Cadena", cadena);
			break;

		case "SLR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", true);
			context.put("LALR", false);
			List<Object> conjuntosSLR = creacadenasconjuntosSLR(analisis, producciones, terminales, true,false);
			List<Object> conjuntosSLRSin = creacadenasconjuntosSLR(analisis, producciones, terminales, false,false);
			List<Taccioneir> accioneiraSLR = creacadenasaccioneiraLR(analisis, TodosSimbolos, true);
			List<Taccioneir> AccioneiraSLRSin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);
			context.put("ConjuntosSLR", conjuntosSLR);
			context.put("ConjuntosSLRSin", conjuntosSLRSin);
			context.put("AccioneiraSLR", accioneiraSLR);
			context.put("AccioneiraSLRSin", AccioneiraSLRSin);
			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTraza = creacadenasTraza(analisis, cadena, producciones, true, false);
				context.put("RowTraza", RowTraza);
				List<Object> RowTrazaSin = creacadenasTraza(analisis, cadena, producciones, false, false);
				context.put("RowTrazaSin", RowTrazaSin);
			}
			context.put("Cadena", cadena);
			break;

		case "LALR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", true);

			List<Object> conjuntosLALR = creacadenasconjuntosLR(analisis, producciones, terminales, true, false);
			List<Object> conjuntosLALRSin = creacadenasconjuntosLR(analisis, producciones, terminales, false, false);
			List<Taccioneir> accioneiraLALR = creacadenasaccioneiraLR(analisis, TodosSimbolos, true);
			List<Taccioneir> accioneiraLALRSin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);
			context.put("ConjuntosLALR", conjuntosLALR);
			context.put("AccioneiraLALR", accioneiraLALR);
			context.put("ConjuntosLALRSin", conjuntosLALRSin);
			context.put("AccioneiraLALRSin", accioneiraLALRSin);

			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTraza = creacadenasTraza(analisis, cadena, producciones, true, false);
				context.put("RowTraza", RowTraza);
				List<Object> RowTrazaSin = creacadenasTraza(analisis, cadena, producciones, false, false);
				context.put("RowTrazaSin", RowTrazaSin);
			}
			context.put("Cadena", cadena);
			break;

		case "LR":
			context.put("LL", false);
			context.put("LR", true);
			context.put("SLR", false);
			context.put("LALR", false);
			List<Object> conjuntosLR = creacadenasconjuntosLR(analisis, producciones, terminales, true, false);
			List<Taccioneir> accioneiraLR = creacadenasaccioneiraLR(analisis, TodosSimbolos, true);
			List<Taccioneir> accioneiraLRsin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);
			context.put("ConjuntosLR", conjuntosLR);
			context.put("AccioneiraLR", accioneiraLR);
			context.put("AccioneiraLRSin", accioneiraLRsin);
			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTrazaXML = creacadenasTraza(analisis, cadena, producciones, true, false);
				context.put("RowTrazaXML", RowTrazaXML);
			}
			context.put("Cadena", cadena);
			break;
		}

		if (ficherosalida == "") {
			for (char x : gramatica.toCharArray())
				if (x == '.') {
					gramaticasin = gramatica.substring(0, gramatica.indexOf('.'));
					break;
				}
			ficherosalida = tipoanalisis + "" + gramaticasin;
		}

		context.put("ficherosalida", ficherosalida);

		Mustache mustache;
		File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "informes"
				+ System.getProperty("file.separator"), ficherosalida + ".xml");

		File fichero2 = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "informes"
				+ System.getProperty("file.separator"), ficherosalida + ".tmp");

		try {
			mustache = mf.compile("plantillamoodle");

			// BufferedWriter bw = new BufferedWriter(new
			// FileWriter(fichero),"UTF-8");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "UTF-8"));

			mustache.execute(bw, context);
			bw.close();
			ruta = fichero2.getPath();
			System.out.println("Fichero.XML generado correctamente en " + fichero.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		fichero2.delete();
	}

	@SuppressWarnings("unchecked")
	private static List<Object> creacadenasconjuntosSLR(Analisis analisis, List<Object> producciones,
			List<Object> terminales, boolean preguntas,boolean tex) {

		List<Object> theconjuntos = new ArrayList<Object>();
		Automata ana = analisis.obtenerAutomataAnalisis();
		ArrayList<Object> itemmarcado = new ArrayList<Object>();
		ArrayList<Object> item = new ArrayList<Object>();

		ArrayList<Object> itemmarcadocadena = new ArrayList<Object>();
		ArrayList<Object> todositems = new ArrayList<Object>();
		ArrayList<Object> todossub = new ArrayList<Object>();
		for (int i = 0; i < ana.numeroNodosAutomata(); i++) {
			for (String j : ana.obtenerNodoAutomata(i).toString().trim().split("\n")) {
				todossub.add(j);
			}
		}
		for (int i = 0; i < ana.numeroNodosAutomata(); i++) {

			int numitems = ana.obtenerNodoAutomata(i).numeroElementosCjtoConfig();

			for (String j : ana.obtenerNodoAutomata(i).toString().trim().split("\n")) {
				itemmarcado.add(j);
			}
			for (Object j : itemmarcado) {
				item.add(j);
				List<Object> alternativas;
				if (preguntas) {
					alternativas = creaAlternativasitems(item.toString(), todossub);
					itemmarcadocadena.add(obtenerMultichoice(alternativas, (List<Object>) item.clone()));

				} else {
					alternativas = creaAlternativasitems(item.toString(), todossub);
					itemmarcadocadena.add(eliminarparentesis(item.clone()));
				}
				item.clear();
				alternativas.clear();
			}

			for (int j = 0; j < numitems; j++) {
				
				if (tex) {
					String itemtex = itemmarcadocadena.get(j).toString().replace("->", "\\rightarrow ");
					todositems.add(new Items(itemtex.replace("$", "\\$").replace("->", "\\rightarrow "),null));
				} else {
					todositems.add(new Items(itemmarcadocadena.get(j), null));
				}
				
			}
			
			ConjuntoLR c = new ConjuntoLR(i, (ArrayList<Items>) todositems.clone());
			todositems.clear();

			itemmarcadocadena.clear();
			itemmarcado.clear();
			theconjuntos.add(c);
		}
		return theconjuntos;

	}

	private static List<Taccioneir> creacadenasaccioneiraLR(Analisis analisis, List<Object> todosSimbolos, boolean b) {

		Tabla tabla = analisis.obtenerTablaAnalisis();
		TablaAscendente tabla_principal1 = (TablaAscendente) tabla;
		Object elemento;
		List<Taccioneir> theTAeir = new ArrayList<Taccioneir>();
		List<Object> correctas = new ArrayList<>();
		ArrayList<Object> accion = new ArrayList<Object>();

		int num = analisis.obtenerAutomataAnalisis().numeroNodosAutomata();
		for (int i = 0; i < num; i++) {
			for (Object j : todosSimbolos) {
				elemento = tabla_principal1.tabla_principal.get(i).get(j);
				if (elemento == null) {
					if (b) {

						correctas.add("-");
						int aleatorio = (int) (Math.random() * 10 + 1);
						String Respuestaprod;
						if (aleatorio > 9) {
							Respuestaprod = obtenerShortanswer(correctas.get(0).toString());
						} else {
							Respuestaprod = "-";
						}
						accion.add(Respuestaprod);
					} else {
						correctas.add("-");
						accion.add(eliminarparentesis(correctas));
					}

				} else {
					if (b) {
						elemento = eliminarparentesis(elemento);
						String Respuestaprod = obtenerShortanswer(elemento.toString());
						accion.add(Respuestaprod);
					} else {
						elemento = eliminarparentesis(elemento);
						accion.add(elemento);
					}
				}
				correctas.clear();
			}
			@SuppressWarnings("unchecked")
			Taccioneir e = new Taccioneir(i, (List<Object>) accion.clone());

			theTAeir.add(e);
			accion.clear();

		}

		return theTAeir;
	}

	public static String eliminarparentesis(Object elemento) {
		String a = "";
		a = a.concat(elemento.toString());
		a = a.replace("(", "");
		a = a.replace("[", "");
		a = a.replace(")", "");
		a = a.replace("]", "");
		a = a.trim();

		return a;
	}

	/**
	 * Metodo que crea las cadenas de los conjuntos para la plantilla
	 * 
	 * @param analisis
	 *            analisis de la gramatica
	 * @param producciones
	 *            producciones de la gramatica
	 * @param terminales
	 *            terminales de la gramatica
	 * @param con
	 *            alternativas o sin ellas
	 * @param tex
	 *            indica si es para un .tex o un .xml
	 * @return Lista con las cadenas
	 */

	@SuppressWarnings("unchecked")
	private static List<Object> creacadenasconjuntosLR(Analisis analisis, List<Object> producciones,
			List<Object> terminales, boolean b, boolean tex) {

		List<Object> theconjuntos = new ArrayList<Object>();
		Automata ana = analisis.obtenerAutomataAnalisis();
		ArrayList<Object> itemmarcado = new ArrayList<Object>();
		ArrayList<Object> simboloanticipacioncadena = new ArrayList<Object>();
		ArrayList<Object> item = new ArrayList<Object>();
		ArrayList<Object> simb = new ArrayList<Object>();
		ArrayList<Object> simboloanticipacion = new ArrayList<Object>();

		ArrayList<Object> itemmarcadocadena = new ArrayList<Object>();
		ArrayList<Object> todositems = new ArrayList<Object>();
		ArrayList<Object> todossub = new ArrayList<Object>();
		for (int i = 0; i < ana.numeroNodosAutomata(); i++) {
			for (String j : ana.obtenerNodoAutomata(i).toStringSA().trim().split("\n")) {
				todossub.add(j);
			}
		}
		for (int i = 0; i < ana.numeroNodosAutomata(); i++) {

			int numitems = ana.obtenerNodoAutomata(i).numeroElementosCjtoConfig();
			for (String j : ana.obtenerNodoAutomata(i).toString().trim().split("\n")) {
				simboloanticipacion.add(obtenersimboloanticipacion(j));
			}
			for (String j : ana.obtenerNodoAutomata(i).toStringSA().trim().split("\n")) {
				itemmarcado.add(j);
			}
			for (Object j : itemmarcado) {
				item.add(j);
				List<Object> alternativas;
				if (b) {
					alternativas = creaAlternativasitems(item.toString(), todossub);
					itemmarcadocadena.add(obtenerMultichoice(alternativas, (List<Object>) item.clone()));

				} else {
					alternativas = creaAlternativasitems(item.toString(), todossub);
					itemmarcadocadena.add(eliminarparentesis((List<Object>) item.clone()));
				}

				item.clear();
				alternativas.clear();
			}
			for (Object h : simboloanticipacion) {
				if (b) {
					simb.add(h);
					List<Object> alternativas = creaAlternativassimbolo(h.toString(), terminales);
					simboloanticipacioncadena.add(obtenerMultichoice(alternativas, (List<Object>) simb.get(0)));
				} else {
					simb.add(h);
					simboloanticipacioncadena.add(eliminarparentesis(simb.get(0)));
				}
				simb.clear();
			}
			for (int j = 0; j < numitems; j++) {
				if (tex) {
					String itemtex = itemmarcadocadena.get(j).toString().replace("->", "\\rightarrow ");
					String simtex = simboloanticipacioncadena.get(j).toString().replace("->", "\\rightarrow ");
					todositems.add(new Items(itemtex.replace("$", "\\$").replace("->", "\\rightarrow "),
							simtex.replace("$", "\\$").replace("->", "\\rightarrow ")));
				} else {
					todositems.add(new Items(itemmarcadocadena.get(j), simboloanticipacioncadena.get(j)));
				}

			}
			ConjuntoLR c = new ConjuntoLR(i, (ArrayList<Items>) todositems.clone());
			todositems.clear();
			simboloanticipacion.clear();
			simboloanticipacioncadena.clear();
			itemmarcadocadena.clear();
			itemmarcado.clear();
			theconjuntos.add(c);
		}
		return theconjuntos;
	}

	@SuppressWarnings("unchecked")
	private static List<Object> creaAlternativasitems(String item, ArrayList<Object> todossub) {
		List<Object> alt = new ArrayList<Object>();
		List<Object> alt2 = new ArrayList<Object>();
		HashSet<Object> hashSet = new HashSet<Object>();
		item = item.replace("[", " ");
		item = item.replace("]", " ");
		item = item.trim();
		hashSet.addAll(todossub);
		hashSet.add(item);
		hashSet.remove(item);
		alt.addAll((Collection<? extends Object>) hashSet.clone());
		int aleatorio = (int) (Math.random() * alt.size() - 4);
		if (aleatorio < 0) {
			aleatorio = 0;
		}
		for (int i = aleatorio; i < aleatorio + 4; i++) {
			alt2.add(alt.get(i));
		}
		return alt2;
	}

	@SuppressWarnings("unchecked")
	private static List<Object> creaAlternativassimbolo(String h, List<Object> terminales) {

		List<Object> alt = new ArrayList<Object>();
		HashSet<Object> hashSet = new HashSet<Object>();
		h = h.replace("[", " ");
		h = h.replace("]", " ");
		h = h.trim();
		hashSet.addAll(terminales);
		hashSet.add(h);
		hashSet.remove(h);
		alt.addAll((Collection<? extends Object>) hashSet.clone());
		hashSet.clear();
		return alt;
	}

	/**
	 * Metodo que obtiene los simbolos de anticipacion
	 * 
	 * @param x
	 *            items del conjunto
	 * @param num
	 *            numero de simbolos a obtener
	 * @return Lista con los simbolos
	 */
	private static List<Object> obtenersimboloanticipacion(String x) {

		List<Object> thesimb = new ArrayList<Object>();
		int init = 0;
		int fin = 0;
		init = x.indexOf('{') + 1;
		fin = x.indexOf('}');
		String sim = x.substring(init, fin);
		thesimb.add(sim);
		return thesimb;
	}

	/**
	 * método para obtener las cadenas de la traza de la cadena introducida
	 * 
	 * @param g
	 * @param analisis
	 * @param cadena
	 * @param producciones
	 * @param noterminales
	 * @param terminales
	 * @param producciones
	 * @param b
	 *            Con multichoice o no
	 * @param tex
	 *            Si es para un fichero tex
	 * @return
	 */

	public static List<Object> creacadenasTraza(Analisis analisis, String cadena, List<Object> producciones, boolean b,
			boolean tex) {

		List<Object> thetraza = new ArrayList<Object>();
		List<Object> salida = new ArrayList<Object>();

		List<Object> alter = new ArrayList<Object>();
		try {

			VectorSimbolos ent = new VectorSimbolos();

			for (String j : cadena.split(" ")) {
				Terminal a = new Terminal(j);
				ent.insertarSimbolo(a);

			}
			Nulo nulo = new Nulo();
			ent.insertarSimbolo(nulo);
			analisis.iniciarAnalisis(ent);

			int i = 0;
			while (true) {

				analisis.realizarIteracion(i, i + 1);
				if (i == 0) {
					Traza e;
					if (tex) {
						e = new Traza(
								eliminarparentesis(analisis.obtenerEstadoPilaAnalisis().toString().replace("$", "\\$")
										.replace("->", "\\rightarrow ")),
								analisis.obtenerEstadoEntradaAnalisis().toString().replace("$", "\\$").replace("->",
										"\\rightarrow "),
								analisis.obtenerProduccionSalida().replace("$", "\\$").replace("->", "\\rightarrow "));
					} else {
						e = new Traza(eliminarparentesis(analisis.obtenerEstadoPilaAnalisis()),
								analisis.obtenerEstadoEntradaAnalisis(), analisis.obtenerProduccionSalida());
					}
					thetraza.add(e);

				} else {

					String pila = analisis.obtenerEstadoPilaAnalisis().toString();
					String entrada = analisis.obtenerEstadoEntradaAnalisis().toString();
					salida.add(analisis.obtenerProduccionSalida());
					String salidamul;
					if (b) {
						alter = creaAlternativasProd(salida, producciones);
						salidamul = obtenerMultichoice(alter, (List<Object>) salida);
					} else {
						salidamul = salida.toString();
					}
					Traza e;
					if (tex) {
						e = new Traza(eliminarparentesis(pila).replace("$", "\\$").replace("->", "\\rightarrow "),
								entrada.replace("$", "\\$").replace("->", "\\rightarrow "),
								eliminarparentesis(salidamul.replace("$", "\\$").replace("->", "\\rightarrow ")));
					} else {
						e = new Traza(obtenerShortanswer(eliminarparentesis(pila)), obtenerShortanswer(entrada),
								salidamul);
					}

					salida.remove(analisis.obtenerProduccionSalida());
					thetraza.add(e);
				}
				i++;
			}
		} catch (Exception de) {
			try {
				String pila = analisis.obtenerEstadoPilaAnalisis().toString();
				String entrada = analisis.obtenerEstadoEntradaAnalisis().toString();
				Traza e;
				if (b) {
					e = new Traza(obtenerShortanswer(eliminarparentesis(pila)), obtenerShortanswer(entrada), "");
				} else {
					e = new Traza(eliminarparentesis(pila).replace("$", "\\$"),
							entrada.replace("$", "\\$").replace("->", "\\rightarrow "), "");
				}

				thetraza.set(thetraza.size() - 1, e);
			} catch (NullPointerException s) {
			}
		}

		return thetraza;
	}

	private static List<Object> creaAlternativasProd(List<Object> salida, List<Object> producciones) {

		ArrayList<Object> alter = new ArrayList<Object>();
		for (int i = 0; i < producciones.size(); i++) {
			if (!salida.get(0).toString().equals(producciones.get(i).toString())) {
				alter.add(producciones.get(i).toString());
			}
		}
		return alter;
	}

	/**
	 * 
	 * 
	 * Metodo para obtener las cadenas de la tabla de análisis sintáctico
	 * predictivo
	 * 
	 * 
	 * @param g
	 *            gramatica
	 * @param analisis
	 *            analisis
	 * @param noterminales
	 *            No terminales
	 * @param terminales
	 *            terminales
	 * @param producciones
	 *            producciones
	 * @param b
	 * @return cadena con las cadenas de la TASP ya tratadas
	 */
	@SuppressWarnings("unchecked")
	private static List<TASP> creacadenasTASP(Gramatica g, Analisis analisis, List<Object> noterminales,
			List<Object> terminales, List<Object> producciones, boolean preguntas, boolean tex) {

		List<TASP> theTASP = new ArrayList<TASP>();
		Tabla tabla = analisis.obtenerTablaAnalisis();
		TablaDescendente tabla_principal = (TablaDescendente) tabla;
		Object elemento;
		List<Object> correctas = new ArrayList<>();
		ArrayList<Object> produccion = new ArrayList<Object>();
		for (Object j : noterminales) {
			for (Object i : terminales) {
				elemento = tabla_principal.tabla_principal.get(i).get(j);
				String Respuestaprod;
				if (elemento.toString().equals("") || elemento.toString().trim().equals(0)) {
					if (tex) {
						correctas.add("-");
					} else {
						correctas.add("---");
					}
					if (preguntas) {
						Respuestaprod = obtenerMultichoice(producciones, correctas);
					} else {
						Respuestaprod = eliminarparentesis(correctas);
					}
					produccion.add(Respuestaprod);

				} else {

					correctas.add(elemento.toString());
					if (preguntas) {
						List<Object> alter = creaAlternativasProd(correctas, producciones);
						if (tex) {
							alter.add("-");
						} else {
							alter.add("---");
						}
						Respuestaprod = obtenerMultichoice(alter, correctas);
					} else {
						Respuestaprod = eliminarparentesis(correctas);
					}
					if (tex) {
						produccion.add(Respuestaprod.replace("->", "\\rightarrow ").replace("$", "\\$ "));
					} else {
						produccion.add(Respuestaprod);
					}

				}
				correctas.clear();
			}
			TASP e;
			if (tex) {
				e = new TASP(j.toString().replace("->", "\\rightarrow "), (List<Object>) produccion.clone());
			} else {
				e = new TASP(j.toString(), (List<Object>) produccion.clone());
			}

			theTASP.add(e);
			produccion.clear();
		}

		return theTASP;
	}

	/**
	 * Metodo para obtener las cadenas del First y del Follow
	 * 
	 * @param tabla
	 *            tabla con los First
	 * @param tabla2
	 *            tabla con los Follow
	 * @param noterminales
	 *            No terminales
	 * @param terminales
	 *            Terminales
	 * @param b
	 * @return cadena con las cadenas del First y del Follow ya tratadas
	 */
	private static List<Object> creacadenasFirtsFollow(Hashtable<String, VectorSimbolos> tabla,
			Hashtable<String, VectorSimbolos> tabla2, List<Object> noterminales, List<Object> terminales, boolean b,
			boolean tex) {

		List<Object> cadena = new ArrayList<>();
		List<Object> first = new ArrayList<>();
		List<Object> follow = new ArrayList<>();
		for (Object j : noterminales) {
			first.add(tabla.get(j));
			follow.add(tabla2.get(j));
			String RespuestaFirst;
			String RespuestaFollow;
			if (b) {
				List<Object> alternativafirst = GeneraRespuestasAleatorias(first, terminales);
				List<Object> alternativafollow = GeneraRespuestasAleatorias(follow, terminales);

				RespuestaFirst = obtenerMultichoice(alternativafirst, first);
				RespuestaFirst = eliminarparentesis(RespuestaFirst);

				RespuestaFollow = obtenerMultichoice(alternativafollow, follow);
				RespuestaFollow = eliminarparentesis(RespuestaFollow);
			} else {
				RespuestaFirst = eliminarparentesis(first.toString());
				RespuestaFollow = eliminarparentesis(follow.toString());
			}
			Noterminal e;
			if (tex) {
				e = new Noterminal(j.toString(), RespuestaFirst.replace("$", "\\$").replace("->", "\\rightarrow "),
						RespuestaFollow.replace("$", "\\$").replace("->", "\\rightarrow "));
			} else {
				e = new Noterminal(j.toString(), RespuestaFirst, RespuestaFollow);
			}

			cadena.add(e);
			first.clear();
			follow.clear();
		}
		return cadena;
	}

	/**
	 * Metodo para obtener las cadenas de multiples respuestas
	 * 
	 * @param respuestasalternativas
	 *            Respuestas alternativas
	 * @param correcta
	 *            Respuesta correcta
	 * @return Cadena con el multichoice
	 */
	private static String obtenerMultichoice(List<Object> respuestasalternativas, List<Object> correcta) {
		String Respuesta = "{1:MULTICHOICE:=" + correcta.get(0);
		for (int i = 0; i < respuestasalternativas.size(); i++) {
			Respuesta = Respuesta + "~" + respuestasalternativas.get(i);
		}
		Respuesta = Respuesta + "}";
		return Respuesta;
	}

	/**
	 * Metodo para obtener las cadenas para una unica solución
	 * 
	 * 
	 * @param elemento
	 *            Respuesta correcta
	 * @return Cadena con el multichoice
	 */
	private static String obtenerShortanswer(String elemento) {
		String Respuesta = "{1:SHORTANSWER:=" + elemento;
		Respuesta = Respuesta + "}";
		return Respuesta;
	}

	/**
	 * Método que genera el informe en .TEX
	 * 
	 * @param g
	 * @param gramatica
	 * @param analisis
	 * @param tipoanalisis
	 * @throws IOException
	 */
	public static void creaTex(Gramatica g, String gramatica, Analisis analisis, String tipoanalisis, String cadena)
			throws IOException {
		String gramaticasin = gramatica;
		List<Object> producciones = new ArrayList<>();
		Map<String, Object> context = Maps.newHashMap();
		List<Object> terminales = new ArrayList<>();
		List<Object> noterminales = new ArrayList<>();
		List<Object> NumTerminalesTEX1 = new ArrayList<>();
		List<Object> NumNoTerminalesTEX1 = new ArrayList<>();
		List<Object> TodosSimbolos = new ArrayList<>();
		List<Object> orden = new ArrayList<>();
		if (ficherosalida == "") {
			for (char x : gramatica.toCharArray())
				if (x == '.') {
					gramaticasin = gramatica.substring(0, gramatica.indexOf('.'));
					break;
				}
			ficherosalida = tipoanalisis + "" + gramaticasin;
		}
		context.put("ficherosalida", ficherosalida);
		context.put("NombreGramatica", gramatica);
		context.put("TipoAnalisis", tipoanalisis);
		context.put("SimboloInicio", g.getSimboloInicio());

		for (int i = 0; i < g.obtenerTerminales().simbolosIntroducidos(); i++) {
			terminales.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
			TodosSimbolos.add(g.obtenerTerminales().obtenerSimbolo(i).toString());
		}
		TodosSimbolos.add("\\$");
		for (int i = 0; i < g.obtenerNoTerminales().simbolosIntroducidos(); i++) {
			noterminales.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
			TodosSimbolos.add(g.obtenerNoTerminales().obtenerSimbolo(i).toString());
		}
		orden.addAll(noterminales);
		orden.addAll(terminales);
		context.put("Terminales", terminales);
		context.put("NumTerminalesmas1", g.obtenerTerminales().simbolosIntroducidos() + 1);
		context.put("NoTerminales", noterminales);
		context.put("NumNoTerminales", g.obtenerNoTerminales().simbolosIntroducidos());
		for (int i = 0; i < g.obtenerTerminales().simbolosIntroducidos() + 1; i++) {
			NumTerminalesTEX1.add(i);
		}
		context.put("NumTerminalesTEX1", NumTerminalesTEX1);
		for (int i = 0; i < g.obtenerNoTerminales().simbolosIntroducidos() + 1; i++) {
			NumNoTerminalesTEX1.add(i);
		}
		context.put("NumNoTerminalesTEX1", NumNoTerminalesTEX1);
		context.put("NumNoTerminales", g.obtenerNoTerminales().simbolosIntroducidos());
		context.put("Orden", orden);
		context.put("TodosSimbolos", TodosSimbolos);

		for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
			String x = g.obtenerProduccionGramatica(i).toString();
			x = x.replace("->", "\\rightarrow ").replace("$", "\\$ ");
			producciones.add(x);
		}
		context.put("Producciones", producciones);
		switch (tipoanalisis) {

		case "LL":
			context.put("LL", true);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", false);
			context.put("First", g.obtenerFirst());
			context.put("Follow", g.obtenerFollow());

			List<Object> FirstFollowSin = creacadenasFirtsFollow(g.first.tabla, g.follow.tabla, noterminales,
					terminales, false, true);

			context.put("FirstFollowSinTEX", FirstFollowSin);
			List<TASP> RowTASPSin = creacadenasTASP(g, analisis, noterminales, terminales, producciones, false, true);
			context.put("RowTASPSinTEX", RowTASPSin);

			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTrazaSinTex = creacadenasTraza(analisis, cadena, producciones, false, true);
				context.put("RowTrazaSinTex", RowTrazaSinTex);
			}
			context.put("Cadena", cadena);
			break;

		case "SLR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", true);
			context.put("LALR", false);

			List<Object> conjuntosSLRSin = creacadenasconjuntosSLR(analisis, producciones, terminales, false,true);

			List<Taccioneir> AccioneiraSLRSin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);

			context.put("ConjuntosSLRSin", conjuntosSLRSin);

			context.put("AccioneiraSLRSin", AccioneiraSLRSin);
			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTrazaSinTex = creacadenasTraza(analisis, cadena, producciones, false, true);
				context.put("RowTrazaSinTex", RowTrazaSinTex);
			}
			context.put("Cadena", cadena);
			break;

		case "LALR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", true);

			List<Object> conjuntosLALRSin = creacadenasconjuntosLR(analisis, producciones, terminales, false, true);
			List<Taccioneir> accioneiraLALRSin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);
			context.put("ConjuntosLALRSin", conjuntosLALRSin);
			context.put("AccioneiraLALRSin", accioneiraLALRSin);

			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);
				List<Object> RowTrazaSinTex = creacadenasTraza(analisis, cadena, producciones, false, true);
				context.put("RowTrazaSinTex", RowTrazaSinTex);
			}
			context.put("Cadena", cadena);
			break;

		case "LR":
			context.put("LL", false);
			context.put("LR", true);
			context.put("SLR", false);
			context.put("LALR", false);
			List<Object> ConjuntosLRSin = creacadenasconjuntosLR(analisis, producciones, terminales, false, true);
			List<Taccioneir> accioneiraLRsin = creacadenasaccioneiraLR(analisis, TodosSimbolos, false);

			context.put("ConjuntosLRSin", ConjuntosLRSin);
			context.put("AccioneiraLRSin", accioneiraLRsin);

			if (cadena == null) {
				context.put("Cadena", false);
			} else {
				context.put("Cadena", cadena);

				List<Object> RowTrazaSinTex = creacadenasTraza(analisis, cadena, producciones, false, true);
				context.put("RowTrazaSinTex", RowTrazaSinTex);

			}
			context.put("Cadena", cadena);
			break;
		}
		File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "informes"
				+ System.getProperty("file.separator"), ficherosalida + ".tex");

		Mustache mustache;

		try {
			MustacheFactory mf = new DefaultMustacheFactory();

			mustache = mf.compile("plantillaTEX");
	
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "UTF-8"));

		
			mustache.execute(bw, context);
			bw.close();

			ruta = fichero.getPath();
			System.out.println("Fichero.XML generado correctamente en " + fichero.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para generar una cadena de respuestas incorrectas similares a la
	 * solucion correcta para su posterior uso en la plantilla.
	 * 
	 * @param correcta
	 * @param terminales
	 * @return
	 */
	private static List<Object> GeneraRespuestasAleatorias(List<Object> correcta, List<Object> terminales) {
		int n = terminales.size();
		List<Object> toadd = new ArrayList<>();
		List<Object> del1 = new ArrayList<>();
		List<Object> del2 = new ArrayList<>();
		List<Object> del3 = new ArrayList<>();
		List<Object> add1 = new ArrayList<>();
		List<Object> add2 = new ArrayList<>();
		List<Object> add12 = new ArrayList<>();
		List<Object> sub1 = new ArrayList<>();
		List<Object> sub2 = new ArrayList<>();
		List<Object> sub3 = new ArrayList<>();
		List<Object> rs = new ArrayList<>();
		List<Object> asets = new ArrayList<>();
		toadd.addAll(terminales);
		toadd.remove(correcta.get(0).toString());// eliminar la correcta
		for (int i = 0; i < toadd.size(); i++) {
			rs.add(i);
		}
		Collections.shuffle(rs);
		if (n > 1) {
			del1 = terminales.subList(1, n); // delete first one
			del2 = terminales.subList(0, n - 1); // delete last one
		}
		if (n > 2) {
			int r = (int) (Math.random() * n);
			del3.addAll(terminales.subList(0, r));
			del3.addAll(terminales.subList(r + 1, n));// delete other
		}
		if (rs.size() > 1) {
			add1.add(toadd.get(0));// add one
			add2.add(toadd.get(1));// add other
			add12.add(toadd.get(0));
			add12.add(toadd.get(1));// add two
			sub1.addAll(terminales.subList(1, n - 1));
			sub1.add(toadd.get((int) rs.get(0)));
			sub2.addAll(terminales.subList(1, n - 1));
			sub2.add(toadd.get((int) rs.get(1)));
		}
		if (rs.size() > 0) {
			sub1.clear();
			sub1.addAll(terminales.subList(1, n - 1));
			sub1.add(toadd.get((int) rs.get(0)));
		}
		if (rs.size() > 2) {
			sub3.addAll(terminales.subList(0, n - 1));
			sub3.add(toadd.get((int) rs.get(2)));
		}
		if (n == 1) {

			asets.add(add1);
			asets.add(add12);
			asets.add(sub1);
			asets.add(sub2);
		} else {
			if (n == 2) {
				asets.add(del1);
				asets.add(del2);
				asets.add(add1);
				asets.add(sub1);
			} else {
				asets.add(del1);
				asets.add(add1);
				asets.add(del2);
				asets.add(add2);
			}
		}
		return asets;
	}

	/**
	 * Clase que permite conocer el first y el follow de un No terminal
	 * 
	 * @author Victor
	 *
	 */
	static class Noterminal {
		/**
		 * Constructor de la clase
		 * 
		 * @param name
		 *            nombre del No terminal
		 * @param first
		 *            first
		 * @param follow
		 *            follow
		 */
		Noterminal(String name, String first, String follow) {
			this.name = name;
			this.first = first;
			this.follow = follow;
		}

		String name, first, follow;

	}

	/**
	 * Clase que permite conocer la tabla de análisis sintáctico predictivo
	 * 
	 * @author Victor
	 *
	 */
	static class TASP {
		TASP(String name, List<Object> produccion) {
			this.name = name;
			this.prod = produccion;

		}

		String name;
		List<Object> prod = new ArrayList<>();

	}

	static class ConjuntoLR {

		ConjuntoLR(int numero, ArrayList<Items> items) {
			this.numero = numero;
			this.items = items;
		}

		int numero;
		ArrayList<Items> items;

	}

	static class Items {

		Items(Object object, Object object2) {
			this.item = object;
			this.simbolo = object2;

		}

		Object item;
		Object simbolo;

	}

	/**
	 * Clase que permite conocer la tabla de accion e ir
	 * 
	 * @author Victor
	 *
	 */
	static class Taccioneir {

		Taccioneir(int num, List<Object> accion) {
			this.num = num;
			this.accion = accion;

		}

		int num;
		List<Object> accion;
	}

	/**
	 * Clase que permite conocer la tabla de accion e ir
	 * 
	 * @author Victor
	 *
	 */
	static class Traza {
		/**
		 * Constructor de la clase
		 * 
		 * @param name
		 *            nombre del No terminal
		 * @param first
		 *            first
		 * @param follow
		 *            follow
		 */
		Traza(String pila, String entrada, String salida) {
			this.pila = pila;
			this.entrada = entrada;
			this.salida = salida;
		}

		String pila, entrada, salida;

	}
}
