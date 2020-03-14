package com.carlins.picture.pictures.model;

import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Picture {
	
	public @Id @GeneratedValue Long id;
	public String title;
	public String author;
	public Long shopId;
	public String price;
	public LocalDate addedDate;
		
	public Picture() {}
	
	public Picture(Long shopId, String title, String author, String price) {
		
		this.title=title;
		this.shopId=shopId;
		this.price=price;
		this.addedDate= LocalDate.now();
		if ("".equals(author) ||  author==null){
			this.author="Anonymous";
		}
		else this.author=author;
	}


	

}
