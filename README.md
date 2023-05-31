# java_xmlparser_example1

Java標準APIでのXML読み込み方4つを大紹介 サンプルで比較しよう  
https://www.bold.ne.jp/engineer-club/java-xml-read#-XML

【Java】XMLファイルを出力するサンプルコードを紹介！  
https://style.potepan.com/articles/29550.html

Java での Pretty-Print XML　※見やすくフォーマットして出力→dom4j  
https://www.baeldung.com/java-pretty-print-xml


Javaで文字コードを気にしつつファイルの読み込み・書き込み  
https://tohokuaiki.hateblo.jp/entry/20140205/1391589045
dom4jのSAXReaderでUTF8の日本語XMLを読み込むとどうしても内部で文字化けしてるのかXMLをreadするときに例外が発生してた。


Javaバージョンを1.7から17に変更  
pom.xml
```xml
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>
```

TODO
フォーマット後の可読性が低い