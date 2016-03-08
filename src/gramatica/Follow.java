package gramatica;
import java.util.Hashtable;
import java.util.Enumeration;
/**
* <b>Descripción</b><br>
* Clase que implementa la operacion del cálculo del FOLLOW.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se realizan todas las operaciones necesarias para el cálculo del conjunto de seguidores o FOLLOW de una gramática.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Follow
{
  Gramatica gramatica;//Gramática a la que pertenece el First.
  private Hashtable <String,VectorSimbolos> tabla;//Tabla que almacen al conjunto de seguidores.
  /**
   * Constructor básico.
   * @param gr Gramatica.
  **/
  public Follow(Gramatica gr)
  {
    tabla=new Hashtable <String,VectorSimbolos> ();
    gramatica=gr;
    calcularFollow();
  }
  /**
   * Funcion que calcula el FOLLOW.
  **/
  private void calcularFollow()
  {
    //Se introducen los no terminales en la tabla del first.
    VectorSimbolos noTerminales=gramatica.obtenerNoTerminales();
    for(int i=0;i<gramatica.numeroNoTerminales();i++)
    {
      VectorSimbolos vs=new VectorSimbolos();
      tabla.put(noTerminales.obtenerSimbolo(i).toString(),vs);
    }
    //Se introduce en el follow del simbolo de inicio el elemento nulo.
    VectorSimbolos inicio=tabla.get(gramatica.getSimboloInicio().toString());
    inicio.insertarSimbolo(new Nulo());
    First first=gramatica.obtenerFirst();
    boolean modificado=true;
    while(modificado)
    {
      modificado=false;
      //Se recorren las producciones de la gramatica.
      for(int i=0;i<gramatica.produccionesIntroducidasGramatica();i++)
      {
      	Produccion p=gramatica.obtenerProduccionGramatica(i);
      	VectorSimbolos p_derecha=p.obtenerParteDerecha();
      	//Para cada elemento de la parte derecha que sea NoTerminal.
      	for(int n=0;n<p_derecha.simbolosIntroducidos();n++)
      	{
      	  if(!p_derecha.obtenerSimbolo(n).esNoTerminal())
      	    continue;
      	  //Se obtiene la lista de los elementos que estan a la derecha del NoTerminal.  
      	  VectorSimbolos copia=p_derecha.copiar();
      	  for(int m=0;m<=n;m++)
      	    copia.eliminarSimbolo(0);
      	  //Se calcula el first de los elementos que estan a la derecha del NoTerminal.
      	  VectorSimbolos resultado=first.calcularFirstVectorSimbolos(copia); 
      	  boolean esta_nulo=resultado.estaSimbolo(new Nulo());
      	  if(esta_nulo)
      	  {resultado.eliminarSimbolo(new Nulo());}
          for(int k=0;k<resultado.simbolosIntroducidos();k++)
      	  {
      	  	if(!tabla.get(p_derecha.obtenerSimbolo(n).toString()).estaSimbolo(resultado.obtenerSimbolo(k)))
      	  	{
      	  	  tabla.get(p_derecha.obtenerSimbolo(n).toString()).insertarSimbolo(resultado.obtenerSimbolo(k));
      	  	  modificado=true;
      	  	}
      	  }
      	  if(!esta_nulo)
      	  {continue;}
      	  VectorSimbolos lista=tabla.get(p_derecha.obtenerSimbolo(n).toString());
      	  VectorSimbolos lista2=tabla.get(p.obtenerParteIzquierda().toString());
      	  for(int kk=0;kk<lista2.simbolosIntroducidos();kk++)
      	  {
      	  	if(!lista.estaSimbolo(lista2.obtenerSimbolo(kk)))
      	  	{ 
      	  	  lista.insertarSimbolo(lista2.obtenerSimbolo(kk));
      	  	  modificado=true;
      	    }
      	  }	
      	}
      }	
    }
  }
  /**
   * Funcion que calcula el FOLLOW de un no terminal.
   * @param nt No terminal del que se calcula el follow.
   * @return El follow del no terminal. 
  **/
  public VectorSimbolos obtenerFollowDe(NoTerminal nt)
  {return tabla.get(nt.toString());}
  /**
   * Obtiene el contenido del FOLLOW de la gramatica.
   * @return El contenido del FOLLOW de la gramatica.
  **/
  public String toString()
  {
    String cadena="";
    Enumeration e=tabla.keys();
    while(e.hasMoreElements())
    {
      String s=(String)e.nextElement();
      cadena=cadena+s+"{";
      VectorSimbolos v=(VectorSimbolos)tabla.get(s);
      for(int i=0;i<v.simbolosIntroducidos();i++)
      {
      	cadena=cadena+v.obtenerSimbolo(i).toString();
      	if((i+1)<v.simbolosIntroducidos())
      	  cadena=cadena+" ";
      }
      cadena=cadena+"}\n";
    }
    return cadena;
  }
  /**
  * Muestra por pantalla el contenido del FOLLOW de la gramatica.
  **/
  public void debug()
  {System.out.println(toString());}
}