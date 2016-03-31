import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

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
 * <b>Descripción</b><br>
 * Prototipo de línea de comandos utilizando clases de Burgram
 * <p>
 * <b>Detalles</b><br>
 * La idea es tener una pequeña aplicación en modo texto que:<br>
 * 
 * 1. Toma como argumento el nombre de un archivo con la especificación de una
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
 * Genere un informe de la extension deseada
 * </p>
 * 
 * @author Victor Renuncio
 * @version 1.0
 */

public class Prototipo {
	public static List<String> argumentos = null;
	public static String informe;

	public static void main(String[] args) {

		final String DEF_INFORME = "ALL";
		CommandLineParser parser = null;
		CommandLine cmdLine = null;

		///////////////////////////////////////////////////////////////////////
		// Fase 1: Configuramos las opciones de validación de entrada.
		///////////////////////////////////////////////////////////////////////

		Options options = new Options();
		options.addOption("g", true, "Nombre de la gramatica a analizar");
		options.addOption("t", true, "Tipo de analisis a realizar[LL,SLR,LARL,LR] ");
		options.addOption("i", true, "Extension del informe [.TEX,.XML],por defecto .ALL");

		options.addOption("h", "help", false, "Imprime el mensaje de ayuda");

		// No pueden aparecen las dos opciones simultáneamente.
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("err", "Salida estándar de errores"));
		group.addOption(new Option("console", "Salida estándar"));
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

			// ..............................................................
			// Aquí van las tareas que tiene que realizar la aplicación
			// ..............................................................

			Gramatica g = null;
			// GramaticaId ="GRAMATICA1.yc";
			Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
					+ System.getProperty("file.separator") + "gramaticas" + System.getProperty("file.separator"));
			ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
			try {
				g = pg.parsearGramaticaArchivo(path + System.getProperty("file.separator") + gramatica);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
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
				creaTex(g, gramatica, analisis);
				break;
			case "XML":
				creaXML(g, gramatica, analisis);
				break;
			case "ALL":
				creaTex(g, gramatica, analisis);
				creaXML(g, gramatica, analisis);
				break;

			}

		} catch (org.apache.commons.cli.ParseException ex) {
			System.out.println(ex.getMessage());

			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i] ", options); // Error,
			// imprimimos
			// la
			// ayuda
		} catch (java.lang.NumberFormatException ex) {
			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName() + " -g -t [-i] ", options); // Error,
			// imprimimos
			// la
			// ayuda
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
	 */
	private static void creaXML(Gramatica g, String gramatica, Analisis analisis) {
		// TODO Auto-generated method stub
		File fichero = new File(System.getProperty("user.dir"), "fichero.HTML");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			bw.write("<!DOCTYPE html>\n");

			bw.write("<html lang=\"es\">\n");
			bw.write("<head>\n");
			bw.write("<meta charset=\"UTF-8\"></meta>\n");
			bw.write("<title>Moodle template for LL(1) exercises</title>\n");
			bw.write("</head>\n");
			bw.write("<body>\n");
			bw.write("<h1>Moodle template for LL(1) exercises</h1>");
			bw.write("<p style=\"width: 20%; -moz-border-bottom-colors: none; -moz-border-left-colors: none; "
					+ "-moz-border-right-colors: none; -moz-border-top-colors: none; background-color: #fefef1; border-"
					+ "bottom-right-radius: 12px; border-color: #f7a600; border-style: solid; border-width: 1px 1px 1px "
					+ "5px; box-shadow: 2px 3px 5px #ccc; color: #222; display: table; line-height: 1.4em; margin: 10px "
					+ "20px 20px; overflow: hidden; padding: 10px 16px;\">\n");
			bw.write(" </p>\n");
			bw.write("   <!-- This is the part that must be automatically generated -->\n");

			bw.write("   <!-- END of automatically generated code -->\n");
			bw.write("</body>\n");
			bw.write("</html>");
			bw.close();
			System.out.println("Fichero.XML generado correctamente");
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
	 */
	private static void creaTex(Gramatica g, String gramatica, Analisis analisis) {
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

			bw.write("\n\\section{Tabla análisis sintáctico predictivo}\n");
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
