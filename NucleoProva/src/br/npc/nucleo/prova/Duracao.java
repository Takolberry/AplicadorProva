package br.npc.nucleo.prova;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duracao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MIN_FORMAT = "(\\d+?)\\s*?[M|m][I|i][N|n]";
	private int minutos;

	public Duracao(String duracao) {
		Pattern p = Pattern.compile(MIN_FORMAT);
		Matcher m = p.matcher(duracao);		
		if (!m.matches()) {
			throw new IllegalArgumentException("Formato inválido de duracao >> '" + MIN_FORMAT + "'");
		}
		this.minutos = Integer.parseInt(m.group(1));
	}
	
	public Duracao(int minutos) {
		this.minutos = minutos;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	
	@Override
	public String toString() {
		return String.format("%d min", this.minutos);
	}

	public static void main(String[] args) {
		String duracaoStr;
		duracaoStr = "60 min";
		test(duracaoStr);
		
		duracaoStr = "35min";
		test(duracaoStr);
		
		duracaoStr = "1Min";
		test(duracaoStr);
		
		duracaoStr = "5   \tMin";
		test(duracaoStr);
		
		duracaoStr = "5 in";
		test(duracaoStr);
	}

	private static void test(String duracaoStr) {
		Duracao d;
		try {
			d = new Duracao(duracaoStr);
			System.out.println("Minutos:" + d.getMinutos());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
