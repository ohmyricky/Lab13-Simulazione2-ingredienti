package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private Map<Integer, Condiment> idMap;
	
	//ricorsione
	private List<Condiment> pest;
	private double pestCal;
	
	public Model() {
		this.dao= new FoodDao();
		this.idMap= new HashMap<Integer, Condiment>();
	}
	
	public void creaGrafo(Double calorie) {
		this.grafo= new SimpleWeightedGraph<Condiment, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		for(Condiment c: dao.listAllCondiment()) {
			idMap.put(c.getFood_code(), c);
		}
		
		for(Edge e: dao.listEdges(idMap)) {
			if(e.getC1().getCondiment_calories()<calorie && e.getC2().getCondiment_calories()<calorie) {
				if(!grafo.containsEdge(e.getC1(), e.getC2()))
					Graphs.addEdgeWithVertices(this.grafo, e.getC1(), e.getC2(), e.getPeso());
				//co questo metodo non considera vertici quegli alimenti che anche se sono sotto il numero di calorie,
				//non hanno però collegamenti con altri condimenti (tramite piatti comuni)
			}
		}
		
		System.out.println("Vertici grafo: "+grafo.vertexSet().size());
		System.out.println("Edges grafo: "+grafo.edgeSet().size());
	}
	
	public List<piattiCollegati> listaPiatti() {
		
		List<piattiCollegati> res= new ArrayList<piattiCollegati>();
		
		for(Condiment c: this.grafo.vertexSet()) {
			int pesoTot=0;
			List<Condiment> temp=new ArrayList<Condiment>();
			temp=Graphs.neighborListOf(this.grafo, c);
			for(Condiment c2: temp) {
				pesoTot+=this.grafo.getEdgeWeight(this.grafo.getEdge(c, c2));
			}
			piattiCollegati pc=new piattiCollegati(c, c.getCondiment_calories(), pesoTot);
			res.add(pc);
		}
		Collections.sort(res);
		return res;
	}
	
	public List<Condiment> calorieMax(Condiment co) {
		pest=null;
		pestCal=0;
		List<Condiment> parziale=new ArrayList<Condiment>();
		List<Condiment> visited=new ArrayList<Condiment>();
		
		ricorsione(co, parziale, visited);
		
		return pest;
	}
	
	private void ricorsione(Condiment co, List<Condiment> parziale, List<Condiment> visited) {
		//do always
		parziale.add(co);
		visited.add(co);
		for(Condiment c: Graphs.neighborListOf(this.grafo, co)) {
			if(!visited.contains(c))
				visited.add(c);
		}
		//term
		if(visited.size()==this.grafo.vertexSet().size()) {
			double locale=calorieTot(parziale);
			if(pest==null || locale<calorieTot(pest)) {
				pest= new ArrayList<>(parziale);
			}
		}else {
			for(Condiment c: this.grafo.vertexSet()) {
				if(!visited.contains(c))
					ricorsione(c, parziale, visited);
			}
		}
	}
	
/*	public List<Condiment> calorieMax(Condiment co) {
		pest=null;
		pestCal=0;
		List<Condiment> parziale=new ArrayList<Condiment>();
		List<Condiment> evita=new ArrayList<Condiment>();
		List<Condiment> rimasti=new ArrayList<Condiment>();
		rimasti.addAll(this.grafo.vertexSet());
		
		ricorsione(co, parziale, evita, rimasti);
		
		return pest;
	}

	private void ricorsione(Condiment co, List<Condiment> parziale, List<Condiment> evita, List<Condiment> rimasti) {
		//do always
		parziale.add(co);
		evita=Graphs.neighborListOf(this.grafo, co);
		for(Condiment c: evita) {
			if(rimasti.contains(c))
				rimasti.remove(c);
		}
		
		List<Condiment> last=new ArrayList<Condiment>();
		last.addAll(rimasti);
		last.removeAll(parziale);
		
		//term
		if(last.isEmpty()) {
			double locale=calorieTot(parziale);
			if(pest==null || locale<calorieTot(pest)) {
				pest=new ArrayList<>(parziale);
			}
		}else {
		
		//cycle
			for(Condiment c: rimasti) {
				if(!parziale.contains(c))
					ricorsione(c, parziale, evita, rimasti);
			}
		}
		
		
		
	}	*/

	private double calorieTot(List<Condiment> parziale) {
		double somma=0;
		
		for(Condiment c: parziale) {
			somma+=c.getCondiment_calories();
		}
		
		return somma;
	}

	public Graph<Condiment, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public Map<Integer, Condiment> getIdMap() {
		return idMap;
	}
	
	public List<Condiment> vertici() {
		List<Condiment> vertici=new ArrayList<Condiment>();
		vertici.addAll(this.grafo.vertexSet());
		return vertici;
		
	}
	

}
