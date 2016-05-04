package com.bupt.muyundefng.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
/*
 * This class about Nijia info.
 * Definate a model
 * @author muyundefeng
 */
public class Nijia {

	public String name;
	public Date birthDate;
	public String category;
	public List<MultipartFile> images;
	public Nijia(){}
	public Nijia(String name, Date birthDate, String category, List<MultipartFile> images) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.category = category;
		this.images = images;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	
}
