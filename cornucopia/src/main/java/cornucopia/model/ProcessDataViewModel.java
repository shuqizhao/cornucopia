package cornucopia.model;

import cornucopia.util.XmlUtil;

public class ProcessDataViewModel {
	
	public String getXmlStr() {
		return XmlUtil.json2xml(jsonStr);
	}
	
	public void setXmlStr(String xmlStr) {
	}

	private String jsonStr;

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

}
