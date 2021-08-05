package com.mindtree.talent.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="testcase")
public class Testcase {

	

		@XmlAttribute
		private String name;
		
		@XmlAttribute
		private String classname;
		
		private String failure;

		public Testcase() {
			super();
		}

		public Testcase(String name, String classname, String failure) {
			super();
			this.name = name;
			this.classname = classname;
			this.failure = failure;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClassname() {
			return classname;
		}

		public void setClassname(String classname) {
			this.classname = classname;
		}

		public String getFailure() {
			return failure;
		}

		public void setFailure(String failure) {
			this.failure = failure;
		}
		
		@Override
		public String toString() {
			return "Testcase [ name= " + name + ", classname= " + classname + ", failure= " + failure + "]";
		}
		
		
		
}
