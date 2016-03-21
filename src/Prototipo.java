import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
 * </p>
 * 
 * @author Victor Renuncio
 * @version 1.0
 */
public class Prototipo {
	public static void main(String[] args) {
		Gramatica g = null;
		String GramaticaId = null;
		GramaticaId = args[0];
		//GramaticaId ="GRAMATICA1.yc";
		Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "gramaticas" + System.getProperty("file.separator"));
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(path + System.getProperty("file.separator") + GramaticaId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Analisis analisis = null;
		if (args.length > 0) {

			System.out.println("gramatica:   " + GramaticaId);
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

		String informe = args[2];
		System.out.println(informe);
		switch (informe) {
		case "TEX":
			creaTex(g,args,analisis);
			break;
		case "XML":
			creaXML(g,args,analisis);
			break;
		case "ALL":
			creaTex(g,args,analisis);
			creaXML(g,args,analisis);
			break;

		}
		
	}

	private static void creaXML(Gramatica g, String[] args, Analisis analisis) {
		// TODO Auto-generated method stub
		
	}

	private static void creaTex(Gramatica g,String args[], Analisis analisis) {
		File fichero = new File(System.getProperty("user.dir"), "fichero.tex");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
			bw.write("\\documentclass {article}\n" + "\\usepackage [spanish] {babel}\n"
					+ "\n\\usepackage [T1]{fontenc}\n" + "\\usepackage [latin1]{inputenc}\n"
					+ "\\begin{document} \n");
			bw.write("\n\\title{" + args[0] + "}\n");
			
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
				y=y+("\\$");
			else if (x == '{')
				y=y+("\\{");
			else if (x == '}')
				y=y+("\\}$\n\n$");
			else
				y=y+(x);
		}
		return y;
	}

	private static String escribirgramatica(char[] cadena) {
		String y = "";
		for (char x : cadena) {
			if (x == '$')
				y=y+("\\$");
			else if (x == '{')
				y=y+("\\{");
			else if (x == '}')
				y=y+("\\}$\n\n$");
			else
				y=y+(x)+"\n";
		}
		return y;
	}
}
