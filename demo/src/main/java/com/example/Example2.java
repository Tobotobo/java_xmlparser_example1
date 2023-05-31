package com.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// 指定したフォルダ内からXMLファイルを探し
// outputフォルダに元のフォルダ構成を維持したまま見つかったXMLファイルをコピーする
public class Example2 {
    public static void main(String[] args) throws Exception {
        // 対象フォルダ
        Path srcDirPath = Paths.get("sampleFiles").toAbsolutePath();

        // 対象フォルダからXMLファイルのリストを取得
        var xmlFiles = Utils.getFiles(srcDirPath.toFile(), ".xml");

        // 出力先フォルダ
        Path dstDirPath = Paths.get("output").toAbsolutePath();

        // 既に出力先フォルダが存在する場合は削除
        Utils.deleteFolder(dstDirPath.toFile());

        // フォルダを作成しつつXMLファイルをコピー
        for (var file : xmlFiles) {
            var srcPaht = file.toPath().toAbsolutePath();
            var dstPath = dstDirPath.resolve(srcDirPath.relativize(srcPaht));

            // フォルダ作成※再帰的
            dstPath.getParent().toFile().mkdirs();

            // コピー実行
            Files.copy(srcPaht, dstPath);
        }

        System.out.println("完了");
    }

}
