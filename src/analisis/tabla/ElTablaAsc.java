package analisis.tabla;
/**
* <b>Descripción</b><br>
* Clase que implementan los elementos de una tabla de analisis ascendente.
* <p>
* <b>Detalles</b><br>
* Elementos de la tabla de análisis sintáctico ascendente que indican la operación a realizar.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class ElTablaAsc
{
  private int tipo;//Tipo de operación a realizar.
  private int valor;//Valor de la operación a realizar.
  /**Operación reducir.
  **/
  public static int REDUCIR=0;
  /**Operación desplazar.
  **/
  public static int DESPLAZAR=1;
  /**Operación aceptar.
  **/
  public static int ACEPTAR=2;
  /**Operación ir_a.
  **/
  public static int IR_A=3;
  /**
   * Constructor básico.
   * @param t Tipo de operación.
   * @param v Valor de la operación.
  **/
  public ElTablaAsc(int t,int v)
  {tipo=t;valor=v;}
  /**
   * Obtiene si la operación es de reducir.
   * @return "True" si la operación es de reducir o "False" si no.
  **/
  public boolean esReducir()
  {return (tipo==REDUCIR);}
  /**
   * Obtiene si la operación es de desplazar.
   * @return "True" si la operación es de desplazar o "False" si no.
  **/
  public boolean esDesplazar()
  {return (tipo==DESPLAZAR);}
  /**
   * Obtiene si la operación es de aceptar.
   * @return "True" si la operación es de aceptar o "False" si no.
  **/
  public boolean esAceptar()
  {return (tipo==ACEPTAR);}
   /**
   * Obtiene si la operación es de "ir_a".
   * @return "True" si la operación es de "ir_a" o "False" si no.
  **/
  public boolean esIrA()
  {return (tipo==IR_A);}
  /**
   * Obtiene el valor de la operación si es "Ir_a"
   * @return El valor si es "Ir_a" o -1 si no lo es.
  **/
  public int obtenerValorIrA()
  {
  	if(!esIrA())
  	  return -1;
  	return valor;
  }
  /**
   * Obtiene el valor de la operación si es "Desplazar"
   * @return El valor si es "Desplazar" o -1 si no lo es.
  **/
  public int obtenerValorDesplazar()
  {
    if(!esDesplazar())
  	  return -1;
  	return valor;
  }
  /**
   * Obtiene el valor de la operación si es "Reducir"
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