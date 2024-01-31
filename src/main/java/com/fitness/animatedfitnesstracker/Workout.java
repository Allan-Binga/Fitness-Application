package com.fitness.animatedfitnesstracker;

public class Workout {
	public String name;
	public double percentage;
	public Workout(String name, double progress, int goal)
	{
		this.percentage = (progress/goal) *100;
		this.name = name;
	}
	public Workout(String name, double percentage)
	{
		this.percentage = percentage * 100;
		this.name = name;
	}
	
	
}
