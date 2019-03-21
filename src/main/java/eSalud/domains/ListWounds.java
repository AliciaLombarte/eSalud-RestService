package eSalud.domains;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"list"})
public class ListWounds{
    
	private ArrayList<WoundTracks> list;
	private String nombre;
	
	public ListWounds() {}

	public ArrayList<WoundTracks> getList() {
		return list;
	}

	public void setList(ArrayList<WoundTracks> list) {
		this.list = list;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
