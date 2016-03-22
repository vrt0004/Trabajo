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
 * La idea es tener una pequeña aplicación en modo texto que:
 * 
 * 1. Toma como argumento el nombre de un archivo con la especificación de una
 * gramática en el formato YACC
 * 
 * 2. Utilice su contenido para utilizando las clases adecuadas de Burgram para
 * instanciar un objeto 'Gramatica'
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
		options.addOption("gramatica", true, "Gramatica para analizar");
		options.addOption("tipoanalisis", true, "Tipo de analisis a realizar ");
		options.addOption("informe", true, "Extension del informe");

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

			System.out.println(cmdLine.getOptionValue("gramatica"));

			String gramatica = cmdLine.getOptionValue("gramatica");
			if (gramatica == null) {
				throw new org.apache.commons.cli.ParseException("La gramatica es requerida");
			}
			String tipoanalisis = cmdLine.getOptionValue("tipoanalisis");
			if (tipoanalisis == null) {
				throw new org.apache.commons.cli.ParseException("El tipo de analisis a realizar es requerido");
			}

			cmdLine.getOptionValue("tipoanalisis");

			// Si el usuario ha especificado el informe lo leemos

			if (cmdLine.hasOption("informe")) {
				informe = cmdLine.getOptionValue("informe");
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

				String tipo = args[1];

				switch (tipo) {

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

			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName(), options); // Error,
																						// imprimimos
																						// la
																						// ayuda
		} catch (java.lang.NumberFormatException ex) {
			new HelpFormatter().printHelp(Prototipo.class.getCanonicalName(), options); // Error,
																						// imprimimos
																						// la
																						// ayuda
		}
	}

	private static void creaXML(Gramatica g, String gramatica, Analisis analisis) {
		// TODO Auto-generated method stub
		File fichero = new File(System.getProperty("user.dir"), "fichero.XML");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));

			System.out.println("Fichero.XML generado correctamente");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void creaTex(Gramatica g, String gramatica, Analisis analisis) {
		File fichero = new File(System.getProperty("user.dir"), "fichero.tex");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			bw.write("\\documentclass {article}\n" + "\\usepackage [spanish] {babel}\n"
					+ "\n\\usepackage [T1]{fontenc}\n" + "\\usepackage [latin1]{inputenc}\n" + "\\begin{document} \n");
			bw.write("\n\\title{" + gramatica + "}\n");

			bw.write("\n\\section{Gramatica}\n");
			String gra = escribirgramatica(g.toString().toCharArray());
			bw.write(gra);

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
			// String TASP =
			// escribir(analisis.obtenerTablaAnalisis().toString().toCharArray());
			// bw.write(TASP);
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
			else if (x == '}')
				y = y + ("\\}$\n\n$");
			else
				y = y + (x);
		}
		return y;
	}

	private static String escribirgramatica(char[] cadena) {
		String y = "";
		for (char x : cadena) {
			if (x == '$')
				y = y + ("\\$");
			else if (x == '{')
				y = y + ("\\{");
			else if (x == '}')
				y = y + ("\\}$\n\n$");
			else
				y = y + (x) + "\n";
		}
		return y;
	}
}
