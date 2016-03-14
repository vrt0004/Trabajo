package analisis;
import gramatica.*;

public interface ISalidaAnalisis
{
  public void resetearAnalisis();	
  //Escenario.
  public void setErrorAnalisis();
  public void setFinalizadoAnalisis();
  public void resetearEscenarioAnalisis();
  
  
  
  public void introducirPilaAnalisis(String s);
  public void eliminarPilaAnalisis();
  public void introducirColaAnalisis(String s);
  public void eliminarColaAnalisis();
  //Panel de producciones.
  public void introducirProduccionSalidaAnalisis(Produccion pr);
  
  
  //Panel de arbol.
  public void iniciarProduccionArbolAnalisis(Produccion pr);
  public void nuevoTerminalArbolAnalisis(Simbolo si);
  public void nuevoNoTerminalArbolAnalisis(Simbolo si);
  public void finalizarProduccionArbolAnalisis();
  

  //public void obtenerInformeAnalisis();
  
  
  
  
  
  
  
  
}