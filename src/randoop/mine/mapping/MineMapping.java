package randoop.mine.mapping;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import randoop.main.GenInputsAbstract;
import randoop.mine.util.FileOperate;

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

		Constructor<?>[] constructors = clazz.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if((constructor.getModifiers() & Modifier.PUBLIC) != 0){
				Class<?>[] parameters = constructor.getParameterTypes();
				MethodRecord methodRecord = new MethodRecord(clazz, constructor.getName(), null, new ArrayList<>(Arrays.asList(parameters)));
				GenInputsAbstract.addMethodRecord(methodRecord);
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
				Class<?>[] parameters = method.getParameterTypes();
				MethodRecord methodRecord = new MethodRecord(clazz, methodName, returnType,
						new ArrayList<>(Arrays.asList(parameters)));
				GenInputsAbstract.addMethodRecord(methodRecord);
			}
		}

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
