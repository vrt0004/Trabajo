package ui;

import analisis.analisisSintactico.LALR1;
import analisis.analisisSintactico.LL1;
import analisis.analisisSintactico.LR1;
import analisis.analisisSintactico.SLR1;

public class Problema<T> {
	
	
	private enum Tipo {
		LL, 
		LR, 
		LALR, 
		SLR, 
	}

	private Tipo tipo;
	private T problema;
	private String fichero;
	private int numero;

	private Problema(T problema) {
		this.problema = problema;
	}

	public static Problema<LL1> LLConstruccion(
			LL1 problema, int numero) {
		Problema<LL1> LLProblema = new Problema<>(problema);
		LLProblema.tipo = Tipo.LL;
		LLProblema.setNumero(numero);
		return LLProblema;
	}

	public static Problema<LR1> LRConstruccion(
			LR1 problema, int numero,String a) {
		Problema<LR1> LRProblema = new Problema<>(problema);
		LRProblema.tipo = Tipo.LR;
		LRProblema.fichero=a;
		LRProblema.setNumero(numero);
		return LRProblema;
	}

	public static Problema<SLR1> SLRConstruccion(
			SLR1 problema, int numero) {
		Problema<SLR1> SLRProblema = new Problema<>(problema);
		SLRProblema.tipo = Tipo.SLR;
		SLRProblema.setNumero(numero);
		return SLRProblema;
	}
	public static Problema<LALR1> LALRConstruccion(
			LALR1 problema, int numero) {
		Problema<LALR1> LALRProblema = new Problema<>(problema);
		LALRProblema.tipo = Tipo.LALR;
		LALRProblema.setNumero(numero);
		return LALRProblema;
	}

	public T getProblema() {
		return this.problema;
	}
	public String getfichero() {
		return this.fichero;
	}
	public String getTipo() {
		switch (tipo) {
		case LL:
			return "LL";
		case LR:
			return "LR";
		case SLR:
			return "SLR";
		case LALR:
			return "LALR";
		
		default:
			throw new UnsupportedOperationException(
					"Argumento tipo no soportado.");
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Problema))
			return false;
		if (!((Problema) o).getTipo().equals(getTipo()))
			return false;

		switch (tipo) {
		case LL:
		case LR:
		case SLR:

		case LALR:
		default:
			return false;
		}
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		
		this.numero = numero;
	}
	public void setFichero(String fichero2) {
		
		this.fichero = fichero2;
	}
}
