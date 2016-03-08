package gramatica;
/**
* <b>Descripci�n</b><br>
* Clase que implementa el s�mbolo de anticipaci�n.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar con las producciones que utilizan s�mbolos de anticipaci�n, utilizadas en el an�lisis ascendente.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class Anticipacion
{
  //S�mbolos de anticipaci�n.
  private VectorSimbolos anticipacion;
  
  /**
   * Constructor b�sico de los simbolos de anticipaci�n.
  **/
  public Anticipacion()
  {anticipacion=new VectorSimbolos();}	
  /**
   * Establece los s�mbolos de anticipaci�n a trav�s de un vector de s�mbolos.
   * @param vs Establece los s�mbolos de anticipaci�n.
  **/
  public void setAnticipacion(VectorSimbolos vs)
  {anticipacion=vs;}
  /**
   * Obtiene el n�mero de s�mbolos de los que se compone la anticipaci�n.
   * @return El n�mero de elementos que tiene la anticipaci�n.
  **/
  public int numeroElementosAnticipacion()
  {return anticipacion.simbolosIntroducidos();}
  /**
   * Introduce un nuevo s�mbolo en la anticipaci�n.
   * @param s El s�mbolo que se quiere introducir en la anticipaci�n.
  **/
  public void insertarSimboloAnticipacion(Simbolo s)
  {anticipacion.insertarSimbolo(s);}
  /**
   * Obtiene el s�mbolo de anticipaci�n en una determinada posici�n.
   * @param n La posici�n del s�mbolo de anticipaci�n 
   * @return El s�mbolo de anticipaci�n en la posici�n indicada, si no existe null. 
  **/
  public Simbolo obtenerSimboloAnticipacion(int n)
  {return anticipacion.obtenerSimbolo(n);}
  /**
   * Fusiona la anticipaci�n actual con una nueva anticipaci�n.
   * @param atcp La nueva anticipaci�n con la que se fusionar� la actual.
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
   * Obtiene una copia de la anticipaci�n.
   * @return Una copia de la anticipaci�n.
  **/
  public Anticipacion copiar()
  {
  	Anticipacion nuevo=new Anticipacion();
  	nuevo.anticipacion=anticipacion.copiar();
  	return nuevo;
  }
  /**
   * Compara si la anticipaci�n actual y "at" son iguales.
   * @param at Simbolo que se comparara con el simbolo actual.
   * @return "True" si las dos anticipaciones son iguales o "False" si son distintas.
  **/
  public boolean esIgual(Anticipacion at)
  {return at.anticipacion.esIgual(anticipacion);}
  /**
   * Obtiene el contenido de la anticipaci�n en un String.
   * @return El contenido de la anticipaci�n.
  **/
  public String toString()
  {return anticipacion.toString();}
  /**
   * Muestra por pantalla el contenido de la anticipaci�n.
  **/
  public void debug()
  {System.out.println(toString());}
}