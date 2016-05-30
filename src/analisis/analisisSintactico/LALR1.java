package analisis.analisisSintactico;
import gramatica.*;
import analisis.tabla.*;
import java.util.Vector;
import java.util.Hashtable;
import analisis.analisisSintactico.ascendente.*;
/**
* <b>Descripción</b><br>
* Clase que implementa el analisis LALR1 de una gramática.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se genera la tabla necesaria para realizar el analisis LR1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class LALR1 extends AnalisisAscendente
{
  /**
   * Constructor básico.
   * @param gr Gramatica
  **/
  public LALR1(Gramatica gr)
  {
    super(gr);
    automata=new AutomataLALR();
    iniciarAnalisis();
  }
  
  
  
  protected void calcularConjuntoInicial()
  {
    CjtoConfig cjto0= new CjtoConfig(0);
    Anticipacion atp=new Anticipacion();
    atp.insertarSimboloAnticipacion(new Nulo());
    Produccion produccion_inicio=gramatica_ampliada.obtenerProduccionGramatica(0);
    cjto0.insertarProduccionCjtoConfig(new ProduccionPTOCA(produccion_inicio.obtenerParteIzquierda(),produccion_inicio.obtenerParteDerecha(),0,atp));
    calcularCierre(cjto0);
    automata.nuevoNodoAutomata(cjto0);
  }
  
  
  
  
    protected  void calcularConjuntos()
  {
    
	  
	  for(int i=0;i<automata.numeroNodosAutomata();i++)
    {
      VectorSimbolos vs=new VectorSimbolos();
      Hashtable <String,Vector <Integer>> htent=new Hashtable <String,Vector <Integer>> ();
      Vector <Integer> vtent =new Vector <Integer> ();
      CjtoConfig cjto_actual=automata.obtenerNodoAutomata(i);
      
      //Se añaden los nodos necesarios.
      for(int k=0;k<cjto_actual.numeroElementosCjtoConfig();k++)
      {
	    //Se obtiene las producciones del conjunto de configuracion.
        ProduccionPTO ppto_ca= (ProduccionPTOCA) cjto_actual.obtenerProduccionCjtoConfig(k);
        //Si se llega al fin de la posicion del punto no se continua.
        if(!ppto_ca.haySimboloDerechaPunto())
          continue;
      	
        //Se obtiene la lista   
        vtent=htent.get(ppto_ca.simboloPunto().toString());
        //Si la lista no se ha introducido en la tabla.
      	if(vtent==null)
      	{
          vtent = new Vector <Integer> ();
      	  vtent.add(k);
      	  vs.insertarSimbolo(ppto_ca.simboloPunto());
      	  htent.put(ppto_ca.simboloPunto().toString(),vtent);
      	  continue;
      	}
      	//Si la lista se ha introducido pero no existe el elemento.
        if(!vtent.contains(k))
     	{
     	  vtent.add(k);
     	  htent.put(ppto_ca.simboloPunto().toString(),vtent);
     	}
     }
     //Se añaden los arcos necesarios.
     for(int j=0;j<vs.simbolosIntroducidos();j++)
     {
       Simbolo s_actual=vs.obtenerSimbolo(j);
       CjtoConfig rst=this.calcularSucesor(automata,automata.numeroNodosAutomata(),cjto_actual,gramatica_ampliada,s_actual,htent.get(s_actual.toString()));
       automata.nuevoArcoAutomata(cjto_actual.obtenerNumeroCjtoConfig(),rst.obtenerNumeroCjtoConfig(),s_actual.toString());
       if(!automata.estaContenidoNodoAutomata(rst))
         automata.nuevoNodoAutomata(rst);
      }
    }
  }
  protected  void calcularTablaAccion()
  {
    
	  
	  tabla=new TablaAscendente();
    
   for(int i=0;i<automata.numeroNodosAutomata();i++)
   {
     CjtoConfig cjto_actual=automata.obtenerNodoAutomata(i);
     VectorSimbolos vs=new VectorSimbolos();
     vs.insertarSimbolo(gramatica.getSimboloInicio());
     //SE RELLENA LA TABLA DE ACCION CON "ACEPTAR"
     if(cjto_actual.estaProduccionCjtoConfig(new ProduccionPTO((NoTerminal)gramatica_ampliada.getSimboloInicio(),vs,1)))
        ((TablaAscendente)tabla).insertarElementoTablaAscendente(i,new Nulo(),new ElTablaAsc(ElTablaAsc.ACEPTAR,0));
     //SE RELLENA LA TABLA DE ACCION CON "DESPLAZAR" 
      for(int j=0;j<cjto_actual.numeroElementosCjtoConfig();j++) 
      {
	    ProduccionPTO pptoca_actual=(ProduccionPTO) cjto_actual.obtenerProduccionCjtoConfig(j);
	    if(pptoca_actual.haySimboloDerechaPunto())
      	{
      	  Simbolo simb_actual=pptoca_actual.simboloPunto();	
      	  if(!(simb_actual.esTerminal()))
      	    continue;
      	@SuppressWarnings("rawtypes")
      	  Vector arcos=automata.obtenerArcosAutomata(i,simb_actual.toString());
      	  if(arcos==null)
      	    continue;
      	  //Se obtienen los arcos del terminal que salen hacia los nodos.
          for(int l=0;l<arcos.size();l++)
      	    ((TablaAscendente)tabla).insertarElementoTablaAscendente(i,simb_actual,new ElTablaAsc(ElTablaAsc.DESPLAZAR,(Integer)arcos.get(l)));
      	  continue;
      	}
      	if(pptoca_actual.haySimboloDerechaPunto())
      	  continue;
      	Simbolo part_izq=pptoca_actual.obtenerParteIzquierda();
      	if(gramatica_ampliada.getSimboloInicio().esIgual(part_izq))
      	  continue;
      	  
      	Produccion prdc=new Produccion(new NoTerminal(part_izq.toString()),pptoca_actual.obtenerParteDerecha());
      	int valor=gramatica_ampliada.obtenerPosicionProduccionGramatica(prdc);
      	Anticipacion atp=((ProduccionPTOCA)pptoca_actual).obtenerAnticipacion();
      	
      	for(int l=0;l<atp.numeroElementosAnticipacion();l++)
      	{
          ((TablaAscendente)tabla).insertarElementoTablaAscendente(i,atp.obtenerSimboloAnticipacion(l),new ElTablaAsc(ElTablaAsc.REDUCIR,valor));
      	}
      }
    } 
  }
  protected  void calcularTablaIrA()
  {
    
	  Arco arco_actual;
  	for(int i=0;i<automata.numeroArcosAutomata();i++)
  	{
  	  arco_actual=automata.obtenerArcoEnAutomata(i);
  	  if(gramatica_ampliada.estaIntroducidoNoTerminal(arco_actual.obtenerEtiquetaArco()))
  		((TablaAscendente)tabla).insertarElementoTablaAscendente(arco_actual.obtenerOrigenArco(),new NoTerminal(arco_actual.obtenerEtiquetaArco()),new ElTablaAsc(ElTablaAsc.IR_A,arco_actual.obtenerDestinoArco()));	
  	}  
  }

  protected  void calcularCierre(CjtoConfig cjto_inic)
  {
    
	for(int i=0;i<cjto_inic.numeroElementosCjtoConfig();i++)
  	{
  	  ProduccionPTOCA pptoca_actual=(ProduccionPTOCA)cjto_inic.obtenerProduccionCjtoConfig(i);
  	  Simbolo sbo_actual;
  	  //Si quedan elementos No terminales despues del punto se continua con el proceso, si no, se continua procesando.
      if(pptoca_actual.haySimboloDerechaPunto())
      {
	    sbo_actual=pptoca_actual.simboloPunto();
	    //Si el simbolo no es un un NoTerminal no se continua.
      	if(!sbo_actual.esNoTerminal())
      	  continue;
      }
      else
        continue;
        
  	 //Se obtienen las producciones que tienen la parte izquierda el No Terminal.
     VectorProducciones vp=gramatica_ampliada.obtenerProduccionesIzquierdaIgual((NoTerminal)sbo_actual);
      
     Anticipacion lanticipacion=new Anticipacion();
     
     //Se obtiene el siguiente simbolo despues del punto.
     Simbolo proximo=pptoca_actual.obtenerParteDerecha().obtenerSimbolo(pptoca_actual.posicionPunto()+1);
     if(proximo!=null)
     {
	   //Si proximo (siguiente simbolo despues del punto) es un terminal.
	   if(proximo.esTerminal())
	   {
		 //Se introduce en la futura anticipacion.
	     lanticipacion.insertarSimboloAnticipacion(proximo);
	   }
       else
       {
	     //Si proximo (siguiente simbolo despues del punto) no es un terminal.
         VectorSimbolos temp=new VectorSimbolos();
         temp.insertarSimbolo(proximo);

	     for(int l= pptoca_actual.posicionPunto()+2;l<pptoca_actual.obtenerParteDerecha().simbolosIntroducidos();l++)
      	 {
      	   temp.insertarSimbolo(pptoca_actual.obtenerParteDerecha().obtenerSimbolo(l));
      	 } 
      	 //Se obtiene el first de los simbolos de despues del punto.
      	 temp=gramatica_ampliada.obtenerFirstDe(temp);
      	 if(temp.estaSimbolo(new Nulo()))
      	 {
	       //Si dentro del first se encuentra el simbolo nulo se eliminar.
      	   temp.eliminarSimbolo(new Nulo());
      	   lanticipacion=pptoca_actual.obtenerAnticipacion().copiar();
      	   Anticipacion antAux=new Anticipacion();
      	   antAux.setAnticipacion(temp);
      	   lanticipacion.fusionar(antAux);
      	 }
      	 else
      	 {
      	   lanticipacion=new Anticipacion();
      	   lanticipacion.setAnticipacion(temp);
      	 }	
       }
     }
     
     //Si no hay ningun simbolo despues del simbolo del punto.
     
     else
     {
       @SuppressWarnings("unused")
	Anticipacion test_ant=new Anticipacion();
       lanticipacion=pptoca_actual.obtenerAnticipacion().copiar();
     }
     //UNA VEZ CALCULADA LA ANTICIPACION SE CREA UNA PRODUCCION PUNTO CON ANTICIPACION Y SE INTRODUCE.
     for(int j=0;j<vp.produccionesIntroducidas();j++)
     {
       Produccion pr=vp.obtenerProduccion(j);
       ProduccionPTOCA nueva=new ProduccionPTOCA(pr.obtenerParteIzquierda(),pr.obtenerParteDerecha(),0,lanticipacion);
       //Si no existe en el conjunto inicial se introduce.
       if(!cjto_inic.estaProduccionCjtoConfig(nueva))
         cjto_inic.insertarProduccionCjtoConfig(nueva);	
       //Si existe se 
       else
       {
         ProduccionPTOCA introducida= (ProduccionPTOCA) cjto_inic.obtenerProduccionCjtoConfig(nueva);
      	 introducida.obtenerAnticipacion().fusionar(nueva.obtenerAnticipacion());
      	}
      }
    }
  }
  
  
  
  
  protected  CjtoConfig calcularSucesor(Automata automata,int ind,CjtoConfig cjto_inicial,Gramatica G,Simbolo s,Vector <Integer> v)
  {
    CjtoConfig nuevo_conjunto=new CjtoConfig(ind++);
  	for(int i=0;i<v.size();i++)
  	{
  	  ProduccionPTOCA ppto_ca= (ProduccionPTOCA) cjto_inicial.obtenerProduccionCjtoConfig(v.get(i));
  	  ProduccionPTOCA ppto_ca_nuevo=new ProduccionPTOCA(ppto_ca.obtenerParteIzquierda(),ppto_ca.obtenerParteDerecha(),(ppto_ca.posicionPunto())+1,ppto_ca.obtenerAnticipacion());
  	  nuevo_conjunto.insertarProduccionCjtoConfig(ppto_ca_nuevo);
  	}
  	calcularCierre(nuevo_conjunto);
  	if(automata.estaContenidoNodoAutomata(nuevo_conjunto))
  	{
	  int identificador=automata.obtenerIdentificadorAutomata(nuevo_conjunto);
	  CjtoConfig existente=automata.obtenerNodoAutomata(identificador);
  	  existente.agruparCjtoConfig(nuevo_conjunto);
  	  nuevo_conjunto.modificarNumeroCjtoConfig(automata.obtenerIdentificadorAutomata(nuevo_conjunto));
    }
    return nuevo_conjunto;
  }
}