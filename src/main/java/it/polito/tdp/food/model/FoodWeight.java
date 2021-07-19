package it.polito.tdp.food.model;

public class FoodWeight implements Comparable<FoodWeight>{
	Food food;
	Double peso;
	public FoodWeight(Food food, Double peso) {
		super();
		this.food = food;
		this.peso = peso;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(FoodWeight other) {
		return other.getPeso().compareTo(this.getPeso());
	}
	@Override
	public String toString() {
		return food + " | " + peso + "\n";
	}
	
}
