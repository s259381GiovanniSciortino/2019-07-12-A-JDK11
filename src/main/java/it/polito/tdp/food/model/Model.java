package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	Map<Integer, Food> foodIdMap;
	Graph<Food,DefaultWeightedEdge> grafo;
	
	
	public String doCreaGrafo(int nPorzioni) {
		FoodDao dao = new FoodDao();
		foodIdMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(Food f: dao.listAllFoods())
			foodIdMap.put(f.getFood_code(), f);
		for(Integer id: dao.getVertexId(nPorzioni))
			grafo.addVertex(foodIdMap.get(id));
		foodIdMap = new HashMap<>();
		for(Food f: grafo.vertexSet())
			foodIdMap.put(f.getFood_code(), f);
		for(EdgeAndWeight e: dao.getArchi(foodIdMap))
			Graphs.addEdge(grafo,foodIdMap.get(e.getFoodId1()) , foodIdMap.get(e.getFoodId2()), e.getPeso());
		String result = "";
		if(this.grafo==null) {
			result ="Grafo non creato";
			return result;
		}
		result = "Grafo creato con :\n# "+this.grafo.vertexSet().size()+" VERTICI\n# "+this.grafo.edgeSet().size()+" ARCHI\n";
		return result;
	}
	
	public String doCalorie(Food f) {
		List<FoodWeight> foodWeight = new ArrayList<>();
		for(Food food: Graphs.neighborListOf(grafo, f))
			foodWeight.add(new FoodWeight(food,grafo.getEdgeWeight(grafo.getEdge(f, food))));
		Collections.sort(foodWeight);
		String res = "Elenco dei 5 cibi aventi le “calorie congiunte” massime tra quelli adiacenti a "+f+":\n";
		int i=1;
		for(FoodWeight fw: foodWeight) {
			res+=fw;
			
			if(i==5)
				break;
			i++;
		}
		return res;	
		
	}
	
	public List<FoodWeight> getMaxCalCongiunte(Food f){
		List<FoodWeight> foodWeight = new ArrayList<>();
		for(Food food: Graphs.neighborListOf(grafo, f))
			foodWeight.add(new FoodWeight(food,grafo.getEdgeWeight(grafo.getEdge(f, food))));
		Collections.sort(foodWeight);
		return foodWeight;
	}
	
	public String doSimulazione(Food partenza, int K) {
		Simulator sim = new Simulator(this.grafo,K,this,partenza);
		sim.init();
		String msg = sim.run();
		return msg;
	}
	
	public Set<Food> getFoods(){
		return grafo.vertexSet();
	}
}
