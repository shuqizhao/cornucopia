package cornucopia.model;

import cornucopia.util.XmlExercise;

public class ProcessDataViewModel {
	
	public String getXmlStr() {
		return XmlExercise.json2xml(jsonStr);
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
