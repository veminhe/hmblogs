package com.hmblogs.backend.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLExample {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // 创建根元素
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

            // 创建子元素
            Element child = doc.createElement("child");
            child.appendChild(doc.createTextNode("Value"));
            rootElement.appendChild(child);

            // 将DOM内容写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new java.io.File("output.xml"));

            // 输出到文件
            transformer.transform(source, result);

            System.out.println("XML file created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
