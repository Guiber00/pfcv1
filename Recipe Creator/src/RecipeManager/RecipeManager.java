package RecipeManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RecipeManager {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static String recipeName_;
	private static String msg;

	public static void main(String[] args) {
		try {
			int opc;
			System.out.println("1: Create a Recipe");
			System.out.println("2: View a Recipe");
			System.out.println("3: Delete a Recipe");
			System.out.println("0: EXIT\n");
			System.out.println("Choose a menu option (only available 1): ");
			opc = Integer.parseInt(br.readLine());
			
			switch(opc) {
				case 1: 
					System.out.println("Recipe name: ");
					recipeName_ = br.readLine().toLowerCase();
					new RecipeCreator(recipeName_);
					break;
			
				case 2:
					System.out.println("UNDER CONSTRUCTION");
					break;
				
				case 3:
					System.out.println("UNDER CONSTRUCTION");
					break;
				
				case 0:
					System.out.println("BYE!");
					break;
					
				default:
					throw new Exception("INVALID MENU OPTION");
			}
		}
		catch (Exception MenuError) {
			msg = MenuError.getMessage();
			System.out.println(getException());
		}
	}
	
	//Recogedor de excepciones
	public static String getException() {
		return msg;
	}
}
