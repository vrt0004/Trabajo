package analisis.analisisSintactico;
import gramatica.*;
import analisis.tabla.*;
import analisis.analisisSintactico.ascendente.*;
/**
* <b>Descripci�n</b><br>
* Clase abstracta que representa los an�lisis sint�cticos.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se agrupan los m�todos comunes a todos los an�lisis sint�cticos, a traves de una clase abstracta.<br>
* </p> 
* @author Carlos G�mez Palacios.
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
  /**Gramatica de la que se efect�a el analisis.
  **/
  protected Gramatica gramatica;
  /**Gram�tica ampliada.
  **/
  protected Gramatica gramatica_ampliada=null;
  /**
   * Obtiene la gram�tica del an�lisis sint�ctico.
   * @return La gram�tica.
  **/
  public Gramatica obtenerGramatica()
  {return gramatica;}
  /**
   * Obtiene la gram�tica ampliada del an�lisis sint�ctico.
   * @return La gram�tica si existe o null si no existe (analisis LL1).
  **/
  public Gramatica obtenerGramaticaAmpliada()
  {return gramatica_ampliada;}
  /**
   * Obtiene la tabla de an�lisis sint�ctico.
   * @return La tabla de analisis.
  **/
  public  Tabla obtenerTabla()
  {return tabla;}
  /**
   * Obtiene el aut�mata generado por el an�lisis.
   * @return El aut�mata si existe o null si no existe (analisis LL1).
  **/
  public Automata obtenerAutomata()
  {return automata;}
}