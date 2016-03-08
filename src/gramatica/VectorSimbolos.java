package gramatica;
import java.util.Vector;
/**
* <b>Descripci�n</b><br>
* Clase que implementa un Vector para almacenar s�mbolos.
* <p>
* <b>Detalles</b><br>
* Clase que permite trabajar comodamente con listas de s�mbolos, permitiendo realizar las operaciones b�sicas en ellas (a�adir, borrar, eliminar).<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class VectorSimbolos
{
  //Variable que almacena el vector de simbolos.
  private Vector <Simbolo> v;
  
  /**
   * Constructor b�sico que crea un vector vac�o de s�mbolos.
  **/
  public VectorSimbolos()
  {v=new Vector <Simbolo> ();}
  /**
   * Inserta el simbolo en el vector.
   * @param s Simbolo que se introduce en el vector de simbolos.
  **/
  public void insertarSimbolo(Simbolo s)
  {v.addElement(s);}
  /**
   * Comprueba si un s�mbolo se encuentra introducido en el vector:se comprueba tanto el tipo como el contenido.
   * @param s Simbolo que se comprueba si esta en el vector de s�mbolos.
   * @return "True" si se encuentra introducido o "False" si no se encuentra introducido. 
  **/
  public boolean estaSimbolo(Simbolo s)
  {
    for(int i=0;i<v.size();i++)
    {
	  //Si se encuentra el simbolo se devuelve "True".
      if(((Simbolo)v.elementAt(i)).esIgual(s))
        return true;
    }
    //Si no se encuentra se devuelve "False".
    return false;
  }
  /**
   * Obtiene el n�mero de s�mbolos introducidos en el vector.
   * @return El n�mero de s�mbolos que hay introducidos en el vector.
  **/
  public int simbolosIntroducidos()
  {return v.size();}
  /**
   * Obtiene el s�mbolo del vector de s�mbolos en la posici�n que se le indica.
   * @param i Posici�n que ocupa el s�mbolo que se quiere obtener.
   * @return Si hay un s�mbolo en la posici�n introducida se devuelve el s�mbolo, si no existe el s�mbolo se devuelve NULL. 
  **/
  public Simbolo obtenerSimbolo(int i)
  {
    if(i>=v.size())
  	  return null;  
  	return (Simbolo) v.elementAt(i);
  }
  /**
   * Se elimina el s�mbolo del vector de s�mbolos en la posici�n que se le indica.
   * @param i Posici�n que ocupa el s�mbolo que se quiere eliminar.
   * @return "True" si se ha podido eliminar o "False" si no se ha podido eliminar.
  **/
  public boolean eliminarSimbolo(int i)
  {return v.remove(v.elementAt(i));}
  
  /**
   * Se elimina el s�mbolo que se le indica como par�metro del vector de s�mbolos.
   * @param s Simbolo que se quiere eliminar.
   * @return "True" si se ha podido eliminar o "False" si no se ha podido eliminar.
  **/
  public boolean eliminarSimbolo(Simbolo s)
  {
    for(int i=0;i<v.size();i++)
  	{
      if(((Simbolo)v.elementAt(i)).esIgual(s))
  	  {return v.remove(v.elementAt(i));}
  	}
  	return false;
  }
  /**
   * Obtiene una copia del vector de simbolos
   * @return Una copia del vector de simbolos.
  **/
  public VectorSimbolos copiar()
  {
	//Se crea un nuevo vector de simbolos.
    VectorSimbolos nuevo=new VectorSimbolos();
    //Se crea una copia de cada uno de los elementos y se introduce al nuevo vector.
  	for(int i=0;i<v.size();i++)
  	  nuevo.insertarSimbolo(((Simbolo)v.elementAt(i)).copiar());
  	return nuevo;
  }
  /**
   * Compara si el vector de simbolos actual es igual a otro pasado por par�metros.
   * @param vs Vector de s�mbolos que se compara.
   * @return "True" si los dos vectores de simbolos son iguales y "False" si no lo son.
  **/
  public boolean esIgual(VectorSimbolos vs)
  {
	//Se comprueba que no es nulo el valor.
	if(vs==null)
  	  return false;
  	//Si no hay el mismo n�mero de elementos se devuelve "False"
  	if(vs.simbolosIntroducidos()!=v.size())
	  return false; 
	//Se comprueba si cada simbolo del vector origen se encuentra en el vector "vs"
  	for(int i=0;i<v.size()&&i<vs.simbolosIntroducidos();i++)
  	{
  	  //if(!((Simbolo)v.get(i)).esIgual(vs.obtenerSimbolo(i)))
  	  if(!estaSimbolo((Simbolo)vs.obtenerSimbolo(i)))
  	    return false;
  	}
  	return true;
  }
   /**
   * Comprueba si el vector de simbolos es anulable (Todos los simbolos son nulos, o no tiene el elemento vacio).
   * @return "True" si es anulable, "False" si no lo es.
  **/
  public boolean esAnulable()
  {
	//Modificaci�n 12-12-07
    if(simbolosIntroducidos()==0)
  	  return true;
  	for(int i=0;i<simbolosIntroducidos();i++)
    {
      if(!obtenerSimbolo(i).esNulo())
  	    return false;
     }
	 return true;
  }
  /**
   * Obtiene el contenido del Vector de Simbolos en un String.
   * @return El contenido del Vector de Simbolos.
  **/
  public String toString()
  {
  	String cadena="";
  	for(int i=0;i<v.size();i++)
  	{
  	  cadena=cadena+((Simbolo)v.get(i)).toString();
  	  if((i+1)!=v.size())
  	    cadena=cadena+",";
  	}
  	return cadena;
  }
  /**
   * Muestra por pantalla el contenido del Vector de Simbolos..
  **/
  public void debug()
  {System.out.println(toString());}
}