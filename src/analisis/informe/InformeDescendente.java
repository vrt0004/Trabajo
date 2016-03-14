package analisis.informe;
import gramatica.*;
import analisis.*;
import analisis.tabla.*;
import java.util.Vector;
import java.awt.Color;
import com.lowagie.text.Cell;
import com.lowagie.text.Table;
import com.lowagie.text.DocumentException;
import com.lowagie.text.*;
/**
* <b>Descripción</b><br>
* Clase que implementa la realización de un informe descendente.
* <p>
* <b>Detalles</b><br>
* A través de esta clase abstracta se representan las operaciones comunes a la generación de informes de un analisis sintáctico.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/

public class InformeDescendente extends Informe
{
  public InformeDescendente()
  {super();}

  protected String obtenerTitulo()
  {return ("ANALISIS_LL1_"+analisis.obtenerGramaticaAnalisis().getNombreGramatica());}
  
  
  
  
  
  public void crearAnalisis()
  {
	if(DEBUG==true)System.out.println("\t#DEBUG_INFORME# CREA EL ANALISIS DESCENDENTE"); 
  	Paragraph p_analisis=new Paragraph();
  	//Crear la fuente.
    Font fuente_titulo=new Font(Font.COURIER,20,Font.BOLD);
	fuente_titulo.setColor(Color.BLUE);
    Phrase t_analisis=new Phrase(titulo,fuente_titulo);
	p_analisis.setAlignment(Paragraph.ALIGN_CENTER);
	p_analisis.add(t_analisis);
	p_analisis.add(crearTablaDescendente());
	try 
	{document.add(p_analisis);}
	catch (DocumentException de) 
	{System.err.println(de.getMessage());} 
  }
  public Table crearTablaDescendente()
  {
    TablaDescendente tabla_analisis=(TablaDescendente)analisis.obtenerTablaAnalisis();
    int filas=(tabla_analisis.obtenerNoTerminales().simbolosIntroducidos())+1;
    int columnas=(tabla_analisis.obtenerTerminales().simbolosIntroducidos())+1;
    Table tabla_descendente = null;
  	try
  	{
	  
      tabla_descendente = new Table(columnas);
	  tabla_descendente.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
	  tabla_descendente.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
	  Cell cabecera_nt=new Cell("No terminal");
	  cabecera_nt.setHeader(true);
	  cabecera_nt.setRowspan(2);
	  cabecera_nt.setBackgroundColor(COLOR_CABECERA);
	  tabla_descendente.addCell(cabecera_nt);
	  //CABECERA DE ACCION.
  	  Cell cabecera_accion = new Cell("Simbolo de entrada:");
  	  cabecera_accion.setHeader(true);
  	  cabecera_accion.setColspan(columnas-1);
  	  cabecera_accion.setBackgroundColor(COLOR_CABECERA);
	  tabla_descendente.addCell(cabecera_accion);
	  for(int i=0;i<columnas-1;i++)
	  {
	    Cell cabecera_t=new Cell(tabla_analisis.obtenerTerminales().obtenerSimbolo(i).toString());
	  	cabecera_t.setHeader(true);
	  	tabla_descendente.addCell(cabecera_t);
	  }
	  //RELLENAR DATOS:	
	  for(int i=0;i<(filas-1);i++)
	  {
	  	Cell cabecera_f=new Cell(tabla_analisis.obtenerNoTerminales().obtenerSimbolo(i).toString());
	  	cabecera_f.setHeader(true);
	  	tabla_descendente.addCell(cabecera_f);
	  	for(int j=0;j<(columnas-1);j++)
	  	{
		  Simbolo t_actual=tabla_analisis.obtenerTerminales().obtenerSimbolo(j);
	  	  NoTerminal nt_actual=(NoTerminal)tabla_analisis.obtenerNoTerminales().obtenerSimbolo(i);
	  	  Vector v=tabla_analisis.obtenerVectorElementos(t_actual,nt_actual);
	  	  String valor_celda="";
	  	  if(v!=null)
	  	  {
	  	    for(int k=0;k<v.size();k++)
	  	    {
		  	  valor_celda=valor_celda+v.get(k).toString()+"\n";
		    }
	  	    tabla_descendente.addCell(valor_celda);
  	      }
  	    }
	  }
  	}
  	catch (DocumentException de) 
   {System.err.println(de.getMessage());} 
   return tabla_descendente;
  }
  public void crearSimulacionAnalisis()
  {
    
    Paragraph p_simulacion=new Paragraph();
    
    Font fuente_titulo=new Font(Font.COURIER,20,Font.BOLD);
    fuente_titulo.setColor(Color.BLUE);
    Phrase t_simulacion=new Phrase("SIMULACION",fuente_titulo);
    p_simulacion.setAlignment(Paragraph.ALIGN_CENTER);
    p_simulacion.add(t_simulacion);
    
    
    
    
    Table tabla_simulacion=null;
    
    try
    {
	  tabla_simulacion=new Table(3);
	  Cell cabecera_pila=new Cell("PILA");
	  cabecera_pila.setHeader(true);
	  cabecera_pila.setBackgroundColor(COLOR_CABECERA);
	  tabla_simulacion.addCell(cabecera_pila);
	  
	  Cell cabecera_entrada=new Cell("ENTRADA");
	  cabecera_entrada.setBackgroundColor(COLOR_CABECERA);
	  cabecera_entrada.setHeader(true);
	  tabla_simulacion.addCell(cabecera_entrada);
	  
	  Cell cabecera_salida=new Cell("SALIDA");
	  cabecera_salida.setBackgroundColor(COLOR_CABECERA);
	  cabecera_salida.setHeader(true);
	  tabla_simulacion.addCell(cabecera_salida);
	  
	  
	  boolean fin=false;
	  int i=0;
	  
	  while(!fin)
	  {
	    int resultado=analisis.realizarIteracion(i,i+1);
	    if(resultado==Analisis.ERROR_ANALISIS)
	    {
		  fin=true;
		  tabla_simulacion.addCell("ERROR EN EL ANALISIS");
	    
	    }
	    else
	    {
		  if(resultado==Analisis.ANALISIS_FINALIZADO)
		  {
		    fin=true;
		  }
		  tabla_simulacion.addCell(analisis.obtenerEstadoPilaAnalisis().toString());
	      tabla_simulacion.addCell(analisis.obtenerEstadoEntradaAnalisis().toString());
	      tabla_simulacion.addCell(analisis.obtenerProduccionSalida());
		  i++;
	   }
      }  
	}
    catch (DocumentException de) 
   {System.err.println(de.getMessage());}
	 try 
	{
	  document.newPage();
	  document.add(p_simulacion);
	  document.add(tabla_simulacion);
	  document.newPage();
	  
	  //document.add(obtenerGrafico(analisis.obtenerPanelArbolAnalisis(),RUTA_INFORMES+"/"+titulo+"_ARBOL.jpg",rotar_arbol));
    } 
	catch (DocumentException de) 
	{System.err.println(de.getMessage());} 
	
   
   
   
   
   
   
   
   
   
   
   
   
    //p_simulacion.add(tabla_simulacion);
    
    
    

  }
  
  
  
  
  
 
}