package gramatica;
/**
* <b>Descripci�n</b><br>
* Clase que implementa una producci�n punto.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones en las que se necesita conocer el n�mero de s�mbolos de la parte derecha  que se han analizado, utilizadas en el an�lisis ascendente.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class ProduccionPTO extends Produccion
{
  private int posPTO;//Variable que almacena la posici�n del punto.
  /**
   * Constructor b�sico de producci�n con punto.
   * @param parte_izquierda Parte izquierda de la producci�n.
   * @param parte_derecha Parte derecha de la producci�n.
   * @param punto N�mero de s�mbolos de la parte derecha que se han analizado.
  **/
  public ProduccionPTO(NoTerminal parte_izquierda,VectorSimbolos parte_derecha,int punto)
  {
    super(parte_izquierda,parte_derecha);
	posPTO=punto;	
  }
  /**
   * Obtiene el n�mero de elementos que se han analizado (la posici�n del punto).
   * @return Devuelve la posici�n del �ltimo s�mbolo analizado de la parte derecha.
  **/
  public int posicionPunto()
  {return posPTO;}
  /**
   * Obtiene el siguiente s�mbolo en analizar.
   * @return Siguiente simbolo que se analizar�.
  **/
  public Simbolo simboloPunto()
  {return obtenerParteDerecha().obtenerSimbolo(posicionPunto());}
  /**
   * Compara si la producci�n con punto es igual a otra pasada como par�metro del mismo tipo.
   * @param pr Producci�n punto que se compara.
   * @return "True" si las dos producciones con punto son iguales y "False" si no lo son.
  **/
  public boolean esIgual(ProduccionPTO pr)
  {
    if(super.esIgual(pr))
  	  return (pr.posicionPunto()==this.posicionPunto());
  	return false;
  }
  /**
   * Comprueba si se han analizado todos los puntos de la parte derecha.
   * @return "True" si se han analizado todos, o "False" en casa contrario.
  **/
  public boolean haySimboloDerechaPunto()
  {
	  if(posicionPunto()<pd.simbolosIntroducidos())
	  {
		  if(simboloPunto().esNulo())
		    return false;
	    
		  return true;
	    
      }
	  return false;
  }
  /**
   * Obtiene el contenido de la producci�n con la posici�n del punto.
   * @return El contenido de la producci�n con el punto en un String.
  **/
  public String toString()
  {
  	String cadena="";
  	cadena=pi.toString()+"->";
  	for(int i=0;i<=pd.simbolosIntroducidos();i++)
  	{
  	  if(i==posPTO)
  	    cadena=cadena+".";
  	  if(i<pd.simbolosIntroducidos())
  	    cadena=cadena+pd.obtenerSimbolo(i).toString()+" ";
  	}
  	return cadena;
  }
  /**
   * Muestra por pantalla la producci�n con la posici�n del punto.
  **/
  public void debug()
  {toString();}
}