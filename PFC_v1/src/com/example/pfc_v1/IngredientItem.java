package com.example.pfc_v1;


public class IngredientItem {
	
	//Clase para los tipos de ingredientes
	public class IngredientType {
		private String type;
		private String measureUnit;
		
		public IngredientType(String tp, String measure) {
			type = tp;
			measureUnit = measure;
		}
	};
	
	//Ingredientes disponibles
	public class Ingredient {
		private String name;
		private String determinant;
		private IngredientType type;
		private float calories;
		
		public Ingredient(String nm, String det, IngredientType tp, float cal) {
			name = nm;
			determinant = det;
			type = tp;
			calories = cal;
		}		
	};	
	
	//Un ítem ingrediente compuesto por el ingrediente más su cantidad
	private Ingredient ingredient;
	private int amount;

	public IngredientItem(Ingredient ing, int amnt) {
		ingredient = ing;
		amount = amnt;
	}
		
	public Ingredient ingredient(){
		return ingredient;
	}
	
	public int amount(){
		return amount;
	}
	
	
	public void ingredient(Ingredient ing){
		ingredient = ing;
	}
	
	public void amount(int amnt){
		amount = amnt;
	}
};
