package gramatica;
/**
* <b>Descripción</b><br>
* Clase que implementa una producción.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar comodamente con las producciones (parte izquierda y parte derecha) y que permite realizar las opciones básicas con ellas.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Produccion
{
  /**Parte izquierda de la producción.**/
  protected NoTerminal pi;
  /**Parte derecha de la producción.**/
  protected VectorSimbolos pd;
  
   /**
   * Constructor básico de la producción.
   * 
   * @param parte_izquierda Parte izquierda de la producción.
   * @param parte_derecha Parte derecha de la producción.
  **/
  public Produccion(NoTerminal parte_izquierda,VectorSimbolos parte_derecha)
  {
  	pi=parte_izquierda;
  	pd=parte_derecha;
  }
  /**
   * Obtiene la parte izquierda de la producción.
   * @return Simbolo no terminal que forma la parte izquierda de la producción.
  **/
  public NoTerminal obtenerParteIzquierda()
  {return pi;}
  /**
   * Obtiene la parte derecha de la producción.
   * @return Vector de Simbolos que forma la parte derecha de la producción.
  **/
  public VectorSimbolos obtenerParteDerecha()
  {return pd;}
  /**
   * Obtiene el símbolo indicado como parámetro, de la parte derecha de la producción.
   * @param i Posición del símbolo que se quiere obtener.
   * @return Simbolo que ocupa la posicíon indicada en la parte derecha o nullo si no existe.
  **/
  public Simbolo obtenerSimboloParteDerecha(int i)
  {
    if(i<pd.simbolosIntroducidos())
  	  return pd.obtenerSimbolo(i);
  	return null;
  }
  /**
   * Comprueba si la parte derecha es anulable.
   * @return "True" si la parte derecha es anulable, "False" si la parte derecha no es anulable.
  **/
  public boolean esParteDerechaAnulable()
  {return pd.esAnulable();}
  /**
   * Compara si la producción actual es igual a otra pasada como parámetro.
   * @param pr Producción que se compara.
   * @return "True" si las dos producciones son iguales y "False" si no lo son.
  **/
  public boolean esIgual(Produccion pr)
  {
	//Se comprueba que la producción pasada como parámetro no sea nula.
  	if(pr==null)
  	  return false;
  	//Se comprueba si la parte izquierda es igual.
    if(!pr.obtenerParteIzquierda().esIgual(pi))
  	  return false;
  	//Se comprueba si la parte derecha es igual.
    if(!pr.obtenerParteDerecha().esIgual(this.obtenerParteDerecha()))
  	  return false;
  	return true;
  }
  /**
   * Obtiene una copia de la producción.
   * @return Una copia de la producción.
  **/
  public Produccion copiar()
  {return new Produccion((NoTerminal)pi.copiar(),pd.copiar());}
  /**
   * Obtiene el contenido de la producción en un String.
   * @return El contenido de la producción.
  **/
  public String toString()
  {
  	String cadena="";
  	cadena=pi.toString()+"->";
  	for(int i=0;i<pd.simbolosIntroducidos();i++)
  	{
  	  cadena=cadena+pd.obtenerSimbolo(i).toString()+" ";
  	}
  	return cadena;
  }
  /**
   * Muestra por pantalla el contenido de la producción
  **/
  public void debug()
  {System.out.println(toString());}
}