package analisis;

import gramatica.*;
import analisis.tabla.*;

import analisis.informe.*;

import analisis.analisisSintactico.*;
import analisis.analisisSintactico.ascendente.*;

/**
 * <b>Descripci�n</b><br>
 * Clase abstracta que implementa la simulaci�n de los analisis sint�cticos.
 * <p>
 * <b>Detalles</b><br>
 * A trav�s de esta clase abstracta se representan las operaciones comunes a
 * todas las simulaciones an�lisis sint�cticos.<br>
 * </p>
 * 
 * @author Carlos G�mez Palacios.
 * @version 1.0
 */
public abstract class Analisis {
	// Resultados que emite el analisis.
	/**
	 * Paso realizado correctamente
	 */
	public static int PASO_REALIZADO = 2;
	/**
	 * Analisis finalizado
	 */
	public static int ANALISIS_FINALIZADO = 1;
	/**
	 * Paso no realizado.
	 */
	public static int PASO_NO_REALIZADO = 0;
	/**
	 * Error en el analisis.
	 */
	public static int ERROR_ANALISIS = -1;

	/**
	 * Pila utilizada en el analisis.
	 **/
	public Pila pi;
	/**
	 * Iteracion actual.
	 **/
	protected int it_actual = 0;
	/**
	 * Entrada del analisis.
	 **/
	public VectorSimbolos entrada;
	/**
	 * Contenido actual de la entrada.
	 **/
	public VectorSimbolos entrada_actual;
	/**
	 * Gramatica del analisis sint�ctico.
	 **/
	protected Gramatica gramatica;
	/**
	 * Algoritmo que realiza el an�lisis sint�ctico.
	 **/
	protected AnalisisSintactico algoritmo;
	/**
	 * Informe que genera el analisis.
	 **/
	protected Informe informe;
	/**
	 * Variable que almacena el estado de la salida.
	 **/
	protected String produccion_salida = "";
	/**
	 * Variable que conecta la parte grafica.
	 **/
	public ISalidaAnalisis salida = null;

	/**
	 * Constructor b�sico del an�lisis.
	 * 
	 * @param gr
	 *            Gramatica del analisis.
	 **/
	public Analisis(Gramatica gr) {
		gramatica = gr;
	}

	/**
	 * Mzzztodo abstracto que permite ir a un determinado estado de la
	 * simulaci�n.
	 * 
	 * @param it_inicio
	 *            Iteraci�n de inicio.
	 * @param it_fin
	 *            Iteraci�n de fin.
	 * @return int
	 **/
	public abstract int realizarIteracion(int it_inicio, int it_fin);

	/**
	 * Establece la clase que se utilizada para la salida del an�lisis.
	 * 
	 * @param sa
	 *            La clase que obtiene las salidas que genera esta clase.
	 **/
	public void setSalidaAnalisis(ISalidaAnalisis sa) {
		salida = sa;
	}

	/**
	 * Obtiene la clase que se utiliza para la salida del an�lisis.
	 * 
	 * @return La clase que obtiene las salidas que genera esta clase.
	 **/
	public ISalidaAnalisis getSalidaAnalisis() {
		return salida;
	}

	/**
	 * Obtiene la gramatica del analisis.
	 * 
	 * @return La gramatica del analisis.
	 **/
	public Gramatica obtenerGramaticaAnalisis() {
		return algoritmo.obtenerGramatica();
	}

	/**
	 * Obtiene la gramatica ampliada del analisis.
	 * 
	 * @return La gramatica ampliada del analisis.
	 **/
	public Gramatica obtenerGramaticaAmpliadaAnalisis() {
		return algoritmo.obtenerGramaticaAmpliada();
	}

	/**
	 * Obtiene la tabla de analisis del analisis sint�ctico.
	 * 
	 * @return La tabla de analisis.
	 **/
	public Tabla obtenerTablaAnalisis() {
		return algoritmo.obtenerTabla();
	}

	/**
	 * Obtiene el algoritmo de an�lisis sint�ctico
	 * 
	 * @return El algoritmo de an�lisis.
	 **/
	public AnalisisSintactico obtenerAlgoritmoAnalisis() {
		return algoritmo;
	}

	/**
	 * Obtiene el automata de an�lisis sint�ctico
	 * 
	 * @return El automata genera de an�lisis (SLR,LR,LALR).
	 **/
	public Automata obtenerAutomataAnalisis() {
		return algoritmo.obtenerAutomata();
	}

	/**
	 * Comienza una simulaci�n del an�lisis sint�ctico para una entrada.
	 * 
	 * @param ent
	 *            Entrada que se simula.
	 **/
	public void iniciarAnalisis(VectorSimbolos ent) {
		for (int i = 0; i < ent.simbolosIntroducidos(); i++) {
			Simbolo s_actual = ent.obtenerSimbolo(i);
			if (s_actual.esNulo() && (i == 0))
				return;
			if (!s_actual.esNulo()) {
				if (!obtenerGramaticaAnalisis().estaIntroducidoTerminal(s_actual.toString())) {

					return;
				}
			}
		}
		entrada = ent;
		resetearAnalisis();
	}

