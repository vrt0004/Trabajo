package analisis.tabla;
import gramatica.*;
/**
* <b>Descripción</b><br>
* Clase abstracta que representa una tabla de análisis.
* <p>
* <b>Detalles</b><br>
* Esta tabla define los métodos comunes a las tablas de anlisis sintáctico.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public abstract class Tabla
{
  /**Vector de terminales de la tabla.
  **/
  protected VectorSimbolos VT;
  /**Vector de no terminales.
  **/
  protected VectorSimbolos VNT;
  /**Almacena si existen casillas con varios valores.
  **/
  protected boolean casilla_multivalor;
  /**
   * Funcion que obtiene los  terminales de la tabla.
   * @return Los terminales de la tabla.
  **/
  public VectorSimbolos obtenerTerminales()
  {return VT;}
   /**
   * Funcion que obtiene el numero de terminales de la tabla.
   * @return El numero de terminales.
  **/
  public int obtenerNumeroTerminales()
  {return VT.simbolosIntroducidos();}
  /**
   * Funcion que obtiene los no terminales de la tabla.
   * @return El numero de no terminales.
  **/
  public VectorSimbolos obtenerNoTerminales()
  {return VNT;}
  /**
   * Funcion que obtiene el numero de no terminales de la tabla.
   * @return Los no terminales de la tabla.
  **/
  public int obtenerNumeroNoTerminales()
  {return VNT.simbolosIntroducidos();}
  /**
   * Funcion que obtiene si la tabla tiene alguna casilla con varias producciones.
   * @return "True" si hay casillas multivalor o "False" si no.
  **/
  public boolean existeCasillaMultivalor()
  {return casilla_multivalor;}
  /**
   * Obtiene el contenido de la tabla de análisis.
   * @return El contenido de la tabla de análisis.
  **/
  public abstract String toString();
  /**
  * Muestra por pantalla el contenido de la tabla de análisis.
  **/
  public abstract void debug();
}