package it.polito.tdp.food.model;

public class TestModel {
	public static void main(String[] args) {
		Model m= new Model();
		
		m.creaGrafo(5.0);
	
		System.out.println(m.listaPiatti());
		System.out.println(m.listaPiatti().size());
		System.out.println("\n\n");
		System.out.println(m.calorieMax(m.getIdMap().get(75113000)));
		System.out.println(m.calorieMax(m.getIdMap().get(75113000)).size());

	}
}
