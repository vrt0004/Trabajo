package analisis.informe;
import analisis.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;


/**
* <b>Descripci�n</b><br>
* Clase abstracta que implementa la realizaci�n de un informe.
* <p>
* <b>Detalles</b><br>
* A trav�s de esta clase abstracta se representan las operaciones comunes a la generaci�n de informes de un analisis sint�ctico.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/
public abstract class Informe  
{
  /**Variable de DEBUG
  **/	
  protected static  boolean DEBUG=false;	
  /**Analisis que se realizar�
  **/
  protected Analisis analisis;
  /**Variable donde se almacena el informe que se va generando.
  **/
  protected Document document;
  /**Color de cabecera.
  **/
  protected static Color COLOR_CABECERA=Color.lightGray;
  /**Variable donde se almacena el informe.
  **/
  protected  String RUTA_INFORMES=System.getProperty("user.dir")+"/INFORMES";
  /**Variable que accede a los componentes gr�ficos.
  **/
  protected ISalidaAnalisis salida;
  /**Titulo del analisis
  **/
  protected String titulo="";
  /**Variable que indica si el automata se rotar�
  **/
  protected boolean rotar_automata=false;
  /**Variable que indica si se rota el �rbol de analisis
  **/
  protected boolean rotar_arbol=false;
  /**Metodo abstracto que en su implementacion a�ade al titulo al informe**/
  protected abstract String obtenerTitulo();
  /**Metodo abstracto que en su implementacion a�ade el analisis al informe**/
  protected abstract void crearAnalisis();
  /**Metodo abstracto que en su implementacion a�ade la simulacion al informe**/
  protected abstract void crearSimulacionAnalisis();
  /**Genera el informe del analisis
  **@param anl Analisis del que se realiza el inform
  **@param ruta Ruta donde se almacenar�
  **@param raut Variable que indica si se rotar� el automata.
  **@param rarb Variable que indica si se rotar� el �rbol de analisis.
  ** @return boolean
  **/
  public boolean obtenerInforme(Analisis anl,String ruta,boolean raut,boolean rarb)
  {
	analisis=anl;
	RUTA_INFORMES=ruta;
	salida=analisis.getSalidaAnalisis();
	if(salida==null)
	 return false;
	analisis.resetearAnalisis();
	rotar_automata=raut;
	rotar_arbol=rarb;
	titulo=obtenerTitulo();
    document = new Document();
    try 
	{
	  //Declara los tipos de documentos que se van a crear.
      PdfWriter writer_pdf=PdfWriter.getInstance(document,new FileOutputStream(RUTA_INFORMES+"/"+titulo+".pdf"));
	  RtfWriter2.getInstance(document,new FileOutputStream(RUTA_INFORMES+"/"+titulo+".rtf"));
	  HtmlWriter.getInstance(document,new FileOutputStream(RUTA_INFORMES+"/"+titulo+".htm"));
	  //Se introduce una plantilla con el encabezado y el pie de pagina.
	  writer_pdf.setPageEvent(new Plantilla());
	  //Abre el documento.
	  if(DEBUG==true)System.out.println("\t#DEBUG_INFORME# ABRE EL DOCUMENTO");
	  document.open();
	  //Se a�ade el titulo.
	  document.add(crearTitulo(analisis.obtenerGramaticaAnalisis().getNombreGramatica()));
	  //Se a�ade la gramatica.
	  document.add(crearGramatica());
	  //Se a�ade el analisis.
	  crearAnalisis();
	  //Se a�ade la simulacion si hay simbolos de entrada.
	  if(analisis.hayEntradaAnalisis())
	    crearSimulacionAnalisis();
	} 
	catch (DocumentException de) 
	{return false;}
	catch (IOException ioe) 
	{return false;}
    document.close();
    return true;
  }
  /////////////////////////////////////////////
  //    CREAR TITULO
  /////////////////////////////////////////////
  private Paragraph crearTitulo(String titulo)
  {
  	 if(DEBUG==true)System.out.println("\t#DEBUG_INFORME# CREA EL TITULO");
	//Crear la fuente.
    Font fuente_titulo=new Font(Font.COURIER,40,Font.BOLD);
	fuente_titulo.setColor(Color.BLUE);
  	//Crea la frase
    Phrase frase_titulo=new Phrase(titulo,fuente_titulo);
    Paragraph titulo_informe=new Paragraph(frase_titulo);
   	titulo_informe.setAlignment(Paragraph.ALIGN_CENTER);
	titulo_informe.spacingAfter();
	return titulo_informe;
  }
  /////////////////////////////////////////////
  //    CREAR LA GRAMATICA DEL INFORME
  /////////////////////////////////////////////
  private Paragraph crearGramatica()
  {
	if(DEBUG==true)System.out.println("\t#DEBUG_INFORME# CREA LA  GRAMATICA");
  	//Crear la fuente.
    Font fuente_titulo=new Font(Font.COURIER,18,Font.BOLD);
	fuente_titulo.setColor(Color.BLUE);
	Font fuente_texto=new Font(Font.COURIER,10,Font.BOLD);
	Paragraph gramatica=new Paragraph();
  	try
  	{
	  int capacidad=3;
	  if(analisis.obtenerGramaticaAmpliadaAnalisis()!=null)
	    capacidad=4;
  	  Table	tabla_gramatica = new Table(capacidad);
  	  //Mostrar Gramatica
  	  Paragraph p_gramatica=new Paragraph();
  	  p_gramatica.setAlignment(Paragraph.ALIGN_CENTER);
  	  Phrase frase_tgramatica=new Phrase("GRAMATICA\n",fuente_titulo);
  	  String ct_gramatica=analisis.obtenerGramaticaAnalisis().toString();
  	  Phrase frase_gramatica=new Phrase(ct_gramatica,fuente_texto);
  	  p_gramatica.add(frase_tgramatica);
  	  p_gramatica.add(frase_gramatica);
  	  tabla_gramatica.addCell(p_gramatica);
  	  //Mostrar First.
  	  Paragraph p_first=new Paragraph();
  	  p_first.setAlignment(Paragraph.ALIGN_CENTER);
  	  Phrase frase_tfirst=new Phrase("FIRST\n",fuente_titulo);
  	  String ct_first=analisis.obtenerGramaticaAnalisis().obtenerFirst().toString();
  	  Phrase frase_first=new Phrase(ct_first,fuente_texto);
  	  p_first.add(frase_tfirst);
  	  p_first.add(frase_first);
  	  tabla_gramatica.addCell(p_first);
  	  //Mostrar Follow.
  	  Paragraph p_follow=new Paragraph();
  	  p_follow.setAlignment(Paragraph.ALIGN_CENTER);
  	  Phrase frase_tfollow=new Phrase("FOLLOW\n",fuente_titulo);
  	  String ct_follow=analisis.obtenerGramaticaAnalisis().obtenerFollow().toString();
  	  Phrase frase_follow=new Phrase(ct_follow,fuente_texto);
  	  p_follow.add(frase_tfollow);
  	  p_follow.add(frase_follow);
  	  tabla_gramatica.addCell(p_follow);
  	  gramatica.add(tabla_gramatica);	
  	  //MOSTRAR LA GRAMATICA AMPLIADA SI EXISTE.
  	  if(capacidad==4)
  	  {
	    Paragraph p_gramatica_amp=new Paragraph();
  	    p_gramatica_amp.setAlignment(Paragraph.ALIGN_CENTER);
  	    Phrase frase_tgramatica_amp=new Phrase("GRAMATICA AMPLIADA\n",fuente_titulo);
  	    String ct_gramatica_amp=analisis.obtenerGramaticaAmpliadaAnalisis().toString();
  	    Phrase frase_gramatica_amp=new Phrase(ct_gramatica_amp,fuente_texto);
  	    p_gramatica_amp.add(frase_tgramatica_amp);
  	    p_gramatica_amp.add(frase_gramatica_amp);
  	    tabla_gramatica.addCell(p_gramatica_amp);
	  }
    }
    catch (DocumentException de) 
    {System.err.println(de.getMessage());}
    return gramatica;
  }
  
  /**Cabecera y pie de pagina del informe**/
  private class Plantilla extends PdfPageEventHelper 
  {
    protected BaseFont helv;
	protected PdfTemplate total;
    public void onOpenDocument(PdfWriter writer, Document document) 
	{
	  total = writer.getDirectContent().createTemplate(100, 100);
	  	total.setBoundingBox(new Rectangle(-20, -20, 100, 100));
		try {
			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
					BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
    public void onEndPage(PdfWriter writer, Document document) 
	{
	  PdfContentByte cb = writer.getDirectContent();
	  Date fechaActual = new Date();
	  SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	  String cadenaFecha = formato.format(fechaActual);
	  Phrase header = new Phrase("BURGRAM ("+cadenaFecha+")");
	  ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,(document.right() - document.left()) / 2+ document.leftMargin(), document.top() + 10, 0);
	  cb.saveState();
	  String text = "Pagina " + writer.getPageNumber() + " de ";
	  float textBase = document.bottom() - 20;
	  float textSize = helv.getWidthPoint(text, 12);
	  cb.beginText();
	  cb.setFontAndSize(helv, 12);
	  if ((writer.getPageNumber() % 2) == 1) 
	  {
	    cb.setTextMatrix(document.left(), textBase);
	  	cb.showText(text);
	  	cb.endText();
	  	cb.addTemplate(total, document.left() + textSize, textBase);
	  }
      else 
      {
	    float adjust = helv.getWidthPoint("0", 12);
	  	cb.setTextMatrix(document.right() - textSize - adjust, textBase);
	  	cb.showText(text);
	  	cb.endText();
	  	cb.addTemplate(total, document.right() - adjust, textBase);
	  }
	  cb.restoreState();
	}
    public void onCloseDocument(PdfWriter writer, Document document) 
	{
      total.beginText();
	  total.setFontAndSize(helv, 12);
	  total.setTextMatrix(0, 0);
	  total.showText(String.valueOf(writer.getPageNumber() - 1));
	  total.endText();
	}
  }
}