package cornucopia.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class XmlExercise {

    /**
     * 将xml字符串<STRONG>转换</STRONG>为JSON字符串
     * 
     * @param xmlString
     *            xml字符串
     * @return JSON<STRONG>对象</STRONG>
     */
    public static String xml2json(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }

    /**
     * JSON(数组)字符串<STRONG>转换</STRONG>成XML字符串
     * 
     * @param jsonString
     * @return
     */
    public static String json2xml(String jsonString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
        // return xmlSerializer.write(JSONArray.fromObject(jsonString));//这种方式只支持JSON数组
    }

}