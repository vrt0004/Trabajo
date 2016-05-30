package analisis;
import gramatica.*;
import analisis.tabla.*;
import java.util.Vector;
import analisis.informe.*;
import analisis.analisisSintactico.*;
/**
* <b>Descripci�n</b><br>
* Clase que implementa la simulaci�n del analisis SLR1.
* <p>
* <b>Detalles</b><br>
* A trav�s de esta clase se representan las operaciones  necesarias para una simulaci�n de un analisis SLR1.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public class AnalisisSLR1 extends Analisis 
{
  /**
   * Constructor b�sico de la simulaci�n del analisis SLR1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisSLR1(Gramatica gr)
  {
  	super(gr);
  	algoritmo=new SLR1(gr);
  	informe=new InformeAscendente();
  }	
  /**
   * M�todo que permite ir a un determinado estado de la simulaci�n del an�lisis SLR1.
   * @param it_inicio Iteraci�n de inicio.
   * @param it_fin Iteraci�n de fin.
  **/

@SuppressWarnings({ "unused", "rawtypes" })
public int realizarIteracion(int it_inicio,int it_fin)
  {
    Simbolo s_cima;//Simbolo que se encuentra en la cima de la pila.
    Simbolo s_actual;//Simbolo seleccionado de la cola de elementos.
    for(int y=it_inicio;y<it_fin;y++)
    {
      if(y==0)
  	  {
  	  	resetearAnalisis();
  	  	insertarSimboloPila(new Terminal("0"));
  	  }
      else
  	  {
	  	
	  	
	    //Se obtiene el primer elemento de la cola.
  	    s_actual=obtenerSimboloCola();
  	    //Se obtiene el elemento de la cima de la pila.
  	    s_cima=pi.obtenerCimaPila();
  	    //Obtiene de la tabla de acciones el contenido de [s_cima,s_actual]
  	    
		Vector acciones=((TablaAscendente)obtenerTablaAnalisis()).obtenerElementoTablaAscendente(s_actual,Integer.valueOf(s_cima.toString()));
  	    //Si no hay acciones ERROR de analisis.
  	    if(acciones==null)
        {
          salida.setErrorAnalisis();
          return ERROR_ANALISIS;
        }
        ElTablaAsc accion_actual;
        accion_actual=(ElTablaAsc) acciones.get(0);
        //SI LA ACCION ES ACEPTAR.
        if(accion_actual.esAceptar())
        {
        	salida.setFinalizadoAnalisis();
        	return Analisis.ANALISIS_FINALIZADO;
        }
        //SI LA ACCION ES DESPLAZAR.
        if(accion_actual.esDesplazar())
        {
          insertarSimboloPila(s_actual);
          elimarSimboloCola();
          insertarSimboloPila(new NoTerminal(""+accion_actual.obtenerValorDesplazar()));
        }
        //SI LA ACCION ES REDUCIR.
        if(accion_actual.esReducir())
        {
          int indice=accion_actual.obtenerValorReducir()-2;
          Produccion pr_actual=obtenerGramaticaAnalisis().obtenerProduccionGramatica(indice);
          
          int simbolos_sacar=pr_actual.obtenerParteDerecha().simbolosIntroducidos()*2;
          if(pr_actual.obtenerParteDerecha().simbolosIntroducidos()==1)
            if(pr_actual.obtenerParteDerecha().obtenerSimbolo(0).esNulo())
              simbolos_sacar=0;
              
            
          
          
          //Sacar dos veces la longitud de la parte derecha de la producci�n.
          for(int i=0;i<simbolos_sacar;i++)
          {
            desapilarSimbolo();
          }
          s_cima=pi.obtenerCimaPila();
          insertarSimboloPila(pr_actual.obtenerParteIzquierda());
          acciones=((TablaAscendente)obtenerTablaAnalisis()).obtenerElementoTablaAscendente(pr_actual.obtenerParteIzquierda(),Integer.valueOf(s_cima.toString()));
          accion_actual=(ElTablaAsc)acciones.get(0);
         if(acciones==null)
          {
           salida.setErrorAnalisis();
           return ERROR_ANALISIS;
          }
          insertarSimboloPila(new NoTerminal(""+accion_actual.obtenerValorIrA()));	
          produccionSalida(pr_actual);
        }
      } 
  	it_actual++;
  }
  return PASO_REALIZADO;
  }
}