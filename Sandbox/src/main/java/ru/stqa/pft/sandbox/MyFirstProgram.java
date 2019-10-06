package ru.stqa.pft.sandbox;

public class MyFirstProgram {

	public static void main(String[] args){
		System.out.println("Hello, world!");

		double l = 5;
		System.out.println("Pole kwadratu o boku " + l + " = " + area(l));

		double a = 4;
		double b = 6;
		System.out.println("Pole prostokąta o bokach " + a + " i "  + b + " = " + area(a, b));
	}

	public static double area (double len){
		return len * len;
	}
	public static double area(double a, double b){
		return a *b;
	}

}
