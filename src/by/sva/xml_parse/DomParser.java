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

//���������� XML-���� � ������� ���������� DOM
//DOM �������� �� XML ������ ���� ��������� �������� ��� �����, ������� �������
//Java ����� ����� ��������� ��� ������ � XML, � ������� �� JSON

public class DomParser {
	
	public Root parse() {
	
		Root root = new Root(); // ������, ���������� ��� ��������
		
		Document doc = null;
		try {
			doc = openFile("src/source.xml"); // ������ ��� ��������
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Node rootNode = doc.getFirstChild(); // �������� ������ �������� �������
		
		NodeList nodeList = doc.getElementsByTagName("name"); // �������� ��� �������� �� ���� "name" ���������� �� �����������
																// ������, ���� ����� ����� �� ����������� �� ������ ������� �����������
		
		NodeList rootChilds = rootNode.getChildNodes(); // �������� ������ ��������� � rootNode ���������, ������� �������
		
		printNodes(rootNode, nodeList, rootChilds);
		
		String rootName = null;
		Node peopleNode = null; // ������� � ����� "people"
		
		// ������� ������ �� �������� root, ������� �������� ������
		for(int i=0; i<rootChilds.getLength(); i++) {
			if(rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // ���� ������� �� �������� �����
																		// �������� �� "�� ��������" ��� ����, ����� �������� ������� ����������� ���������� if
				continue; // ������� � ��������� ��������
			}
			System.out.print("rootChild: " + rootChilds.item(i).getNodeName()); // ������� �������
			
			switch (rootChilds.item(i).getNodeName()){
				case "name":{ // ���� ��� ���� "name"
					rootName = rootChilds.item(i).getTextContent(); // �������� ���������� ����
					System.out.println(" - " + rootName);
					root.setName(rootName); // ������ ��� �������
					break;
				}
				case "people": {
					peopleNode = rootChilds.item(i); // �������� ������� � ����� "people"
					System.out.println(" - ������ ���������, ��������� � people");
					break;
				}
			}
		}
		
		if(peopleNode == null) {
			return null;
		}
		
		List<People> peopleList = parsePeople(peopleNode); // ���������� peopleNode
		
		root.setPeople(peopleList); // � ������ people �������� � ������ root
		
		System.out.println("\nRoot " + root.toString());
		
	//������� - �����
		root.getPeople().stream().filter(people -> {
			return people.getAge() == 20;
		}).forEach(people -> {
			System.out.println("\n������� � ��������� 20 ���: " + people.toString());
		});
		
		return root;
	}
	
	
	// ��������� ���� ���������
	private Document openFile(String fileName) {
		File file = new File(fileName); // ���������� �����
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			doc = dbf.newDocumentBuilder().parse(file); // ������� ���� ��� ��������
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
		System.out.println("������ Node: " + rootNode.getNodeName()); // ������� ��� Node (����)
		
		System.out.println();
		System.out.println("�������� ���� ����� � ������ name");
		for(int i=0; i<nodeList.getLength(); i++) { // ������ �� ������� ���������
			System.out.println("��� name: " + nodeList.item(i).getTextContent()); // ������� �������(#text - �����-�� �����, �������� �������, �������)
		}
		
		System.out.println();
		System.out.println("��� ��������, ��������� � rootNode, ������� ������� � �������");
		
		// ������� ��� �������� rootChilds
		for(int i=0; i<rootChilds.getLength(); i++) { // ������ �� ������� ���������
			System.out.println("rootChild: " + rootChilds.item(i).getNodeName()); // ������� �������(#text - �����-�� �����, �������� �������, �������)
		}
		
		System.out.println();
		System.out.println("����� � �������� ���� �����, ��������� � rootNode");		
	}
	
	
	// ���������� peopleNode
	private List<People> parsePeople(Node peopleNode){
		NodeList peopleChilds = peopleNode.getChildNodes(); // �������� ������ ��������� � people ���������
	
		List<People> peopleList = new ArrayList<>(); // ������ ��������� �� people/element
		
		// ������� ������ �� �������� people, ������� �������� ������
		for(int i=0; i<peopleChilds.getLength(); i++) {
			if(peopleChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // ���� ������� �� �������� �����
																			// �������� �� "�� ��������" ��� ����, ����� �������� ������� ����������� ���������� if
				continue;
			}
			
			if(!peopleChilds.item(i).getNodeName().equals("element")) {
				continue;
			}
			
			People people = parseElement(peopleChilds.item(i)); // ���������� elementNode
			
			peopleList.add(people); 				// �������� ������ people � ������ peopleList
		}
		
		return peopleList;
	}
	
	// ���������� elementNode
	private People parseElement (Node elementNode) {
		String name = "";
		int age = 0;
		
		NodeList elementChilds = elementNode.getChildNodes(); // �������� ������ ��������� � element ���������
		
		for (int i=0; i<elementChilds.getLength(); i++) {
			if(elementChilds.item(i).getNodeType() != Node.ELEMENT_NODE) { // ���� ������� �� �������� �����
																		// �������� �� "�� ��������" ��� ����, ����� �������� ������� ����������� ���������� if
				continue;
			}
			
			switch(elementChilds.item(i).getNodeName()){
				case "name": {
					name = elementChilds.item(i).getTextContent(); // �������� �������� ���� name
					break;
				}
				case "age": {
					try {
						age = Integer.valueOf(elementChilds.item(i).getTextContent()); // �������� �������� ���� age � ������������� ��� � int
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		People people = new People(name, age); // ������ ����� ������� ������ people
		
		return people; 
	}

}
