package cornucopia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;

public interface FunctionDao {
	@Select("call sp_function_all()")
	public List<FunctionEntity> getAll();

	@Select("call sp_function_exists(#{funcName})")
	public int exists(@Param("funcName") String funcName);

	@Insert("call sp_function_insert(#{func.name},#{func.desc})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="func.id", before=false, resultType=int.class)
	public int insert(@Param("func") FunctionEntity functionEntity);

	@Update("call sp_function_disable(#{id})")
	public int disable(@Param("id")int id);
	
	@Update("call sp_function_enable(#{id})")
	public int enable(@Param("id")int id);
	
	@Update("call sp_function_delete(#{id})")
	public int delete(@Param("id")int id);

	@Insert("call sp_function_para_insert(#{funcPara.funcId},#{funcPara.name},#{funcPara.desc},#{funcPara.type})")
	@SelectKey(statement="Select LAST_INSERT_ID()", keyProperty="funcPara.id", before=false, resultType=int.class)
	public int insertPara(@Param("funcPara") FunctionParameterEntity funcParaEntity);

	@Select("call sp_function_get(#{id})")
	public FunctionEntity get(@Param("id")int id);

	@Select("call sp_function_para_get(#{id})")
	public List<FunctionParameterEntity> getParas(int id);

	@Update("call sp_function_update(#{func.id},#{func.name},#{func.desc})")
	public void update(@Param("func") FunctionEntity function);
	
	@Delete("call sp_function_para_delete_by_func_id(#{id})")
	public int deletePara(@Param("id")int id);
}
