package cornucopia.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import net.sf.json.JSONObject;


import cornucopia.entity.ProcessDataEntity;

public class XmlUtil {

	public static Node selectSingleNode(String xml, String xpath)
			throws DocumentException, UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Node node = document.selectSingleNode(xpath);
		return node;
	}

	public static String selectSingleText(String xml, String xpath)
			throws DocumentException, UnsupportedEncodingException {
		Node node = selectSingleNode(xml, xpath);
		if (node != null) {
			return node.getText();
		} else {
			return "";
		}
	}

	public static String createElement(String xml, String xpath, String tag, String value)
			throws DocumentException, UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			if (node instanceof Element) {
				Element el = (Element) node;
				Element childEl = el.addElement(tag);
				childEl.setText(value);
				return document.asXML();
			}
			return xml;
		} else {
			return xml;
		}
	}

	public static void createElementForPd(ProcessDataEntity pd, String xpath, String tag, String value)
			throws DocumentException, UnsupportedEncodingException {
		String xml = createElement(pd.getBizData(), xpath, tag, value);
		pd.setBizData(xml);
	}

	public static String toJSONString(String xml) {
		
		return xml2JSON(xml);
	}

	/**
     * 转换一个xml格式的字符串到json格式
     * 
     * @param xml
     *            xml格式的字符串
     * @return 成功返回json 格式的字符串;失败反回null
     */
    @SuppressWarnings("unchecked")
    public static  String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一个迭代方法
     * 
     * @param element
     * @return java.util.Map 实例
     */
    @SuppressWarnings("unchecked")
    private static Map  iterateElement(Element element) {
        List jiedian = element.elements();
        Element et = null;
        Map obj = new HashMap();
        Object temp;
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.elements().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    temp = obj.get(et.getName());
                    if(temp instanceof List){
                        list = (List)temp;
                        list.add(iterateElement(et));
                    }else if(temp instanceof Map){
                        list.add((HashMap)temp);
                        list.add(iterateElement(et));
                    }else{
                        list.add((String)temp);
                        list.add(iterateElement(et));
                    }
                    obj.put(et.getName(), list);
                }else{
                    obj.put(et.getName(), iterateElement(et));
                }
            } else {
                if (obj.containsKey(et.getName())) {
                    temp = obj.get(et.getName());
                    if(temp instanceof List){
                        list = (List)temp;
                        list.add(et.getTextTrim());
                    }else if(temp instanceof Map){
                        list.add((HashMap)temp);
                        list.add(iterateElement(et));
                    }else{
                        list.add((String)temp);
                        list.add(et.getTextTrim());
                    }
                    obj.put(et.getName(), list);
                }else{
                    obj.put(et.getName(), et.getTextTrim());
                }
                
            }
            
        }
        return obj;
    }
}
