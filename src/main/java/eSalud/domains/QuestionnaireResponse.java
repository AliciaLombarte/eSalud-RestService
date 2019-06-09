package eSalud.domains;

import java.util.Arrays;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"responseId","email","questionnaire","date","score","result"})

public class QuestionnaireResponse {

	private int responseId;
	private String email;
	private String questionnaire;
	private Date date;
	private int score;
	private int[] result;
	
	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(String questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int[] getResult() {
		return result;
	}

	public void setResult(int[] result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "QuestionnaireResponse [responseId=" + responseId + ", email=" + email + ", questionnaire="
				+ questionnaire + ", date=" + date + ", score=" + score + ", result=" + Arrays.toString(result) + "]";
	}

	public QuestionnaireResponse() {}
}
