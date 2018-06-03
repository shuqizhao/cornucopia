package cornucopia.model;

import cornucopia.entity.FunctionEntity;
import cornucopia.entity.FunctionParameterEntity;

public class FunctionViewModel {
	private FunctionEntity function;

	public FunctionEntity getFunction() {
		return function;
	}

	public void setFunction(FunctionEntity function) {
		this.function = function;
	}

	public FunctionParameterEntity[] getFunctionParas() {
		return functionParas;
	}

	public void setFunctionParas(FunctionParameterEntity[] functionParas) {
		this.functionParas = functionParas;
	}

	private FunctionParameterEntity[] functionParas;
}
