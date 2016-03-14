package analisis.analisisSintactico;
import gramatica.*;
import analisis.tabla.*;
import analisis.analisisSintactico.ascendente.*;
import java.util.Vector;
import java.util.Hashtable;
/**
* <b>Descripción</b><br>
* Clase que implementa el analisis SLR1 de una gramática.
* <p>
* <b>Detalles</b><br>
* Mediante esta clase se genera la tabla necesaria para realizar el analisis SLR1.<br>
* </p> 
* @author Carlos Gómez Palacios.
* @version 1.0
*/
public class SLR1 extends AnalisisAscendente
{
  /**
   * Constructor básico.
   * @param gr Gramatica
  **/
 public SLR1(Gramatica gr)
  {
   super(gr);
   automata=new Automata();
   iniciarAnalisis();
  }
  /**
   * Función que  calcula el conjunto inicial.
  **/
  protected void calcularConjuntoInicial()
  {
    CjtoConfig cjto0= new CjtoConfig(0);
    //Obtiene la producción que amplia la gramatica.
    Produccion produccion_inicio=gramatica_ampliada.obtenerProduccionGramatica(0);
    cjto0.insertarProduccionCjtoConfig(new ProduccionPTO(produccion_inicio.obtenerParteIzquierda(),produccion_inicio.obtenerParteDerecha(),0));
    //Calcula el cierre del primer conjunto de items.
    calcularCierre(cjto0);
    automata.nuevoNodoAutomata(cjto0);
  }
  
  
  
