package com.hmblogs.backend.util;

import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathConstants;
import java.io.File;

public class HuToolTest {


    public HuToolTest(){

    }
    public static void main(String[] args) {
        Document docResult= XmlUtil.readXML(new File("test.xml"));
// 结果为“ok”
        Object value = XmlUtil.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        System.out.println("message值："+value);
// 说明：Element对象目前仅能支持一层一层的向下解析，所以请不要跳级去做查询，
//       否则会报null。如果想直接获取到某个标签的文本，在有准确定位的情况下
//       可以直接写出路径获取，
//       但是如果该层级存在相同的标签则只获取第一个标签的数据。
        String xmlData="xml字符串";
        Document document= XmlUtil.parseXml(xmlData);
//获得XML文档根节点
        Element elementG=XmlUtil.getRootElement(document);
//通过固定路径获取到数据
        Object bString = XmlUtil.getByXPath("//root/base/message/event_no", document, XPathConstants.STRING);
        System.out.println("event_no元素节点值："+bString);
    }
}
