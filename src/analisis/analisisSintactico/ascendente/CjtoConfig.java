package analisis.analisisSintactico.ascendente;
import gramatica.*;
/**
* <b>Descripción</b><br>
* Clase que implementa un conjunto de items.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa los conjuntos de items utilizados en los análisis ascendentes.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class CjtoConfig 
{
  private int numero;//Número que identifica el conjunto de items.
  private VectorProducciones conjunto;//Vector donde se almacenan las producciones.
  /**
   * Constructor básico.
   * @param n Número del conjunto.
  **/
  public CjtoConfig(int n)
  {
  	numero=n;
  	conjunto=new VectorProducciones();
  }
  /**
   * Introduce una producción en el conjunto de configuración.
   * @param ppto La producción que se introducirá.
  **/
  public void insertarProduccionCjtoConfig(ProduccionPTO ppto)
  {conjunto.insertarProduccion(ppto);}
  /**
   * Obtiene una producción del conjunto de configuración.
   * @param i El número de la producción que se quiere obtener.
   * @return La producción indicada.
  **/
  public ProduccionPTO obtenerProduccionCjtoConfig(int i)
  {return (ProduccionPTO)conjunto.obtenerProduccion(i);}
  /**
   * Obtiene todas las producciones del conjunto de configuración.
   * @return Las producciones del conjunto.
  **/
  public VectorProducciones obtenerProduccionesCjtoConfig()
  {return conjunto;}
  /**
   * Obtiene el identificador del conjunto de configuración.
   * @return El identificador.
  **/
  public int obtenerNumeroCjtoConfig()
  {return numero;}
  /**
   * Modifica el identificador del conjunto de configuración.
   * @param n El nuevo identificador.
  **/
  public void modificarNumeroCjtoConfig(int n)
  {numero=n;}
  /**
   * Obtiene el número de producciones del conjunto de configuración.
   * @return El número de producciones.
  **/
  public int numeroElementosCjtoConfig()
  {return conjunto.produccionesIntroducidas();}
  /**
   * Obtiene si una producción se encuentra en conjunto de configuración o no.
   * @param p Producción que se busca.
   * @return "True" si se encuentra la producción o "False" si no se encuentra.
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
   * @param nuevo El conjunto con el que se agrupará el conjunto actual.
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
   * Obtiene el contenido de un conjunto de configuración en un String.
   * @return El contenido del conjunto de configuración.
  **/
  public String toString()
  {
    String cadena="";  	  	
    for(int i=0;i<conjunto.produccionesIntroducidas();i++)
    {cadena=cadena+"\n\t"+conjunto.obtenerProduccion(i).toString();}
    return cadena;
  }
  /**
   * Obtiene el contenido de un conjunto de configuración, sin la anticipación de las producciones, en un String.
   * @return El contenido del conjunto de configuración.
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
   * Muestra el contenido del conjunto de configuración por pantalla.
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