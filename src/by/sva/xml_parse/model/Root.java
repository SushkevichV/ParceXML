package by.sva.xml_parse.model;

import java.util.List;

public class Root {
	
	private String name; // по названию первого тэга файла, содержащего строку
	private List<People> people; // по названию второго тэга, содержащего несколько вложенных элементов

	public void setName(String name) {
		this.name = name;
	}

	public void setPeople(List<People> people) {
		this.people = people;
	}

	public String getName() {
		return name;
	}

	public List<People> getPeople() {
		return people;
	}

	@Override
	public String toString() {
		return "Root [name=" + name + ", people=" + people + "]";
	}

}
