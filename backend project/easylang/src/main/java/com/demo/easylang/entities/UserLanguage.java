package com.demo.easylang.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "userlanguages")
public class UserLanguage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "userlanguages_id_seq", sequenceName = "userlanguages_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "language")
    private Integer language;

    @Column(name = "proficiency")
    private String proficiency;
    
    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(name = "active")
    		private boolean active;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "UserLanguage{" +
                "id=" + id +
                ", userId=" + userId +
                ", language='" + language + '\'' +
                ", proficiency='" + proficiency + '\'' +
                '}';
    }
}
