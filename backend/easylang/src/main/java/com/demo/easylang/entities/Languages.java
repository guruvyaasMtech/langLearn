package com.demo.easylang.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "languages")
public class Languages {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_id_seq")
	@SequenceGenerator(name = "languages_id_seq", sequenceName = "languages_id_seq", allocationSize = 1)
	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
