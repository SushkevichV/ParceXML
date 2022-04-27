package by.sva.xml_parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.sva.xml_parse.model.People;
import by.sva.xml_parse.model.Root;

//Распарсить XML-файл с помощью библиотеки DOM
//DOM проходит по XML сверху вниз попорядку считывая все блоки, включая пробелы
//Java имеет набор библиотек для работы с XML, в отличии от JSON

public class DomParser {
	
	public Root parse() {
	
		Root root = new Root(); // объект, получаемый при парсинге
		
		Document doc = null;
		try {
			doc = openFile("src/source.xml"); // объект для парсинга
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Node rootNode = doc.getFirstChild(); // получить первый дочерний элемент
		
		NodeList nodeList = doc.getElementsByTagName("name"); // получить все элементы по тэгу "name" независимо от вложенности
																// удобно, если имена тэгов не повторяются на разных уровнях вложенности
		
		NodeList rootChilds = rootNode.getChildNodes(); // получить массив вложенных в rootNode элементов, включая пробелы
		
		printNodes(rootNode, nodeList, rootChilds);
		
		String rootName = null;
		Node peopleNode = null; // элемент с тэгом "people"
		
		// выводит только те элементы root, которые являются тэгами
		for(int i=0; i<rootChilds.getLength(); i++) {
			if(rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // если елемент НЕ является тэгом
																		// проверка на "НЕ является" для того, чтобы избежать большой вложенности операторов if
				continue; // переход к следующий итерации
			}
			System.out.print("rootChild: " + rootChilds.item(i).getNodeName()); // выводит элемент
			
			switch (rootChilds.item(i).getNodeName()){
				case "name":{ // если имя тэга "name"
					rootName = rootChilds.item(i).getTextContent(); // получить содержимое тэга
					System.out.println(" - " + rootName);
					root.setName(rootName); // задать имя объекта
					break;
				}
				case "people": {
					peopleNode = rootChilds.item(i); // получить элемент с тэгом "people"
					System.out.println(" - массив элементов, вложенных в people");
					break;
				}
			}
		}
		
		if(peopleNode == null) {
			return null;
		}
		
		List<People> peopleList = parsePeople(peopleNode); // распарсить peopleNode
		
		root.setPeople(peopleList); // и массив people положить в объект root
		
		System.out.println("\nRoot " + root.toString());
		
	//выборка - круть
		root.getPeople().stream().filter(people -> {
			return people.getAge() == 20;
		}).forEach(people -> {
			System.out.println("\nЧеловек с возрастом 20 лет: " + people.toString());
		});
		
		return root;
	}
	
	
	// загружает файл полностью
	private Document openFile(String fileName) {
		File file = new File(fileName); // переменная файла
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			doc = dbf.newDocumentBuilder().parse(file); // открыть файл для парсинга
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		
		return doc;
	}
	
	private void printNodes(Node rootNode, NodeList nodeList, NodeList rootChilds) {
		System.out.println("Первый Node: " + rootNode.getNodeName()); // вывести имя Node (тэга)
		
		System.out.println();
		System.out.println("Значения всех тэгов с именем name");
		for(int i=0; i<nodeList.getLength(); i++) { // пройти по массиву элементов
			System.out.println("тэг name: " + nodeList.item(i).getTextContent()); // выводит элемент(#text - какой-то текст, например пробелы, отступы)
		}
		
		System.out.println();
		System.out.println("Все элементы, вложенные в rootNode, включая пробелы и отступы");
		
		// выводит все элементы rootChilds
		for(int i=0; i<rootChilds.getLength(); i++) { // пройти по массиву элементов
			System.out.println("rootChild: " + rootChilds.item(i).getNodeName()); // выводит элемент(#text - какой-то текст, например пробелы, отступы)
		}
		
		System.out.println();
		System.out.println("Имена и значения всех тэгов, вложенных в rootNode");		
	}
	
	
	// распарсить peopleNode
	private List<People> parsePeople(Node peopleNode){
		NodeList peopleChilds = peopleNode.getChildNodes(); // получить массив вложенных в people элементов
	
		List<People> peopleList = new ArrayList<>(); // массив элементов из people/element
		
		// выводит только те элементы people, которые являются тэгами
		for(int i=0; i<peopleChilds.getLength(); i++) {
			if(peopleChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // если елемент НЕ является тэгом
																			// проверка на "НЕ является" для того, чтобы избежать большой вложенности операторов if
				continue;
			}
			
			if(!peopleChilds.item(i).getNodeName().equals("element")) {
				continue;
			}
			
			People people = parseElement(peopleChilds.item(i)); // распарсить elementNode
			
			peopleList.add(people); 				// добавить объект people в массив peopleList
		}
		
		return peopleList;
	}
	
	// распарсить elementNode
	private People parseElement (Node elementNode) {
		String name = "";
		int age = 0;
		
		NodeList elementChilds = elementNode.getChildNodes(); // получить массив вложенных в element элементов
		
		for (int i=0; i<elementChilds.getLength(); i++) {
			if(elementChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // если елемент НЕ является тэгом
																		// проверка на "НЕ является" для того, чтобы избежать большой вложенности операторов if
				continue;
			}
			
			switch(elementChilds.item(i).getNodeName()){
				case "name": {
					name = elementChilds.item(i).getTextContent(); // получить значение тэга name
					break;
				}
				case "age": {
					try {
						age = Integer.valueOf(elementChilds.item(i).getTextContent()); // получить значение тэга age и преобразовать его в int
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		People people = new People(name, age); // теперь можно создать объект people
		
		return people; 
	}

}
