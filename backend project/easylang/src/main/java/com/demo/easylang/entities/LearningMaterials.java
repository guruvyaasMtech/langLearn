package com.demo.easylang.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "learningmaterials")
public class LearningMaterials {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "learning_materials_id_seq", sequenceName = "learning_materials_id_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "language_id")
	private Integer language;

	@Column(name = "category")
	private String category;

	@Column(name = "content_url")
	private String Contenturl;

	@Column(name = "sequence")
	private Integer sequence;

	@Column(name = "proficiency")
	private String proficiency;

}
