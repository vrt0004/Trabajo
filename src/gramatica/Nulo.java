package gramatica;
/**
* <b>Descripción</b><br>
* Clase que implementa el simbolo Nulo.
* <p>
* <b>Detalles</b><br>
* Clase que implementa la clase Simbolo para el uso de simbolos nulos.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Nulo extends Simbolo
{
  /**
   * Constructor básico.
  **/	
  public Nulo()
  {super("$");}
  /**
   * Identifica si el simbolo es un terminal.
   * @return "True" si es un terminal o "False" si no es un terminal. 
  **/
  public boolean esTerminal()
  {return false;}
   /**
   * Identifica si el simbolo es un no terminal.
   * @return "True" si es un no terminal o "False" si no es un no terminal. 
  **/
   public boolean esNoTerminal()
  {return false;}
   /**
   * Identifica si el simbolo es un simbolo nulo.
   * @return "True" si es un simbolo nulo o "False" si no es un simbulo nulo. 
  **/
  public boolean esNulo()
  {return true;}
   /**
   * Compara si el simbolo actual y "s" son iguales. Iguales en tipo e iguales en contenido.
   * @param s Simbolo que se comparara con el simbolo actual.
   * @return "True" si los dos simbolos son iguales o "False" si son distintos.
  **/
  public boolean esIgual(Simbolo s)
  {
  	if(s==null)
  	  return false;
  	if(s.esNulo())
  	  return true;
  	return false;
  }
  /**
   * Obtiene una copia del simbolo actual.
   * @return El simbolo copiado. 
  **/
  public Simbolo copiar()
  {
  	return new Nulo();
  }
}