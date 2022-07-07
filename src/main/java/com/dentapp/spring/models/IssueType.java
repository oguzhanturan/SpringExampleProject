package com.dentapp.spring.models;

import com.dentapp.spring.models.Auth.ERole;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "issueType")
@AllArgsConstructor
public class IssueType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String name;

	public IssueType() {

	}

	public IssueType(String name) {
		this.name = name;
	}

	public Long getId(){
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}