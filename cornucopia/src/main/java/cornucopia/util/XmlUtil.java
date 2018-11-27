package cornucopia.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;

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

	public static String createOrUpdateElement(String xml, String xpath, String tag, String value)
			throws DocumentException, UnsupportedEncodingException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			if (node instanceof Element) {
				Element el = (Element) node;
				Node childNode = el.selectSingleNode("//" + tag);
				if (childNode != null) {
					childNode.setText(value);
				} else {
					Element childEl = el.addElement(tag);
					childEl.setText(value);
				}

				return document.asXML();
			}
			return xml;
		} else {
			return xml;
		}
	}

	public static void createOrUpdateElementForPd(ProcessDataEntity pd, String xpath, String tag, String value)
			throws DocumentException, UnsupportedEncodingException {
		String xml = createOrUpdateElement(pd.getBizData(), xpath, tag, value);
		pd.setBizData(xml);
	}
}
