package by.sva.xml_parse;

import by.sva.xml_parse.model.Root;

// Распарсить XML-файл с помощью библиотеки DOM
// DOM проходит по XML сверху вниз попорядку считывая все блоки, включая пробелы
// у DOM нужно прописывать поиск и обработку каждого елемента, включая отступы
// SAX сам находит все элементы, являющиеся тэгами
// Java имеет набор библиотек для работы с XML, в отличии от JSON

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
