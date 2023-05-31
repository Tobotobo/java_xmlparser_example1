package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// 指定したフォルダ内からXMLファイルを探し
// outputフォルダに元のフォルダ構成を維持したまま
// 見つかったXMLファイルを加工して格納する
public class Example3 {
    public static void main(String[] args) throws Exception {
        // 対象フォルダ
        Path srcDirPath = Paths.get("sampleFiles_ex3").toAbsolutePath();

        // 対象フォルダからXMLファイルのリストを取得
        var xmlFiles = Utils.getFiles(srcDirPath.toFile(), ".xml");

        // 出力先フォルダ
        Path dstDirPath = Paths.get("output").toAbsolutePath();

        // 既に出力先フォルダが存在する場合は削除
        Utils.deleteFolder(dstDirPath.toFile());

        // フォルダを作成しつつXMLファイルを加工して格納
        for (var file : xmlFiles) {
            var srcPath = file.toPath().toAbsolutePath();
            var dstPath = dstDirPath.resolve(srcDirPath.relativize(srcPath));

            // フォルダ作成※再帰的
            dstPath.getParent().toFile().mkdirs();

            // XMLファイルを加工
            processXml(srcPath, dstPath);
        }

        System.out.println("完了");
    }

    // XMLファイルを開く → Document取得 → 加工(別メソッドで) → 加工後のDocumentを保存
    static void processXml(Path srcPath, Path dstPath) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        // 元ファイルを読み込む
        Document document = builder.parse(srcPath.toFile());

        // 加工
        _processXml(document);

        // 加工後のDOcumentを保存
        Utils.saveXml(dstPath.toFile(), document);
    }

    // XMLファイルを加工
    static void _processXml(Document document) {

        // Documentのエラーを標準化
        document.getDocumentElement().normalize();

        // 4. Documentから、ルート要素(BookList)を取得する
        Element bookList = document.getDocumentElement();

        // 5. BookList配下にある、Book要素を取得する
        NodeList books = bookList.getElementsByTagName("Book");

        // 6. 取得したBook要素でループする
        for (int i = 0; i < books.getLength(); i++) {

            // 7. Book要素をElementにキャストする
            Element book = (Element) books.item(i);

            // 8. 属性を追加する
            book.setAttribute("test", "aaa");
        }
    }

}
