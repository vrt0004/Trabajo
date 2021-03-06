/* Generated By:JavaCC: Do not edit this line. ParserGramatica.java */
package parser;
import java.io.*;
import gramatica.*;

/**
* <b>Descripci�n</b><br>
* Clase que implementa el parser YACC.
* <p>
* <b>Detalles</b><br>
* Clase que permite cargar un archivo guardado, en el sistema.<br>
* </p> 
* @author Carlos G�mez Palacios.
* @version 1.0
*/





public class ParserGramatica implements ParserGramaticaConstants {
  private static boolean DEBUG=false;//Permite mostrar en pantalla mensajes de estado.
  private  ParserGramaticaTokenManager TokMan;
  @SuppressWarnings("unused")
private  ParserGramatica parser;
  @SuppressWarnings("unused")
private   ParserGramatica nuevo;
  private  static String s_nulo="EPS";
  private  IParserYacc implementacion=null;//Implementacion para dar de alta la gram�tica.

  /**
   * Constructor del parser para generar la gram�tica.
   * @param d Flag que indica si se quieren mostrar por pantalla los estados por los que pasar el parser o no.
   * @param ipy Implementaci�n de la interface para dar de alta la gram�tica en el sistema.
  **/
  public ParserGramatica(boolean d,IParserYacc ipy)
  {
    DEBUG=d;
    implementacion=ipy;
    if(DEBUG==true)
      System.out.println("\tDEBUG:Parser creado.");
  }
  /**
   * Funci�n que parsea una cadena de texto pasada como par�metro. 
   * @param datos Cadena de texto que contiene la gram�tica en formato YACC.
   * @return Gram�tica generada por la entrada. 
   * @throws ParseException ParseException
  **/
  public Gramatica parsearGramaticaCadena(String datos)throws ParseException
  {
    @SuppressWarnings("unused")
	JavaCharStream jcs;
    TokMan=new ParserGramaticaTokenManager(jcs=new JavaCharStream(new StringReader(datos)),DEFAULT);
    if(DEBUG==true)System.out.println("\tDEBUG:Comienza a parsear la cadena.");
    token_source=TokMan;
    ReInit(token_source);
    if(DEBUG==true)System.out.println("\tDEBUG:Se inicializa el Token Manager.");
        try
        {Inicio();}
    catch(ParseException e)
    {throw e;}
    return implementacion.obtenerGramatica();
  }
  /**
   * Funci�n que parsea el contenido de un archivo de texto cuya ruta espasada como par�metro. 
   * @param origen Ruta del archivo de texto que contiene la gram�tica en formato YACC.
   * @return Gram�tica generada por la entrada. 
   * @throws IOException IOException
   * @throws ParseException ParseException

  **/
  public Gramatica parsearGramaticaArchivo(String origen)throws IOException,ParseException
  {
        FileReader fr;
    @SuppressWarnings("unused")
	JavaCharStream jcs;
    try
    {fr=new FileReader(origen);}
    catch(IOException e){throw (e);}
    TokMan=new ParserGramaticaTokenManager(jcs=new JavaCharStream(new BufferedReader(fr)),DEFAULT);
    if(DEBUG==true)System.out.println("\tDEBUG:Comienza a parsear el parser con archivo :"+origen);
    token_source=TokMan;
    ReInit(token_source);
    if(DEBUG==true)System.out.println("\tDEBUG:Se inicializa el Token Manager.");
    try
    {
          Inicio();
          if(implementacion!=null)
            return implementacion.obtenerGramatica();
        }
    catch(ParseException e)
        {throw (e);}
        return null;
  }
  //////////////////////////////////////////////////////////////////////////
  //Funciones que llaman a la interface:
  //////////////////////////////////////////////////////////////////////////
  public  void terminal(String nuevo_terminal)
  {
    if(implementacion!=null)
      implementacion.setTerminal(nuevo_terminal);
  }
  public  void noTerminal(String nuevo_no_terminal)
  {
    if(implementacion!=null)
    {
          //if(nuevo_no_terminal.equals(s_nulo))
            //implementacion.setNulo();
          //else
        implementacion.setNoTerminal(nuevo_no_terminal);
    }
  }
  public  void simbolo_inicial(String simbolo_inicial)
  {
    if(implementacion!=null)
     implementacion.setSimboloInicial(simbolo_inicial);
  }
  public  void simbolo_nulo(String nulo)
  {
    if(implementacion!=null)
      s_nulo=nulo;
  }
  public  void inicio_produccion(String pi)
  {
    if(implementacion!=null)
      implementacion.setParteIzquierdaProduccion(pi);
  }
  public  void anadir_parte_derecha_simbolo(String pd)
  {
    if(implementacion!=null)
    {
      if(pd.equals(s_nulo))
            implementacion.setNuloParteDerechaProduccion();
          else
        implementacion.setParteDerechaSimboloProduccion(pd);
    }
  }
  public  void anadir_parte_derecha_terminal(String pd)
  {
    if(implementacion!=null)
      implementacion.setParteDerechaTerminalProduccion(pd);
  }
  public  void or()
  {
    if(implementacion!=null)
      implementacion.orProduccion();
  }
  public  void fin_produccion()
  {
    if(implementacion!=null)
      implementacion.finProduccion();
  }

  final public void Inicio() throws ParseException {
                 if(DEBUG==true)System.out.println("\tDEBUG:Comienza el parseo........");
    definiciones();
    jj_consume_token(SEPARADOR);
    Reglas();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEPARADOR:
      Rutinas();
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
  }

