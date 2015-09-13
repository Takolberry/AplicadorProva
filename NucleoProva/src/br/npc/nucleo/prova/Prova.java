package br.npc.nucleo.prova;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Prova implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String desc;
	private String turma;
	private String disciplina;
	private String professor;
	private Duracao duracao = new Duracao(0);
	private String escola;
	private String tipo;
	private Aplicacao aplicacao = new Aplicacao(TipoAplicacao.NORMAL, -1, -1);
	
	private List<Questao> questoes =  new ArrayList<Questao>();

	public Prova(List<Questao> questoes) {
		this.questoes = questoes;
	}
	
	public Prova() {
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Duracao getDuracao() {
		return duracao;
	}

	public void setDuracao(Duracao duracao) {
		this.duracao = duracao;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}

	public int getNumeroRespondidas() {
		int respondidas = 0;
		for (Questao q : getQuestoes()) {
			if (q.isRespondida()) {
				respondidas++;
			}
		}
		return respondidas;
	}
	
	private Resultado avaliar(String desc, String tempoRestante) {
		Resultado resultado = new Resultado(getQuestoes());
		resultado.setDescricaoProva(desc);
		resultado.setTempoRestante(tempoRestante);
		for (Questao q : getQuestoes()) {
			if (!q.isRespondida()) {
				resultado.getNaoRespondidas().add(q);
			} else {
				if (q.isCorreta()) {
					resultado.getCorretas().add(q);
				} else {
					resultado.getErradas().add(q);
				}
			}
		}
		return resultado;
	}
	
	public Resultado getResultado(String tempoRestante) {
		return avaliar(getDesc(), tempoRestante);
	}
	
	@Override
	public String toString() {
		return "Prova [" + (desc != null? desc : "") + "]";
	}
}
