package analisis;
import gramatica.*;
import analisis.informe.*;
import analisis.analisisSintactico.*;
/**
* <b>Descripción</b><br>
* Clase que implementa la simulación del analisis LALR1.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se representan las operaciones  necesarias para una simulación de un analisis LALR1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class AnalisisLALR1 extends AnalisisSLR1
{
  /**
   * Constructor básico de la simulación del analisis LAR1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisLALR1(Gramatica gr)
  {
  	super(gr);
  	algoritmo=new LALR1(gramatica);
  	informe=new InformeAscendente();
  }	
}