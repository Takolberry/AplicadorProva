package br.npc.nucleo.prova;

import java.io.Serializable;

public abstract class Questao implements Serializable , Comparable<Questao>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String texto;
	private double valor;

	public Questao(String id, String texto, double valor) {
		this.id = id;
		this.texto = texto;
		this.valor = valor;
	}
	
	public Questao() {
	}

	/**
	 * 
	 * <p>
	 * <ol>
	 * 		<li> false :
	 * 				<ol>
	 * 					<li> Caso nenhuma resposta tenha sido fornecida para a questão 
	 * ()</li>
	 * 					<li> Caso a resposta fornecida para a questao nao seja a correta</li>
	 *  			</ol>
	 *  	</li>
	 * 		<li> true : Caso a resposta fornecida para a questao seja a correta </li>
	 * </ol>
	 * @see {@link Questao#isRespondida()}
	 * </p>
	 * @return
	 */
	public abstract boolean isCorreta();
	
	/**
	 * <p>
	 * 	Retorna true quando ja foi dada uma resposta para a questao e false caso contrário
	 * </p>
	 * @return
	 */
	public abstract boolean isRespondida();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public abstract double getValorObtido();

	@Override
	public String toString() {
		return "Questao - " + id + "[" + valor + "]'" + texto + "'";
	}
	
	public String toStringResumido(int tam) {
		String nohText = toString().replaceAll("Questao - ", "").trim();
		String nohTextFinal = nohText.substring(0, Math.min(tam, nohText.length()));
		if (nohText.length() > nohTextFinal.length()) {
			nohTextFinal += "...";
		}
		nohText = nohTextFinal;
		return nohText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Questao q) {
		int thisId = Integer.parseInt(this.getId());
		int qId = Integer.parseInt(q.getId());
		return thisId - qId;
	}
	
	
}
