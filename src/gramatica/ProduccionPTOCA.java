package gramatica;

/**
* <b>Descripci�n</b><br>
* Clase que implementa una producci�n punto con anticipaci�n.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones en las que se necesitar conocer el n�mero de s�mbolos que se han analizado y en las que se necesita una lista de s�mbolos de anticipaci�n.Son utilizadas en el an�lisis ascendente.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/

public class ProduccionPTOCA extends ProduccionPTO
{
  private Anticipacion anticipacion;//Almacena la anticipaci�n.
  
  /**
   * Constructor b�sico de producci�n con punto y s�mbolos de anticipaci�n.
   * @param parte_izquierda Parte izquierda de la producci�n.
   * @param parte_derecha Parte derecha de la producci�n.
   * @param punto N�mero de s�mbolos de la parte derecha que se han analizado.
   * @param atp Listas de s�mbolos anticipaci�n.
  **/
  public ProduccionPTOCA(NoTerminal parte_izquierda,VectorSimbolos parte_derecha,int punto,Anticipacion atp)
  {
    super(parte_izquierda,parte_derecha,punto);
    anticipacion=atp;
  }
  /**
   * Obtiene la anticipaci�n de la producci�n con punto y anticipaci�n actual.
   * @return La anticipaci�n de la producci�n con punto y anticipaci�n.
  **/
  public Anticipacion obtenerAnticipacion()
  {return anticipacion;}
  /**
   * Compara si la producci�n con punto y anticipaci�n actual es igual a otra pasada como par�metro del mismo tipo.
   * @param pr Producci�n con punto y anticipaci�n que se compara.
   * @return "True" si las dos producciones con punto y anticipaci�n son iguales y "False" si no lo son.
  **/
  public boolean esIgual(ProduccionPTOCA pr)
  {
    if(super.esIgual(pr))
  	  return (pr.obtenerAnticipacion().esIgual(anticipacion));
  	return false;
  }
  /**
   * Obtiene el contenido de la producci�n con la posici�n del punto y los s�mbolos de anticipaci�n.
   * @return El contenido de la producci�n con el punto y los s�mbolos de anticipaci�n, en un String.
  **/
  public String toString()
  {
  	String cadena=super.toString();
  	cadena=cadena+"{"+anticipacion+"}";
  	return cadena;
  }
  /**
   * Obtiene el contenido de la producci�n con la posici�n del punto y sin los s�mbolos de anticipaci�n.
   * @return El contenido de la producci�n con el punto y sin los s�mbolos de anticipaci�n, en un String.
  **/
  public String toStringSA()
  {
  	String cadena=super.toString();
  	return cadena;
  }
  /**
   * Muestra por pantalla la producci�n con la posici�n del punto y los simbolos de anticipaci�n.
  **/
  public void debug()
  {System.out.println(toString());}
  /**
   * Muestra por pantalla la producci�n con la posici�n del punto,sin mostrar los simbolos de anticipaci�n.
  **/
  public void debugSA()
  {System.out.println(toStringSA());}
}