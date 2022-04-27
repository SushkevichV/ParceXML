package by.sva.xml_parse;

import by.sva.xml_parse.model.Root;

// ���������� XML-���� � ������� ���������� DOM
// DOM �������� �� XML ������ ���� ��������� �������� ��� �����, ������� �������
// � DOM ����� ����������� ����� � ��������� ������� ��������, ������� �������
// SAX ��� ������� ��� ��������, ���������� ������
// Java ����� ����� ��������� ��� ������ � XML, � ������� �� JSON

public class Main {

	public static void main(String[] args) {
		System.out.println("DOM parsing");
		
		DomParser domParser = new DomParser();
		Root root = domParser.parse();
		
		System.out.println();
		
		System.out.println("\nSAX parsing");
		
		MySaxParser saxParser = new MySaxParser();
		root = saxParser.parse();
		
		System.out.println("SAX parsing: "+ root.toString());
	}
}
