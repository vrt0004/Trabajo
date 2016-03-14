package analisis.analisisSintactico;
import gramatica.*;
import analisis.tabla.*;
import analisis.analisisSintactico.ascendente.*;
/**
* <b>Descripción</b><br>
* Clase abstracta que representa los análisis sintácticos.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se agrupan los métodos comunes a todos los análisis sintácticos, a traves de una clase abstracta.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public abstract class AnalisisSintactico
{
  /**Tabla de analisis sintactico.
  **/
  protected Tabla tabla;
  /**Automata.
  **/
  protected Automata automata;
  /**Gramatica de la que se efectúa el analisis.
  **/
  protected Gramatica gramatica;
  /**Gramática ampliada.
  **/
  protected Gramatica gramatica_ampliada=null;
  /**
   * Obtiene la gramática del análisis sintáctico.
   * @return La gramática.
  **/
  public Gramatica obtenerGramatica()
  {return gramatica;}
  /**
   * Obtiene la gramática ampliada del análisis sintáctico.
   * @return La gramática si existe o null si no existe (analisis LL1).
  **/
  public Gramatica obtenerGramaticaAmpliada()
  {return gramatica_ampliada;}
  /**
   * Obtiene la tabla de análisis sintáctico.
   * @return La tabla de analisis.
  **/
  public  Tabla obtenerTabla()
  {return tabla;}
  /**
   * Obtiene el autómata generado por el análisis.
   * @return El autómata si existe o null si no existe (analisis LL1).
  **/
  public Automata obtenerAutomata()
  {return automata;}
}