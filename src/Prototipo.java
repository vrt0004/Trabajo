
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		options.addOption("i", true, "Extension del informe [.TEX,.XML],por defecto .ALL");
		options.addOption("ca", true, "Cadena a analizar");
		options.addOption("h", "help", false, "Imprime el mensaje de ayuda");

		// No pueden aparecen las dos opciones simultaneamente.
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("err", "Salida est�ndar de errores"));
		group.addOption(new Option("console", "Salida est�ndar"));
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

			// Si est� la opcion de ayuda, la imprimimos y salimos.
			if (cmdLine.hasOption("h")) {
				new HelpFormatter().printHelp(Prototipo.class.getCanonicalName(), options);
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
			// ..............................................................
			// Aqu� van las tareas que tiene que realizar la aplicaci�n
			// ..............................................................

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
					System.out.println("Tabla de análisis sintáctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "SLR":
					analisis = new AnalisisSLR1(g);
					System.out.println("Tabla de análisis sintáctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "LALR":
					analisis = new AnalisisLALR1(g);
					System.out.println("Tabla de análisis sintáctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "LR":
					analisis = new AnalisisLR1(g);
					System.out.println("Tabla de análisis sintáctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				}
			}

			switch (informe) {
			case "TEX":
				creaTex(g, gramatica, analisis, tipoanalisis);
				break;
			case "XML":
				creaXML(g, gramatica, analisis, tipoanalisis, cadena);
				break;
			case "ALL":
				creaTex(g, gramatica, analisis, tipoanalisis);
				creaXML(g, gramatica, analisis, tipoanalisis, cadena);
				break;

			}

		} catch (org.apache.commons.cli.ParseException ex) {
			System.out.println(ex.getMessage());

			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i][-ca] ", options); // Error,
			// imprimimos la ayuda
		} catch (java.lang.NumberFormatException ex) {
			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i][-ca] ", options); // Error,
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
	private static void creaXML(Gramatica g, String gramatica, Analisis analisis, String tipoanalisis, String cadena)
			throws IOException {

		List<Object> producciones = new ArrayList<>();
		List<Object> traza = new ArrayList<>();
		List<Object> terminales = new ArrayList<>();
		List<Object> noterminales = new ArrayList<>();
		List<Object> TodosSimbolos = new ArrayList<>();
		List<Object> orden = new ArrayList<>();

		traza.add("Pila");
		traza.add("Entrada");
		traza.add("Salida");
		MustacheFactory mf = new DefaultMustacheFactory();

		Map<String, Object> context = Maps.newHashMap();
		context.put("NombreGramatica", gramatica);
		context.put("TipoAnalisis", tipoanalisis);
		if (cadena == null) {
			context.put("Cadena", false);
		} else {
			context.put("Cadena", cadena);
		}

		context.put("Cadena", cadena);
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
		context.put("NoTerminales", noterminales);
		context.put("NumNoTerminales", g.obtenerNoTerminales().simbolosIntroducidos());
		context.put("TodosSimbolos", TodosSimbolos);
		context.put("First", g.obtenerFirst());
		context.put("Follow", g.obtenerFollow());
		context.put("Traza", traza);

		List<Object> FirstFollow = creacadenasFirtsFollow(g.first.tabla, g.follow.tabla, noterminales, terminales);
		context.put("FirstFollow", FirstFollow);

		for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
			producciones.add(g.obtenerProduccionGramatica(i));
		}
		context.put("Producciones", producciones);
		List<Object> RowTraza = creacadenasTraza(g, analisis, noterminales, terminales, producciones);
		context.put("RowTraza", RowTraza);
		switch (tipoanalisis) {

		case "LL":
			context.put("LL", true);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", false);
			List<TASP> RowTASP = creacadenasTASP(g, analisis, noterminales, terminales, producciones);
			context.put("RowTASP", RowTASP);
			break;
		case "SLR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", true);
			context.put("LALR", false);
			break;
		case "LALR":
			context.put("LL", false);
			context.put("LR", false);
			context.put("SLR", false);
			context.put("LALR", true);
			break;
		case "LR":
			context.put("LL", false);
			context.put("LR", true);
			context.put("SLR", false);
			context.put("LALR", false);
			List<Object> conjuntos = creacadenasconjuntosLR(analisis, producciones, terminales);
			List<Taccioneir> accioneira = creacadenasaccioneiraLR(analisis, TodosSimbolos);
			context.put("Conjuntos", conjuntos);
			context.put("Accioneira", accioneira);

			break;
		}
		Mustache mustache = mf.compile("my-template2.xml");
		File fichero = new File(System.getProperty("user.dir"), "fichero.xml");
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			mustache.execute(bw, context);
			bw.close();
			System.out.println("Fichero.XML generado correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<Taccioneir> creacadenasaccioneiraLR(Analisis analisis, List<Object> todosSimbolos) {

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

					elemento = eliminarparentesis(elemento);
					String Respuestaprod = obtenerShortanswer(elemento.toString());
					accion.add(Respuestaprod);
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

	private static String eliminarparentesis(Object elemento) {
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
	 * @return Lista con las cadenas
	 */

	@SuppressWarnings("unchecked")
	private static List<Object> creacadenasconjuntosLR(Analisis analisis, List<Object> producciones,
			List<Object> terminales) {

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
				List<Object> alternativas = creaAlternativasitems(item.toString(), todossub);
				itemmarcadocadena.add(obtenerMultichoice(alternativas, (List<Object>) item.clone()));
				item.clear();
				alternativas.clear();
			}
			for (Object h : simboloanticipacion) {
				simb.add(h);
				List<Object> alternativas = creaAlternativassimbolo(h.toString(), terminales);
				simboloanticipacioncadena.add(obtenerMultichoice(alternativas, (List<Object>) simb.get(0)));

				simb.clear();
			}
			for (int j = 0; j < numitems; j++) {
				todositems.add(new Items(itemmarcadocadena.get(j), simboloanticipacioncadena.get(j)));
			}
			Conjunto c = new Conjunto(i, (ArrayList<Items>) todositems.clone());
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
	 * @param noterminales
	 * @param terminales
	 * @param producciones
	 * @return
	 */
	private static List<Object> creacadenasTraza(Gramatica g, Analisis analisis, List<Object> noterminales,
			List<Object> terminales, List<Object> producciones) {
		// TODO Auto-generated method stub
		List<Object> thetraza = new ArrayList<Object>();
		return thetraza;
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
	 * @return cadena con las cadenas de la TASP ya tratadas
	 */
	private static List<TASP> creacadenasTASP(Gramatica g, Analisis analisis, List<Object> noterminales,
			List<Object> terminales, List<Object> producciones) {

		List<TASP> theTASP = new ArrayList<TASP>();
		Tabla tabla = analisis.obtenerTablaAnalisis();
		TablaDescendente tabla_principal = (TablaDescendente) tabla;
		Object elemento;
		List<Object> correctas = new ArrayList<>();
		ArrayList<Object> produccion = new ArrayList<Object>();
		for (Object j : noterminales) {
			for (Object i : terminales) {
				elemento = tabla_principal.tabla_principal.get(i).get(j);

				if (elemento.toString().equals("") || elemento.toString().trim().equals(0)) {

					correctas.add("---");
					String Respuestaprod = obtenerMultichoice(producciones, correctas);

					produccion.add(Respuestaprod);

				} else {

					correctas.add(elemento.toString());
					String Respuestaprod = obtenerMultichoice(producciones, correctas);
					produccion.add(Respuestaprod);

				}
				correctas.clear();
			}

			@SuppressWarnings("unchecked")
			TASP e = new TASP(j.toString(), (List<Object>) produccion.clone());
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
	 * @return cadena con las cadenas del First y del Follow ya tratadas
	 */
	private static List<Object> creacadenasFirtsFollow(Hashtable<String, VectorSimbolos> tabla,
			Hashtable<String, VectorSimbolos> tabla2, List<Object> noterminales, List<Object> terminales) {

		List<Object> cadena = new ArrayList<>();
		List<Object> firts = new ArrayList<>();
		List<Object> follow = new ArrayList<>();
		for (Object j : noterminales) {
			firts.add(tabla.get(j));
			follow.add(tabla2.get(j));
			List<Object> alternativafirst = GeneraRespuestasAleatorias(firts, terminales);
			List<Object> alternativafollow = GeneraRespuestasAleatorias(follow, terminales);
			String RespuestaFirst = obtenerMultichoice(alternativafirst, firts);
			String RespuestaFollow = obtenerMultichoice(alternativafollow, follow);
			Noterminal e = new Noterminal(j.toString(), RespuestaFirst, RespuestaFollow);
			cadena.add(e);
			firts.clear();
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
	 */
	private static void creaTex(Gramatica g, String gramatica, Analisis analisis, String tipoanalisis) {
		File fichero = new File(System.getProperty("user.dir"), "fichero.tex");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			bw.write("\\documentclass {article}\n" + "\\usepackage [spanish] {babel}\n"
					+ "\n\\usepackage [T1]{fontenc}\n" + "\\newcommand{\\h}[1]{#1}"
					+ "\n%\\renewcommand{\\h}[1]{\\color{white}#1\\color{black}}" + "\\usepackage [latin1]{inputenc}\n"
					+ "\\begin{document} \n");
			bw.write("\n\\title{" + gramatica + "}\n\\maketitle");

			bw.write("\n\\section{Gramatica}\n");

			bw.write("$\n");
			String gram = escribir(g.toString().toCharArray());
			bw.write(gram);

			bw.write("$\n");
			bw.write("\n\\section{First}\n");
			bw.write("$\n");
			String first = escribir(g.obtenerFirst().toString().toCharArray());
			bw.write(first);
			bw.write("$\n");

			bw.write("\n\\section{Follow}\n");
			bw.write("$\n");
			String follow = escribir(g.obtenerFollow().toString().toCharArray());
			bw.write(follow);
			bw.write("$\n");

			bw.write("\n\\section{Tabla an�lisis sint�ctico predictivo}\n");
			bw.write("$\n");
			String TASP = escribir(analisis.obtenerTablaAnalisis().toString().toCharArray());
			bw.write(TASP);
			bw.write("$\n");

			bw.write("\n\\end{document}\n");
			bw.close();
			System.out.println("Fichero.TEX generado correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String escribir(char[] charArray) {
		String y = "";
		for (char x : charArray) {
			if (x == '$')
				y = y + ("\\$");
			else if (x == '{')
				y = y + ("\\{");
			else if (x == '\\')
				y = y + ("\\");
			else if (x == '}')
				y = y + ("\\}$\n\n$");
			else
				y = y + x;
		}
		return y;
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
		toadd.remove(correcta.get(0));// eliminar la correcta
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
			// substitute first one
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
		 * @param firts
		 *            first
		 * @param follow
		 *            follow
		 */
		Noterminal(String name, String firts, String follow) {
			this.name = name;
			this.firts = firts;
			this.follow = follow;
		}

		String name, firts, follow;

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

	static class Conjunto {

		Conjunto(int numero, ArrayList<Items> items) {
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
}
