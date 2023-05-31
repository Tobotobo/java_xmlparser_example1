package com.example;

import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomSample {
    public static void main(String[] args) throws Exception {
        // 1. DocumentBuilderFactoryのインスタンスを取得する
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 2. DocumentBuilderのインスタンスを取得する
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
        Document document = builder.parse(Paths.get("bookList.xml").toFile());
        // 4. Documentから、ルート要素(BookList)を取得する
        Element bookList = document.getDocumentElement();
        // 5. BookList配下にある、Book要素を取得する
        NodeList books = bookList.getElementsByTagName("Book");

        // 6. 取得したBook要素でループする
        for (int i = 0; i < books.getLength(); i++) {
            // 7. Book要素をElementにキャストする
            Element book = (Element) books.item(i);

            // 8. Book要素の属性値と、テキストノードの値を取得する
            String isbn = book.getAttribute("isbn");
            String title = book.getAttribute("title");
            String author = book.getAttribute("author");
            String content = book.getTextContent();

            System.out.println("isbn = " + isbn);
            System.out.println("author = " + author);
            System.out.println("title = " + title);
            System.out.println("text = " + content);
            System.out.println();
        }
    }
}