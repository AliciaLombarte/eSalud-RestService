package eSalud.domains;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"nombre"})
public class Protocolo{
    
	private String nombre;
	private String cuestionario;
	private int frecuencia;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(String cuestionario) {
		this.cuestionario = cuestionario;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}
	
}
