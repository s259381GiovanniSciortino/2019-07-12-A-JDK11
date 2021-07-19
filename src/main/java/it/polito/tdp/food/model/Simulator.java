package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	PriorityQueue<Event> codaEventi;
	List<Food> preparati; //Lista di cibi preparati o in preparazione
	Graph<Food,DefaultWeightedEdge> grafo;
	Model model;
	
	int K;
	Food iniziale;
	Integer cibiPreparati=0;
	Double TempoTot = 0.0;
	public Simulator(Graph<Food,DefaultWeightedEdge> grafo, int K, Model model, Food iniziale) {
		this.model=model;
		this.K=K;
		this.grafo=grafo;
		this.iniziale = iniziale;
	}
	
	public void init() {
		codaEventi = new PriorityQueue<>();
		preparati = new ArrayList<>();
		List<FoodWeight> maxCal = new ArrayList<>(model.getMaxCalCongiunte(iniziale));
		int i=0;
		for(FoodWeight fw: maxCal) {
			Food food = fw.getFood();
			codaEventi.add(new Event(grafo.getEdgeWeight(grafo.getEdge(iniziale, food)),food));
			preparati.add(food);
			i++;
			if(i==K)
				break;
		}
	}
	
	public String run() {
		while(!codaEventi.isEmpty()) {
			Event evento = codaEventi.poll();
			Food ciboFinito= evento.getFood();
			Double tempoPreparazione = evento.getMin();
			this.cibiPreparati++;
			TempoTot=tempoPreparazione;
			for(Food food:Graphs.neighborListOf(grafo, ciboFinito)) {
				if(!preparati.contains(food)) {
					codaEventi.add(new Event(grafo.getEdgeWeight(grafo.getEdge(ciboFinito, food))+tempoPreparazione,food));
					preparati.add(food);
				}
			}
		}
		String res = "Simulazione avvenuta con successo!!!\nSono stati preparati #"+cibiPreparati+" cibi in un tempo di "+TempoTot+" minuti";
		return res;
	}
}
