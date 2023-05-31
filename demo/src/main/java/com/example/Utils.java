package com.example;

import org.w3c.dom.Document;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Utils {

    public static List<File> getFiles(File folder, String... extensions) {
        var files = new ArrayList<File>();
        _getFiles(folder, files, extensions);
        return files;
    }

    public static void _getFiles(File folder, List<File> files, String[] extensions) {
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                var fileName = file.getName().toLowerCase();
                var f = extensions.length == 0;
                for (var ext : extensions) {
                    if (fileName.endsWith(ext)) {
                        f = true;
                        break;
                    }
                }
                if (f) {
                    files.add(file);
                }
            } else {
                _getFiles(file, files, extensions);
            }
        }
    }

    public static void deleteFolder(File foler) throws Exception {
        if (foler.exists()) {
            if (foler.isFile()) {
                // 対象がファイルの場合は削除
                _deleteFile(foler);

            } else if (foler.isDirectory()) {
                // 対象がディレクトリの場合

                // ディレクトリ内の一覧を取得
                var files = foler.listFiles();

                // 再帰的に削除を実行
                for (var file : files) {
                    deleteFolder(file);
                }

                // 中身が空になったディレクトリを削除
                _deleteFile(foler);
            }
        }
    }

    public static void _deleteFile(File file) throws Exception {
        if (!file.delete()) {
            throw new Exception(file.getAbsolutePath() + " の削除に失敗しました。");
        }
    }

    public static void saveXml(File file, Document doc) throws Exception {
        var factory = TransformerFactory.newInstance();
        var transformer = factory.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // インデントのスペース数
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
}
