package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.ParseException;

// 指定したフォルダ内のファイルを列挙
public class Example1 {
    public static void main(String[] args) throws Exception {
        Path targetDirPath = Paths.get("sampleFiles").toAbsolutePath();
        File folder = targetDirPath.toFile();
        System.out.println(folder.getAbsolutePath());
        System.out.println("---------------------------");
        findAllFilesInFolder(folder);
    }

    public static void findAllFilesInFolder(File folder) {
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println(file.getAbsolutePath());
            } else {
                findAllFilesInFolder(file);
            }
        }
    }
}
