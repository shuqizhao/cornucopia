package cornucopia.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cornucopia.dao.FunctionDao;
import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;
import cornucopia.entity.ParainstEntity;
import cornucopia.util.MyBatisHelper;
import cornucopia.util.XmlUtil;

public class FunctionService {
	private static FunctionService instance = new FunctionService();

	private FunctionService() {
	}

	public static FunctionService getInstance() {
		return instance;
	}

	FunctionDao funcdao = MyBatisHelper.getMapper(FunctionDao.class);

	public List<FunctionEntity> getAll(int id) {
		return funcdao.getAll(id);
	}

	public int exists(String funcName) {
		return funcdao.exists(funcName);
	}

	public int insert(FunctionEntity funcEntity) {
		return funcdao.insert(funcEntity);
	}

	public int insertPara(FunctionParameterEntity funcParaEntity) {
		return funcdao.insertPara(funcParaEntity);
	}

	public int disable(int id) {
		return funcdao.disable(id);
	}

	public int enable(int id) {
		return funcdao.enable(id);
	}

	public int delete(int id) {
		return funcdao.delete(id);
	}

	public int deletePara(int id) {
		return funcdao.deletePara(id);
	}

	public void insertFunctionParas(FunctionParameterEntity[] funcParaEntities) {
		for (FunctionParameterEntity fp : funcParaEntities) {
			insertPara(fp);
		}
	}

	public FunctionEntity get(int id) {
		return funcdao.get(id);
	}

	public List<FunctionParameterEntity> getParas(int id) {
		return funcdao.getParas(id);
	}

	public void update(FunctionEntity function) {
		funcdao.update(function);
	}

	public void updateFunctionParas(int funcId, FunctionParameterEntity[] functionParas) {
		deletePara(funcId);
		insertFunctionParas(functionParas);
	}

	public int addOrUpdateParainst(String parainstId, int functionId, String parainstJson) {
		return funcdao.addOrUpdateParainst(parainstId, functionId, parainstJson);
	}

	public ParainstEntity getParainst(String parainstId) {
		return funcdao.getParainst(parainstId);
	}

	public List<Integer> executeGetUserIds(String parainstId, String bizData) {
		ParainstEntity pe = getParainst(parainstId);
		String jsonObj = pe.getParainstJson();
		Map<String, Object> map = parseJSON2Map(jsonObj);
		FunctionEntity fe = get(pe.getFunctionId());
		String sp = fe.getName() + "(";
		for (String key : map.keySet()) {
			String value = map.get(key).toString();
			if (value.startsWith("//")) {
				try {
					value = XmlUtil.selectSingleText(bizData, value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				sp += "'" + value + "',";
			} else {
				sp += "'" + value + "',";
			}
			sp = sp.substring(0, sp.length() - 1);
		}
		sp += ")";
		return funcdao.executeGetUserIds(sp);
	}
	
	public String executeGetText(String parainstId, String bizData) {
		ParainstEntity pe = getParainst(parainstId);
		String jsonObj = pe.getParainstJson();
		Map<String, Object> map = parseJSON2Map(jsonObj);
		FunctionEntity fe = get(pe.getFunctionId());
		String sp = fe.getName() + "(";
		for (String key : map.keySet()) {
			String value = map.get(key).toString();
			if (value.startsWith("//")) {
				try {
					value = XmlUtil.selectSingleText(bizData, value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				sp += "'" + value + "',";
			} else {
				sp += "'" + value + "',";
			}
			sp = sp.substring(0, sp.length() - 1);
		}
		sp += ")";
		return funcdao.executeGetText(sp);
	}

	public Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.parseObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<Object> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = (JSONObject) it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}
