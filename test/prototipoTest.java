import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import Prototipo.Prototipo;
import analisis.Analisis;
import analisis.AnalisisLALR1;
import analisis.AnalisisLL1;
import analisis.AnalisisLR1;
import analisis.AnalisisSLR1;
import analisis.informe.Informe;
import ui.BloquePreguntas;
import ui.LALRPanel;
import ui.Main;
import ui.ProblemaPanel;
import gramatica.Gramatica;
import gramatica.Nulo;
import gramatica.Terminal;
import gramatica.VectorSimbolos;

import parser.ParseException;
import parser.ParserGramatica;
import parser.ParserYacc;
import parser.TokenMgrError;

public class prototipoTest {

	@Test
	public void testCargaGramatica() {
		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\gramatica1.yc");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
	}

	@Test
	public void testFIRST() {
		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaCadena(
					"%start E %nulo EPS %token id %% E : T J ; J : '+' T J | EPS ; T : F U ; U : '*' F U | EPS ;F : '(' E ')' | id ; %%");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		g.obtenerFirst();
	}

	@Test
	public void testFOLLOW() {
		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaCadena(
					"%start E %nulo EPS %token id %% E : T J ; J : '+' T J | EPS ; T : F U ; U : '*' F U | EPS ;F : '(' E ')' | id ; %%");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		g.obtenerFollow();
	}

