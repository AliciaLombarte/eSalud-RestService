package eSalud.domains;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"fecha","dolorPierna","dolorEspalda","temperatura","emailUser","photo","score"})
public class WoundTracks{
    
	private Date fecha;
	private String dolorPierna;
	private String dolorEspalda;
	private String temperatura;
	private String photo;
	private String emailUser;
	private String score;
	
	public WoundTracks() {}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDolorPierna() {
		return dolorPierna;
	}

	public void setDolorPierna(String dolorPierna) {
		this.dolorPierna = dolorPierna;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDolorEspalda() {
		return dolorEspalda;
	}

	public void setDolorEspalda(String dolorEspalda) {
		this.dolorEspalda = dolorEspalda;
	}

	
}
