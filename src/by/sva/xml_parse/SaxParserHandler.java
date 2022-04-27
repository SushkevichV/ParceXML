package by.sva.xml_parse;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.sva.xml_parse.model.People;
import by.sva.xml_parse.model.Root;

public class SaxParserHandler extends DefaultHandler {
	
	private Root root = new Root(); // ����������� ������
	private List<People> peopleList = new ArrayList<>(); // ��������� ��������� people
	private String currentTagName; // ��� �������� ����
	private boolean isPeople = false; // �����, �������� �� ������� ��� �������� �� ���� people
	private String name = null;
	private int age = 0;
	
	public Root getRoot() { // ���������� ����������� ������
		return root;
	}
	
	@Override // ������ ���������
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}
	
	
	@Override // ����� ���������
	public void endDocument() throws SAXException {
		root.setPeople(peopleList); // �������� peopleList � root
		System.out.println("End document");
	}
	
	@Override // ������ ����
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("Start element " + qName);
		currentTagName = qName; // �������� ��� �������� ����
		if(qName.equals("people")) { // ���� ��� �������� ������������ ���� people
			isPeople = true; // ��������, ��� ��������� ������ ���� people
		}
	}
	
	@Override // ����� ���� (����������� ���)
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("End element " + qName);
		
		if(qName.equals("people")) { // ���� ��� �������� ������������ ���� people
			isPeople = false; // ��������, ��� ����� �� ���� people
		}
		
		if(qName.equals("element")) { // ���� ��� �������� ������������ ���� element
			peopleList.add(new People(name, age)); // �������� ������� � ���������
		}
				
		currentTagName = null; // ����������� �������� ������� �������
	}
	
	@Override // ���������� ����
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		// �� ������ ���� ������� ��� �����
		if(currentTagName == null) {
			return;
		}
		
		System.out.println("���������� ����: " + new String(ch, start, length)); // ������ �������� ch, ������� � ������� start, length ��������
		
		if(!isPeople && currentTagName.equals("name")) { // ���� ������� ��� �� �������� �� people � ��� ���� "name"
			root.setName(new String(ch, start, length)); // �������� �������� ���� � �������� ��� � ������ root
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
