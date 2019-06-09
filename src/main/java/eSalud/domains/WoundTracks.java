package eSalud.domains;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"fecha","dolorPierna","dolorEspalda","temperatura","emailUser","photo", "fileName","score"})
public class WoundTracks{
    
	private Date fecha;
	private String dolorPierna;
	private String dolorEspalda;
	private String temperatura;
	private String photo;
	private String fileName;
	private String emailUser;
	private int score;
	private String questionnaireName;
	
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDolorEspalda() {
		return dolorEspalda;
	}

	public void setDolorEspalda(String dolorEspalda) {
		this.dolorEspalda = dolorEspalda;
	}

	@Override
	public String toString() {
		return "WoundTracks [fecha=" + fecha + ", dolorPierna=" + dolorPierna + ", dolorEspalda=" + dolorEspalda
				+ ", temperatura=" + temperatura + ", photo=" + photo + ", fileName=" + fileName + ", emailUser="
				+ emailUser + ", score=" + score + ", questionnaireName=" + questionnaireName + "]";
	}

	public String getFileName() {
		return fileName;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
