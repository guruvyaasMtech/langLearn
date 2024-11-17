package com.demo.easylang.entities;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	private Integer id;
	private String email;
	private String password;
	private Integer role_id;
	private String mobile;
	private String full_name;
	
    @Column(name = "isTempPassword")
	private boolean isTempPassword;
    public boolean isTempPassword() {
		return isTempPassword;
	}


	public void setTempPassword(boolean isTempPassword) {
		this.isTempPassword = isTempPassword;
	}


	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role_id + '\'' +
              
                '}';
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFull_name() {
		return full_name;
	}


	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getRole_id() {
		return role_id;
	}


	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
    
    
}
