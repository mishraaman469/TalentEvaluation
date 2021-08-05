package com.mindtree.talent.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="failure")
public class Failure {
	
	@XmlAttribute
	private String message;
	
	@XmlAttribute
	private String type;

	public Failure() {
		super();
	}

	public Failure(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Failure  [ message= " + message + ", type= " + type + "]";
	}
	
	
		
	
}
