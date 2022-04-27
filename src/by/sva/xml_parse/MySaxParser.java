package by.sva.xml_parse;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import by.sva.xml_parse.model.Root;

public class MySaxParser {
	public Root parse() {
		
		SAXParserFactory spf = SAXParserFactory.newInstance(); // ������� ������
		SaxParserHandler handler = new SaxParserHandler(); // ������� ����������
		SAXParser parser = null;
		
		try {
			parser = spf.newSAXParser(); // �������� ������ �� �������
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null; // ������ ����� ����� ���������� ����������� ������ ����� throws
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; // ������ ����� ����� ���������� ����������� ������ ����� throws
		}
		
		File file = new File("src/source.xml"); // ���������� �����
		
		try {
			parser.parse(file, handler); // ������� ����� file ������������ handler, ��������� �������� � handler
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; // ������ ����� ����� ���������� ����������� ������ ����� throws
		}
		
		return handler.getRoot(); // �������� � ������� ����������� ������
	}

}
