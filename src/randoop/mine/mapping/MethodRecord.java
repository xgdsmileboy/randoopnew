package randoop.mine.mapping;

import java.util.List;

public class MethodRecord {
	private Class<?> inClazz;
	private String methodName;
	private Class<?> returnClazz;
	private List<Class<?>> inputClazzList;
	
	public MethodRecord(Class<?> inClazz, String MethodName, Class<?> returnType, List<Class<?>> inputList){
		this.inClazz = inClazz;
		this.methodName = MethodName;
		this.returnClazz = returnType;
		this.inputClazzList = inputList;
	}
	
	public Class<?> getClazz(){
		return this.inClazz;
	}
	
	public String getMethodName(){
		return this.methodName;
	}
	
	public Class<?> getReturnType(){
		return this.returnClazz;
	}
	
	public List<Class<?>> getInputClassList(){
		return this.inputClazzList;
	}

	@Override
	public String toString() {
		String parameters;
		if(inputClazzList.size() == 0){
			parameters = "";
		} else {
			parameters = inputClazzList.get(0).toString();
			for(int i = 1; i < inputClazzList.size(); i++){
				parameters += ", "+inputClazzList.get(i);
			}
		}
		
		return "MethodRecord [ " + inClazz.toString() + " : " + returnClazz + " " + methodName + "(" + parameters + ")" + " ]";
	}
	
	
	
}
