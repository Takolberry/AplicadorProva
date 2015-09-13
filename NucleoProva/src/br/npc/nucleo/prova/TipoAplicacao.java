package br.npc.nucleo.prova;

public enum TipoAplicacao {

	NORMAL,
	RANDOMICA {
		@Override
		public String toString() {
			return "RANDÔMICA";
		}
	}
}
