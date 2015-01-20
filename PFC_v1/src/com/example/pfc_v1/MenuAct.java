package com.example.pfc_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.menu_act);
		
		Button chronoB = (Button) findViewById(R.id.chrono);
		chronoB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(MenuAct.this, ChronoAct.class));
				}
		});
		
		
		
		
		
		
		
		
	}
	
	//Botón de menú
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
       return true; /** true -> el menú ya está visible */
    }
   
	//Selección de opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case R.id.aboutApp:
        		startActivity(new Intent(MenuAct.this, AboutText.class));
        		break;
        	
        	case R.id.settings:
        		Toast t = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.newUserStart) + "Preferencias de configuración, próximamente" + 
						getResources().getString(R.string.newUserEnd), Toast.LENGTH_LONG);
        		t.show();
        		break;
        }
             return true;
    }

}
