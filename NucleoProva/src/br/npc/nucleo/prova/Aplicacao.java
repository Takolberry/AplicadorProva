package br.npc.nucleo.prova;

import java.io.Serializable;

public class Aplicacao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TipoAplicacao tipo;
	private int numMaxQuestoes;
	private int numMaxAlterntativas;
	
	public Aplicacao(
			TipoAplicacao tipo, 
			int numMaxQuestoes,
			int numMaxAlterntativas) {
		
		this.tipo = tipo;
		this.numMaxQuestoes = numMaxQuestoes;
		this.numMaxAlterntativas = numMaxAlterntativas;
	}
	
	public TipoAplicacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoAplicacao tipo) {
		this.tipo = tipo;
	}
	public int getNumMaxQuestoes() {
		return numMaxQuestoes;
	}
	public void setNumMaxQuestoes(int numMaxQuestoes) {
		this.numMaxQuestoes = numMaxQuestoes;
	}
	public int getNumMaxAlterntativas() {
		return numMaxAlterntativas;
	}
	public void setNumMaxAlterntativas(int numMaxAlterntativas) {
		this.numMaxAlterntativas = numMaxAlterntativas;
	}
	
	public static Aplicacao getAplicacaoNormal() {
		return new Aplicacao(TipoAplicacao.NORMAL, -1, -1);
	}
	
	public static Aplicacao getAplicacaoRandomica(int numMaxQuestoes, int numMaxAlt) {
		return new Aplicacao(TipoAplicacao.RANDOMICA, numMaxQuestoes, numMaxAlt);
	}
	
}
