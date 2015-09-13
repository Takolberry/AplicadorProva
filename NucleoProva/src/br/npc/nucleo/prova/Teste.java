package br.npc.nucleo.prova;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Teste extends Questao implements Cloneable {
	
	private Map<String, Alternativa> alternativas;
	private Set<Alternativa> alternativasCorretas = new HashSet<Alternativa>();
	private Set<Alternativa> opcoes = new HashSet<Alternativa>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Teste(
			String id, 
			String texto, 
			double valor,
			Map<String, Alternativa> alternativas,
			Set<Alternativa> alternativasCorretas) {
		super(id, texto, valor);
		this.alternativas = alternativas;
		this.alternativasCorretas.addAll(alternativasCorretas);
	}

	public Teste() {
		super();
	}

	
//	public void setOpcao(Alternativa opcao) {
//		this.opcao = opcao;
//	}
//
//	@Override
//	public boolean isCorreta() {
//		return opcao == null ? false : opcao.equals(this.alternativaCorreta);
//	}
	
	@Override
	/**
	 * Retorna verdadeiro se todas as alternativas estiverem corretas
	 */
	public boolean isCorreta() {
		Set<Alternativa> opcoes = new HashSet<Alternativa>(this.alternativasCorretas);
		opcoes.retainAll(this.opcoes);
		if (opcoes.size() == this.alternativasCorretas.size()) {
			return true;//Se todas as alternativas estiverem corretas
		} else {
			return false;//Ao menos uma alternativa estiver correta
		}
	}
	
	@Override
	/**
	 * Retorna true quando uma resposta ja foi atribuida para a questao
	 * ou false caso contrário
	 */
	public boolean isRespondida() {
		return (this.opcoes.size() > 0);
	}
	
	public double getValorObtido() {
		double valorObtido = 0.0;
		if (this.alternativasCorretas.size() == 1) {
			if (isCorreta()) {
				valorObtido = super.getValor();
			}
		} else {
			Set<Alternativa> opcoesFalsasMarcadas = new HashSet<Alternativa>(this.opcoes);
			opcoesFalsasMarcadas.removeAll(this.alternativasCorretas);
			
			Set<Alternativa> opcoesVerdadeirasNaoMarcadas = new HashSet<Alternativa>(this.alternativasCorretas);
			opcoesVerdadeirasNaoMarcadas.removeAll(this.opcoes);
			
			int numAlternativas = this.alternativas.keySet().size();
			int numAlternativasCorretamenteMarcadas = numAlternativas - opcoesFalsasMarcadas.size() - opcoesVerdadeirasNaoMarcadas.size();
			
			double proporcao = numAlternativasCorretamenteMarcadas/(double)numAlternativas;
			
			valorObtido = proporcao * super.getValor();
		}
		return valorObtido;
	}

	public Alternativa getAlternativa(String id) {
		return this.alternativas.get(id);
	}
	
	/**
	 * Retorna o conjunto das opcoes selecionadas
	 * @return
	 */
	public Set<Alternativa> getOpcoes() {
		return opcoes;
	}

	public Map<String, Alternativa> getAlternativas() {
		return this.alternativas;
	}

	public Set<Alternativa> getAlternativasCorretas() {
		return alternativasCorretas;
	}

	public void setAlternativasCorretas(Set<Alternativa> alternativasCorretas) {
		this.alternativasCorretas = alternativasCorretas;
	}

	public void setAlternativas(Map<String, Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

	
	public boolean exatamenteIgual(Teste t) {
		if (!this.equals(t)) {
			return false;
			
		} else if (super.getTexto().equals(t.getTexto()) 
				&& super.getValor() == t.getValor()
				&& this.alternativas.equals(t.alternativas)
				&& this.alternativasCorretas.equals(t.alternativasCorretas)
				&& this.opcoes.equals(t.opcoes)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