	@Test
	public void testInformeLL1() {
		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\test.yc");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		Analisis anl = new AnalisisLL1(g);
		// Inicializa una entrada correcta.
		VectorSimbolos vs = new VectorSimbolos();
		vs.insertarSimbolo(new Terminal("("));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal("+"));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal(")"));
		vs.insertarSimbolo(new Nulo());
		anl.iniciarAnalisis(vs);
		anl.obtenerInformeAnalisis().obtenerInforme(anl, System.getProperty("user.dir") + "\\informes", false, false);
	}

	@Test
	public void testInformeSLR1() {

		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\test.yc");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		Analisis anl = new AnalisisSLR1(g);
		// Inicializa una entrada correcta.
		VectorSimbolos vs = new VectorSimbolos();
		vs.insertarSimbolo(new Terminal("("));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal("+"));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal(")"));
		vs.insertarSimbolo(new Nulo());
		anl.iniciarAnalisis(vs);
		anl.obtenerInformeAnalisis().obtenerInforme(anl, System.getProperty("user.dir") + "\\informes", false, false);
	}

	/**
	 * Realiza un informe de un analisis LR1
	 **/
	@Test
	public void testInformeLR1() {

		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\test.yc");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		Analisis anl = new AnalisisLR1(g);
		// Inicializa una entrada correcta.
		VectorSimbolos vs = new VectorSimbolos();
		vs.insertarSimbolo(new Terminal("("));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal("+"));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal(")"));
		vs.insertarSimbolo(new Nulo());
		anl.iniciarAnalisis(vs);
		anl.obtenerInformeAnalisis().obtenerInforme(anl, System.getProperty("user.dir") + "\\informes", false, false);
	}

	/**
	 * Realiza un informe de un analisis LALR1
	 **/
	@Test
	public void testInformeLALR1() {

		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			g = pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\test.yc");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
		Analisis anl = new AnalisisLALR1(g);

		// Inicializa una entrada correcta.
		VectorSimbolos vs = new VectorSimbolos();
		vs.insertarSimbolo(new Terminal("("));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal("+"));
		vs.insertarSimbolo(new Terminal("id"));
		vs.insertarSimbolo(new Terminal(")"));
		vs.insertarSimbolo(new Nulo());
		anl.iniciarAnalisis(vs);

	}

	/**
	 * Se intenta parsear un archivo inexistente.
	 **/
	@Test
	public void testArchivoInexistente() {
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaArchivo("gg");
		} catch (IOException e) {
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
	}

	/**
	 * Se parsea una gram�tica valida.
	 **/
	@Test
	public void testGramaticaValida1() {
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\gramaticaYACC.y");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
	}

	/**
	 * Se parsea una gramática válida mas completa que la anterior.
	 **/
	@Test
	public void testGramaticaValida2() {
		System.out.println(System.getProperty("user.dir") + "\\gramaticas\\gramatica.y");
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\gramatica.y");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
	}

	/**
	 * Se parsea una gramática con error lexico
	 **/
	@Test
	public void testGramaticaErrorLexico() {
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\gramaticaEL.y");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
		}
	}

	/**
	 * Se parsea una gramática con un error sint�ctico.
	 **/
	@Test
	public void testGramaticaErrorSintactico() {
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaArchivo(System.getProperty("user.dir") + "\\gramaticas\\gramaticaES.y");
		} catch (IOException e) {
			fail("ERROR AL ABRIR EL ARCHIVO.");
		} catch (ParseException ex) {
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
			
		}
	}

	/**
	 * Se parsea una gramática introducida en una cadena.
	 **/
	@Test
	public void testCadena() {
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			pg.parsearGramaticaCadena("%start E %nulo EPS %token id %% E : T J ; J : '+' T J | EPS ; T : F U ; U : '*' F U | EPS ;F : '(' E ')' | id ; %%");
		} catch (ParseException ex) {
			fail("ERROR SINTACTICO EN EL PARSEO: " + ex.getMessage());
		} catch (TokenMgrError tk) {
			fail("ERROR LEXICO EN EL PARSEO: " + tk.getMessage());
		}
	}

	/**
	 * Se comprueba una gramática LR.
	 **/
	@Test
	public void testgramaticaLR() {
		String a = "-g gramatica1.yc -t LR";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Se comprueba una gramática LL.
	 **/
	@Test
	public void testgramaticaLL() {
		String a = "-g gramatica1.yc -t LL";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);

		} catch (Exception e) {

			e.printStackTrace();
			
		}
	}

	/**
	 * Se comprueba una gramática SLR.
	 **/
	@Test
	public void testgramaticaSLR() {
		String a = "-g gramatica1.yc -t SLR";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);
		} catch (IOException e) {

			e.printStackTrace();
		} 
	}

	/**
	 * Se comprueba una gramática LALR.
	 **/
	@Test
	public void testgramaticaLALR() {
		String a = "-g gramatica1.yc -t LALR";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se comprueba una gramática con solo el fichero .XML.
	 **/
	@Test
	public void testgramaticaXML() {
		String a = "-g gramatica1.yc -t LALR -i XML";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se comprueba una gramática con solo el fichero .TEX.
	 **/
	@Test
	public void testgramaticaTEX() {
		String a = "-g gramatica1.yc -t LALR -i TEX";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Se comprueba una gramática con un nombre de fichero de salida.
	 **/
	@Test
	public void testgramaticafichero() {
		String a = "-g gramatica1.yc -t LALR -o fichero";
		String[] args = a.split(" ");
		try {
			Prototipo.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Se comprueba una gramática con una nombre de fichero de salida.
	 **/
	@Test
	public void testgramaticacadena() {
		String[] args = { "-g", "gramatica1.yc", "-t", "LL", "-ca", "b a a c"};
			try {
			Prototipo.main(args);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Se comprueba el main.
	 **/
	@SuppressWarnings("static-access")
	@Test
	public void testmainLL() {
		String a = "-g gramatica1.yc -t LL ";
		String[] args = a.split(" ");
		
		try {
			
			 Main ap=new Main();
			 ap.main(args);
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Se comprueba el main.
	 **/
	@SuppressWarnings("static-access")
	@Test
	public void testmainLR() {
		String a = "-g gramatica1.yc -t LR ";
		String[] args = a.split(" ");
		
		try {
			
			 Main ap=new Main();
			 ap.main(args);
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Se comprueba el main.
	 **/
	@SuppressWarnings("static-access")
	@Test
	public void testmainSLR() {
		String a = "-g gramatica1.yc -t SLR ";
		String[] args = a.split(" ");
		
		try {
			
			 Main ap=new Main();
			 ap.main(args);
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Se comprueba el main.
	 **/
	@SuppressWarnings("static-access")
	@Test
	public void testmainLALR() {
		String a = "-g gramatica1.yc -t LALR ";
		String[] args = a.split(" ");
		
		try {
			
			 Main ap=new Main();
			 ap.main(args);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Se comprueba el main.
	 **/
	@SuppressWarnings("static-access")
	@Test
	public void testmainmover() {
		String a = "-g gramatica1.yc -t LALR ";
		String[] args = a.split(" ");
		
		try {
			
			 Main ap=new Main();
			 ap.main(args);
			 ProblemaPanel<?> problema = null;
			
			 ap.moverProblemaAbajo( problema);
			 ap.moverProblemaArriba( problema);
			 ap.actualizaVistaPrevia(problema);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Se comprueba el análisis.
	 **/
	@Test
	public void testanalisis() {
		Gramatica g = null;
		ParserGramatica pg = new ParserGramatica(false, new ParserYacc());
		try {
			
			 g = pg.parsearGramaticaCadena(
						"%start E %nulo EPS %token id %% E : T J ; J : c T J | EPS ; T : F U ; U : d F U | EPS ;F : a E b | id ; %%");
				Analisis anl = new AnalisisLR1(g);
				Analisis anl1=new AnalisisLL1(g);
				Analisis anl2=new AnalisisSLR1(g);
				Analisis anl3=new AnalisisLALR1(g);
				// Inicializa una entrada correcta.
				VectorSimbolos vs = new VectorSimbolos();
				vs.insertarSimbolo(new Terminal("a"));
				vs.insertarSimbolo(new Terminal("id"));
				vs.insertarSimbolo(new Terminal("c"));
				vs.insertarSimbolo(new Terminal("id"));
				vs.insertarSimbolo(new Terminal("b"));
				vs.insertarSimbolo(new Nulo());
				anl.iniciarAnalisis(vs);
				anl1.iniciarAnalisis(vs);
				anl2.iniciarAnalisis(vs);
				anl3.iniciarAnalisis(vs);
				anl.resetearAnalisis();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
