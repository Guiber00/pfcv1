package RecipeManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class RecipeCreator {
	
	private String ingrType_; 
	private String ingrName_;
	private String determinant_;
	private float calories_ = 0;
	private float amount_ = 0;
	private String measure_;
	private float grams_ = 0;
	private String techType_; 
	private String techName_;
	
	public class ItemInStep {
		private int itemID_;
		private String itemName_;
		private int numUses_;
		
		public ItemInStep(int id, String name) {
			itemID_ = id;
			itemName_ = name;
			numUses_ = 0;
		}

		public String getItemInStep() {
			return "\t" + itemName_ + "\t" + itemID_ +  ",\t" + numUses_;
		}
		
		public int getItemID() {return itemID_;}
		public String getItemName() {return itemName_;}
		public void numUsesInc() {numUses_ += 1;}
	}
	private ArrayList<ItemInStep> ItemList;
	
	
	private SQLConnector connection;
	private String[][] queryResult;
	private SQLHelper queryResultSet;
	private String querySelect;
	private String queryInsert;
	private String menuOpc;
	private String msg;
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


	public RecipeCreator(String recipeName_) throws Exception {
		connection = new SQLConnector();
		recipeName_.toLowerCase();
		select("Recipes", "recipeName", recipeName_);		System.out.println("breakpoint existencia receta");
		
		//Comprueba si existe una receta con ese nombre en la tabla
		try {			System.out.println("HOLA!!!!!");				System.out.println("queryResult.noRow()****:     " + noRow(queryResult));
			if(noRow(queryResult)) //isEmpty???
				insert("Recipes", "recipeName", recipeName_);
			else
				throw new Exception("THE RECIPE " + recipeName_  + " ALREADY EXISTS");
		}
		catch (Exception existsRecipe) {
			msg = existsRecipe.getMessage();
			System.out.println(getException());
			System.exit(0);
		}
		
		//Creación de la lista de ingredientes
		System.out.println("Begin the Ingredients introduction: ");	
		menuOpc = "yes";
		try {
			ItemList = new ArrayList<ItemInStep>();
			while (menuOption(menuOpc)) {
				System.out.println("Ingredient number " + (ItemList.size() + 1) + ": \n");
				System.out.println("By order: ");
				
				//Introduce en la BD el tipo de Ingrediente mediante un procedimiento si no existe
				ingredientTypeCreator();
				//Introduce en la BD el ingrediente mediante un procedimiento si no existe
				ingredientCreator();
				//Introduce en la BD el tipo de medida mediante un procedimiento si no existe
				measureCreator();				
				//Introduce en la BD el ItemIngrediente
				itemCreator();
				
				//Crea un OBJETO Item-ID para poder gestionar en enlace con las técnicas
				int thisItemID = getID("idIngredientItems", "IngredientItems");
				ItemInStep itemID_ = new ItemInStep(thisItemID, ingrName_);
				ItemList.add(itemID_);
				
				//Continuar introduciendo más ítems?
				System.out.println("Continue with the Ingredients introduction?: \n");
				menuOpc = br.readLine().toLowerCase();
			}
		}
		catch (Exception IngredListCreator) {
			msg = IngredListCreator.getMessage();
			System.out.println(getException());
		}
		
		//Creación de PASOS de la receta técnica-ítems ingredientes
		System.out.println("Begin the Technics assignation for each step: \n");	
		menuOpc = "yes";
		try {
			while (menuOption(menuOpc)) {
				System.out.println("Declaration and link of Technics with IngredientItems: \n");
				
				//Introduce en la BD el tipo de Técnica mediante un procedimiento si no existe
				technicTypeCreator();
				//Introduce en la BD la Técnica mediante un procedimiento si no existe
				technicCreator();
				
				while (menuOption(menuOpc)) {
					System.out.println("Linking one Technic with some IngredienItems: \n");
					int idTechnic = getID("idTechnics", "Technics", "techName", techName_);
					System.out.println("Technic name: " + techName_ + "with id: " + idTechnic + "\n");
					
					//Listado de Ítems ingrediente
					System.out.println("IngredItem index \t IngredName \t ItemID \t Times used \n");
					for (int i = 1; i <= ItemList.size(); i++) 
						System.out.println(i + "\t" + ItemList.get(i).getItemInStep() + "\n");
					
					//Selección de ingrediente para ligar con la técnica
					System.out.println("Select one Ingredient for linking with the Technic: ");
					int iSelect =Integer.parseInt(br.readLine());
					if(iSelect > ItemList.size() || iSelect <= 0)
						throw new Exception("The chosen ingredient in the list doesn't exists");
					
					//Comprueba que no se ha usado ya el ingrediente para la técnica
					select("RecipeSteps", "idTechnics", idTechnic, "idItemIngredients", ItemList.get(iSelect).getItemID());
					
					if(noRow(queryResult)) {
						//Unión de una Técnica con un ingrediente y una receta
						insert("RecipeSteps",
								"idRecipes, idTechnics, idItemIngredients",
								getID("idRecipes", "Recipes") + ", " + idTechnic + ", " + ItemList.get(iSelect).getItemID());
						ItemList.get(iSelect).numUsesInc();
					
						//Continuar introduciendo más Ítems para la técnica?
						System.out.println("Continue with the Technic " + techName_ + " linked?: ");
						menuOpc = br.readLine().toLowerCase();
					}
					else 
						throw new Exception("The Item"  + ItemList.get(iSelect).getItemName() + "was selected before");
				}
				//Continuar introduciendo más PASOS?
				System.out.println("Continue with a new STEP?: ");
				menuOpc = br.readLine().toLowerCase();
			}
		}
		catch (Exception StepsCreator) {
			msg = StepsCreator.getMessage();
			System.out.println(getException());
		}
		connection.closeConnector();
		System.out.println("The Recipe " + recipeName_ + " has been created.");
	}
	
	
	//PROCEDIMIENTO DE CREACIÓN DEl TIPO INGREDIENTE
	private void ingredientTypeCreator() {
		try {
			//Comprobación de existencia Tipo de ingrediente
			System.out.println("Ingredient type: "); ingrType_ = br.readLine().toLowerCase();
			select("IngredientTypes", "ingrType", ingrType_);
			if(noRow(queryResult))
				insert("IngredientTypes", "ingrType", ingrType_);
			else
				throw new Exception("ingredientType " + ingrType_ + "ALREADY EXISTS" );
		}
		catch (Exception ingredientTypeCreator) {
			msg = ingredientTypeCreator.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//PROCEDIMIENTO DE CREACIÓN DE INGREDIENTE
	private void ingredientCreator() {
		try {
			//Comprobación de existencia Ingrediente
			System.out.println("Ingredient name: "); ingrName_= br.readLine().toLowerCase();
			select("Ingredients", "ingrName", ingrName_);
			if(noRow(queryResult)) {
				
				//Obtención id de tipo ingrediente
				int idIngrType = getID("idIngredientTypes", "IngredientTypes", "ingrType", ingrType_); System.out.println("breakpoint first getID OUT ***");
				
				//Entrada por teclado de calorias y determinante
				System.out.println("Determinant: "); determinant_= br.readLine().toLowerCase();
				System.out.println("Calories: "); calories_= Float.parseFloat(br.readLine());
				
				//Comprobación del determinante y obtención de id
				select("idDeterminants", "Determinants", "determinant", determinant_);
				int idDetermin = -1;
				if(noRow(queryResult))
					throw new Exception("The determinat " + determinant_ + "IS NOT PERMITTED" );
				else {
					queryResult = queryResultSet.getDataOut();
					idDetermin = Integer.parseInt(queryResult[0][0]);
				}

				//Inserción final en la base de datos del ingrediente
				insert("Ingredients", 
						"ingrName, idDeterminants, ingrCalories, idIngredientTypes",
						ingrName_ + "', " + idDetermin + ", " + calories_ + ", " + idIngrType);
			}
			else
				throw new Exception("INGREDIENT " + ingrName_ + "ALREADY EXISTS" );
		}
		catch (Exception ingredientCreator) {
			msg = ingredientCreator.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//PROCEDIMIENTO DE CREACIÓN DE MEDIDA
	private void measureCreator() {
		try {
			//Entrada por teclado de Medida y comprobación de existencia
			System.out.println("Measure type for ingredient: "); measure_= br.readLine().toLowerCase();				
			select("Measures", "measure", measure_);
			if(noRow(queryResult)) {
				System.out.println("Grams coeficient: "); grams_= Float.parseFloat(br.readLine());
				insert("Measures", "measure, grams", "'" +  measure_, grams_);
			}
			else
				throw new Exception("measure " + measure_ + "ALREADY EXISTS" );
		}
		catch (Exception measureCreator) {
			msg = measureCreator.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//PROCEDIMIENTO DE CREACIÓN DE ITEM
	private void itemCreator() {
		try {
			//Creación del Item o Ingrediente con su cantidad
			System.out.println("Amount: "); amount_= Float.parseFloat(br.readLine());
								
			//Obtención id de tipo de medida
			int idMeasure = getID("idMeasures", "Measures", "measure", measure_);
				
			//Obtención id de Ingrediente
			int idIngredient = getID("idIngredients", "Ingredients", "ingrName", ingrName_);
				
			insert("IngredientItems", 
					"amount, idMeasures, idIngredients",
					amount_ + "', " + idMeasure + ", " + idIngredient);
		}
		catch (Exception itemCreator) {
			msg = itemCreator.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//PROCEDIMIENTO DE CREACIÓN DEL TIPO DE TÉCNICA
	private void technicTypeCreator() {
		try {
			//Comprobación de existencia Tipo de ingrediente
			System.out.println("Technic type: "); techType_ = br.readLine().toLowerCase();
			select("TechnicTypes", "techType", techType_);
			if(noRow(queryResult))
				insert("TechnicTypes", "techType", techType_);
			else
				throw new Exception("TechnicType " + techType_ + "ALREADY EXISTS" );
		}
		catch (Exception technicTypeCreator) {
			msg = technicTypeCreator.getMessage();
			System.out.println(getException());
		}
	}	
	
	//PROCEDIMIENTO DE CREACIÓN DE TÉCNICA
	private void technicCreator() {
		try {
			//Comprobación de existencia Técnica
			System.out.println("Technic name: "); techName_= br.readLine().toLowerCase();
			select("Technics", "techName", techName_);
			
			//Obtención id de tipo técnica
			int idTechType = getID("idTechnicTypes", "TechnicTypes", "techType", techType_);
			
			if(noRow(queryResult)) {
				//Inserción final en la base de datos de la técnica
				insert("Technics", 
						"techName, idTechnicTypes",
						techName_ + "', " + idTechType);
			}
			else
				throw new Exception("TECHNIC " + techName_ + "ALREADY EXISTS" );
		}
		catch (Exception technicCreator) {
			msg = technicCreator.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//Método auxiliar para ejecutar consultas de tipo SELECT
	public String[][] select(String table, String column, String var) {
		querySelect = "SELECT * FROM " + table + " WHERE " + column + " = '" + var + "';";
		queryResultSet = new SQLHelper(connection, querySelect);
		return queryResultSet.getDataOut();
	}
	public String[][] select(String selected, String table, String column, String var) {
		querySelect = "SELECT " + selected + " FROM " + table + " WHERE " + column + " = '" + var + "';";
		queryResultSet = new SQLHelper(connection, querySelect);
		return queryResultSet.getDataOut();
	}
	public String[][] select(String idColumn, String table) {
		querySelect = "SELECT " + idColumn + " FROM " + table + " ORDER BY " + idColumn + " DESC;";
		queryResultSet = new SQLHelper(connection, querySelect);
		return queryResultSet.getDataOut();
	}
	public String[][] select(String table, String column1, int var1, String column2, int var2) {
		querySelect = "SELECT * FROM " + table + " WHERE " + column1 + " = " + var1 + "AND" + column2 + " = " + var2 + ";";
		queryResultSet = new SQLHelper(connection, querySelect);
		return queryResultSet.getDataOut();
	}
	
	//Método auxiliar para ejecutar consultas de tipo INSERT
	public void insert(String table, String column, String var) {
		queryInsert = "INSERT INTO " + table + "(" + column + ") VALUES ('" + var + "');";
		new SQLHelper(connection, queryInsert);
	}
	
	public void insert(String table, String column, String var1, float var2) {
		queryInsert = "INSERT INTO " + table + "(" + column + ") VALUES ('" + var1 + "',  "+ var2 + ");";
		new SQLHelper(connection, queryInsert);
	}
	
	//Método auxiliar para obtener una ID de un elemento concreto
	public int getID(String idField, String table, String column, String var) {
		select(idField, table, column, var);
		System.out.println("breakpoint queryResultSet.getDataOut()[0][0]    " + queryResultSet.getDataOut()[0][0].toString());
		return Integer.parseInt(queryResultSet.getDataOut()[0][0]); //AL NO INTRODUCIR EL TIPO EN LA BD, OBTIENE NULL EN VEZ DE LA ID Y POR ESO FALLA
	}
	
	//Método auxiliar para obtener una ID de un elemento sin referencia explícita (último en ser introducido)
	public int getID(String idColumn, String table) {
		select(idColumn, table);
		return Integer.parseInt(queryResultSet.getDataOut()[0][0]);
	}
	
	//Devuelve el índice de una fila para comprobar si existe o no (0)
	public boolean noRow(String[][] queryResult_) {
		if(queryResult_[0][0] == null)
			return true;
		else
			return false;
	}
	
	
	//Opción para continuar con la creación de elementos, cualquier cosa diferente de yes/sí/si será false
	public boolean menuOption(String opc) {
		if(menuOpc.toLowerCase() == "yes" || menuOpc.toLowerCase() == "si"
					|| menuOpc.toLowerCase() == "sí")
			return true;
		else
			return false;
	}
	
	//Recogedor de excepciones
	public String getException() {
		return msg;
	}
}