package com.example.pfc_v1;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ChronoAct extends Activity {
	
	private TextView chrono;
	private CountDownTimer CDT;
	private Time t;
	private int min, sec;
	private EditText minText;
	private EditText secText;
	private MediaPlayer mepl;
	private ToggleButton controlTime;
	private boolean reseteable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Ocultar barra de título
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.chrono_act);
		
		//Construimos t para poder iniciar, pausar y detener el crono
		t = new Time();
		
		controlTime = (ToggleButton) findViewById(R.id.controlTime);
		controlTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reseteable = true;
				
				//Si el botón está check y nunca lo hemos ejecutado...
				if(((ToggleButton) v).isChecked()){	
					minText = (EditText) findViewById(R.id.setMin);
					secText = (EditText) findViewById(R.id.setSec);
					chrono = (TextView) findViewById(R.id.chronometer);
					mepl = MediaPlayer.create(ChronoAct.this, R.raw.vlad);


					if(t.toMillis(true) == 0){
						//Hacemos que min y sec sean cero si lo dejamos vacío
						min = 00; sec = 00;;
						if(!minText.getText().toString().equals(""))
							min = Integer.parseInt(minText.getText().toString());
						if(!secText.getText().toString().equals(""))
							sec = Integer.parseInt(secText.getText().toString());
				
					
						//Verificación de tiempo válido
						if(min < 0 || min > 99) {
							Toast t = Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.errorMin), Toast.LENGTH_LONG);
							t.show();
						}				
						else if(sec < 0 || sec > 59) {
							Toast t = Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.errorSec), Toast.LENGTH_LONG);
							t.show();
						}
						t.minute = min;
						t.second = sec;
					}
					
					
					//Cuenta atrás con conversor REVISARLO AL FINAL*****************
					CDT = new CountDownTimer(t.toMillis(false) + 999, 250) {	
						public void onTick(long countDown) {
							t.set(countDown);
							//Cadenas con formato aplicado de 2 dígitos
							System.out.println(countDown);
							chrono.setText(String.format("%1$02d", t.minute) +
									":" + String.format("%1$02d", t.second));
						}
						
						public void onFinish(){
							chrono.setText("00:00");
							t.set(0);
							controlTime.setChecked(false);
							mepl.start();
						}
					}.start();
				}
				else {
					CDT.cancel();
				}
			}
		});
		
		
		//Botón de reset
		Button resetTime = (Button) findViewById(R.id.resetTime);
		
		resetTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.longClick), Toast.LENGTH_LONG);
				t.show();
			}
		});
		
		resetTime.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(reseteable){
					chrono.setText("00:00");
					CDT.cancel();
					t.set(0);
					controlTime.setChecked(false);
					minText.setText("");
					secText.setText("");
				}
				return true;
			}
		});
	}
}
