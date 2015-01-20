package com.example.pfc_v1;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainMenu extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
       return true; /** true -> el menú ya está visible */
    }
   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case R.id.aboutApp:
        		startActivity(new Intent(this, AboutText.class));
        		break;
        	
        	case R.id.settings:
        		Toast t = Toast.makeText(getApplicationContext(),
						"Preferencias de configuración, próximamente.." ,Toast.LENGTH_LONG);
        		t.show();
        		break;
        }
             return true; /** true -> consumimos el item, no se propaga*/
    }
}