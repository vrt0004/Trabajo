package gramatica;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <b>Descripción</b><br>
 * Clase que implementa las operaciones necesarias para trabajar con una
 * gramática.
 * <p>
 * <b>Detalles</b><br>
 * A través de esta clase se realizan todas las operaciones referentes a la
 * gramática:creación, parseo, modificación, análisis.....<br>
 * </p>
 * 
 * @author Carlos Gómez Palacios.
 * @version 1.0
 */
public class Gramatica {
	public String nombre = "";// Nombre de la gramática.
	public Hashtable<String, Terminal> tTerminales;// Tabla de terminales.
	public Hashtable<String, NoTerminal> tNoTerminales;// Tabla de no
														// terminales.
	public VectorProducciones producciones;// Vector de producciones.
	public NoTerminal simbolo_inicio;// Simbolo de inicio.
	public First first = null;// First de la gramática.
	public Follow follow = null;// Follow de la gramática.

	/** Constructor básico de la gramática. **/
	public Gramatica() {
		// Se inicializan Terminales y no terminales.
		tTerminales = new Hashtable<String, Terminal>();
		tNoTerminales = new Hashtable<String, NoTerminal>();
		// Se inicializa el vector de producciones.
		producciones = new VectorProducciones();
		// Se inicializa el simbolo de inicio con "S".
		simbolo_inicio = new NoTerminal("S");
		// Se inicializa el nombre de la gramática.
		setNombreGramatica("GRAMATICA");
	}

	/**
	 * Establece el nombre de la gramática.
	 * 
	 * @param n
	 *            Nombre de la gramática.
	 **/
	public void setNombreGramatica(String n) {
		nombre = n;
	}

	/**
	 * Obtiene el nombre de la gramática.
	 * 
	 * @return Nombre de la gramática.
	 **/
	public String getNombreGramatica() {
		return nombre;
	}

	/**
	 * Establece el simbolo de inicio.
	 * 
	 * @param si
	 *            Nombre del simbolo de inicio.
	 **/
	public void setSimboloInicio(String si) {
		simbolo_inicio = new NoTerminal(si);
	}

	/**
	 * Obtiene el simbolo de inicio.
	 * 
	 * @return Simbolo de inicio.
	 **/
	public Simbolo getSimboloInicio() {
		return simbolo_inicio;
	}

	/**
	 * Obtiene una copia de la gramática
	 * 
	 * @return Una copia de la gramática.
	 **/
	public Gramatica copiar() {
		// Crear la gramatica.
		Gramatica nueva = new Gramatica();
		// Inicializa el nombre de la gramática.
		nueva.setNombreGramatica(getNombreGramatica());
		// Inicializar el simbolo de inicio.
		nueva.setSimboloInicio(getSimboloInicio().toString());
		// Inicializar la tabla de terminales.
		@SuppressWarnings("rawtypes")
		Enumeration e = tTerminales.keys();
		while (e.hasMoreElements()) {
			String t = (String) e.nextElement();
			nueva.insertarTerminal(t);
		}
		// Inicializar la tabla de no terminales.
		e = tNoTerminales.keys();
		while (e.hasMoreElements()) {
			String nt = (String) e.nextElement();
			nueva.insertarNoTerminal(nt);
		}
		// Inicializar el Vector de Producciones.
		for (int i = 0; i < producciones.produccionesIntroducidas(); i++) {
			nueva.insertarProduccion(producciones.obtenerProduccion(i).copiar());
		}
		return nueva;
	}

	/**
	 * Comprueba si en la gramática está introducido un terminal.
	 * 
	 * @param t
	 *            Nombre del terminal
	 * @return "True" si se ha introducido y "False" si no se ha introducido.
	 **/
	public boolean estaIntroducidoTerminal(String t) {
		if (tTerminales.containsKey(t))
			return true;
		return false;
	}

	/**
	 * Se introduce un nuevo terminal en la gramática.
	 * 
	 * @param t
	 *            Nombre del terminal
	 **/
	public void insertarTerminal(String t) {
		if ((!estaIntroducidoTerminal(t)) && (!estaIntroducidoNoTerminal(t)))
			tTerminales.put(t, new Terminal(t));
	}

	/**
	 * Se obtienen los terminales de la gramática.
	 ** 
	 * @return Los terminales de la gramática.
	 **/
	public VectorSimbolos obtenerTerminales() {
		VectorSimbolos vt = new VectorSimbolos();
		@SuppressWarnings("rawtypes")
		Enumeration e = tTerminales.keys();
		while (e.hasMoreElements()) {
			Terminal t = (Terminal) tTerminales.get(e.nextElement());
			vt.insertarSimbolo(t);
		}
		return vt;
	}

	/**
	 * Funcion que obtiene el numero de terminales de la gramatica.
	 ** 
	 * @return El numero de temrinales de la gramatica.
	 **/
	public int numeroTerminales() {
		return tTerminales.size();
	}

	/**
	 * Funcion que obtiene si un terminal esta introducido en una gramatica.
	 * 
	 * @param t
	 *            Teminal.
	 * @return "True" si esta introducido o "False" si no esta introducido.
	 **/
	public boolean estaIntroducidoNoTerminal(String t) {
		if (tNoTerminales.containsKey(t))
			return true;
		return false;
	}

