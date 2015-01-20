package com.example.pfc_v1;

import java.util.LinkedList;


//Clase receta: pasos, lista de ingredientes y cantidad de cada ingrediente
public class Recipe {
	
	//Clase para los pasos de la receta: aplicación de técnicas a ingredientes
	public class RecipeStep {
		private LinkedList<IngredientItem> ingredInStep;
		private LinkedList<Technic> techInStep;
		private boolean stepCheck;					//Si el paso ya se ha realizado
		
		//Tengo que decidir si permito más de una tecnica por paso. Si no lo hiciera, cambiar la LinkedList
		//stepList por un objeto del tipo Technic 
			
		//Recibe una lista con uno o más ingredientes y una lista con
		//una o más técnicas que aplicarle a esos ingredientes (posibilidad de variable TIP)
		// Crear paso para receta: Ingrediente(s) + Técnica(s) + Consejo? + Cronómetro?
		public RecipeStep(LinkedList<IngredientItem> ing, LinkedList<Technic> tec) {
			ingredInStep = new LinkedList<IngredientItem>(ing);
			techInStep = new LinkedList<Technic>(tec);
			stepCheck = false;
		}
		
		public LinkedList<IngredientItem> ingredInSteps(){
			return ingredInStep;
		}
		
		public LinkedList<Technic> techInSteps(){
			return techInStep;
		}
		
		public boolean stepCheck(){
			return stepCheck;
		}
		
		
		public void ingredInSteps(LinkedList<IngredientItem> ing){
			ingredInStep.clear();
			ingredInStep.addAll(ing);
		}
		
		public void techInSteps(LinkedList<Technic> tec){
			techInStep.clear();
			techInStep.addAll(tec);
		}
		
		public void stepCheck(boolean check){
			stepCheck = check;
		}
	};
	
	private String name;					//Lista de ingredientes necesarios para la receta
	private LinkedList<IngredientItem> ingredientList;
	private LinkedList<RecipeStep> stepsList;
	private int level;
	private boolean recipeChecked;
	
	//El constructor recibe una lista de Ingredientes con sus cantidades y 
	//una lista de pasos de receta ordenados, e inicializa todos los datos del objeto
	public Recipe(String title, LinkedList<IngredientItem> ingreds, LinkedList<RecipeStep> steps) {
		name = title;
		ingredientList = new LinkedList<IngredientItem>(ingreds);
		stepsList = new LinkedList<RecipeStep>(steps);
		level = stepsList.size();
		recipeChecked = false;
	}
	
	//MÉTODOS RELACIONALES PARA LA INTERFAZ DE LA APLICACIÓN
	public LinkedList<IngredientItem> ingredientList(){
		return ingredientList;
	}
	
	public LinkedList<RecipeStep> stepsList(){
		return stepsList;
	}
	
	public int level(){
		return level;
	}
	
	public boolean recipeChecked(){
		return recipeChecked;
	}
	
	
	public void level(int l){
		level = l;
	}
	
	public void recipeChecked(boolean check){
		recipeChecked = check;
	}
	
	//Información general de la receta: nombre, tipo alimento, calorias, lista ingredientes, nivel...
	public String recipe_info(){
		return "info";
		//Esto irá en la implementación de la actividad para poder formatearlo mejor
	}
};
