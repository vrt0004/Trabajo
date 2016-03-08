package gramatica;
/**
* <b>Descripción</b><br>
* Interface de simbolo.
* <p>
* <b>Detalles</b><br>
* Clase que representa las propiedades comunes a todos los simbolos.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public abstract class Simbolo
{
  //Variable que almacena el contenido del simbolo.
  private String simbolo="";
  /**
   * Constructor básico.
   * 
   * @param s Nombre del simbolo.
  **/
  public Simbolo(String s)
  {simbolo=s;}
  /**
   * Identifica si el simbolo es un terminal.
   * @return "True" si es un terminal o "False" si no es un terminal. 
  **/
  public abstract boolean esTerminal();
  /**
   * Identifica si el simbolo es un no terminal.
   * @return "True" si es un no terminal o "False" si no es un no terminal. 
  **/
  public abstract boolean esNoTerminal();
  /**
   * Identifica si el simbolo es un simbolo nulo.
   * @return "True" si es un simbolo nulo o "False" si no es un simbulo nulo. 
  **/
  public abstract boolean esNulo();
  /**
   * Compara si el simbolo actual y "s" son iguales. Iguales en tipo e iguales en contenido.
   * @param s Simbolo que se comparara con el simbolo actual.
   * @return "True" si los dos simbolos son iguales o  "False" si los dos simbolos no son iguales.
  **/
  public abstract boolean esIgual(Simbolo s);
  /**
   * Compara si el simbolo actual y "s" son iguales en contenido.No se comprueba el tipo.
   * @param s Simbolo que se comparara con el simbolo actual.
   * @return "True" si los dos simbolos son iguales o "False" si no lo son. 
  **/
  public boolean esIgualContenido(Simbolo s)
  {
    if(s.toString().compareTo(simbolo)==0)
	  return true;
	return false;
  }
  /**
   * Obtiene una copia del simbolo actual.
   * @return El simbolo copiado. 
  **/
  public abstract Simbolo copiar();
  /**
   * Obtiene el contenido del simbolo.
   * @return El contenido del simbolo. 
  **/
  public String toString()
  {return simbolo;}
  /**
   * Muestra por pantalla el contenido del simbolo.
  **/
  public void debug()
  {System.out.println(toString());}
}