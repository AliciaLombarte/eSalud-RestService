package eSalud.domains;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"originalPass","newPass","newPass2"})
public class Password{
    
	private String originalPass;
	private String newPass;
	private String newPass2;
	
	public Password() {}

	public String getOriginalPass() {
		return originalPass;
	}

	public void setOriginalPass(String originalPass) {
		this.originalPass = originalPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewPass2() {
		return newPass2;
	}

	public void setNewPass2(String newPass2) {
		this.newPass2 = newPass2;
	}

}
