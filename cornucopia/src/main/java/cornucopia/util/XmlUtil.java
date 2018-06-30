package cornucopia.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
		JSONObject jSONObject;
		try {
			jSONObject = elementToJSONObject(strToDocument(xml).getRootElement());
			return jSONObject.toJSONString();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * String 转 org.dom4j.Document
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Document strToDocument(String xml) throws DocumentException {
		return DocumentHelper.parseText(xml);
	}

	/**
	 * org.dom4j.Document 转 com.alibaba.fastjson.JSONObject
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static JSONObject documentToJSONObject(String xml) throws DocumentException {
		return elementToJSONObject(strToDocument(xml).getRootElement());
	}

	/**
	 * org.dom4j.Element 转 com.alibaba.fastjson.JSONObject
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject elementToJSONObject(Element node) {
		JSONObject result = new JSONObject();
		// 当前节点的名称、文本内容和属性
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			result.put(attr.getName(), attr.getValue());
		}
		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		if (!listElement.isEmpty()) {
			for (Element e : listElement) {// 遍历所有一级子节点
				if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
					result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
				else {
					if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
						result.put(e.getName(), new JSONArray());// 没有则创建
					((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// 将该一级节点放入该节点名称的属性对应的值中
				}
			}
		}
		return result;
	}
}
