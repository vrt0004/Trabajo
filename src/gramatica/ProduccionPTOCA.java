package gramatica;

/**
* <b>Descripción</b><br>
* Clase que implementa una producción punto con anticipación.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones en las que se necesitar conocer el número de símbolos que se han analizado y en las que se necesita una lista de símbolos de anticipación.Son utilizadas en el análisis ascendente.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/

public class ProduccionPTOCA extends ProduccionPTO
{
  private Anticipacion anticipacion;//Almacena la anticipación.
  
  /**
   * Constructor básico de producción con punto y símbolos de anticipación.
   * @param parte_izquierda Parte izquierda de la producción.
   * @param parte_derecha Parte derecha de la producción.
   * @param punto Número de símbolos de la parte derecha que se han analizado.
   * @param atp Listas de símbolos anticipación.
  **/
  public ProduccionPTOCA(NoTerminal parte_izquierda,VectorSimbolos parte_derecha,int punto,Anticipacion atp)
  {
    super(parte_izquierda,parte_derecha,punto);
    anticipacion=atp;
  }
  /**
   * Obtiene la anticipación de la producción con punto y anticipación actual.
   * @return La anticipación de la producción con punto y anticipación.
  **/
  public Anticipacion obtenerAnticipacion()
  {return anticipacion;}
  /**
   * Compara si la producción con punto y anticipación actual es igual a otra pasada como parámetro del mismo tipo.
   * @param pr Producción con punto y anticipación que se compara.
   * @return "True" si las dos producciones con punto y anticipación son iguales y "False" si no lo son.
  **/
  public boolean esIgual(ProduccionPTOCA pr)
  {
    if(super.esIgual(pr))
  	  return (pr.obtenerAnticipacion().esIgual(anticipacion));
  	return false;
  }
  /**
   * Obtiene el contenido de la producción con la posición del punto y los símbolos de anticipación.
   * @return El contenido de la producción con el punto y los símbolos de anticipación, en un String.
  **/
  public String toString()
  {
  	String cadena=super.toString();
  	cadena=cadena+"{"+anticipacion+"}";
  	return cadena;
  }
  /**
   * Obtiene el contenido de la producción con la posición del punto y sin los símbolos de anticipación.
   * @return El contenido de la producción con el punto y sin los símbolos de anticipación, en un String.
  **/
  public String toStringSA()
  {
  	String cadena=super.toString();
  	return cadena;
  }
  /**
   * Muestra por pantalla la producción con la posición del punto y los simbolos de anticipación.
  **/
  public void debug()
  {System.out.println(toString());}
  /**
   * Muestra por pantalla la producción con la posición del punto,sin mostrar los simbolos de anticipación.
  **/
  public void debugSA()
  {System.out.println(toStringSA());}
}