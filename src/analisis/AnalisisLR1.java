package analisis;
import gramatica.*;
import analisis.informe.*;
/**
* <b>Descripción</b><br>
* Clase que implementa la simulación del analisis LR1.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se representan las operaciones  necesarias para una simulación de un analisis LR1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
import analisis.analisisSintactico.*;
public class AnalisisLR1 extends AnalisisSLR1
{
  /**
   * Constructor básico de la simulación del analisis LR1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisLR1(Gramatica gr)
  {
  	super(gr);
    algoritmo=new LR1(gramatica);
    informe=new InformeAscendente();
  }	
}