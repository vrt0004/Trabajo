package analisis;
import gramatica.*;
import java.util.Stack;
/**
* <b>Descripción</b><br>
* Clase que implementa una pila utilizada en el analisis sintáctico.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa una pila que contiene símbolos de análisis.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Pila
{
  private Stack <Simbolo> p;//Pila donde se almacenan los simbolos.
  /**
   * Constructor básico.
  **/
  public Pila()
  {p=new Stack <Simbolo> ();}
  /**
   * Introduce en la pila un simbolo
   * @param si Simbolo que se introduce.
  **/
  public void meterPila(Simbolo si)
  {p.push(si);}
  /**
   * Saca el primer simbolo de la pila.
   * @return Simbolo de la cima de la pila.
  **/
  public  Simbolo sacarPila()
  {return  p.pop();}
  /**
   * Obtiene el elemento de la cima de la pila, pero no le saca.
   * @return Simbolo de la cima de la pila.
  **/
  public Simbolo obtenerCimaPila()
  {return (Simbolo) p.peek();}
  /**
   * Obtiene si en la pila solo esta el simbolo nulo.
   * @return "True" si es cierto o "False" si no.
  **/
  public boolean elementoInicialPila()
  {
    if((p.size()==1)&&(obtenerCimaPila().esNulo()))
	  return true;
	return false;
  }
  /**
   * Obtiene el contenido de la pila en un String.
   * @return El contenido de la pila.
  **/
  public String toString()
  {return (p.toString());}
  /**
   * Muestra el contenido de la pila por pantalla.
  **/
  public void debug()
  {System.out.println(p.toString());}
}