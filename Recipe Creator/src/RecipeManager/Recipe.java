package RecipeManager;

import java.util.ArrayList;

//Clase para manejar los ingredientes y sus cantidades
class IngredientItem {	
	private String ingredientType;
	private String ingredientName;
	private String determinant;
	private float calories;
	private float amount;
	private String measure;
	private float grams;
		
	public IngredientItem(String ingredientType_, String ingredientName_, String determinant_,
			float calories_, float amount_, String measure_, float grams_) {	
		String ingredientType = ingredientType_; //También se podrían crear con new String(cad); Equivalentes
		String ingredientName = ingredientName_;
		String determinant = determinant_;
		float calories = calories_;
		float amount = amount_;
		String measure = measure_;
		float grams = grams_;		
	}
	
	//Métodos observadores
	public String ingredientType() {return ingredientType;}
	public String ingredientName() {return ingredientName;}
	public String determinant() {return determinant;}
	public float calories() {return calories;}
	public float amount() {return amount;}
	public String measure() {return measure;}
	public float grams() {return grams;}
	
	//Métodos modificadores
	public void ingredientType(String ingredientType_) {ingredientType = ingredientType_;}
	public void ingredientName(String ingredientName_) {ingredientName = ingredientName_;}
	public void determinant(String determinant_) {determinant = determinant_;}
	public void calories(float calories_) {calories = calories_;}
	public void amount(float amount_) {amount = amount_;}
	public void measure(String measure_) {measure = measure_;}
	public void grams(float grams_) {grams = grams_;}
}

//Crea los pasos de las recetas, técnica + ingredientes
class RecipeStep {
	private String technicType;
	private String technic;
	private ArrayList<IngredientItem> ingredientsInStep;
	
	public RecipeStep(String technicType_, String technic_, ArrayList<IngredientItem> ingredientList_) {
		technicType = technicType_;
		technic = technic_;
		ingredientsInStep = new ArrayList<IngredientItem>(ingredientList_);
	}
	
	//Métodos observadores
	public String technicType() {return technicType;}
	public String technic() {return technic;}
	public ArrayList<IngredientItem> ingredientInStep() {return ingredientsInStep;}
	
	//Métodos modificadores
	public void technicType(String technicType_) {technicType = technicType_;}
	public void technic(String technic_) {technic = technic_;}
	public void ingredientInStep(ArrayList<IngredientItem> ingredientsInStep_) {
		ingredientsInStep = new ArrayList<IngredientItem>(ingredientsInStep_);
		}
}

//Creación base de una Receta: nombre
public class Recipe {
	private String recipeName;
	private ArrayList<RecipeStep> stepsInRecipe;

	public Recipe(String recipeName_, ArrayList<RecipeStep> stepsInRecipe_) {
		recipeName = recipeName_;
		stepsInRecipe = new ArrayList<RecipeStep> (stepsInRecipe_);
	}
	
	//Métodos observadores
	public String recipeName() {return recipeName;}
	public ArrayList<RecipeStep> stepsInRecipe() {return stepsInRecipe;}
	
	//Métodos modificadores
	public void recipeName(String recipeName_) {recipeName = recipeName_;}
	public void stepsInRecipe(ArrayList<RecipeStep> stepsInRecipe_) {
		stepsInRecipe = new ArrayList<RecipeStep> (stepsInRecipe_);
		}
}