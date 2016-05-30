package gramatica;
import java.util.Hashtable;
import java.util.Enumeration;
/**
* <b>Descripción</b><br>
* Clase que implementa la operacion del cálculo del FIRST.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se realizan todas las operaciones necesarias para el cálculo del conjunto de iniciales o FIRST de una gramática.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class First
{
  Gramatica gramatica;//Gramática a la que pertenece el First.
  public Hashtable <String,VectorSimbolos> tabla;//Tabla que almacen al conjunto de iniciales.
  private tablaAnulable tablaAnulables;//Tabla que almacena los simbolos anulables.
  /**
   * Constructor básico.
   * @param gr Gramatica.
  **/
  public First(Gramatica gr)
  {
    tabla=new Hashtable <String,VectorSimbolos> ();
    gramatica=gr;
    calcularFirst();
  }
  /**
   * Funcion que calcula el FIRST.
  **/
  private void calcularFirst()
  {
    //Se introducen los terminales en la tabla del first.
    VectorSimbolos terminales=gramatica.obtenerTerminales();
    for(int i=0;i<gramatica.numeroTerminales();i++)
    {
      VectorSimbolos vs=new VectorSimbolos();
      vs.insertarSimbolo(terminales.obtenerSimbolo(i));
      tabla.put(terminales.obtenerSimbolo(i).toString(),vs);
    }
    //Se introducen los no terminales en la tabla del first.
    VectorSimbolos noTerminales=gramatica.obtenerNoTerminales();
    for(int i=0;i<gramatica.numeroNoTerminales();i++)
    {
      VectorSimbolos vs=new VectorSimbolos();
      tabla.put(noTerminales.obtenerSimbolo(i).toString(),vs);
    }
    //Se calcula la tabla con los simbolos anulables.
    tablaAnulables=new tablaAnulable();
    //Si el no terminal es anulable se introduce en el first.
    for(int i=0;i<gramatica.numeroNoTerminales();i++)
    {
      if(tablaAnulables.esAnulableSimbolo(noTerminales.obtenerSimbolo(i)))
      {
      	VectorSimbolos vs=tabla.get(noTerminales.obtenerSimbolo(i).toString());
      	vs.insertarSimbolo(new Nulo());
      }
    }
    //Se actualiza el FIRST del primer elemento de la parte derecha.
    for(int i=0;i<gramatica.numeroNoTerminales();i++)
    {
      VectorProducciones v_producciones=gramatica.obtenerProduccionesIzquierdaIgual((NoTerminal)noTerminales.obtenerSimbolo(i));
      for(int j=0;j<v_producciones.produccionesIntroducidas();j++)
      {
      	Produccion pr_actual=v_producciones.obtenerProduccion(j);
      	//Si la parte derecha es anulable o el primer simbolo de la parte derecha es un terminal no se calcula.
      	if(pr_actual.esParteDerechaAnulable()||(pr_actual.obtenerSimboloParteDerecha(0).esNoTerminal()))
      	  continue;
      	VectorSimbolos vs=tabla.get(pr_actual.obtenerParteIzquierda().toString());
      	if(vs.estaSimbolo(pr_actual.obtenerSimboloParteDerecha(0))==false)
      	  vs.insertarSimbolo(pr_actual.obtenerSimboloParteDerecha(0));
      }	
    }
    //Se sigue calculando el FIRST hasta que no se modifique.
    boolean modificado=true;
    while(modificado)
    {
      modificado=false;
      for(int i=0;i<gramatica.produccionesIntroducidasGramatica();i++)
      {
      	//Para cada produccion.
        Produccion p=gramatica.obtenerProduccionGramatica(i);
        //Se calcula su first.
        VectorSimbolos resultado=calcularFirstVectorSimbolos(p.obtenerParteDerecha());
        //Se añaden los elementos del first calculado que no existan.
        VectorSimbolos actual=tabla.get(p.obtenerParteIzquierda().toString());
        for(int j=0;j<resultado.simbolosIntroducidos();j++)
        {
          if(!actual.estaSimbolo(resultado.obtenerSimbolo(j)))
          {
          	actual.insertarSimbolo(resultado.obtenerSimbolo(j));
          	modificado=true;
          }	
        }
      }
    }
  }
  /**
   * Funcion que calcula el FIRST de un conjunto de simbolos.
   * @param pd Conjunto de simbolos.
   * @return FIRST del conjunto de simbolos.
  **/
  public VectorSimbolos calcularFirstVectorSimbolos(VectorSimbolos pd)
  {
  	VectorSimbolos resultado=new VectorSimbolos();
  	//Si es anulable la parte derecha, se introduce el simbolo nulo en el FIRST y se retorna.
  	if(pd.esAnulable())
  	{
  	  resultado.insertarSimbolo(new Nulo());
    }
  	else
  	{
  	  VectorSimbolos first_primero=tabla.get(pd.obtenerSimbolo(0).toString());
  	  //Se comprueba el first del primer elemento.
  	  for(int j=0;j<first_primero.simbolosIntroducidos();j++)
  	  {
  	  	if(!resultado.estaSimbolo(first_primero.obtenerSimbolo(j)))
  	  	  resultado.insertarSimbolo(first_primero.obtenerSimbolo(j));
  	  }
  	  int i;
  	  //Se comprueba el first de los siguientes elementos al primero.
  	  for(i=1;(i<pd.simbolosIntroducidos())&&(tabla.get(pd.obtenerSimbolo(i-1).toString()).estaSimbolo(new Nulo()));i++)
  	  {
  	  	first_primero=tabla.get(pd.obtenerSimbolo(i).toString());
  	  	for(int j=0;j<first_primero.simbolosIntroducidos();j++)
  	  	{
  	  	  if((!resultado.estaSimbolo(first_primero.obtenerSimbolo(j)))&&(first_primero.obtenerSimbolo(j).esNulo()))
  	      {
  	    	resultado.insertarSimbolo(first_primero.obtenerSimbolo(j));
  	      }
  	    }
  	  }
  	  if
  	  (
  	    (i==pd.simbolosIntroducidos())&&
  	  	(tabla.get(pd.obtenerSimbolo(pd.simbolosIntroducidos()-1).toString()).estaSimbolo(new Nulo()))&&
  	  	(!resultado.estaSimbolo(new Nulo()))
      )
      {
  	    resultado.insertarSimbolo(new Nulo());
  	  }
  	  if(i<pd.simbolosIntroducidos())
  	  {
  	    resultado.eliminarSimbolo(new Nulo());
      }
  	  if
  	  (
  	    (i==pd.simbolosIntroducidos())&&
  	    (!tabla.get(pd.obtenerSimbolo(pd.simbolosIntroducidos()-1).toString()).estaSimbolo(new Nulo()))
      )
      {
        resultado.eliminarSimbolo(new Nulo());
      }
  	}
    return resultado;
  }
  /**
   * Obtiene si un elemento es anulable.
   * @param nt No terminal
   * @return "True" si es anulable o "False" si no lo es.
  **/
  public boolean esAnulableNoTerminal(NoTerminal nt)
  {return tablaAnulables.esAnulableSimbolo(nt);}
  /**
   * Obtiene el contenido del FIRST de la gramatica.
   * @return El contenido del FIRST de la gramatica.
  **/
  public String toString()
  {
  	String cadena="";
  	@SuppressWarnings("rawtypes")
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
  * Muestra por pantalla el contenido del FIRST de la gramatica.
  **/
  public void debug()
  {System.out.println(toString());}
  /**
   * Muestra por pantalla el contenido de la tabla de simbolos anulables utilizados para calcular el FIRST.
  **/
  public void debugAnulable()
  {tablaAnulables.debug();}
  
  /****************************************
  **********  TABLA ANULABLE:    **********
  ****************************************/
  
  /**
  * <b>Descripción</b><br>
  * Clase que implementa la operacion de cálculo de la tabla de anulables.
  * <p>
  * <b>Detalles</b><br>
  * Esta clase es utilizada por la clase del cálculo del FIRST para calcular los elementos anulables.<br>
  * </p> 
  * @author Carlos Gómez Palacios.
  * @version 1.0
  */
  private class tablaAnulable
  {
    private Hashtable <String,Boolean> tAnulables;//Tabla que almacena el nombre del no terminal y un boolean que indica si es anulable.
    /**Constructor básico de la tabla de anulables.**/
    public tablaAnulable()
    {
      tAnulables=new Hashtable <String,Boolean> ();
      rellenarAnulables();
    }	
    /**
    * Establece el valor de un simbolo en la tabla de anulables.
    * @param si Simbolo que se introduce
    * @param valor "True" si es anulable o "False" si no lo es.
    **/
    public void insertarSimbolo(Simbolo si,Boolean valor)
    {tAnulables.put(si.toString(),valor);}
    /**
    * Obtiene el valor de un simbolo en la tabla de anulables.
    * @param si Simbolo que se comprueba.
    * @return valor "True" si es anulable o "False" si no lo es.
    **/
    public boolean esAnulableSimbolo(Simbolo si)
    {return tAnulables.get(si.toString());}
    /**Calcula el contenido de la tabla.**/
    public void rellenarAnulables()
    {
      //Añade a la tabla con "false" los terminales.
      VectorSimbolos terminales=gramatica.obtenerTerminales();
      for(int i=0;i<gramatica.numeroTerminales();i++)
      {
        @SuppressWarnings("unused")
		VectorSimbolos vs=new VectorSimbolos();
        tAnulables.put(terminales.obtenerSimbolo(i).toString(),false);
      }
      //Añade a la tabla con "false" los no terminales.
      VectorSimbolos noTerminales=gramatica.obtenerNoTerminales();
      for(int i=0;i<gramatica.numeroNoTerminales();i++)
      {
        @SuppressWarnings("unused")
		VectorSimbolos vs=new VectorSimbolos();
        tAnulables.put(noTerminales.obtenerSimbolo(i).toString(),false);
      }
      boolean modificado=true;
      while(modificado==true)
      {
        modificado=false;      
        //Recorrer todos los no terminales hasta que no se modifique la tabla.
        for(int i=0;i<noTerminales.simbolosIntroducidos();i++)
        {
          NoTerminal nt=(NoTerminal)noTerminales.obtenerSimbolo(i);
          //Se obtienen las producciones que en la parte izquierda tienen el noterminal.
    	  VectorProducciones pr_nt=gramatica.obtenerProduccionesIzquierdaIgual(nt);
    	  
    	  //Se analizan las producciones.
    	  for(int j=0;j<pr_nt.produccionesIntroducidas();j++)
    	  {
    	    Produccion pr=pr_nt.obtenerProduccion(j);
    	    //Si ya es anulable se continua.
    	    if(esAnulableSimbolo(pr.obtenerParteIzquierda()))
    	      continue;
    	    //Si la parte derecha no tiene elementos.
            if(pr.esParteDerechaAnulable())
    	    {
    	  	  if(esAnulableSimbolo(pr.obtenerParteIzquierda())==false)
    	  	    modificado=true;
    	  	  insertarSimbolo(pr.obtenerParteIzquierda(),true);
    	  	  continue;			
    	    }
    	    //Si la parte derecha tiene el primer elemento anulable se comprueba si el resto son anulables.
    	    boolean valor;
    	    if(esAnulableSimbolo(pr.obtenerParteDerecha().obtenerSimbolo(0))==false)
    	      valor=false;
    	    else
    	      valor=true;
    	    for(int k=1;k<pr.obtenerParteDerecha().simbolosIntroducidos()&&valor;k++)
    	    {
    	  	  if(esAnulableSimbolo(pr.obtenerParteDerecha().obtenerSimbolo(k))==false)
    	  	    valor=false;
    	    }
    	    if(!valor)
    	      continue;
    	    if(esAnulableSimbolo(pr.obtenerParteIzquierda())==false)
    	  	  modificado=true;
    	    insertarSimbolo(pr.obtenerParteIzquierda(),true);
    	  }
    	}
      }  	
    }
    /**
    * Obtiene el contenido de la tabla de anulables.
    * @return El contenido de la tabla de anulables.
    **/
    public String toString()
    {
      String cadena="\n------\nANULABLES\n";
      @SuppressWarnings("rawtypes")
	Enumeration e=tAnulables.keys();
      while(e.hasMoreElements())
      {
        String s=(String)e.nextElement();
        cadena=cadena+s+"--"+tAnulables.get(s)+"\n";
      }   
      return cadena;
    }
    /**
    * Muestra por pantalla el contenido de la tabla de simbolos anulables.
    **/
    public void debug()
    {System.out.println(toString());}
  }
}