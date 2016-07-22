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
import org.w3c.dom.NodeList;

import com.github.muyundefeng.pageProcess.PageProcessor;


public class readXml {
	private  String siteId;
	private  String homePage;
	private  String charSet;
	private  XPath xPath;
	private  Document document;
	private PageProcessor pageProcessor;
	
	
	public  void setId(String id){
		siteId = id;
	}
	public readXml(){
		try{
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream inputStream = readXml.class.getResourceAsStream("/site.xml");
			document = documentBuilder.parse(inputStream);
			XPathFactory xPathFactory = XPathFactory.newInstance();
			xPath = xPathFactory.newXPath();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  String getHomePage(){
		try {
			
			XPathExpression expression = xPath.compile("//site[@id='"+siteId+"']");
			Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
			//System.out.println(nodes.getLength());
			NodeList childNodeList = node.getChildNodes();
			//System.out.println(childNodeList.getLength());
			if(childNodeList != null){
				homePage = childNodeList.item(1).getTextContent().trim();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return homePage;
	}
	public  String getCharset(){
		try {
			
			XPathExpression expression = xPath.compile("//site[@id='"+siteId+"']");
			Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
			//System.out.println(nodes.getLength());
			NodeList childNodeList = node.getChildNodes();
			//System.out.println(childNodeList.getLength());
			if(childNodeList != null){
				charSet = childNodeList.item(3).getTextContent().trim();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charSet;
	}
	
	public  String getPapgeProcessor(){
		try {
			
			XPathExpression expression = xPath.compile("//site[@id='"+siteId+"']");
			Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
			//System.out.println(nodes.getLength());
			NodeList childNodeList = node.getChildNodes();
			//System.out.println(childNodeList.getLength());
			if(childNodeList != null){
				charSet = childNodeList.item(5).getTextContent().trim();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charSet;
	}
	
	public PageProcessor getProcessor(){
		String processor = getPapgeProcessor();
		Class c;
		PageProcessor o = null;
		try{
			c = Class.forName(processor);
			o = (PageProcessor) (c.getClassLoader().loadClass(processor)).newInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}
	
	public static void main(String[] args) {
		readXml rXml = new readXml();
		rXml.setId("1");
		System.out.println(rXml.getPapgeProcessor());
	}

}
