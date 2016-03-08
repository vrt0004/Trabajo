package gramatica;
import java.util.Vector;
/**
* <b>Descripci�n</b><br>
* Clase que implementa un Vector para almacenar producciones.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar comodamente con listas de producciones, permitiendo realizar las operaciones b�sicas en ellas (a�adir, borrar, eliminar).<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class VectorProducciones 
{
  //Variable que almacena el vector de producciones.
  private Vector <Produccion> v;
  
  /**
   * Constructor b�sico que crea un vector vac�o de producciones.
  **/
  public VectorProducciones()
  {v=new Vector <Produccion> ();}
  /**
   * Inserta la producci�n en el vector de producciones.
   * @param pr La producci�n que se insertar�.
  **/
  public void insertarProduccion(Produccion pr)
  {v.addElement(pr);}
  /**
   * Inserta la producci�n en el inicio del vector de producciones.
   * @param pr La producci�n que se insertar�.
  **/
  public void insertarInicioProduccion(Produccion pr)
  {v.insertElementAt(pr,0);}
  /**
   * Comprueba si una producci�n se encuentra introducida en el vector de producciones.
   * @param pr Producci�n que se comprueba si esta en el vector de producciones.
   * @return "True" si se encuentra introducida o "False" si no se encuentra introducido. 
  **/
  public boolean estaProduccion(Produccion pr)
  {
  	for(int i=0;i<v.size();i++)
  	{
  	  if(!obtenerProduccion(i).esIgual(pr))
  	    return false;
  	}
  	return true;
  }
  /**
   * Obtiene el n�mero de producciones introducidas en el vector.
   * @return El n�mero de producciones que hay introducidos en el vector de producciones.
  **/
  public int produccionesIntroducidas()
  {return v.size();}
  /**
   * Obtiene la producci�n del vector de producciones en la posici�n que se le indica.
   * @param i Posici�n que ocupa la producci�n que se quiere obtener.
   * @return Si hay una producci�n en la posici�n indicada se devuelve la producci�n, si no existe la producci�n se devuelve NULL. 
  **/
  public Produccion obtenerProduccion(int i)
  {return (Produccion) v.elementAt(i);}
  /**
   * Obtiene las producciones del vector de producciones que tienen una parte izquierda com�n
   * @param nt Parte izquierda que tienen en com�n la producciones.
   * @return "VectorProducciones" con las producciones encontradas.
  **/
  public VectorProducciones obtenerProduccionesIzquierdaIgual(NoTerminal nt)
  {
    VectorProducciones v=new VectorProducciones();
    for(int i=0;i<produccionesIntroducidas();i++)
    {
      if(obtenerProduccion(i).obtenerParteIzquierda().esIgual(nt))
        v.insertarProduccion(obtenerProduccion(i));
    }
    return v;
  }
   /**
   * Obtiene la posici�n que ocupa una producci�n en el vector de producciones.
   * @param prd Producci�n que se quiere buscar
   * @return La posici�n que ocupa la producci�n si se encuentra o -1 si no se encuentra.
  **/
  public int obtenerPosicionProduccion(Produccion prd)
  {
  	for(int i=0;i<v.size();i++)
  	{
      if(prd.esIgual(v.get(i)))
  	    return i;
  	}
  	return -1;
  }
  /**
   * Obtiene el contenido del Vector de producciones en un String.
   * @return El contenido del Vector de producciones.
  **/
  public String toString()
  {
  	String cadena="";
  	for(int i=0;i<v.size();i++)
  	{
  	  cadena=cadena+((Produccion)v.get(i)).toString();
  	  if((i+1)!=v.size())
  	    cadena=cadena+"\n";
  	}
  	return cadena;
  }
  /**
   * Muestra por pantalla el contenido del Vector de producciones.
  **/
  public void debug()
  {System.out.println(toString());}
}