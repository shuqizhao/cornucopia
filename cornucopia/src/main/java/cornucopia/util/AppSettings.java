package cornucopia.util;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import xcfg4j.XmlConfig;

@XmlRootElement(name = "appSettings")
public class AppSettings extends XmlConfig<AppSettings> {
	@XmlElement(name = "add")
	private List<AppSetting> adds;

	@XmlTransient
	public List<AppSetting> getAdds() {
		return adds;
	}

	public void setAdds(List<AppSetting> adds) {
		this.adds = adds;
	}

	public String get(String key) {
		if (adds == null) {
			return "";
		}

		for (AppSetting appSetting : adds) {
			if (appSetting.getKey().equals(key)) {
				return appSetting.getValue();
			}
		}
		return "";
	}
}
