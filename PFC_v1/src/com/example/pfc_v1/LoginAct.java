package com.example.pfc_v1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginAct extends Activity {

	private ArrayList<String> data = new ArrayList<String>();
	private Spinner players;
	private String userSelected;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.login_act);
		
		//Preferencia compartida para almacenar las claves de usuarios
		prefs  = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
		data.addAll(prefs.getAll().keySet());
			
		//Menú desplegable de selección de jugador
		players = (Spinner)findViewById(R.id.listPlayers);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
											android.R.layout.simple_spinner_item, data);
		players.setAdapter(adapter);
		players.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int val, long l) {				
				//Si no hay usuarios, lanza mensaje, si hay, lo almacena en userSelected para eliminar o jugar
				if(data.isEmpty()) {
					Toast msg = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.emptyPlayer), Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					
					userSelected = (String) players.getItemAtPosition(val);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				Toast msg = Toast.makeText(getApplicationContext(),
						"aqui no hace nada", Toast.LENGTH_LONG);
				msg.show();
			}
		});
		
		//Botón para acceder al juego
		Button summitB = (Button) findViewById(R.id.summit);
		summitB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(data.isEmpty()) {
					Toast msg = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.emptyPlayer), Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					Intent intent = new Intent(LoginAct.this, MenuAct.class);
					intent.putExtra("userSelected", userSelected);
					startActivity(intent);
					finish();
				}
			}
		});
		
		//Botón para nuevo jugador
		Button newB = (Button) findViewById(R.id.newPlayer);
		newB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LoginAct.this, NewPlayer.class));
					finish();
				}
		});
		
		//Botón para eliminar usuario
		Button deleteB = (Button) findViewById(R.id.deletePlayer);
		deleteB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(data.isEmpty()) {
					Toast msg = Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.emptyPlayer), Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					Intent intent = new Intent(LoginAct.this, DeletePlayer.class);
					intent.putExtra("userSelected", userSelected);
					startActivity(intent);
					finish();
				}
			}
		});
	}
}