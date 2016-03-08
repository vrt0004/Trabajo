package parser;
import gramatica.*;
/**
* <b>Descripci�n</b><br>
* Interface que representa las opciones b�sicas para crear una gram�tica.
* <p>
* <b>Detalles</b><br>
* Mediante la implementaci�n de esta interface se va formando la gram�tica, implementando los m�todos que se especifican en esta interface.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public interface IParserYacc
{
  /**
   * Metodo que a trav�s de su implementaci�n a�ade un terminal a la gram�tica.
   * @param t  Terminal que se a�ade a la gram�tica.
  **/
  public void setTerminal(String t);
  /**
   * Metodo que a trav�s de su implementaci�n a�ade un no terminal a la gram�tica.
   * @param nt  No Terminal que se a�ade a la gram�tica.
  **/
  public void setNoTerminal(String nt);
  /**
   * Metodo que a trav�s de su implementaci�n establece el s�mbolo inicial de la gram�tica.
   * @param si  No Terminal que se establece como simbolo inicial.
  **/
  public void setSimboloInicial(String si);
  /**
   * Metodo que a trav�s de su implementaci�n establece la parte izquierda de una producci�n.
   * @param pi  No Terminal que forma la parte izquierda de la producci�n.
  **/
  public void setParteIzquierdaProduccion(String pi);
  /**
   * Metodo que a trav�s de su implementaci�n a�ade un "Simbolo" en la parte derecha de una producci�n.
   * @param pd  No Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaSimboloProduccion(String pd);
   /**
   * Metodo que a trav�s de su implementaci�n a�ade un "Terminal" en la parte derecha de una producci�n.
   * @param pd  No Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaTerminalProduccion(String pd);
   /**
   * Metodo que a trav�s de su implementaci�n a�ade un "NoTerminal" en la parte derecha de una producci�n.
   * @param pd  No Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaNoTerminalProduccion(String pd);
  /**
   * Metodo que a trav�s de su implementaci�n a�ade un "Simbolo Nulo" en la parte derecha de una producci�n.
  **/
  public void setNuloParteDerechaProduccion();
  /**
   * Metodo que a trav�s de su implementaci�n finaliza la parte derecha actual, y comienza una nueva con la misma parte izquierda.
  **/
  public void orProduccion();
  /**
   * Metodo que a trav�s de su implementaci�n finaliza la producci�n.
  **/
  public void finProduccion();
  /**
   * Metodo que a trav�s de su implementaci�n obtiene la gram�tica parseada.
   * @return La gram�tica generada.
  **/
  public Gramatica obtenerGramatica();
}