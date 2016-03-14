package analisis.analisisSintactico.ascendente;
import gramatica.*;
/**
* <b>Descripci�n</b><br>
* Clase que implementa un conjunto de items.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa los conjuntos de items utilizados en los an�lisis ascendentes.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class CjtoConfig 
{
  private int numero;//N�mero que identifica el conjunto de items.
  private VectorProducciones conjunto;//Vector donde se almacenan las producciones.
  /**
   * Constructor b�sico.
   * @param n N�mero del conjunto.
  **/
  public CjtoConfig(int n)
  {
  	numero=n;
  	conjunto=new VectorProducciones();
  }
  /**
   * Introduce una producci�n en el conjunto de configuraci�n.
   * @param ppto La producci�n que se introducir�.
  **/
  public void insertarProduccionCjtoConfig(ProduccionPTO ppto)
  {conjunto.insertarProduccion(ppto);}
  /**
   * Obtiene una producci�n del conjunto de configuraci�n.
   * @param i El n�mero de la producci�n que se quiere obtener.
   * @return La producci�n indicada.
  **/
  public ProduccionPTO obtenerProduccionCjtoConfig(int i)
  {return (ProduccionPTO)conjunto.obtenerProduccion(i);}
  /**
   * Obtiene todas las producciones del conjunto de configuraci�n.
   * @return Las producciones del conjunto.
  **/
  public VectorProducciones obtenerProduccionesCjtoConfig()
  {return conjunto;}
  /**
   * Obtiene el identificador del conjunto de configuraci�n.
   * @return El identificador.
  **/
  public int obtenerNumeroCjtoConfig()
  {return numero;}
  /**
   * Modifica el identificador del conjunto de configuraci�n.
   * @param n El nuevo identificador.
  **/
  public void modificarNumeroCjtoConfig(int n)
  {numero=n;}
  /**
   * Obtiene el n�mero de producciones del conjunto de configuraci�n.
   * @return El n�mero de producciones.
  **/
  public int numeroElementosCjtoConfig()
  {return conjunto.produccionesIntroducidas();}
  /**
   * Obtiene si una producci�n se encuentra en conjunto de configuraci�n o no.
   * @param p Producci�n que se busca.
   * @return "True" si se encuentra la producci�n o "False" si no se encuentra.
  **/
  public boolean estaProduccionCjtoConfig(ProduccionPTO p)
  {
  	boolean resultado=false;
  	for(int i=0;i<conjunto.produccionesIntroducidas();i++)
  	{
  	  if(((ProduccionPTO)conjunto.obtenerProduccion(i)).esIgual(p))
  	    return true;
  	}
  	return false;
  }
  /**
   * Agrupa el conjunto de configuraciones actual con otro.
   * @param nuevo El conjunto con el que se agrupar� el conjunto actual.
  **/
  public void agruparCjtoConfig(CjtoConfig nuevo)
  {
  	for(int i=0;i<nuevo.numeroElementosCjtoConfig();i++)
  	{
  	  ProduccionPTOCA pto_ca_actual=(ProduccionPTOCA)obtenerProduccionCjtoConfig(i);
  	  ProduccionPTOCA pto_ca_nuevo=(ProduccionPTOCA)nuevo.obtenerProduccionCjtoConfig(i);
  	  pto_ca_actual.obtenerAnticipacion().fusionar(pto_ca_nuevo.obtenerAnticipacion());
  	}  	
  }
  /**Obtiene la produccion PTO de una produccion
  **@param p La produccion que se busca.
  **@return La produccio PTO que esta en el conjunto.
  **/
  public Produccion obtenerProduccionCjtoConfig(Produccion p)
  {
  	for(int i=0;i<conjunto.produccionesIntroducidas();i++)
  	{
  	  if(((ProduccionPTO)conjunto.obtenerProduccion(i)).esIgual(p))
  	    return (ProduccionPTO)conjunto.obtenerProduccion(i);
  	}
  	return null;
  }
  /**
   * Obtiene el contenido de un conjunto de configuraci�n en un String.
   * @return El contenido del conjunto de configuraci�n.
  **/
  public String toString()
  {
    String cadena="";  	  	
    for(int i=0;i<conjunto.produccionesIntroducidas();i++)
    {cadena=cadena+"\n\t"+conjunto.obtenerProduccion(i).toString();}
    return cadena;
  }
  /**
   * Obtiene el contenido de un conjunto de configuraci�n, sin la anticipaci�n de las producciones, en un String.
   * @return El contenido del conjunto de configuraci�n.
  **/
  public String toStringSA()
  {
    String cadena="";  	  	
    for(int i=0;i<conjunto.produccionesIntroducidas();i++)
    {
    	cadena=cadena+"\n\t"+((ProduccionPTOCA)conjunto.obtenerProduccion(i)).toStringSA();
    }
    return cadena;
  }
  /**
   * Muestra el contenido del conjunto de configuraci�n por pantalla.
  **/
  public void debug()
  {System.out.println(toString());}
  /**
  **Obtiene un dodigo que identifica al conjunto de configuraciones en el automata.
  **@return El codigo generado.
  **/
  public String codigo()
  {

    String cadena="";  	  	
    for(int i=0;i<conjunto.produccionesIntroducidas();i++)
    {
    	cadena=cadena+"\\n"+conjunto.obtenerProduccion(i).toString();
    }
    return cadena;
  }
}