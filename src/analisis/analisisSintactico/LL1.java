package analisis.analisisSintactico;
import gramatica.*;
import analisis.tabla.*;
/**
* <b>Descripción</b><br>
* Clase que implementa el analisis LL1 de una gramática.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se genera la tabla necesaria para realizar el analisis LL1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class LL1 extends AnalisisSintactico
{
  @SuppressWarnings("unused")
private VectorSimbolos VT;//Terminales de la gramática.
  @SuppressWarnings("unused")
private VectorSimbolos VNT;//No terminales de la gramática.
  /**
   * Constructor básico.
   * @param gr Gramatica
  **/
  public LL1(Gramatica gr)
  {
    gramatica=gr;
  	crearTabla();//Inicializa la tabla. 
  	rellenarTabla();//Rellena la tabla.
  }
  /**
   * Funcion que inicializa la tabla de análisis descendente.
  **/
  private void crearTabla()
  {
    tabla=new TablaDescendente(gramatica.obtenerTerminales(),gramatica.obtenerNoTerminales());
  	VT=((TablaDescendente)tabla).obtenerTerminales();
  	VNT=((TablaDescendente)tabla).obtenerNoTerminales();
  }
  /**
   * Funcion que rellena la tabla de análisis descendente.
  **/
  private void rellenarTabla()
  {
    First first=gramatica.obtenerFirst();
    Follow follow=gramatica.obtenerFollow();
    
    boolean es_nulo=false;
    
    for(int i=0;i<gramatica.produccionesIntroducidasGramatica();i++)
    {
      Produccion pr_actual=((Produccion)gramatica.obtenerProduccionGramatica(i));
      VectorSimbolos first_parcial=first.calcularFirstVectorSimbolos(pr_actual.obtenerParteDerecha());
      for(int j=0;j<first_parcial.simbolosIntroducidos();j++)
      {
      	Simbolo simb=(Simbolo)first_parcial.obtenerSimbolo(j);
      	if(!simb.esNulo())
      	  ((TablaDescendente)tabla).insertar((Terminal)simb,pr_actual.obtenerParteIzquierda(),pr_actual);
      	else
      	  es_nulo=true;
      }
      if(es_nulo)
      {
        VectorSimbolos follow_calculado=follow.obtenerFollowDe(pr_actual.obtenerParteIzquierda());
        
	    for(int k=0;k<follow_calculado.simbolosIntroducidos();k++)
	    {
	      Simbolo s_follow=follow_calculado.obtenerSimbolo(k);
		  if(s_follow.esTerminal())
		    ((TablaDescendente)tabla).insertar((Terminal)s_follow,pr_actual.obtenerParteIzquierda(),pr_actual);
	    }
	    if(follow_calculado.estaSimbolo(new Nulo()))
	      ((TablaDescendente)tabla).insertarColumnaNulo(pr_actual.obtenerParteIzquierda(),pr_actual);
	    es_nulo=false;
      }   
    }
  }
  /**
   * Funcion que obtiene la tabla de analisis es LL1.
   * @return La tabla de analisis descendente.
  **/
  public TablaDescendente getTabla()
  {return ((TablaDescendente)tabla);}
  /**
   * Funcion que obtiene si la tabla de analisis es LL1.
   * @return "True" si es LL1 o "False" si no.
  **/
  public boolean esLL1()
  {return (((TablaDescendente)tabla).existeCasillaMultivalor());}

}