package cornucopia.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cornucopia.dao.FunctionDao;
import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;
import cornucopia.entity.ParainstEntity;
import cornucopia.entity.ProcessDataEntity;
import cornucopia.util.Log4jHelper;
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

	public List<Integer> executeGetUserIds(String parainstId, ProcessDataEntity pde) {
		ParainstEntity pe = getParainst(parainstId);
		String jsonObj = pe.getParainstJson();
		Map<String, Object> map = parseJSON2Map(jsonObj);
		FunctionEntity fe = get(pe.getFunctionId());
		String sp = "call " + fe.getName() + "(";
		for (String key : map.keySet()) {
			if (key.equals("functionId")) {
				continue;
			}
			String value = map.get(key).toString();
			if (value.startsWith("//")) {
				try {
					value = XmlUtil.selectSingleText(pde.getBizData(), value);
				} catch (Exception e) {
					Log4jHelper.LOGGER
							.error(String.format("准备执行函数%s时获取xpath异常->%s->parainstId=%s", sp, value, parainstId), e);
				}
				sp += "'" + value + "',";
			} else {
				sp += "'" + value + "',";
			}
		}
		sp = sp.substring(0, sp.length() - 1);
		sp += ")";
		Log4jHelper.LOGGER.info(String.format("[%s]->准备执行函数->%s->parainstId=%s", pde.getFormCode(), sp, parainstId));
		List<Integer> userIds = funcdao.executeGetUserIds(sp);
		String userIdsStr = "";
		for (int userId : userIds) {
			userIdsStr += userId + ",";
		}
		Log4jHelper.LOGGER.info(String.format("[%s]->执行函数完成->%s->Result:%s->parainstId=%s", pde.getFormCode(), sp,
				userIdsStr, parainstId));
		return userIds;
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
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JSONObject json = JSONObject.parseObject(jsonStr);
		List<Object> keys = new ArrayList<>(json.keySet());
		Collections.reverse(keys);
		for (Object k : keys) {
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
