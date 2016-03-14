package analisis.analisisSintactico.ascendente;
/**
* <b>Descripción</b><br>
* Clase que implementa un arco de un autómata.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se implementa cada uno de los arcos etiquetados de un autómata.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class Arco
{
  private int origen;//Cjto Config de origen.
  private int destino;//Cjto Config de destino.
  private String etiqueta;//Etiqueta del arco
  /**
   * Constructor básico.
   * @param or Nodo de origen.
   * @param dest Nodo de destino.
   * @param etiq Etiqueta del arco.
  **/
  public Arco(int or,int dest,String etiq)
  {
    origen=or;
    destino=dest;
    etiqueta=etiq;
  }
  /**
   * Obtiene el origen del arco.
   * @return El número del nodo de origen.
  **/
  public int obtenerOrigenArco()
  {return origen;}
  /**
   * Obtiene el destino del arco.
   * @return El número del nodo de destino.
  **/
  public int obtenerDestinoArco()
  {return destino;}
  /**
   * Obtiene la etiqueta del arco.
   * @return Etiqueta del arco.
  **/
  public String obtenerEtiquetaArco()
  {return etiqueta;}
  /**
   * Obtiene el contenido del arco en un String.
   * @return El contenido del arco.
  **/
  public String toString()
  {return (origen+"-"+etiqueta+"->"+destino);}
  /**
   * Muestra el contenido del arco por pantalla.
  **/
  public void debug()
  {System.out.println(toString());}
}