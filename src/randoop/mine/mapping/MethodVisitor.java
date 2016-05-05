package randoop.mine.mapping;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class MethodVisitor extends ASTVisitor{

	public boolean visit(MethodDeclaration methodDeclaration){
		
		if(methodDeclaration.getReturnType2() instanceof PrimitiveType){
			return true;
		}
		
//		TypeDeclaration typeDeclaration = (TypeDeclaration) methodDeclaration.getParent();
//		CompilationUnit compilationUnit = (CompilationUnit) typeDeclaration.getParent();
		
		System.out.println(ASTNode.nodeClassForType(methodDeclaration.getReturnType2().getNodeType()));
		
		if(methodDeclaration.getReturnType2() instanceof SimpleType){
			SimpleType simpleType = (SimpleType) methodDeclaration.getReturnType2();
			System.out.println(simpleType.getName());
			
			return true;
		}
		
		System.out.println(methodDeclaration.getReturnType2().getClass());
		
		return true;
	}
}
