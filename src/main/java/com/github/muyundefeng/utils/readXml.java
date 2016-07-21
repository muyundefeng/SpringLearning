package com.github.muyundefeng.utils;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class readXml {
	private static  String siteId;
	private static String homePage;
	
	private static void setId(String id){
		siteId = id;
	}
	
	private static String getHomePage(){
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream inputStream = readXml.class.getResourceAsStream("/site.xml");
			Document document = documentBuilder.parse(inputStream);
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression expression = xPath.compile("//site[@id='"+siteId+"']");
			Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
			if(node != null){
				homePage = node.getTextContent().trim();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return homePage;
	}
	public static void main(String[] args) {
		setId("1");
		System.out.println(getHomePage());
	}
}
