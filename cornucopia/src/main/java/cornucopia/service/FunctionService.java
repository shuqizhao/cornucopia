package cornucopia.service;

import java.util.List;

import cornucopia.dao.FunctionDao;
import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;
import cornucopia.util.MyBatisHelper;

public class FunctionService {
	private static FunctionService instance = new FunctionService();

	private FunctionService() {
	}

	public static FunctionService getInstance() {
		return instance;
	}

	FunctionDao funcdao = MyBatisHelper.getMapper(FunctionDao.class);

	public List<FunctionEntity> getAll() {
		return funcdao.getAll();
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

	public void updateFunctionParas(int funcId,FunctionParameterEntity[] functionParas) {
		deletePara(funcId);
		insertFunctionParas(functionParas);
	}
}
