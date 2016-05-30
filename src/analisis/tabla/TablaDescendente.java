package analisis.tabla;
import gramatica.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
/**
* <b>Descripción</b><br>
* Clase que implementa una tabla de analisis descendente.
* <p>
* <b>Detalles</b><br>
* Esta tabla es utilizada por el an�lisis sint�ctico descedente LL1.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class TablaDescendente extends Tabla
{
  @SuppressWarnings("unused")
private int filas;//Filas que tiene la tabla.
  @SuppressWarnings("unused")
private int columnas;//Columnas que tiene la tabla.
  @SuppressWarnings("rawtypes")
public Hashtable <String,Hashtable> tabla_principal;//Tabla hash donde se almecenan los elementos.
  /**
   * Constructor básico.
   * @param vt Terminales.
   * @param vnt No Terminales.
  **/
  @SuppressWarnings("rawtypes")
public TablaDescendente(VectorSimbolos vt,VectorSimbolos vnt)
  {
    //Se inicializan los campos.
    VT=vt;
  	VNT=vnt;	
  	casilla_multivalor=false;
  	tabla_principal=new Hashtable <String,Hashtable> ();
  	filas=vnt.simbolosIntroducidos();
  	columnas=vnt.simbolosIntroducidos();
  	//Se inicializa la tabla con los terminales y los no terminales.
  	for(int iT=0;iT<vt.simbolosIntroducidos();iT++)
  	{
  	  Hashtable  <String,VectorProducciones> sub_tabla=new Hashtable <String,VectorProducciones>();
  	  for(int iNT=0;iNT<vnt.simbolosIntroducidos();iNT++)
  	  {sub_tabla.put(vnt.obtenerSimbolo(iNT).toString(),new VectorProducciones());}
  	  tabla_principal.put(vt.obtenerSimbolo(iT).toString(),sub_tabla);	
  	}
  	//Se introduce la fila del simbolo Anulable.
    Hashtable  <String,VectorProducciones> sub_tabla=new Hashtable <String,VectorProducciones>();
  	for(int iNT=0;iNT<vnt.simbolosIntroducidos();iNT++)
  	{sub_tabla.put(vnt.obtenerSimbolo(iNT).toString(),new VectorProducciones());}
  	tabla_principal.put(new Nulo().toString(),sub_tabla);
  	vt.insertarSimbolo(new Nulo());
  }
  /**
   * Funcion que insertar una produccion en la tabla.
   * @param t Terminal de la producci�n.
   * @param nt NoTerminal de la producci�n.
   * @param pr Produccion 
  **/
  public void insertar(Terminal t,NoTerminal nt, Produccion pr)
  {
  	String T=t.toString();
  	String NT=nt.toString();
    @SuppressWarnings("rawtypes")
	Hashtable sub_tabla=  tabla_principal.get(T);
    if(sub_tabla!=null)
    {
      VectorProducciones vp= (VectorProducciones) sub_tabla.get(NT.toString());
      vp.insertarProduccion(pr);
      //Si existe mas de una producci�n en una casilla se activa el flag multivalor.
      if(vp.produccionesIntroducidas()>1)
        casilla_multivalor=true;
    }
  }
  /**
   * Funcion que insertar una produccion en la tabla para el s�mbolo nulo.
   * @param nt NoTerminal de la producci�n.
   * @param pr produccion.
  **/
  public void insertarColumnaNulo(NoTerminal nt,Produccion pr)
  {
	String NT=nt.toString();
    @SuppressWarnings("rawtypes")
	Hashtable sub_tabla=  tabla_principal.get(new Nulo().toString());
    if(sub_tabla!=null)
    {
      VectorProducciones vp= (VectorProducciones) sub_tabla.get(NT.toString());
      vp.insertarProduccion(pr);
      if(vp.produccionesIntroducidas()>1)
        casilla_multivalor=true;
    }
  }
  /**Obtiene los elementos de una casilla de la tabla en un vector de producciones
  **@param t Terminal 
  **@param nt No terminal
  **@return El vector de producciones que representan la casilla.
  */
  public VectorProducciones obtenerElemento(Simbolo t,NoTerminal nt)
  {
  	@SuppressWarnings("rawtypes")
	Hashtable sub_tabla=  tabla_principal.get(t.toString());
  	VectorProducciones vp= (VectorProducciones) sub_tabla.get(nt.toString());
    return vp;
  }
  /**Obtiene los elementos de una casilla de la tabla en un vector.
  **@param t Terminal 
  **@param nt No terminal
  **@return El vector que representan la casilla.
  **/
  @SuppressWarnings("rawtypes")
public Vector obtenerVectorElementos(Simbolo t,NoTerminal nt)
  {
    VectorProducciones vp=obtenerElemento(t,nt);
    Vector <Produccion> v=new Vector <Produccion> ();
    for(int i=0;i<vp.produccionesIntroducidas();i++)
    {v.add(vp.obtenerProduccion(i));}
	return v;
  }
  /**
   * Obtiene el contenido de la tabla de an�lisis descendente.
   * @return El contenido de la tabla de an�lisis descendente.
  **/
  public String toString()
  {
    String cadena="";
    cadena=cadena+"-----\n";
  	@SuppressWarnings("rawtypes")
	Enumeration e1=tabla_principal.keys();
  	while(e1.hasMoreElements())
  	{
  		String NT=(String)e1.nextElement();
  		cadena=cadena+NT+"\n";
  		@SuppressWarnings("rawtypes")
		Hashtable sub_tabla=tabla_principal.get(NT);
  		@SuppressWarnings("rawtypes")
		Enumeration e2=sub_tabla.keys();
  		while(e2.hasMoreElements())
  		{
  		  String VT=(String)e2.nextElement();
  		  cadena=cadena+"\t"+VT;
  		  VectorProducciones vp=(VectorProducciones)sub_tabla.get(VT);
  		  cadena=cadena+"(";
  		  for(int i=0;i<vp.produccionesIntroducidas();i++)
  		  {
  		  	cadena=cadena+vp.obtenerProduccion(i).toString();
  		  	if((i+1)<vp.produccionesIntroducidas())
  		  	  cadena=cadena+",";
  		  }
  		  cadena=cadena+")";
  		}
  		cadena=cadena+"\n";
  	}
  	cadena=cadena+"-----";
  	
  	return cadena;
  }
  /**
  * Muestra por pantalla el contenido de la tabla de an�lisis descendente.
  **/
   public void debug()
  {System.out.println(toString());}
}