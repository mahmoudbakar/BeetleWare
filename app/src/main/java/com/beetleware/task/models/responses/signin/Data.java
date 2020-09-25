package com.beetleware.task.models.responses.signin;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("authorization")
	private String authorization;

	public void setAuthorization(String authorization){
		this.authorization = authorization;
	}

	public String getAuthorization(){
		return authorization;
	}
}