package com.beetleware.task.models.responses.profile;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}
}