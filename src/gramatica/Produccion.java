package gramatica;
/**
* <b>Descripci�n</b><br>
* Clase que implementa una producci�n.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar comodamente con las producciones (parte izquierda y parte derecha) y que permite realizar las opciones b�sicas con ellas.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class Produccion
{
  /**Parte izquierda de la producci�n.**/
  protected NoTerminal pi;
  /**Parte derecha de la producci�n.**/
  protected VectorSimbolos pd;
  
   /**
   * Constructor b�sico de la producci�n.
   * 
   * @param parte_izquierda Parte izquierda de la producci�n.
   * @param parte_derecha Parte derecha de la producci�n.
  **/
  public Produccion(NoTerminal parte_izquierda,VectorSimbolos parte_derecha)
  {
  	pi=parte_izquierda;
  	pd=parte_derecha;
  }
  /**
   * Obtiene la parte izquierda de la producci�n.
   * @return Simbolo no terminal que forma la parte izquierda de la producci�n.
  **/
  public NoTerminal obtenerParteIzquierda()
  {return pi;}
  /**
   * Obtiene la parte derecha de la producci�n.
   * @return Vector de Simbolos que forma la parte derecha de la producci�n.
  **/
  public VectorSimbolos obtenerParteDerecha()
  {return pd;}
  /**
   * Obtiene el s�mbolo indicado como par�metro, de la parte derecha de la producci�n.
   * @param i Posici�n del s�mbolo que se quiere obtener.
   * @return Simbolo que ocupa la posic�on indicada en la parte derecha o nullo si no existe.
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
   * Compara si la producci�n actual es igual a otra pasada como par�metro.
   * @param pr Producci�n que se compara.
   * @return "True" si las dos producciones son iguales y "False" si no lo son.
  **/
  public boolean esIgual(Produccion pr)
  {
	//Se comprueba que la producci�n pasada como par�metro no sea nula.
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
   * Obtiene una copia de la producci�n.
   * @return Una copia de la producci�n.
  **/
  public Produccion copiar()
  {return new Produccion((NoTerminal)pi.copiar(),pd.copiar());}
  /**
   * Obtiene el contenido de la producci�n en un String.
   * @return El contenido de la producci�n.
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
   * Muestra por pantalla el contenido de la producci�n
  **/
  public void debug()
  {System.out.println(toString());}
}