package com.example.pfc_v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeletePlayer extends Activity {
	
	private String str;
	private SharedPreferences prefs;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deleteplayer_act);
		
		//Carga del nombre de usuario a partir de la clave de la shared_pref
		prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
		bundle = getIntent().getExtras();
		str = prefs.getString(bundle.getString("userSelected"), "");
		
		//Definición de nombre de usuario
		TextView user = (TextView) findViewById(R.id.userName);
		user.setText(str);
		
		//Botón sí para eliminar un usuario
		Button yesB = (Button) findViewById(R.id.deleteYes);
		yesB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Eliminamos la preferencia que hay en str de las shared_preferences
					SharedPreferences.Editor editor = prefs.edit();
					editor.remove(str);
					editor.commit();

					Toast t = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.delUserStart) + str +
							getResources().getString(R.string.delUserEnd), Toast.LENGTH_LONG);
					t.show();
					
					startActivity(new Intent(DeletePlayer.this, LoginAct.class));
					finish();
				}
		});
		
		//Botón no para no eliminar un usuario
		Button noB = (Button) findViewById(R.id.deleteNo);
		noB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(DeletePlayer.this, LoginAct.class));
					finish();	
				}
		});
	}
}