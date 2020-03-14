package com.carlins.picture.pictures.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Shop {
	
	public @Id @GeneratedValue Long id;
	public String name;
	public int maxCapacity;
	public int currentCapacity;
	
	public Shop() {}
	
	
	public Shop(String name, int maxCapacity) {
		
		this.name = name;
		this.maxCapacity = maxCapacity;
		this.currentCapacity=0;
	}
	
	

}
