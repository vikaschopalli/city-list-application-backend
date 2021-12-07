package com.test.citylist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name",length= 255)
	private String name;

	@Column(name = "photo", length= 1000)
	private String photo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "CityList [id=" + id + ", name=" + name + ", photo=" + photo + "]";
	}
}
