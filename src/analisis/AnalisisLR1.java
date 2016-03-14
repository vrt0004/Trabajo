package analisis;
import gramatica.*;
import analisis.informe.*;
/**
* <b>Descripci�n</b><br>
* Clase que implementa la simulaci�n del analisis LR1.
* <p>
* <b>Detalles</b><br>
* A trav�s de esta clase se representan las operaciones  necesarias para una simulaci�n de un analisis LR1.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
import analisis.analisisSintactico.*;
public class AnalisisLR1 extends AnalisisSLR1
{
  /**
   * Constructor b�sico de la simulaci�n del analisis LR1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisLR1(Gramatica gr)
  {
  	super(gr);
    algoritmo=new LR1(gramatica);
    informe=new InformeAscendente();
  }	
}