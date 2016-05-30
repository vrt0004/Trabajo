package analisis.analisisSintactico.ascendente;
import java.util.Vector;
import java.util.Hashtable;
/**
* <b>Descripción</b><br>
* Clase que implementa un autómata.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa el autómata que se genera en los análisis ascendentes LR y SLR.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Automata
{ 
  /**Vector donde se introducen los nodos.
  **/
  public Vector <CjtoConfig> nodos;
  /**Tabla donde se introduce el contenido de los nodos.
  **/
  public Hashtable <String,Integer> t_nodos;
  /**Tabla donde se introducen los nodos que tiene cada arco.
  **/
  public Vector <Arco> arcos ;
  /**
   * Constructor básico de autómata.
  **/
  public Automata()
  {
    nodos=new  Vector <CjtoConfig> ();
	t_nodos=new Hashtable <String,Integer> ();
	arcos=new Vector <Arco>();
  }
  /**
   * Añade un nuevo nodo al automata.
   * @param cjto Nodo del automata.
  **/
  public void nuevoNodoAutomata(CjtoConfig cjto)
  {
    if(!estaNombreNodoAutomata(cjto)&&!estaContenidoNodoAutomata(cjto))
    {
      nodos.add(cjto);
      t_nodos.put(cjto.toString(),nodos.size()-1);    
    }
  }
  /**
   * Añade un nuevo arco al automata.
   * @param origen Nodo origen del arco.
   * @param destino Nodo destino del arco.
   * @param etiqueta Etiqueta del arco.
  **/
  public void nuevoArcoAutomata(int origen, int destino, String etiqueta)
  {arcos.add(new Arco(origen,destino,etiqueta));}
  /**
   * Comprueba si un automata tiene introducido un nodo con el mismo identificador.
   * @param n Nodo que se comprueba.
   * @return "True" si se encuentra introducido o "False" si no se encuentra introducido.
  **/
  protected boolean estaNombreNodoAutomata(CjtoConfig n)
  {
    for(int i=0;i<nodos.size();i++)
	{
	  if(n.obtenerNumeroCjtoConfig()==nodos.get(i).obtenerNumeroCjtoConfig())
	    return true;
	}
	return false;
  }
  /**
   * Comprueba si un automata tiene introducido un nodo con el mismo contenido que otro.
   * @param cjt Nodo que se comprueba.
   * @return "True" si se encuentra introducido o "False" si no se encuentra introducido.
  **/
  public boolean estaContenidoNodoAutomata(CjtoConfig cjt)
  {
  	if(t_nodos.get(cjt.toString())==null)
  	  return false;
    return true;
  }
  /**
   * Obtiene un (nodo) conjunto de configuración de un autómata.
   * @param i Nodo que se quiere obtener.
   * @return El conjunto de configuración.
  **/
  public CjtoConfig obtenerNodoAutomata(int i)
  {return nodos.get(i);}
  
  /**
   * Obtiene el número de nodos que tiene el autómata.
   * @return El número de nodos.
  **/
  public int numeroNodosAutomata()
  {return nodos.size();}
  /**
   * Obtiene el número de arcos que tiene el autómata.
   * @return El número de arcos.
  **/
  public int numeroArcosAutomata()
  {return arcos.size();}
  /**
   * Obtiene el identificador de un nodo.
   * @param n Nodo del automata.
   * @return el identificador.
  **/
  public int obtenerIdentificadorAutomata(CjtoConfig n)
  {return nodos.get(t_nodos.get(n.toString())).obtenerNumeroCjtoConfig();}
  /**
   * Obtiene el arco indicado comp parametro.
   * @param i Nodo del automata.
   * @return el arco indicado.
  **/
  public Arco obtenerArcoEnAutomata(int i)
  {return ((Arco) arcos.get(i));}
  /**
   * Obtiene los arcos de los automatas que tienen un origen y una etiqueta pasada como parametro.
   * @param origen Nodo de origen.
   * @param etiqueta Etiqueta del arco
   * @return Vector de los arcos con el origen y la etiqueta indicados.
  **/
  @SuppressWarnings("rawtypes")
public Vector obtenerArcosAutomata(int origen,String etiqueta)
  {
  	Vector <Integer> v=new Vector <Integer> ();
  	for(int i=0;i<arcos.size();i++)
  	{
  	  Arco arc=arcos.get(i);	
  	  if((etiqueta.compareTo(arc.obtenerEtiquetaArco())==0)&&(arc.obtenerOrigenArco()==origen))
  	    v.add(arc.obtenerDestinoArco());
    }
    if(v.size()==0)
      return null;
    return v;
  }
}