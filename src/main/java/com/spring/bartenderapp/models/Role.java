package com.spring.bartenderapp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	@GeneratedValue
	private int id;

	@Column(name = "type", unique = true, nullable = false)
	private String type;

	public Role() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
