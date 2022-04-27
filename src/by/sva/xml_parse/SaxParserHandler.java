package by.sva.xml_parse;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.sva.xml_parse.model.People;
import by.sva.xml_parse.model.Root;

public class SaxParserHandler extends DefaultHandler {
	
	private Root root = new Root(); // распарсеный объект
	private List<People> peopleList = new ArrayList<>(); // коллекция элементов people
	private String currentTagName; // имя текущего тэга
	private boolean isPeople = false; // метка, является ли текущий тэг дочерним от тэга people
	private String name = null;
	private int age = 0;
	
	public Root getRoot() { // возвращает распарсеный объект
		return root;
	}
	
	@Override // начало документа
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}
	
	
	@Override // конец документа
	public void endDocument() throws SAXException {
		root.setPeople(peopleList); // записать peopleList в root
		System.out.println("End document");
	}
	
	@Override // начало тэга
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("Start element " + qName);
		currentTagName = qName; // записать имя текущего тэга
		if(qName.equals("people")) { // если имя текущего открывающего тэга people
			isPeople = true; // пометить, что находится внутри тэга people
		}
	}
	
	@Override // конец тэга (закрывающий тэг)
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("End element " + qName);
		
		if(qName.equals("people")) { // если имя текущего закрывающего тэга people
			isPeople = false; // пометить, что вышел из тэга people
		}
		
		if(qName.equals("element")) { // если имя текущего закрывающего тэга element
			peopleList.add(new People(name, age)); // добавить элемент в коллекцию
		}
				
		currentTagName = null; // ОБЯЗАТЕЛЬНО обнулить текущий элемент
	}
	
	@Override // содержимое тэга
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		// на случай если элемент без имени
		if(currentTagName == null) {
			return;
		}
		
		System.out.println("содержимое тэга: " + new String(ch, start, length)); // массив символов ch, начиная с позиции start, length символов
		
		if(!isPeople && currentTagName.equals("name")) { // если текущий тэг не дочерний от people и имя тэга "name"
			root.setName(new String(ch, start, length)); // получить значение тэга и положить его в объект root
		} else {
			switch (currentTagName) {
				case "name": {
					name = new String(ch, start, length);
					break;
				}
				case "age": {
					age = Integer.valueOf(new String(ch, start, length));
					break;
				}
			}
		}
	}

}
