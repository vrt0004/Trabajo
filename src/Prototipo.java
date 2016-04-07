import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;

import analisis.Analisis;
import analisis.AnalisisLALR1;
import analisis.AnalisisLL1;
import analisis.AnalisisLR1;
import analisis.AnalisisSLR1;
import gramatica.Gramatica;
import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;

/**
 *
 * <b>Descripci�n</b><br>
 * Prototipo de l�nea de comandos utilizando clases de Burgram
 * <p>
 * <b>Detalles</b><br>
 * La idea es tener una peque�a aplicaci�n en modo texto que:<br>
 * 
 * 1. Toma como argumento el nombre de un archivo con la especificaci�n de una
 * gram�tica en el formato YACC<br>
 * 
 * 2. Utilice su contenido para utilizando las clases adecuadas de Burgram para
 * instanciar un objeto 'Gramatica'<br>
 * 
 * 3. Interrogue el objeto gram�tica para mostrar el first y follow de los
 * s�mbolos de la gram�tica.<br>
 * 
 * 4.Muestre la tabla de analisis sintactico predictivo
 * 
 * Genere un informe de la extension deseada
 * </p>
 * 
 * @author Victor Renuncio
 * @version 1.0
 */
public class Prototipo {
	public static List<String> argumentos = null;
	public static String informe;

	public static void main(String[] args) throws IOException {

		final String DEF_INFORME = "ALL";
		CommandLineParser parser = null;
		CommandLine cmdLine = null;

		///////////////////////////////////////////////////////////////////////
		// Fase 1: Configuramos las opciones de validaci�n de entrada.
		///////////////////////////////////////////////////////////////////////

		Options options = new Options();
		options.addOption("g", true, "Nombre de la gramatica a analizar");
		options.addOption("t", true, "Tipo de analisis a realizar[LL,SLR,LARL,LR] ");
		options.addOption("i", true, "Extension del informe [.TEX,.XML],por defecto .ALL");

		options.addOption("h", "help", false, "Imprime el mensaje de ayuda");

		// No pueden aparecen las dos opciones simult�neamente.
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("err", "Salida est�ndar de errores"));
		group.addOption(new Option("console", "Salida est�ndar"));
		options.addOptionGroup(group);

		try {

			///////////////////////////////////////////////////////////////////////
			// Fase 2: Parseamos la entrada con la configuraci�n establecida
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
			// un error pues es un par�metro requerido.

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

			// ..............................................................
			// Aqu� van las tareas que tiene que realizar la aplicaci�n
			// ..............................................................

			Gramatica g = null;
			// GramaticaId ="GRAMATICA1.yc";
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
					System.out.println("Tabla de an�lisis sint�ctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "SLR":
					analisis = new AnalisisSLR1(g);
					System.out.println("Tabla de an�lisis sint�ctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "LALR":
					analisis = new AnalisisLALR1(g);
					System.out.println("Tabla de an�lisis sint�ctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				case "LR":
					analisis = new AnalisisLR1(g);
					System.out.println("Tabla de an�lisis sint�ctico predictivo");
					System.out.println(analisis.obtenerTablaAnalisis());
					break;
				}
			}

			switch (informe) {
			case "TEX":
				creaTex(g, gramatica, analisis, tipoanalisis);
				break;
			case "XML":
				creaXML(g, gramatica, analisis, tipoanalisis);
				break;
			case "ALL":
				creaTex(g, gramatica, analisis, tipoanalisis);
				creaXML(g, gramatica, analisis, tipoanalisis);
				break;

			}

		} catch (org.apache.commons.cli.ParseException ex) {
			System.out.println(ex.getMessage());

			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i] ", options); // Error,
			// imprimimos la ayuda
		} catch (java.lang.NumberFormatException ex) {
			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i] ", options); // Error,
			// imprimimos la ayuda
		}
	}

	/**
	 * Método que genera el informe en .XML
	 * 
	 * @param g
	 *            Gram�tica a anilizar
	 * @param gramatica
	 *            Nombre de la gram�tica
	 * @param analisis
	 *            An�lisis de la gram�tica
	 * @param tipoanalisis
	 *            Tipo de an�lisis
	 * @throws IOException
	 */
	private static void creaXML(Gramatica g, String gramatica, Analisis analisis, String tipoanalisis)
			throws IOException {

		String producciones = "";
		Jinjava jinjava = new Jinjava();
		Map<String, Object> context = Maps.newHashMap();
		context.put("NombreGramatica", gramatica);
		context.put("TipoAnalisis", tipoanalisis);
		context.put("SimboloInicio", g.getSimboloInicio());
		context.put("Terminales", g.obtenerTerminales());
		context.put("NumTerminales", g.obtenerTerminales().simbolosIntroducidos());
		context.put("NoTerminales", g.obtenerNoTerminales());
		context.put("NumNoTerminales", g.obtenerNoTerminales().simbolosIntroducidos());
		for (int i = 0; i < g.produccionesIntroducidasGramatica(); i++) {
			producciones = producciones + g.obtenerProduccionGramatica(i) + "\n";
		}
		context.put("Producciones", producciones);
		context.put("First", g.obtenerFirst());
		context.put("Follow", g.obtenerFollow());
		String template = Resources.toString(Resources.getResource("my-template.html"), Charsets.UTF_8);
		String renderedTemplate = jinjava.render(template, context);

		File fichero = new File(System.getProperty("user.dir"), "fichero.HTML");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			bw.write(renderedTemplate);
			bw.close();
			System.out.println("Fichero.HTML generado correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}

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

}
