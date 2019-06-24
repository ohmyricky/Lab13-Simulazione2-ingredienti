package it.polito.tdp.food.model;

import it.polito.tdp.food.db.Condiment;

public class piattiCollegati implements Comparable<piattiCollegati>{
	
	private Condiment c;
	private Double calorie;
	private int piatti;
	private String nome;
	
	public piattiCollegati(Condiment c, Double calorie, int piatti) {
		super();
		this.c = c;
		this.calorie = calorie;
		this.piatti = piatti;
	}

	public Condiment getC() {
		return c;
	}

	public void setC(Condiment c) {
		this.c = c;
	}

	public Double getCalorie() {
		return calorie;
	}

	public int getPiatti() {
		return piatti;
	}

	@Override
	public int compareTo(piattiCollegati o) {
		return o.getCalorie().compareTo(this.getCalorie());
	}

	@Override
	public String toString() {
		return "c=" + c + ", calorie=" + calorie + ", piatti=" + piatti + "\n";
	}
	
	

}