  ///////////////////////////////////////////////////
  //DEFINICIONES:
  ///////////////////////////////////////////////////
  final public void definiciones() throws ParseException {
                       if(DEBUG==true)System.out.println("\tDEBUG:Comienzan las definiciones......");
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case _DEF_CODIGO:
      jj_consume_token(_DEF_CODIGO);
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    label_1:
    while (true) {
      definicionSimbolos();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case _TOKEN:
      case _START:
      case _LEFT:
      case _RIGHT:
      case _TYPE:
      case _UNION:
      case _NONASSOC:
      case _NULO:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_1;
      }
    }
  }

  final public void definicionSimbolos() throws ParseException {
                             Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case _START:
      jj_consume_token(_START);
      t = jj_consume_token(identificador);
                                  simbolo_inicial(t.image);
      break;
    case _UNION:
      jj_consume_token(_UNION);
      jj_consume_token(_ACCION);
      break;
    case _NULO:
      jj_consume_token(_NULO);
      t = jj_consume_token(identificador);
                             simbolo_nulo(t.image);
      break;
    case _LEFT:
    case _RIGHT:
    case _TYPE:
    case _NONASSOC:
      Rword();
      Tag();
      nList();
      break;
    case _TOKEN:
      jj_consume_token(_TOKEN);
      Tag();
      nlistToken();
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void Rword() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case _LEFT:
      jj_consume_token(_LEFT);
      break;
    case _RIGHT:
      jj_consume_token(_RIGHT);
      break;
    case _NONASSOC:
      jj_consume_token(_NONASSOC);
      break;
    case _TYPE:
      jj_consume_token(_TYPE);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void nlistToken() throws ParseException {
                     Token t;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case identificador:
        t = jj_consume_token(identificador);
                          terminal(t.image);
        break;
      case literal:
        t = jj_consume_token(literal);
                    String term=t.image;terminal(term.substring(1,term.length()-1));
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case identificador:
      case literal:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_2;
      }
    }
  }

  final public void nList() throws ParseException {
    nmno();
    masnlist();
  }

  final public void masnlist() throws ParseException {
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case identificador:
      case literal:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_3;
      }
      nmno();
    }
  }

  final public void Tag() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 27:
      jj_consume_token(27);
      jj_consume_token(identificador);
      jj_consume_token(28);
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
  }

  final public void nmno() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case identificador:
      jj_consume_token(identificador);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case numero:
        jj_consume_token(numero);
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }

      break;
    case literal:
      jj_consume_token(literal);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case numero:
        jj_consume_token(numero);
        break;
      default:
        jj_la1[10] = jj_gen;
        ;
      }

      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  ///////////////////////////////////////////////////
  //REGLAS:
  ///////////////////////////////////////////////////
  final public void Reglas() throws ParseException {
                 if(DEBUG==true)System.out.println("\tDEBUG:Comienzan las reglas...");
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case identificador:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_4;
      }
      Regla();
    }
  }

  final public void Regla() throws ParseException {
                Token tok;
    tok = jj_consume_token(identificador);
                              inicio_produccion(tok.image);
    jj_consume_token(COLON);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LLAVE:
      jj_consume_token(LLAVE);
      accion();
      break;
    default:
      jj_la1[13] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case _PREC:
      jj_consume_token(_PREC);
      jj_consume_token(identificador);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LLAVE:
        jj_consume_token(LLAVE);
        accion();
        break;
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
    RBody();
    Prec();
  }

  final public void RBody() throws ParseException {
                Token tok;
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case identificador:
        tok = jj_consume_token(identificador);
                            anadir_parte_derecha_simbolo(tok.image);
        break;
      case literal:
        tok = jj_consume_token(literal);
                       String pd=tok.image;anadir_parte_derecha_terminal(pd.substring(1,pd.length()-1));
        break;
      case VERT_BAR:
        jj_consume_token(VERT_BAR);
                      or();
        break;
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case _ACCION:
        accion();
        break;
      default:
        jj_la1[17] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case _PREC:
        jj_consume_token(_PREC);
        jj_consume_token(identificador);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case _ACCION:
          accion();
          break;
        default:
          jj_la1[18] = jj_gen;
          ;
        }
        break;
      default:
        jj_la1[19] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VERT_BAR:
      case identificador:
      case literal:
        ;
        break;
      default:
        jj_la1[20] = jj_gen;
        break label_5;
      }
    }
  }

  final public void accion() throws ParseException {
    jj_consume_token(_ACCION);
  }

  final public void Prec() throws ParseException {
    jj_consume_token(29);
               fin_produccion();
  }

  final public void Rutinas() throws ParseException {
                  if(DEBUG==true)System.out.println("\tDEBUG:Comienzan las rutinas...");
    jj_consume_token(SEPARADOR);
  }

  public ParserGramaticaTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[21];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x8000,0x400000,0x7bc0,0x7bc0,0x2b00,0x1800000,0x1800000,0x1800000,0x8000000,0x4000000,0x4000000,0x1800000,0x800000,0x100000,0x100000,0x400,0x1840000,0x200000,0x200000,0x400,0x1840000,};
   }

  public ParserGramatica(java.io.InputStream stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ParserGramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  public ParserGramatica(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ParserGramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  public ParserGramatica(ParserGramaticaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  public void ReInit(ParserGramaticaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  @SuppressWarnings("rawtypes")
private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  @SuppressWarnings("unchecked")
public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[30];
    for (int i = 0; i < 30; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 21; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 30; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
