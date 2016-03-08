package parser;
import gramatica.*;
/**
* <b>Descripción</b><br>
* Interface que representa las opciones básicas para crear una gramática.
* <p>
* <b>Detalles</b><br>
* Mediante la implementación de esta interface se va formando la gramática, implementando los métodos que se especifican en esta interface.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public interface IParserYacc
{
  /**
   * Metodo que a través de su implementación añade un terminal a la gramática.
   * @param t  Terminal que se añade a la gramática.
  **/
  public void setTerminal(String t);
  /**
   * Metodo que a través de su implementación añade un no terminal a la gramática.
   * @param nt  No Terminal que se añade a la gramática.
  **/
  public void setNoTerminal(String nt);
  /**
   * Metodo que a través de su implementación establece el símbolo inicial de la gramática.
   * @param si  No Terminal que se establece como simbolo inicial.
  **/
  public void setSimboloInicial(String si);
  /**
   * Metodo que a través de su implementación establece la parte izquierda de una producción.
   * @param pi  No Terminal que forma la parte izquierda de la producción.
  **/
  public void setParteIzquierdaProduccion(String pi);
  /**
   * Metodo que a través de su implementación añade un "Simbolo" en la parte derecha de una producción.
   * @param pd  No Terminal que forma parte de la parte derecha de la producción.
  **/
  public void setParteDerechaSimboloProduccion(String pd);
   /**
   * Metodo que a través de su implementación añade un "Terminal" en la parte derecha de una producción.
   * @param pd  No Terminal que forma parte de la parte derecha de la producción.
  **/
  public void setParteDerechaTerminalProduccion(String pd);
   /**
   * Metodo que a través de su implementación añade un "NoTerminal" en la parte derecha de una producción.
   * @param pd  No Terminal que forma parte de la parte derecha de la producción.
  **/
  public void setParteDerechaNoTerminalProduccion(String pd);
  /**
   * Metodo que a través de su implementación añade un "Simbolo Nulo" en la parte derecha de una producción.
  **/
  public void setNuloParteDerechaProduccion();
  /**
   * Metodo que a través de su implementación finaliza la parte derecha actual, y comienza una nueva con la misma parte izquierda.
  **/
  public void orProduccion();
  /**
   * Metodo que a través de su implementación finaliza la producción.
  **/
  public void finProduccion();
  /**
   * Metodo que a través de su implementación obtiene la gramática parseada.
   * @return La gramática generada.
  **/
  public Gramatica obtenerGramatica();
}