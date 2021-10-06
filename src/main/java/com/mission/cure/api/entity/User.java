package com.mission.cure.api.entity;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

@Entity
@Table(name = "users")
@TypeDef(
	    name = "list-array",
	    typeClass = ListArrayType.class
	)
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Nationalized
	private String name;

	private String email;
	
	private String password;
	
	private String salt;

	private Integer type;

	private Integer subtype;

	private boolean diagnosed;

	@Type(type = "list-array")
    @Column(columnDefinition = "text[]")
	private List<String> diseaseType;

	@Type(type = "list-array")
    @Column(columnDefinition = "text[]")
	private List<String> topics;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubtype() {
		return subtype;
	}

	public void setSubtype(Integer subtype) {
		this.subtype = subtype;
	}

	public boolean isDiagnosed() {
		return diagnosed;
	}

	public void setDiagnosed(boolean isDiagnosed) {
		this.diagnosed = isDiagnosed;
	}

	public List<String> getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(List<String> diseaseType) {
		this.diseaseType = diseaseType;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}