package analisis.informe;
import analisis.*;
import java.awt.Color;
import java.util.Vector;
import analisis.tabla.*;
import com.lowagie.text.*;
import java.io.IOException;
import com.lowagie.text.Image;
import com.lowagie.text.Table;
import com.lowagie.text.Cell;
import com.lowagie.text.Paragraph;
import com.lowagie.text.DocumentException;
/**
* <b>Descripción</b><br>
* Clase que implementa la realización de un informe ascendente.
* <p>
* <b>Detalles</b><br>
* A través de esta clase abstracta se representan las operaciones comunes a la generación de informes de un analisis sintáctico.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/



public class InformeAscendente extends Informe
{

	
	
  //private String RUTA_AUTOMATA=RUTA_INFORMES+"/IMAGENES/Automata.jpg";
  
 
  public InformeAscendente()
  {super();}
  
  protected String obtenerTitulo()
  { 
    String titulo="";
	if(analisis.getClass().equals(AnalisisSLR1.class))
  	{titulo="ANALISIS SLR1";}
  	if(analisis.getClass().equals(AnalisisLR1.class))
  	{titulo="ANALISIS LR1";}
  	if(analisis.getClass().equals(AnalisisLALR1.class))
  	{titulo="ANALISIS LALR1";}
	return (titulo+"_"+analisis.obtenerGramaticaAnalisis().getNombreGramatica());
  }
  public void crearAnalisis()
  {
  	if(DEBUG==true)System.out.println("\t#DEBUG_INFORME# CREA EL ANALISIS ASCENDENTE"); 
	Paragraph p_analisis=new Paragraph();
  	//Crear la fuente.
    Font fuente_titulo=new Font(Font.COURIER,20,Font.BOLD);
	fuente_titulo.setColor(Color.BLUE);
	//Coloca el titulo.
    Phrase t_analisis=new Phrase(titulo,fuente_titulo);
    p_analisis.setAlignment(Paragraph.ALIGN_CENTER);
	p_analisis.add(t_analisis);
	p_analisis.setAlignment(Paragraph.ALIGN_CENTER);
	p_analisis.add(crearTablaAscendente());
	try 
	{
	  document.add(p_analisis);
	  document.newPage();
	  
	  //salida.obtenerAutomataAnalisis();
	  
	  
	  //salida.obtenerAutomataAnalisis(),RUTA_INFORMES+"/"+titulo+"_ARBOL.jpg",rotar_automata)
	  //document.add(obtenerGrafico(analisis.obtenerPanelAutomata(),RUTA_INFORMES+"/"+titulo+"_AUTOMATA.jpg",rotar_automata));
	  
	} 
	catch (DocumentException de) 
	{System.err.println(de.getMessage());} 
  }
  public Table crearTablaAscendente()
  {
    TablaAscendente tabla_analisis=(TablaAscendente)analisis.obtenerTablaAnalisis();
	int columnas_ir_a=tabla_analisis.obtenerNumeroNoTerminales();
    int columnas_accion=tabla_analisis.obtenerNumeroTerminales();
    int columnas=columnas_ir_a+columnas_accion;
    int filas=tabla_analisis.obtenerNumeroFilas();
  	  
  	Table tabla_ascendente=null;
  	try 
	{
	  tabla_ascendente = new Table(columnas+1);
	  tabla_ascendente.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
	  tabla_ascendente.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
	  tabla_ascendente.addCell("");
	  //CABECERA DE ACCION.
  	  Cell cabecera_accion = new Cell("Tabla de Accion");
  	  cabecera_accion.setHeader(true);
  	  cabecera_accion.setColspan(columnas_accion);
  	  cabecera_accion.setBackgroundColor(COLOR_CABECERA);
	  tabla_ascendente.addCell(cabecera_accion);
	  //CABECERA DE IR_A
	  Cell cabecera_ir_a=new Cell("Tabla de Ir A");
	  cabecera_ir_a.setHeader(true);
	  cabecera_ir_a.setColspan(columnas_ir_a);
	  cabecera_ir_a.setBackgroundColor(COLOR_CABECERA);
	  tabla_ascendente.addCell(cabecera_ir_a);
	  //RELLENAR CABECERAS:
	  //CABECERA DE ESTADO.
	  Cell cabecera_estado=new Cell("Estado");
	  cabecera_estado.setColspan(1);
	  cabecera_estado.setHeader(true);
	  tabla_ascendente.addCell(cabecera_estado);
	  
	  
	  for(int i=0;i<columnas_accion;i++)
	  {
	  	Cell cabecera_t=new Cell(tabla_analisis.obtenerTerminales().obtenerSimbolo(i).toString());
	  	cabecera_t.setHeader(true);
	  	tabla_ascendente.addCell(cabecera_t);
	  }
	  for(int i=0;i<columnas_ir_a;i++)
	  {
        Cell cabecera_nt=new Cell(tabla_analisis.obtenerNoTerminales().obtenerSimbolo(i).toString());
	    cabecera_nt.setHeader(true);
	    tabla_ascendente.addCell(cabecera_nt);
	  }
	  //RELLENAR DATOS:	
	  for(int i=0;i<filas;i++)
	  {
		tabla_ascendente.addCell(""+i);
	  	for(int j=0;j<columnas_accion;j++)
	  	{
		 
		  Vector v=tabla_analisis.obtenerElementoTablaAscendente(tabla_analisis.obtenerTerminales().obtenerSimbolo(j),i);
		  String valor_celda="";
	  	  if(v!=null)
	  	  {
	  	    for(int k=0;k<v.size();k++)
	  	    {
		  	  valor_celda=valor_celda+v.get(k).toString()+"\n";
		    }
  	      }
		  tabla_ascendente.addCell(valor_celda);
  	    }
  	    for(int j=0;j<columnas_ir_a;j++)
  	    {
	  	  Vector v=tabla_analisis.obtenerElementoTablaAscendente(tabla_analisis.obtenerNoTerminales().obtenerSimbolo(j),i);
	  	  String valor_celda="";
	  	  if(v!=null)
	  	  {
	  	    for(int k=0;k<v.size();k++)
	  	    {
		  	  valor_celda=valor_celda+v.get(k).toString()+"\n";
		    }
	  	    
  	      }
	  	  tabla_ascendente.addCell(valor_celda);
        }  
	  }
   }
   catch (DocumentException de) 
   {System.err.println(de.getMessage());} 
   return tabla_ascendente;
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
	  
	  //tabla_simulacion.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
	  
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
     } 
	catch (DocumentException de) 
	{System.err.println(de.getMessage());} 
  }
}