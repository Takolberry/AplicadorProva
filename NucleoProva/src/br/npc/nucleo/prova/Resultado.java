package br.npc.nucleo.prova;

import java.util.ArrayList;
import java.util.List;

public class Resultado {

	private List<Questao> questoes;
	
	private List<Questao> corretas;
	
	private List<Questao> erradas;
	
	private List<Questao> naoRespondidas;
	
	private String descricaoProva;
	
	private String tempoRestante;
	
	private static final String MARGEM = String.format("%9s", " ");
	private static final String RECUO_ALT = String.format("%9s", " ");
	
	public Resultado() {
		this.corretas = new ArrayList<Questao>();
		this.erradas = new ArrayList<Questao>();
		this.naoRespondidas = new ArrayList<Questao>();
		this.questoes = new ArrayList<Questao>();
	}

	public Resultado(List<Questao> questoes) {
		this();
		this.questoes.addAll(questoes);
	}

	public List<Questao> getCorretas() {
		return corretas;
	}
	
	public int getNumerosParcialmenteCorretas() {
		int numParcialmenteCorretas = 0;
		for (Questao q : this.questoes) {
			if (q.isRespondida()) {
				if (!q.isCorreta()) {
					if (q.getValorObtido() > 0.0) {
						numParcialmenteCorretas++;
					}
				}
			}
		}
		return numParcialmenteCorretas;
	}

	public void setCorretas(List<Questao> corretas) {
		this.corretas = corretas;
	}

	public List<Questao> getErradas() {
		return erradas;
	}

	public void setErradas(List<Questao> erradas) {
		this.erradas = erradas;
	}

	public List<Questao> getNaoRespondidas() {
		return naoRespondidas;
	}

	public void setNaoRespondidas(List<Questao> naoRespondidas) {
		this.naoRespondidas = naoRespondidas;
	}

	public double getNota() {
		double nota = 0.0;
		for (Questao q : this.questoes) {
			if (q.isRespondida()) {
				nota += q.getValorObtido();
			}
		}
		return nota;
	}
	
	public double getPorcentagemAcertos() {
		double porcAcertos = getNota() / (double)getTotalProva() * 100.0;
		return porcAcertos;
	}
	
	private double getTotalProva() {
		double total = 0.0;
		for (Questao q : this.questoes) {
			total += q.getValor();
		}
		return total;
	}

	public int getNumeroQuestoes() {
		return getCorretas().size() + getNaoRespondidas().size() + getErradas().size();
	}

	public void setDescricaoProva(String descricaoProva) {
		this.descricaoProva = descricaoProva;
	}

	public void setTempoRestante(String tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	public String getResumo() {
		String msg = "";
		msg += String.format("Total de questões : %d \n", getNumeroQuestoes());
		msg += String.format("Acertos : %d \n", getCorretas().size());
		
		if (getNumerosParcialmenteCorretas() > 0) {
			msg += String.format("Acertos Parciais : %d \n", getNumerosParcialmenteCorretas());
		}
		msg += String.format("Nota : %.2f \n", getNota());
		msg += String.format("Porcentagem de acertos : %.2f%%", getPorcentagemAcertos());

		return msg;
	}
	
	public String getRelatorio() {
		StringBuilder sb = new StringBuilder();
		String resumo = String.format(
				MARGEM + 
				"Prova: %s\n" +
				"Tempo Restante: %s\n\n" +
				getResumo().replace("%","%%") +
				"\n%s", 
				this.descricaoProva,
				this.tempoRestante,
				"-------------------------------------------------------");
		
		sb.append(resumo.replaceAll("\n", "\n" + MARGEM));
		for (Questao q : this.questoes) {
			String textQuestao = getTextQuestao(q);
			sb.append(textQuestao).append("\n" + MARGEM + "-------------------------------------------------------\n");
		}
		String rel = sb.toString();
//		rel = rel.replaceAll("\n", "\n                ");
		return rel;
	}

	private String getTextQuestao(Questao q) {
		if (q instanceof Teste) {
			Teste teste = (Teste)q;
			StringBuilder sb = 
				new StringBuilder(
						"\n" + MARGEM + 
						teste.getId() + " - " + 
						teste.getTexto().replaceAll("\n", "\n" + MARGEM));
			char[] letras = "abcdefghijklmnopqrstuvxz".toCharArray();
			int contAlt = 0;
			for (Alternativa alt : teste.getAlternativas().values()) {
				String marcada = " ";
				String marcacaoCorreta = MARGEM;
				if (teste.getOpcoes().contains(alt)) {
					marcada = "X";
					if (!teste.getAlternativasCorretas().contains(alt)) {
						marcacaoCorreta = "[ERRADO]";
					} else {
						marcacaoCorreta = "[OK]";
					}
				} else {
					if (teste.getAlternativasCorretas().contains(alt)) {
						marcacaoCorreta = "[SOLUÇÃO]";
					} 
				}
				String textAlt = 
					String.format("\n%9s%c) ( %s ) %s", 
							marcacaoCorreta, 
							letras[contAlt++], 
							marcada, 
							alt.getValor().replaceAll("\n", "\n" + MARGEM + RECUO_ALT));
				sb.append(textAlt);
			}
			return sb.toString();
		}
		return "";
	}
}
