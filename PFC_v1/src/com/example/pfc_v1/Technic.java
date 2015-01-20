package com.example.pfc_v1;


//Técnicas de cocina
public class Technic {
	
	//Clase para los tipos de técnicas
	public class TechnicType {
		private String type;
				
		public TechnicType(String tp) {
			type = tp;
		}
	};
	
	private String name;
	private TechnicType type;
	
	public Technic(String nm, TechnicType tp) {
		name = nm;
		type = tp;
	}	
};