package br.npc.prova.aplicador.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroExtensao extends FileFilter {

	private String[] exts;

	public FiltroExtensao(String ... exts) {
		this.exts = exts;
	}

	@Override
	public boolean accept(File f) {
		boolean ok = false;
		for (String ext : exts) {
			ok = ok || (f.getName().toLowerCase().endsWith("." + ext) || f.isDirectory());
		}
		return ok;
	}

	@Override
	public String getDescription() {
		StringBuffer sb = new StringBuffer("Imagens ");
		if (exts.length > 0) {
			sb.append(exts[0]);
		}
		for (int i = 1; i < exts.length; i++) {
			String ext = exts[i];
			sb.append(" ou .").append(ext);
		}
		return sb.toString();
	}

}
