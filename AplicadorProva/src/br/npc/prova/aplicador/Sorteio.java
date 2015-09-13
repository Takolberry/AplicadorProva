package br.npc.prova.aplicador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorteio {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite os numeros dos presentes:");
		List<String> lista = new ArrayList<String>();
		String[] chamada = sc.nextLine().split(" ");
		for (String string : chamada) {
			lista.add(string);
		}
		Collections.shuffle(lista);
		int g = 0;
		for (String string : lista) {
			System.out.print(string + " ");
			g++;
			g = g % 3;
			if (g == 0) {
				System.out.println("");
			}
		}
	}
}
