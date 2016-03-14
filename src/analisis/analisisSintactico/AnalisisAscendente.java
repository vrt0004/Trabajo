package analisis.analisisSintactico;
import gramatica.*;
import java.util.Vector;
import analisis.tabla.*;
import java.util.Hashtable;
import analisis.analisisSintactico.ascendente.*;
/**
* <b>Descripción</b><br>
* Clase que implementa el analisis LALR1 de una gramática.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se genera la tabla necesaria para realizar el analisis LR1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public abstract class AnalisisAscendente  extends AnalisisSintactico
{
  /**Tabla donde se almacenan los elementos que se encuentran clausudos.
  **/
  protected Hashtable <Integer,CjtoConfig> tablaClausurados=new Hashtable <Integer,CjtoConfig> ();
  /**
   * Constructor básico.
   * @param gr Gramatica
  **/
  public AnalisisAscendente(Gramatica gr)
  {gramatica=gr;}
  /**
   * Función que inicia el análisis ascendente.
  **/
  protected void iniciarAnalisis()
  {
	//Amplia la gramatica.
    ampliarGramatica();
    //Calcular el cojunto de items inicial.
    calcularConjuntoInicial();
    //Calcular el resto de los conjuntos.
    calcularConjuntos();
    //Calcular la tabla de accion.
    calcularTablaAccion();
    //Calcular la tabla de "ir a"
    calcularTablaIrA();
  }
  /**
   * Función que amplia  la gramática con una nueva producción.
  **/
  private void ampliarGramatica()
  {
	gramatica_ampliada=gramatica.copiar();  
	String in_ant=gramatica_ampliada.getSimboloInicio().toString();
  	String in_nuevo=in_ant+"'";
  	///Se añade la primera produccion.
    gramatica_ampliada.insertarNoTerminal(in_nuevo);
  	gramatica_ampliada.setSimboloInicio(in_nuevo);
    VectorSimbolos pd=new VectorSimbolos();
    pd.insertarSimbolo(new NoTerminal(in_ant));
    gramatica_ampliada.insertarInicioProduccion(new Produccion(new NoTerminal(in_nuevo),pd));
  }
  /**
   * Función abstracta para calculo el conjunto inicial.
  **/
  protected abstract void calcularConjuntoInicial();
  /**
   * Función abstracta que calcula el resto de los conjuntos.
  **/
  protected abstract void calcularConjuntos();
  /**
   * Función abstracta que calcula la tabla de acción.
  **/
  protected abstract void calcularTablaAccion();
  /**
   * Función abstracta que calcula la tabla de "Ir_A".
  **/
  protected abstract void calcularTablaIrA();
  /**
   * Función abstracta que calcula el cierre de un conjunto de items.
  **/
  protected abstract void calcularCierre(CjtoConfig cjto_inic);
  /**
   * Función abstracta que calcula el sucesor de un conjunto de items.
  **/
  protected abstract CjtoConfig calcularSucesor(Automata automata,int ind,CjtoConfig cjto_inicial,Gramatica G,Simbolo s,Vector <Integer> v);
}