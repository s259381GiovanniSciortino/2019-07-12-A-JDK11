package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	Double min;
	Food food;
	public Event(Double min, Food food) {
		super();
		this.min = min;
		this.food = food;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	@Override
	public int compareTo(Event other) {
		return this.getMin().compareTo(other.getMin());
	}
	
}