	/**
	 * Se introduce un nuevo no terminal en la gramática.
	 * 
	 * @param nt
	 *            Nombre del terminal
	 **/
	public void insertarNoTerminal(String nt) {
		if ((!estaIntroducidoTerminal(nt)) && (!estaIntroducidoNoTerminal(nt)))
			tNoTerminales.put(nt, new NoTerminal(nt));
	}

	/**
	 * Se obtienen los no terminales de la gramática.
	 ** 
	 * @return Los no terminales de la gramática.
	 **/
	public VectorSimbolos obtenerNoTerminales() {
		VectorSimbolos vnt = new VectorSimbolos();
		@SuppressWarnings("rawtypes")
		Enumeration e = this.tNoTerminales.keys();
		while (e.hasMoreElements()) {
			NoTerminal nt = (NoTerminal) tNoTerminales.get(e.nextElement());
			vnt.insertarSimbolo(nt);
		}
		return vnt;
	}

	/**
	 * Funcion que obtiene el numero de no terminales de la gramatica.
	 ** 
	 * @return El numero de no temrinales de la gramatica.
	 **/
	public int numeroNoTerminales() {
		return tNoTerminales.size();
	}

	/**
	 * Introduce una produccion en la gramática
	 ** 
	 * @param p
	 *            La produccion que se introduce en la gramatica.
	 **/
	public void insertarProduccion(Produccion p) {
		producciones.insertarProduccion(p);
	}

	/**
	 * Introduce una produccion al inicio de la gramatica.
	 ** 
	 * @param p
	 *            La produccion que se introduce al inicio de la gramatica.
	 **/
	public void insertarInicioProduccion(Produccion p) {
		producciones.insertarInicioProduccion(p);
	}

	/**
	 * Obtiene las producciones que tienen una parte izquierda en comun
	 ** 
	 * @param nt
	 *            Parte izquierda que tienen las producciones
	 ** @return Las producciones que tienen la parte izquierda indicada
	 **/
	public VectorProducciones obtenerProduccionesIzquierdaIgual(NoTerminal nt) {
		return producciones.obtenerProduccionesIzquierdaIgual(nt);
	}

	/**
	 * Obtiene el numero de producciones introducidas
	 ** 
	 * @return El numero de producciones
	 **/
	public int produccionesIntroducidasGramatica() {
		return producciones.produccionesIntroducidas();
	}

	/**
	 * Obtiene la produccion que ocupa la posicion que se indica
	 ** 
	 * @param i
	 *            Posicion que se solicita.
	 ** @return La produccion
	 **/
	public Produccion obtenerProduccionGramatica(int i) {
		return producciones.obtenerProduccion(i);
	}

	/**
	 * Obtiene la posicion que ocupa una produccion
	 ** 
	 * @param prd
	 *            La produccion
	 ** @return La posicion que ocupa
	 **/
	public int obtenerPosicionProduccionGramatica(Produccion prd) {
		int posicion = producciones.obtenerPosicionProduccion(prd);
		if (posicion == -1)
			return -1;
		return posicion + 1;
	}

	/**
	 * Obtiene el FIRST de la gramatica
	 ** 
	 * @return El FIRST de la gramatica
	 **/
	public First obtenerFirst() {
		if (first == null)
			first = new First(this);
		return first;
	}

	/**
	 * Obtiene el FOLLOW de una gramatica
	 ** 
	 * @return El FOLLOW de la gramatica
	 **/
	public Follow obtenerFollow() {
		if (follow == null)
			follow = new Follow(this);
		return follow;
	}

	/**
	 * Obtiene el FIRST de un conjunto de simbolo
	 ** 
	 * @param vs
	 *            Simbolos de los que se quiere calcular el FIRST
	 ** @return El FIRST del conjunto de simbolos
	 **/
	public VectorSimbolos obtenerFirstDe(VectorSimbolos vs) {
		return obtenerFirst().calcularFirstVectorSimbolos(vs);
	}

	/**
	 * Obtiene el FOLLOW de un simbolo
	 ** 
	 * @param nt
	 *            Simbolo del que se quiere calcular el FOLLOW
	 ** @return El FOLLOW del simbolo
	 **/
	public VectorSimbolos obtenerFollowDe(NoTerminal nt) {
		return obtenerFollow().obtenerFollowDe(nt);
	}

	/** Muestra por pantalla el contenido de la gramatica **/
	public void debug() {
		System.out.println(toString());
	}

	/**
	 * Obtiene en una cadena el contenido de la gramatica
	 ** 
	 * @return El contenido de la gramatica
	 */
	public String toString() {
		String cadena = "";
		cadena = cadena + "TERMINALES\n";
		cadena = cadena + "\t" + obtenerTerminales().toString() + "\n";
		cadena = cadena + "NO TERMINALES\n";
		cadena = cadena + "\t" + obtenerNoTerminales().toString() + "\n";
		cadena = cadena + "SIMBOLO INICIO\n";
		cadena = cadena + "\t" + simbolo_inicio.toString() + "\n";
		cadena = cadena + "PRODUCCIONES\n";
		cadena = cadena + producciones.toString();
		return cadena;
	}
}