import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import analisis.Analisis;
import analisis.AnalisisLL1;
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
*	1. Toma como argumento el nombre de un archivo con la especificación de una gramática en el formato YACC
*	2. Utilice su contenido para utilizando las clases adecuadas de Burgram para instanciar un objeto 'Gramatica'
*	3. Interrogue el objeto gramática para mostrar el first y follow de los símbolos de la gramática.<br>
* </p> 
* @author Victor Renuncio
* @version 1.0
*/
public class Prueba {
	public static void main(String[] args) {
	Gramatica g = null;
	String GramaticaId=null;
	GramaticaId ="gramatica1.yc";
	//GramaticaId ="ex2.yp";
	Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")+System.getProperty("file.separator")+"gramaticas"+System.getProperty("file.separator"));
	ParserGramatica pg = new ParserGramatica(false,new ParserYacc());
	try {
		//g=pg.parsearGramaticaArchivo(System.getProperty("user.dir")+"\\gramaticas\\"+GramaticaId);
		g=pg.parsearGramaticaArchivo(path+System.getProperty("file.separator")+GramaticaId);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	Analisis analisis=new AnalisisLL1(g);
	System.out.println("gramatica:   "+GramaticaId);
	System.out.println();
	System.out.println(g.toString());
	System.out.println();
	System.out.println("first");
	System.out.println(g.obtenerFirst());
	System.out.println();
	System.out.println("follow");
	System.out.println(g.obtenerFollow());
	System.out.println();
	System.out.println("Tabla de análisis sintáctico predictivo");
	System.out.println(analisis.obtenerTablaAnalisis());
	
}
}
