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
		
		SAXParserFactory spf = SAXParserFactory.newInstance(); // создать парсер
		SaxParserHandler handler = new SaxParserHandler(); // создать обработчик
		SAXParser parser = null;
		
		try {
			parser = spf.newSAXParser(); // получить парсер из фабрики
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null; // вместо этого лучше возвращать подробности ошибки через throws
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; // вместо этого лучше возвращать подробности ошибки через throws
		}
		
		File file = new File("src/source.xml"); // переменная файла
		
		try {
			parser.parse(file, handler); // парсинг файла file обработчиком handler, результат хранится в handler
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; // вместо этого лучше возвращать подробности ошибки через throws
		}
		
		return handler.getRoot(); // получить и вернуть распарсеный объект
	}

}
