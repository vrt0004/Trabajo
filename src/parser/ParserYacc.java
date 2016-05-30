package parser;
import gramatica.*;

/**
* <b>Descripción</b><br>
* Implementación de la interface IParserYacc.
* <p>
* <b>Detalles</b><br>
* Mediante la implementación de esta clase, se va formando la gramática a través de los métodos indicados en la interface.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/

public class ParserYacc implements IParserYacc
{
  private Gramatica gr;//Gramática que se está dando de alta.
  private static NoTerminal parte_izquierda;//Parte izquierda de la producción.
  private static VectorSimbolos parte_derecha;//Parte derecha de la producción.
  @SuppressWarnings("unused")
private VectorSimbolos NoDeclarados;
  /**
   * Constructor básico que genera una gramática.
  **/
  public ParserYacc()
  {gr=new Gramatica();}
  /**
   * Metodo que añade un no terminal a la gramática.
   * @param nt  No Terminal que se añade a la gramática.
  **/
  public void setNoTerminal(String nt)
  {gr.insertarNoTerminal(nt);}
  /**
   * Metodo que añade un terminal a la gramática.
   * @param t  Terminal que se añade a la gramática.
  **/
  public void setTerminal(String t)
  {gr.insertarTerminal(t);}
  /**
   * Metodo que establece el símbolo inicial de la gramática.
   * @param si  No Terminal que se establece como simbolo inicial.
  **/
  public void setSimboloInicial(String si)
  {gr.setSimboloInicio(si);}
  /**
   * Metodo que establece la parte izquierda de una producción.
   * @param pi  No Terminal que forma la parte izquierda de la producción.
  **/
  public void setParteIzquierdaProduccion(String pi)
  {
    //Añade el no terminal a la gramática.
    gr.insertarNoTerminal(pi);
    //Almacena la parte izquierda.
	parte_izquierda=new NoTerminal(pi);
	//Crea un vector de simbolos para almacenar la parte derecha.
	parte_derecha=new VectorSimbolos();
  }
  /**
   * Metodo queañade un "Simbolo" en la parte derecha de una producción.Comprueba si el símbolo se encuentra en los terminales, y si no se encuentra se añade a los no terminales.
   * @param pd  No Terminal que forma parte de la parte derecha de la producción.
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
   * Metodo que añade un "Terminal" en la parte derecha de una producción.
   * @param pd Terminal que forma parte de la parte derecha de la producción.
  **/
  public void setParteDerechaTerminalProduccion(String pd)
  {
    if(!gr.estaIntroducidoTerminal(pd))
	  setTerminal(pd);
	parte_derecha.insertarSimbolo(new Terminal(pd));
  }
  /**
   * Metodo que añade un "NoTerminal" en la parte derecha de una producción.
   * @param pd  No Terminal que forma parte de la parte derecha de la producción.
  **/
  public void setParteDerechaNoTerminalProduccion(String pd)
  {parte_derecha.insertarSimbolo(new NoTerminal(pd));}
  /**
   * Metodo que añade un "Simbolo Nulo" en la parte derecha de una producción.
  **/
  public void setNuloParteDerechaProduccion()
  {parte_derecha.insertarSimbolo(new Nulo());}
  /**
   * Metodo que finaliza la parte derecha actual, y comienza una nueva con la misma parte izquierda.
  **/
  public void orProduccion()
  {
    //Inserta la producción almacenada en la gramática.
    gr.insertarProduccion(new Produccion(parte_izquierda,parte_derecha));
    //Inicializa la parte derecha, pero mantiene la parte izquierda.
	parte_derecha=new VectorSimbolos();	
  }
  /**
   * Metodo que finaliza la producción.
  **/
  public void finProduccion()
  {
    gr.insertarProduccion(new Produccion(parte_izquierda,parte_derecha));
    //Inicializa la parte derecha, para la siguiente producción.
    parte_derecha=new VectorSimbolos();
  }
  /**
   * Metodo que obtiene la gramática parseada.
   * @return La gramática generada.
  **/
  public Gramatica obtenerGramatica()
  {return gr;}
}