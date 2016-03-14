package analisis.analisisSintactico.ascendente;
import java.util.Vector;
import java.util.Hashtable;
/**
* <b>Descripción</b><br>
* Clase que implementa un autómata LALR.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa el autómata que se genera en los análisis ascendentes LALR.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class AutomataLALR extends Automata
{
  /**
   * Añade un nuevo nodo al automata LALR.
   * @param cjto Nodo del automata.
  **/
  public void nuevoNodoAutomata(CjtoConfig cjto)
  {
    
    if(!estaNombreNodoAutomata(cjto)&&!estaContenidoNodoAutomata(cjto))
    {
      nodos.add(cjto);
      t_nodos.put(cjto.toStringSA(),nodos.size()-1);     
    }
  }
  /**
   * Comprueba si un automata LALR tiene introducido un nodo con el mismo contenido que otro.
   * @param cjt Nodo que se comprueba.
   * @return "True" si se encuentra introducido o "False" si no se encuentra introducido.
  **/
  public boolean estaContenidoNodoAutomata(CjtoConfig cjt)
  {
    if(t_nodos.get(cjt.toStringSA())==null)
    {
      return false;
	}
    return true;
  }
  /**
   * Obtiene el identificador de un nodo.
   * @param n Nodo del automata LALR.
   * @return el identificador.
  **/
  public int obtenerIdentificadorAutomata(CjtoConfig n)
  {return nodos.get(t_nodos.get(n.toStringSA())).obtenerNumeroCjtoConfig();}
}