  //////////////////////////////////////////
  //2-CALCULAR CONJUNTOS:
  //////////////////////////////////////////
  protected void calcularConjuntos()
  {
	  
	 
    for(int i=0;i<automata.numeroNodosAutomata();i++)
    {
	  
	    
	  //Para cada nodo que se añade al automata se calculan sus sucesores. 
	    
      VectorSimbolos vs=new VectorSimbolos();
      Hashtable <String,Vector <Integer>> htent=new Hashtable <String,Vector <Integer>> ();
      Vector <Integer> vtent =new Vector <Integer> ();
    	
      CjtoConfig cjto_actual=automata.obtenerNodoAutomata(i);
      
      
      
      for(int k=0;k<cjto_actual.numeroElementosCjtoConfig();k++)
      {
	    //Se obtiene las producciones del conjunto de configuracion.
        ProduccionPTO ppto= (ProduccionPTO) cjto_actual.obtenerProduccionCjtoConfig(k);
        //Si se llega al fin de la posicion del punto no se continua.
        if(!ppto.haySimboloDerechaPunto())
          continue;
        //Se obtiene la lista   
        vtent=htent.get(ppto.simboloPunto().toString());
        //Si la lista no se ha introducido en la tabla.
      	if(vtent==null)
      	{
          vtent = new Vector <Integer> ();
      	  vtent.add(k);
      	  vs.insertarSimbolo(ppto.simboloPunto());
      	  htent.put(ppto.simboloPunto().toString(),vtent);
      	  continue;
      	}
      	//Si la lista se ha introducido pero no existe el elemento.
        if(!vtent.contains(k))
     	{
     	  vtent.add(k);
     	  htent.put(ppto.simboloPunto().toString(),vtent);
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
  
  
  
  protected void calcularTablaAccion()
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
      	ProduccionPTO ppto_actual=(ProduccionPTO) cjto_actual.obtenerProduccionCjtoConfig(j);
      	if(ppto_actual.haySimboloDerechaPunto())
      	{
      	  Simbolo simb_actual=ppto_actual.simboloPunto();	
      	  if(!(simb_actual.esTerminal()))
      	    continue;
      	 Vector arcos=automata.obtenerArcosAutomata(i,simb_actual.toString());
      	 if(arcos==null)
      		  continue;
      	 //Se obtienen los arcos del terminal que salen hacia los nodos.
         for(int l=0;l<arcos.size();l++)
      	   ((TablaAscendente)tabla).insertarElementoTablaAscendente(i,simb_actual,new ElTablaAsc(ElTablaAsc.DESPLAZAR,(Integer)arcos.get(l)));
      	 continue;
      	}
      	//SE RELLENA LA TABLA DE ACCION CON REDUCIR
      	//Si no hay elementos a la derecha del punto.
      	
      	if(ppto_actual.haySimboloDerechaPunto())
      	  continue;
      	Simbolo part_izq=ppto_actual.obtenerParteIzquierda();
      	if(gramatica_ampliada.getSimboloInicio().esIgual(part_izq))
      	  continue;
      	VectorSimbolos follow=gramatica.obtenerFollowDe((NoTerminal)part_izq);
      	Produccion prdc=new Produccion(new NoTerminal(part_izq.toString()),ppto_actual.obtenerParteDerecha());
      	
      	
      	//int valor=2;
      	int valor=gramatica_ampliada.obtenerPosicionProduccionGramatica(prdc);
      	//SE RELLENA LA TABLA DE ACCION CON LA ACCION REDUCIR.
      	for(int l=0;l<follow.simbolosIntroducidos();l++)
      	{
          ((TablaAscendente)tabla).insertarElementoTablaAscendente(i,follow.obtenerSimbolo(l),new ElTablaAsc(ElTablaAsc.REDUCIR,valor));
      	}
      }
    }
  }
  

  protected void calcularTablaIrA()
  {
  	
	  Arco arco_actual;
  	for(int i=0;i<automata.numeroArcosAutomata();i++)
  	{
  	  arco_actual=automata.obtenerArcoEnAutomata(i);
  	  
  	  
  	  if(gramatica_ampliada.estaIntroducidoNoTerminal(arco_actual.obtenerEtiquetaArco()))
  		((TablaAscendente)tabla).insertarElementoTablaAscendente(arco_actual.obtenerOrigenArco(),new NoTerminal(arco_actual.obtenerEtiquetaArco()),new ElTablaAsc(ElTablaAsc.IR_A,arco_actual.obtenerDestinoArco()));	
  	}
  }
  /**
   * Función  para calculo del cierre:
   * Si el simbolo que precede la marca es un "no terminal" se añaden
   * sus reglas con la marca al principio.
  **/
  protected void calcularCierre(CjtoConfig cjto_inic)
  {
    for(int i=0;i<cjto_inic.numeroElementosCjtoConfig();i++)
  	{
	  ProduccionPTO ppto_actual=cjto_inic.obtenerProduccionCjtoConfig(i);
	  Simbolo sbo_actual=null;
      //Si quedan elementos despues del punto.
      if(ppto_actual.haySimboloDerechaPunto())
      {
	    sbo_actual=ppto_actual.simboloPunto();
	    //Si el simbolo no es un un NoTerminal no se continua.
      	if(!sbo_actual.esNoTerminal())
      	  continue;
      }
      else
        continue;
     //Se obtienen las producciones que tienen la parte izquierda el terminal.
     VectorProducciones vp=gramatica_ampliada.obtenerProduccionesIzquierdaIgual((NoTerminal)sbo_actual);
     for(int j=0;j<vp.produccionesIntroducidas();j++)
     {
       Produccion pr=vp.obtenerProduccion(j);
       ProduccionPTO nueva=new ProduccionPTO(pr.obtenerParteIzquierda(),pr.obtenerParteDerecha(),0);
       //Si no existe en el conjunto inicial se introduce.
       if(!cjto_inic.estaProduccionCjtoConfig(nueva))
         cjto_inic.insertarProduccionCjtoConfig(nueva);	 
      }	
  	}
  }
  /**
   * Función  para calculo del sucesor de un conjunto de configuracion:
   * Obtiene los conjuntos de configuracion a paratir de otro conjunto
   * de configuración.
  **/
  protected CjtoConfig calcularSucesor(Automata automata,int ind,CjtoConfig cjto_inicial,Gramatica G,Simbolo s,Vector <Integer> v)
  {
    CjtoConfig nuevo_conjunto=new CjtoConfig(ind++);
  	for(int i=0;i<v.size();i++)
  	{
  	  ProduccionPTO ppto= (ProduccionPTO) cjto_inicial.obtenerProduccionCjtoConfig(v.get(i));
  	  ProduccionPTO ppto_nuevo=new ProduccionPTO(ppto.obtenerParteIzquierda(),ppto.obtenerParteDerecha(),(ppto.posicionPunto())+1);
  	  nuevo_conjunto.insertarProduccionCjtoConfig(ppto_nuevo);
  	}
  	CjtoConfig aux=tablaClausurados.get(nuevo_conjunto.obtenerNumeroCjtoConfig());
  	if(aux==null)
  	{
  	  calcularCierre(nuevo_conjunto);
  	  if(automata.estaContenidoNodoAutomata(nuevo_conjunto))
  	    nuevo_conjunto.modificarNumeroCjtoConfig(automata.obtenerIdentificadorAutomata(nuevo_conjunto));
  	  tablaClausurados.put(nuevo_conjunto.obtenerNumeroCjtoConfig(),nuevo_conjunto);
  	}
  	return nuevo_conjunto;
  }
}