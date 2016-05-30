package analisis.tabla;

import gramatica.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * <b>Descripción</b><br>
 * Clase que implementa una tabla de analisis ascendente.
 * <p>
 * <b>Detalles</b><br>
 * Esta tabla es utilizada por el análisis sintáctico ascedente SLR1,LR1 y
 * LALR1.<br>
 * </p>
 * 
 * @author Carlos Gómez Palacios.
 * @version 1.0
 */
public class TablaAscendente extends Tabla {
	public Hashtable<Integer, Hashtable<String, Vector<ElTablaAsc>>> tabla_principal;// Tabla
																						// principal.

	/**
	 * Constructor básico.
	 **/
	public TablaAscendente() {
		tabla_principal = new Hashtable<Integer, Hashtable<String, Vector<ElTablaAsc>>>();
		// Se inicializan los vectores de simbolos.
		VT = new VectorSimbolos();
		VNT = new VectorSimbolos();
	}

	/**
	 * Introduce un terminal en la tabla ascendente.
	 * 
	 * @param t
	 *            El terminal que se introduce.
	 **/
	public void introducirTerminalTablaAscendente(Terminal t) {
		if (!VT.estaSimbolo(t)) {
			VT.insertarSimbolo(t);
		}
	}

	/**
	 * Introduce un no terminal en la tabla ascendente.
	 * 
	 * @param nt
	 *            El no terminal que se introduce.
	 **/
	public void introducirNoTerminalTablaAscendente(NoTerminal nt) {
		if (!VNT.estaSimbolo(nt)) {
			VNT.insertarSimbolo(nt);
		}
	}

	/**
	 * Introduce un simbolo nulo en la tabla ascendente.
	 **/
	public void introducirNuloTablaAscendente() {
		if (!VT.estaSimbolo(new Nulo()))
			VT.insertarSimbolo(new Nulo());
	}

	/**
	 * Introduce un valor en una casilla la tabla ascendente.
	 * 
	 * @param nodo
	 *            Fila donde se introduce el valor.
	 * @param s
	 *            Simbolo para el que se introduce el valor.
	 * @param ac
	 *            Elmento que se introduce.
	 **/
	public void insertarElementoTablaAscendente(int nodo, Simbolo s, ElTablaAsc ac) {
		if (s.getClass().equals(Terminal.class)) {
			introducirTerminalTablaAscendente((Terminal) s);
		} else {
			if (s.getClass().equals(NoTerminal.class))
				introducirNoTerminalTablaAscendente((NoTerminal) s);
			else
				introducirNuloTablaAscendente();
		}
		// Tabla auxiliar.
		Hashtable<String, Vector<ElTablaAsc>> aux = tabla_principal.get(nodo);
		if (aux == null) {
			// Si no existe la tabla auxiliar se crea.
			aux = new Hashtable<String, Vector<ElTablaAsc>>();
			// Se crea un vector auxiliar.
			Vector<ElTablaAsc> v = new Vector<ElTablaAsc>();
			v.add(ac);
			aux.put(s.toString(), v);
			tabla_principal.put(nodo, aux);
		}
		// Si ya existe la tabla auxiliar se obtiene.
		else {
			Vector<ElTablaAsc> v = aux.get(s.toString());
			// Si el vector auxiliar no existe se crea.
			if (v == null)
				v = new Vector<ElTablaAsc>();
			// Si existe la casilla tiene mas de un valor.
			else
				casilla_multivalor = true;
			v.add(ac);
			aux.put(s.toString(), v);
		}
	}

	/**
	 * Obtiene los valores de un simbolo para un nodo.
	 * 
	 * @param simb
	 *            Simbolo.
	 * @param nodo
	 *            Nodo.
	 * @return Los elementos de la tabla en un Vector para un simbolo y un nodo.
	 **/
	@SuppressWarnings("rawtypes")
	public Vector obtenerElementoTablaAscendente(Simbolo simb, int nodo) {
		Hashtable aux = tabla_principal.get(nodo);
		if (aux == null)
			return null;
		return (Vector) aux.get(simb.toString());
	}

	/**
	 * Obtiene el numero de filas de la tabla.
	 * 
	 * @return El numero de filas.
	 **/
	public int obtenerNumeroFilas() {
		return tabla_principal.size();
	}

	/**
	 * Obtiene el contenido de la tabla de análisis ascendente en un String.
	 * 
	 * @return El contenido de la tabla de análisis ascendente.
	 **/
	public String toString() {
		String cadena = "";
		cadena = cadena + "-----\n";
		@SuppressWarnings("rawtypes")
		Enumeration e1 = tabla_principal.keys();
		while (e1.hasMoreElements()) {
			int nodo = (Integer) e1.nextElement();
			cadena = cadena + nodo + "\n";
			@SuppressWarnings("rawtypes")
			Hashtable sub_tabla = tabla_principal.get(nodo);
			@SuppressWarnings("rawtypes")
			Enumeration e2 = sub_tabla.keys();
			while (e2.hasMoreElements()) {
				String SIMB = (String) e2.nextElement();
				cadena = cadena + "\t" + SIMB;
				@SuppressWarnings("rawtypes")
				Vector v = (Vector) sub_tabla.get(SIMB);
				cadena = cadena + "(";
				for (int i = 0; i < v.size(); i++) {
					cadena = cadena + ((ElTablaAsc) v.get(i)).toString();
					if ((i + 1) < v.size())
						cadena = cadena + ",";
				}
				cadena = cadena + ")";
			}
			cadena = cadena + "\n";
		}
		cadena = cadena + "-----";
		return cadena;
	}

	/**
	 * Muestra por pantalla el contenido de la tabla de análisis ascendente.
	 **/
	public void debug() {
		System.out.println(toString());
	}
}