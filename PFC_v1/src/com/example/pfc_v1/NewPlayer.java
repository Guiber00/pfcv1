package com.example.pfc_v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPlayer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newplayer_act);
		
		Button okB = (Button) findViewById(R.id.okButton);
		okB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Aquí iría una excepción para no permitir sólo un espacio o que ya exista
					EditText text = (EditText) findViewById(R.id.editNew);
					SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
					String str = text.getText().toString();

					//Si no existe y no es espacio primero ni vacío
					int length = str.length();
					if(prefs.contains(str)) {
						Toast t = Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.newUserTry), Toast.LENGTH_LONG);
						t.show();
					}	
					else if(str.equals("") || str.substring(0, 1).equals(" ")
							|| str.substring(length-1, length).equals(" ")) {
						Toast t = Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.newUserVal), Toast.LENGTH_LONG);
						t.show();
					}
					else{
						SharedPreferences.Editor editorUser = prefs.edit();
						editorUser.putString(str, str);
						editorUser.commit();
						
						Toast t = Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.newUserStart) + str + 
								getResources().getString(R.string.newUserEnd), Toast.LENGTH_LONG);
						t.show();
						startActivity(new Intent(NewPlayer.this, LoginAct.class));
						finish();
					}			
				}
		});
	}
}