package com.beetleware.task.models.responses.counters;

import com.google.gson.annotations.SerializedName;

public class CountersResponse{

	@SerializedName("data")
	private int data;

	@SerializedName("status")
	private String status;

	public void setData(int data){
		this.data = data;
	}

	public int getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}