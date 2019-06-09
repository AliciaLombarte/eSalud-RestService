package eSalud.domains;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"result"})
public class Result{
    
	private int result;
	private String content;
	private ArrayList<User> listUsers;
	
	public Result() {}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public ArrayList<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	@Override
	public String toString() {
		return "Result [result=" + result + ", listUsers=" + listUsers + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
