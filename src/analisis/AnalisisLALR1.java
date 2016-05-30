package analisis;
import gramatica.*;
import analisis.informe.*;
import analisis.analisisSintactico.*;
/**
* <b>Descripci�n</b><br>
* Clase que implementa la simulaci�n del analisis LALR1.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se representan las operaciones  necesarias para una simulaci�n de un analisis LALR1.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class AnalisisLALR1 extends AnalisisSLR1
{
  /**
   * Constructor b�sico de la simulaci�n del analisis LAR1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisLALR1(Gramatica gr)
  {
  	super(gr);
  	algoritmo=new LALR1(gramatica);
  	informe=new InformeAscendente();
  }	
}