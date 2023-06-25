package it.polito.tdp.itunes.model;

public class Arco {
	
	private Album a1;
	private Album a2;
	private Double peso;
	public Arco(Album a1, Album a2, Double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Album getA1() {
		return a1;
	}
	public void setA1(Album a1) {
		this.a1 = a1;
	}
	public Album getA2() {
		return a2;
	}
	public void setA2(Album a2) {
		this.a2 = a2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Arco [a1=" + a1 + ", a2=" + a2 + ", peso=" + peso + "]";
	}
	
	


}
