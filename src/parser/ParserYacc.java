package parser;
import gramatica.*;

/**
* <b>Descripci�n</b><br>
* Implementaci�n de la interface IParserYacc.
* <p>
* <b>Detalles</b><br>
* Mediante la implementaci�n de esta clase, se va formando la gram�tica a trav�s de los m�todos indicados en la interface.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/

public class ParserYacc implements IParserYacc
{
  private Gramatica gr;//Gram�tica que se est� dando de alta.
  private static NoTerminal parte_izquierda;//Parte izquierda de la producci�n.
  private static VectorSimbolos parte_derecha;//Parte derecha de la producci�n.
  @SuppressWarnings("unused")
private VectorSimbolos NoDeclarados;
  /**
   * Constructor b�sico que genera una gram�tica.
  **/
  public ParserYacc()
  {gr=new Gramatica();}
  /**
   * Metodo que a�ade un no terminal a la gram�tica.
   * @param nt  No Terminal que se a�ade a la gram�tica.
  **/
  public void setNoTerminal(String nt)
  {gr.insertarNoTerminal(nt);}
  /**
   * Metodo que a�ade un terminal a la gram�tica.
   * @param t  Terminal que se a�ade a la gram�tica.
  **/
  public void setTerminal(String t)
  {gr.insertarTerminal(t);}
  /**
   * Metodo que establece el s�mbolo inicial de la gram�tica.
   * @param si  No Terminal que se establece como simbolo inicial.
  **/
  public void setSimboloInicial(String si)
  {gr.setSimboloInicio(si);}
  /**
   * Metodo que establece la parte izquierda de una producci�n.
   * @param pi  No Terminal que forma la parte izquierda de la producci�n.
  **/
  public void setParteIzquierdaProduccion(String pi)
  {
    //A�ade el no terminal a la gram�tica.
    gr.insertarNoTerminal(pi);
    //Almacena la parte izquierda.
	parte_izquierda=new NoTerminal(pi);
	//Crea un vector de simbolos para almacenar la parte derecha.
	parte_derecha=new VectorSimbolos();
  }
  /**
   * Metodo quea�ade un "Simbolo" en la parte derecha de una producci�n.Comprueba si el s�mbolo se encuentra en los terminales, y si no se encuentra se a�ade a los no terminales.
   * @param pd  No Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaSimboloProduccion(String pd)
  {
    if(gr.estaIntroducidoTerminal(pd))
      setParteDerechaTerminalProduccion(pd);
    else
    {
      setNoTerminal(pd);
      setParteDerechaNoTerminalProduccion(pd);
    }
  }
  /**
   * Metodo que a�ade un "Terminal" en la parte derecha de una producci�n.
   * @param pd Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaTerminalProduccion(String pd)
  {
    if(!gr.estaIntroducidoTerminal(pd))
	  setTerminal(pd);
	parte_derecha.insertarSimbolo(new Terminal(pd));
  }
  /**
   * Metodo que a�ade un "NoTerminal" en la parte derecha de una producci�n.
   * @param pd  No Terminal que forma parte de la parte derecha de la producci�n.
  **/
  public void setParteDerechaNoTerminalProduccion(String pd)
  {parte_derecha.insertarSimbolo(new NoTerminal(pd));}
  /**
   * Metodo que a�ade un "Simbolo Nulo" en la parte derecha de una producci�n.
  **/
  public void setNuloParteDerechaProduccion()
  {parte_derecha.insertarSimbolo(new Nulo());}
  /**
   * Metodo que finaliza la parte derecha actual, y comienza una nueva con la misma parte izquierda.
  **/
  public void orProduccion()
  {
    //Inserta la producci�n almacenada en la gram�tica.
    gr.insertarProduccion(new Produccion(parte_izquierda,parte_derecha));
    //Inicializa la parte derecha, pero mantiene la parte izquierda.
	parte_derecha=new VectorSimbolos();	
  }
  /**
   * Metodo que finaliza la producci�n.
  **/
  public void finProduccion()
  {
    gr.insertarProduccion(new Produccion(parte_izquierda,parte_derecha));
    //Inicializa la parte derecha, para la siguiente producci�n.
    parte_derecha=new VectorSimbolos();
  }
  /**
   * Metodo que obtiene la gram�tica parseada.
   * @return La gram�tica generada.
  **/
  public Gramatica obtenerGramatica()
  {return gr;}
}