package gramatica;
/**
* <b>Descripción</b><br>
* Clase que implementa una producción punto.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones en las que se necesita conocer el número de símbolos de la parte derecha  que se han analizado, utilizadas en el análisis ascendente.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class ProduccionPTO extends Produccion
{
  private int posPTO;//Variable que almacena la posición del punto.
  /**
   * Constructor básico de producción con punto.
   * @param parte_izquierda Parte izquierda de la producción.
   * @param parte_derecha Parte derecha de la producción.
   * @param punto Número de símbolos de la parte derecha que se han analizado.
  **/
  public ProduccionPTO(NoTerminal parte_izquierda,VectorSimbolos parte_derecha,int punto)
  {
    super(parte_izquierda,parte_derecha);
	posPTO=punto;	
  }
  /**
   * Obtiene el número de elementos que se han analizado (la posición del punto).
   * @return Devuelve la posición del último símbolo analizado de la parte derecha.
  **/
  public int posicionPunto()
  {return posPTO;}
  /**
   * Obtiene el siguiente símbolo en analizar.
   * @return Siguiente simbolo que se analizará.
  **/
  public Simbolo simboloPunto()
  {return obtenerParteDerecha().obtenerSimbolo(posicionPunto());}
  /**
   * Compara si la producción con punto es igual a otra pasada como parámetro del mismo tipo.
   * @param pr Producción punto que se compara.
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
   * Obtiene el contenido de la producción con la posición del punto.
   * @return El contenido de la producción con el punto en un String.
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
   * Muestra por pantalla la producción con la posición del punto.
  **/
  public void debug()
  {toString();}
}