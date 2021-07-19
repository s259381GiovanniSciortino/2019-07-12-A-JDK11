package it.polito.tdp.food.model;

public class EdgeAndWeight {
	int foodId1;
	int foodId2;
	Double peso;
	public EdgeAndWeight(int foodId1, int foodId2, Double peso) {
		super();
		this.foodId1 = foodId1;
		this.foodId2 = foodId2;
		this.peso = peso;
	}
	public int getFoodId1() {
		return foodId1;
	}
	public void setFoodId1(int foodId1) {
		this.foodId1 = foodId1;
	}
	public int getFoodId2() {
		return foodId2;
	}
	public void setFoodId2(int foodId2) {
		this.foodId2 = foodId2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
}
