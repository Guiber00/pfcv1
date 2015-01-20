package com.example.pfc_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class IniAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.ini_act);
		
		View startButton = (View) findViewById(R.id.startButton);
		startButton.setOnClickListener(new View.OnClickListener()
			{ @Override
				public void onClick(View v) {
					startActivity(new Intent(IniAct.this, LoginAct.class));
				}
			});
	}

}