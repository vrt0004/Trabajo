import java.io.IOException;
import gramatica.Gramatica;
import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;


public class Prueba {
	public static void main(String[] args) {
	Gramatica g = null;
	String GramaticaId=null;
	GramaticaId ="gramatica1.yc";
	ParserGramatica pg = new ParserGramatica(false,new ParserYacc());
	try {
		g=pg.parsearGramaticaArchivo(System.getProperty("user.dir")+"\\gramaticas\\"+GramaticaId);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	System.out.println("gramatica:   "+GramaticaId);
	System.out.println();
	System.out.println(g.toString());
	System.out.println();
	System.out.println("first");
	System.out.println(g.obtenerFirst());
	System.out.println();
	System.out.println("follow");
	System.out.println(g.obtenerFollow());
}
}
