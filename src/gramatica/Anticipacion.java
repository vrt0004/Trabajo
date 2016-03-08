package gramatica;
/**
* <b>Descripción</b><br>
* Clase que implementa el símbolo de anticipación.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones que utilizan símbolos de anticipación, utilizadas en el análisis ascendente.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Anticipacion
{
  //Símbolos de anticipación.
  private VectorSimbolos anticipacion;
  
  /**
   * Constructor básico de los simbolos de anticipación.
  **/
  public Anticipacion()
  {anticipacion=new VectorSimbolos();}	
  /**
   * Establece los símbolos de anticipación a través de un vector de símbolos.
   * @param vs Establece los símbolos de anticipación.
  **/
  public void setAnticipacion(VectorSimbolos vs)
  {anticipacion=vs;}
  /**
   * Obtiene el número de símbolos de los que se compone la anticipación.
   * @return El número de elementos que tiene la anticipación.
  **/
  public int numeroElementosAnticipacion()
  {return anticipacion.simbolosIntroducidos();}
  /**
   * Introduce un nuevo símbolo en la anticipación.
   * @param s El símbolo que se quiere introducir en la anticipación.
  **/
  public void insertarSimboloAnticipacion(Simbolo s)
  {anticipacion.insertarSimbolo(s);}
  /**
   * Obtiene el símbolo de anticipación en una determinada posición.
   * @param n La posición del símbolo de anticipación 
   * @return El símbolo de anticipación en la posición indicada, si no existe null. 
  **/
  public Simbolo obtenerSimboloAnticipacion(int n)
  {return anticipacion.obtenerSimbolo(n);}
  /**
   * Fusiona la anticipación actual con una nueva anticipación.
   * @param atcp La nueva anticipación con la que se fusionará la actual.
  **/
  public void fusionar(Anticipacion atcp)
  {
  	for(int i=0;i<atcp.numeroElementosAnticipacion();i++)
  	{
  	  if(!anticipacion.estaSimbolo(atcp.obtenerSimboloAnticipacion(i)))
  	    anticipacion.insertarSimbolo(atcp.obtenerSimboloAnticipacion(i));  
  	}
  }
  /**
   * Obtiene una copia de la anticipación.
   * @return Una copia de la anticipación.
  **/
  public Anticipacion copiar()
  {
  	Anticipacion nuevo=new Anticipacion();
  	nuevo.anticipacion=anticipacion.copiar();
  	return nuevo;
  }
  /**
   * Compara si la anticipación actual y "at" son iguales.
   * @param at Simbolo que se comparara con el simbolo actual.
   * @return "True" si las dos anticipaciones son iguales o "False" si son distintas.
  **/
  public boolean esIgual(Anticipacion at)
  {return at.anticipacion.esIgual(anticipacion);}
  /**
   * Obtiene el contenido de la anticipación en un String.
   * @return El contenido de la anticipación.
  **/
  public String toString()
  {return anticipacion.toString();}
  /**
   * Muestra por pantalla el contenido de la anticipación.
  **/
  public void debug()
  {System.out.println(toString());}
}