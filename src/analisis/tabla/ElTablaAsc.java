package analisis.tabla;
/**
* <b>Descripci�n</b><br>
* Clase que implementan los elementos de una tabla de analisis ascendente.
* <p>
* <b>Detalles</b><br>
* Elementos de la tabla de an�lisis sint�ctico ascendente que indican la operaci�n a realizar.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class ElTablaAsc
{
  private int tipo;//Tipo de operaci�n a realizar.
  private int valor;//Valor de la operaci�n a realizar.
  /**Operaci�n reducir.
  **/
  public static int REDUCIR=0;
  /**Operaci�n desplazar.
  **/
  public static int DESPLAZAR=1;
  /**Operaci�n aceptar.
  **/
  public static int ACEPTAR=2;
  /**Operaci�n ir_a.
  **/
  public static int IR_A=3;
  /**
   * Constructor b�sico.
   * @param t Tipo de operaci�n.
   * @param v Valor de la operaci�n.
  **/
  public ElTablaAsc(int t,int v)
  {tipo=t;valor=v;}
  /**
   * Obtiene si la operaci�n es de reducir.
   * @return "True" si la operaci�n es de reducir o "False" si no.
  **/
  public boolean esReducir()
  {return (tipo==REDUCIR);}
  /**
   * Obtiene si la operaci�n es de desplazar.
   * @return "True" si la operaci�n es de desplazar o "False" si no.
  **/
  public boolean esDesplazar()
  {return (tipo==DESPLAZAR);}
  /**
   * Obtiene si la operaci�n es de aceptar.
   * @return "True" si la operaci�n es de aceptar o "False" si no.
  **/
  public boolean esAceptar()
  {return (tipo==ACEPTAR);}
   /**
   * Obtiene si la operaci�n es de "ir_a".
   * @return "True" si la operaci�n es de "ir_a" o "False" si no.
  **/
  public boolean esIrA()
  {return (tipo==IR_A);}
  /**
   * Obtiene el valor de la operaci�n si es "Ir_a"
   * @return El valor si es "Ir_a" o -1 si no lo es.
  **/
  public int obtenerValorIrA()
  {
  	if(!esIrA())
  	  return -1;
  	return valor;
  }
  /**
   * Obtiene el valor de la operaci�n si es "Desplazar"
   * @return El valor si es "Desplazar" o -1 si no lo es.
  **/
  public int obtenerValorDesplazar()
  {
    if(!esDesplazar())
  	  return -1;
  	return valor;
  }
  /**
   * Obtiene el valor de la operaci�n si es "Reducir"
   * @return El valor si es "Reducir" o -1 si no lo es.
  **/
  public int obtenerValorReducir()
  {
  	if(!esReducir())
  	  return -1;
  	return valor;
  }	
  /**
   * Obtiene el contenido del elemento en un String.
   * @return El cotenido del elemento.
  **/
  public String toString()
  {
  	String cadena="";
  	if(esIrA())
  	  cadena=""+valor;
  	if(esDesplazar())
  	  cadena="d("+valor+")";
  	if(esReducir())
  	  cadena="r("+valor+")";
  	if(esAceptar())
  	  cadena="ACP";
  	return cadena;
  }
  /**
  * Muestra por pantalla el elemento de la tabla.
  **/
  public void debug()
  {System.out.println(toString());}
}