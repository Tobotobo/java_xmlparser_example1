package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.rowset.spi.XmlWriter;

import org.dom4j.Document;

// import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.NodeList;

// 指定したフォルダ内からXMLファイルを探し
// outputフォルダに元のフォルダ構成を維持したまま
// 見つかったXMLファイルを加工して格納する
public class Example4 {
    public static void main(String[] args) throws Exception {
        // 対象フォルダ
        Path srcDirPath = Paths.get("sampleFiles_ex4").toAbsolutePath();

        // 対象フォルダからXMLファイルのリストを取得
        var xmlFiles = Utils.getFiles(srcDirPath.toFile(), ".mayaa");

        // 出力先フォルダ
        Path dstDirPath = Paths.get("output").toAbsolutePath();

        // 既に出力先フォルダが存在する場合は削除
        Utils.deleteFolder(dstDirPath.toFile());

        // フォルダを作成しつつXMLファイルを加工して格納
        for (var file : xmlFiles) {
            var srcPaht = file.toPath().toAbsolutePath();
            var dstPath = dstDirPath.resolve(srcDirPath.relativize(srcPaht));

            // フォルダ作成※再帰的
            dstPath.getParent().toFile().mkdirs();

            // XMLファイルを加工
            processXml(srcPaht, dstPath);
        }

        System.out.println("完了");
    }

    // XMLファイルを開く → Document取得 → 加工(別メソッドで) → 加工後のDocumentを保存
    static void processXml(Path srcPath, Path dstPath) throws Exception {
        // TODO: Streamのクローズ処理がメチャクチャなので要修正
        XMLWriter writer = null;

        try {
            // ファイルからXMLドキュメントを読み込む
            var isr = new InputStreamReader(new FileInputStream(srcPath.toFile()), "UTF-8");
            var document = new SAXReader().read(isr);

            // rootエレメントを取得
            var root = document.getRootElement();

            // テスト
            for (var e : root.elements()) {
                System.out.println(e.getNamespacePrefix() + ":" + e.getName() + " -> " + e.getText());
                for (var a : e.attributes()) {
                    System.out.println("    " + a.getName() + " = " + a.getValue());
                }
            }

            // 新しいエレメントを作成して追加する
            var newElement = DocumentHelper.createElement("newElement");
            newElement.setText("This is a new element"); // テキストを設定
            root.add(newElement); // rootエレメントに新しいエレメントを追加

            OutputFormat format = OutputFormat.createPrettyPrint();
            // XML文書宣言<?xml ～ ?>
            format.setSuppressDeclaration(false); // 宣言を省略するか
            format.setNewLineAfterDeclaration(true); // 宣言の後に空行を入れるか
            format.setEncoding("UTF-8"); // 宣言の encoding ※ここでUTF-8としても自動的にUTF-8で書込まれるわけではない

            // 本文
            format.setIndent(false);
            // format.setIndentSize(4);
            format.setNewlines(false); // falseにすると改行無し = 1行で出力される
            format.setNewLineAfterNTags(2); // 不明
            // format.setXHTML(true); // 不明
            // format.setOmitEncoding(true); // 不明
            format.setTrimText(false); // trueにすると要素内の改行など可読性のために入力した改行が全て削除されてしまう
            format.setPadText(false);

            // ドキュメントを新しいXMLファイルに書き込む
            var osr = new OutputStreamWriter(new FileOutputStream(dstPath.toFile()), "UTF-8");
            writer = new XMLWriter(osr, format);

            writer.write(document);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}
