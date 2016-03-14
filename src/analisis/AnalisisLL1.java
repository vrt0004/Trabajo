package analisis;
import gramatica.*;
import analisis.tabla.*;
import analisis.informe.*;
import analisis.analisisSintactico.*;
/**
* <b>Descripción</b><br>
* Clase que implementa la simulación del analisis LL1.
* <p>
* <b>Detalles</b><br>
* A través de esta clase se representan las operaciones  necesarias para una simulación de un analisis LL1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class AnalisisLL1 extends Analisis
{
  /**
   * Constructor básico de la simulación del analisis LL1.
   * @param gr Gramatica del analisis.
  **/
  public AnalisisLL1(Gramatica gr)
  {
    super(gr);
    algoritmo=new LL1(gramatica);
    informe=new InformeDescendente();
  }
  /**
   * Método que permite ir a un determinado estado de la simulación del análisis LL1.
   * @param it_inicio Iteración de inicio.
   * @param it_fin Iteración de fin.
  **/
  public int realizarIteracion(int it_inicio,int it_fin)
  {
    Simbolo s_cima;//Simbolo que se encuentra en la cima de la pila.
    Simbolo s_actual;//Simbolo seleccionado de la cola de elementos.
    for(int y=it_inicio;y<it_fin;y++)
    {
      //Si es la primera produccion.
      if(y==0)
      {
        resetearAnalisis();
        //Se introduce en la pila el elemento nulo ("$") para controlar cuando se vacia la pila.
        insertarSimboloPila(new Nulo());
        //Se introduce en la pila el no terminal de inicio de la gramatica.
        insertarSimboloPila(obtenerGramaticaAnalisis().getSimboloInicio());
      }
      else
  	  {
	    //Se obtiene el primer elemento de la cola.
  	    s_actual=obtenerSimboloCola();
  	    //Se obtiene el elemento de la cima de la pila.
  	    s_cima=pi.obtenerCimaPila();
  	    //Si el elemento de la pila es el elemento nulo ("$") es que la pila esta vacia.
  	    if(pi.elementoInicialPila())
  	    {
  	      salida.setFinalizadoAnalisis();
  	      return ANALISIS_FINALIZADO;
	    }
	    //Si el simbolo de la cima es un terminal o el simbolo nulo ("$"),
	    if(obtenerGramaticaAnalisis().estaIntroducidoTerminal(s_cima.toString())||s_cima.esNulo())
	    {
		  if(s_cima.esIgual(s_actual))
  	  	  {
  	  	    //Se elimina el elemento de la pila y de la cola.
  	  	    desapilarSimbolo();
  	  	    elimarSimboloCola();
  	  	  }
  	  	  else
  	  	  {
  	  	    //Si el simbolo de la cima de la pila no es igual al prime elemento de la cola.
  	  	    salida.setErrorAnalisis();
	  	  }
  	  	}
		else
		{
		  //Si el simbolo que hay en la cima es un no terminal.
		  if(obtenerGramaticaAnalisis().estaIntroducidoNoTerminal(s_cima.toString()))
		  {
		    VectorProducciones vp=((TablaDescendente)obtenerTablaAnalisis()).obtenerElemento(s_actual,(NoTerminal)s_cima);
		    //Si hay una unica produccion en la tabla.
  	        if(vp.produccionesIntroducidas()!=0)
  	        {
  	  	      //Se desapila el elemento
  	  	      desapilarSimbolo();
  	  	      Produccion p_actual=vp.obtenerProduccion(0);
  	  	      //Se obtiene la parte derecha de la produccion obtenida de la tabla.
  	  	      VectorSimbolos pd=p_actual.obtenerParteDerecha();
  	  	      //Se introduce la parte derecha en orden inverso.
  	  	      for(int i= pd.simbolosIntroducidos() -1;i >=0;i--)
  	  	      {
  	  	  	    if(!pd.obtenerSimbolo(i).esNulo())
  	  	          insertarSimboloPila(pd.obtenerSimbolo(i));
  	  	      }
  	  	      //Se consume la produccion.
  	  	      produccionSalida(p_actual);
  	  	      //escenario.introducirProduccionSalidaAnalisis(p_actual);
	  	    }
  	  	    //Si no hay ninguna produccion o hay mas de una ERROR.
  	  	    else
		    {
		      salida.setErrorAnalisis();
  	          return ERROR_ANALISIS;
		    }
		  }
		  //Si el simbolo que hay en la cima no se encuentra en la gramatica, marcar un error.
		  else
		  {
		    salida.setErrorAnalisis();
  	        return ERROR_ANALISIS;
		  }
	    }
    }
    it_actual++;
  }
  return PASO_REALIZADO;
 }
}