	/**
	 * Realiza el siguiente paso del algoritmo.
	 * 
	 * @return El resultado del paso realizado.
	 *         (PASO_REALIZADO,ANALISIS_FINALIZADO,PASO_NO_REALIZADO,
	 *         ERROR_ANALISIS).
	 **/
	public int siguientePaso() {
		if (hayEntradaAnalisis())
			return realizarIteracion(it_actual, it_actual + 1);
		return PASO_NO_REALIZADO;
	}

	/**
	 * Realiza el paso anterior del algoritmo.
	 * 
	 * @return El resultado del paso realizado.
	 *         (PASO_REALIZADO,ANALISIS_FINALIZADO,PASO_NO_REALIZADO,
	 *         ERROR_ANALISIS).
	 **/
	public int pasoAnterior() {
		if (hayEntradaAnalisis())
			return realizarIteracion(0, it_actual - 1);
		return PASO_NO_REALIZADO;
	}

	/**
	 * Reinicia el an�lisis.
	 **/
	public void resetearAnalisis() {
		if (hayEntradaAnalisis()) {
			it_actual = 0;
			pi = new Pila();
			entrada_actual = new VectorSimbolos();
			if (salida != null)
				salida.resetearAnalisis();// Resetear el salida.
			for (int i = 0; i < entrada.simbolosIntroducidos(); i++) {
				insertarSimboloCola(entrada.obtenerSimbolo(i));
			}
		}
	}

	/**
	 * Introduce un simbolo en la pila de analisis.
	 * 
	 * @param s
	 *            Simbolo que se introduce en la pila.
	 **/
	public void insertarSimboloPila(Simbolo s) {
		pi.meterPila(s);
		if (salida != null)
			salida.introducirPilaAnalisis(s.toString());
	}

	/**
	 * Desapila un simbolo de la pila de analisis.
	 **/
	protected void desapilarSimbolo() {
		pi.sacarPila();
		if (salida != null)
			salida.eliminarPilaAnalisis();
	}

	/**
	 * Introduce un simbolo en la entrada del analisis.
	 * 
	 * @param s
	 *            Simbolo que se introduce en la entrada.
	 **/
	protected void insertarSimboloCola(Simbolo s) {
		entrada_actual.insertarSimbolo(s);
		if (salida != null)
			salida.introducirColaAnalisis(s.toString());
	}

	/**
	 * Elimina un simbolo de la entrada del analisis.
	 **/
	protected void elimarSimboloCola() {
		entrada_actual.eliminarSimbolo(0);
		if (salida != null)
			salida.eliminarColaAnalisis();
	}

	/**
	 * Obtiene el siguiente s�mbolo de la entrada del analisis.
	 * 
	 * @return Simbolo de la cola
	 **/
	public Simbolo obtenerSimboloCola() {
		return (entrada_actual.obtenerSimbolo(0));
	}

	/**
	 * Genera una producci�n de salida en el an�lisis.
	 * 
	 * @param pr
	 *            Produccion de salida.
	 **/
	protected void produccionSalida(Produccion pr) {
		if (salida != null)
			salida.introducirProduccionSalidaAnalisis(pr);
		produccion_salida = pr.toString();
		if (salida != null)
			salida.iniciarProduccionArbolAnalisis(pr);
		for (int i = 0; i < pr.obtenerParteDerecha().simbolosIntroducidos(); i++) {
			if (pr.obtenerSimboloParteDerecha(i).getClass().equals(Terminal.class)
					|| pr.obtenerSimboloParteDerecha(i).getClass().equals(Nulo.class)) {
				if (salida != null)
					salida.nuevoTerminalArbolAnalisis(pr.obtenerSimboloParteDerecha(i));
			} else {
				if (salida != null)
					salida.nuevoNoTerminalArbolAnalisis(pr.obtenerSimboloParteDerecha(i));
			}
		}
		if (salida != null)
			salida.finalizarProduccionArbolAnalisis();
	}

	/**
	 * Comprueba si se ha introducido una cadena para analizar.
	 * 
	 * @return "True" Si hay entrada para analizar o "False" si no hay entrada
	 *         para analizar.
	 **/
	public boolean hayEntradaAnalisis() {
		if (entrada == null)
			return false;
		return true;
	}

	/**
	 * Obtiene el informe del analisis.
	 * 
	 * @return El informe del analisis.
	 **/
	public Informe obtenerInformeAnalisis() {
		return informe;
	}

	/**
	 * Obtiene el estado de la pila de analisis
	 ** 
	 * @return El estado de la pila de analisis.
	 **/
	public String obtenerEstadoPilaAnalisis() {
		return pi.toString();
	}

	/**
	 * Obtiene el estado actual de la entrada de analisis
	 ** 
	 * @return El estado de la entrada de analisis.
	 **/
	public String obtenerEstadoEntradaAnalisis() {
		return entrada_actual.toString();
	}

	/**
	 * Obtiene las producciones de salida emitidas en el analisis
	 ** 
	 * @return Las producciones emitidas.
	 **/
	public String obtenerProduccionSalida() {
		return produccion_salida;
	}
}