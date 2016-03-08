package gramatica;
import java.util.Vector;
/**
* <b>Descripción</b><br>
* Clase que implementa un Vector para almacenar producciones.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar comodamente con listas de producciones, permitiendo realizar las operaciones básicas en ellas (añadir, borrar, eliminar).<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class VectorProducciones 
{
  //Variable que almacena el vector de producciones.
  private Vector <Produccion> v;
  
  /**
   * Constructor básico que crea un vector vacío de producciones.
  **/
  public VectorProducciones()
  {v=new Vector <Produccion> ();}
  /**
   * Inserta la producción en el vector de producciones.
   * @param pr La producción que se insertará.
  **/
  public void insertarProduccion(Produccion pr)
  {v.addElement(pr);}
  /**
   * Inserta la producción en el inicio del vector de producciones.
   * @param pr La producción que se insertará.
  **/
  public void insertarInicioProduccion(Produccion pr)
  {v.insertElementAt(pr,0);}
  /**
   * Comprueba si una producción se encuentra introducida en el vector de producciones.
   * @param pr Producción que se comprueba si esta en el vector de producciones.
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
   * Obtiene el número de producciones introducidas en el vector.
   * @return El número de producciones que hay introducidos en el vector de producciones.
  **/
  public int produccionesIntroducidas()
  {return v.size();}
  /**
   * Obtiene la producción del vector de producciones en la posición que se le indica.
   * @param i Posición que ocupa la producción que se quiere obtener.
   * @return Si hay una producción en la posición indicada se devuelve la producción, si no existe la producción se devuelve NULL. 
  **/
  public Produccion obtenerProduccion(int i)
  {return (Produccion) v.elementAt(i);}
  /**
   * Obtiene las producciones del vector de producciones que tienen una parte izquierda común
   * @param nt Parte izquierda que tienen en común la producciones.
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
   * Obtiene la posición que ocupa una producción en el vector de producciones.
   * @param prd Producción que se quiere buscar
   * @return La posición que ocupa la producción si se encuentra o -1 si no se encuentra.
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