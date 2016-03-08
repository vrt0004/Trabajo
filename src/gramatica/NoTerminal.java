package gramatica;
/**
* <b>Descripción</b><br>
* Clase que implementa un simbolo No Terminal .
* <p>
* <b>Detalles</b><br>
* Clase que implementa la clase Simbolo para el uso de simbolos no terminales.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class NoTerminal extends Simbolo
{
  /**
   * Constructor básico.
   * 
   * @param nt Nombre del no terminal.
  **/
  public NoTerminal(String nt)
  {super(nt);}
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
  {return true;}
 /**
   * Identifica si el simbolo es un simbolo nulo.
   * @return "True" si es un simbolo nulo o "False" si no es un simbulo nulo. 
  **/
  public boolean esNulo()
  {return false;}
  /**
   * Compara si el simbolo actual y "s" son iguales. Iguales en tipo e iguales en contenido.
   * @param s Simbolo que se comparara con el simbolo actual.
   * @return True si los dos simbolos son iguales. 
  **/
  public boolean esIgual(Simbolo s)
  {
  	if(s==null)
  	  return false;
  	if(s.esNoTerminal()&&esIgualContenido(s))
  	  return true;
  	return false;
  }
  /**
   * Obtiene una copia del simbolo actual.
   * @return El simbolo copiado. 
  **/
  public Simbolo copiar()
  {return new NoTerminal(super.toString());}
}