package randoop.mine.mapping;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import randoop.ExecutionOutcome;
import randoop.main.GenInputsAbstract;
import randoop.mine.util.FileOperate;
import randoop.operation.CallableOperation;
import randoop.operation.ConcreteOperation;
import randoop.operation.ConstructorCall;
import randoop.operation.MethodCall;
import randoop.sequence.Variable;
import randoop.types.ConcreteArrayType;
import randoop.types.ConcreteSimpleType;
import randoop.types.ConcreteType;
import randoop.types.ConcreteTypeTuple;

public class MineMapping {

	private static boolean debugOn = false;

	public static boolean mineObjectMapping() {

		if (GenInputsAbstract.generate_object_file_Path == null) {
			if (debugOn) {
				System.out.println("\n dont record other methods' output type!\n");
			}
			return false;
		}

		File file = new File(System.getProperty("user.dir") + "/src/" + GenInputsAbstract.generate_object_file_Path);

		List<String> fileList = FileOperate.ergodic(file, new ArrayList<>());
		if (fileList == null || fileList.size() == 0) {
			if (debugOn) {
				System.out.println(
						"\n cannot find any files at path :" + GenInputsAbstract.generate_object_file_Path + "\n");
				return false;
			}
		}

		for (String clazz : fileList) {
			computeMethodTypeMapping(clazz);
		}

		return true;
	}

	private static void computeMethodTypeMapping(String clazzName) {
		Class clazz = null;
		try {
			clazz = Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// discard abstract class and interface
		if ((clazz.getModifiers() & Modifier.ABSTRACT) != 0 || (clazz.getModifiers() & Modifier.INTERFACE) != 0) {
			return;
		}
		
		ConcreteSimpleType declaringType = new ConcreteSimpleType(clazz);

		Constructor<?>[] constructors = clazz.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if((constructor.getModifiers() & Modifier.PUBLIC) != 0){
				ConstructorCall constructorCall = new ConstructorCall(constructor);
				List<ConcreteType> inputTypes = getParametersConcreteType(constructor.getParameterTypes());
				
				ConcreteOperation concreteOperation = new ConcreteOperation(constructorCall, declaringType, new ConcreteTypeTuple(inputTypes), declaringType);
				GenInputsAbstract.addMethodRecord(new ConcreteSimpleType(clazz), concreteOperation);
				
//				Class<?>[] parameters = constructor.getParameterTypes();
//				MethodRecord methodRecord = new MethodRecord(clazz, constructor.getName(), clazz, new ArrayList<>(Arrays.asList(parameters)));
//				GenInputsAbstract.addMethodRecord(methodRecord);
			}
		}
		
		
		Method[] methods = clazz.getMethods();
		
		for (Method method : methods) {
			String methodName = method.getName();
			// discard methods extends from object class
			if (methodName.equals("wait") || methodName.equals("equals") || methodName.equals("toString")
					|| methodName.equals("hashCode") || methodName.equals("getClass") || methodName.equals("notify")
					|| methodName.equals("notifyAll")) {
				continue;
			}
			
			if ((method.getModifiers() & Modifier.PUBLIC) != 0) {
				Class returnType = method.getReturnType();
				if(returnType.isPrimitive() || returnType.isEnum() || returnType.isArray()){
					continue;
				}
				
				ConcreteType returnConcreteType = new ConcreteSimpleType(returnType);
				MethodCall methodCall = new MethodCall(method);
				List<ConcreteType> inputTypes = getParametersConcreteType(method.getParameterTypes());
				ConcreteOperation concreteOperation = new ConcreteOperation(methodCall, declaringType, new ConcreteTypeTuple(inputTypes), returnConcreteType);
				
				GenInputsAbstract.addMethodRecord(returnConcreteType, concreteOperation);
				
//				Class<?>[] parameters = method.getParameterTypes();
//				MethodRecord methodRecord = new MethodRecord(clazz, methodName, returnType,
//						new ArrayList<>(Arrays.asList(parameters)));
//				GenInputsAbstract.addMethodRecord(methodRecord);
			}
		}

	}
	
	private static List<ConcreteType> getParametersConcreteType(Class<?>[] parameters){
		List<ConcreteType> list = new ArrayList<>();
		
		for (Class<?> clazz : parameters) {
			ConcreteType concreteType = null;
			if(clazz.isArray()){
				concreteType = getArrayType(clazz);
			}else {
				concreteType = new ConcreteSimpleType(clazz); 
			}
			list.add(concreteType);
		}
		
		return list;
	}

	private static ConcreteType getArrayType(Class<?> clazz){
		assert clazz.isArray();
		ConcreteType concreteType = null;
		clazz = clazz.getComponentType();
		if(clazz.isArray()){
			concreteType = getArrayType(clazz.getComponentType());
		} else {
			concreteType = new ConcreteSimpleType(clazz);
		}
		return new ConcreteArrayType(concreteType);
	}
	
	public static void main(String[] args) {
		String file = "mytest";

		File file2 = new File(System.getProperty("user.dir") + "/src/" + file);
		List<String> files = FileOperate.ergodic(file2, new ArrayList<>());

		for (String string : files) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(string);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("=======" + clazz.getName());
			for (Method method : clazz.getMethods()) {
				System.out.println(method.getName());
			}
		}
	}
}
