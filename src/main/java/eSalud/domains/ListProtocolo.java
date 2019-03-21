package eSalud.domains;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"protocolo"})
public class ListProtocolo{
    
	private List<Protocolo> protocolo;

	public List<Protocolo> getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(List<Protocolo> protocolo) {
		this.protocolo = protocolo;
	}
	
}
