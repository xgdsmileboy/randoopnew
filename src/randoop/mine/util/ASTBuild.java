package randoop.mine.util;

import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTBuild {

	public static CompilationUnit parse(String source) {
		if(source == null){
			return null;
		}
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		return cu;
	}
	
	public static CompilationUnit parse(String source, int kind){
		if(source == null){
			return null;
		}
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(kind);
		parser.setResolveBindings(true);
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		return compilationUnit;
	}
	
	public static CompilationUnit genASTFromICU(ICompilationUnit unit){
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		Map<?, ?> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		astParser.setSource(unit);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setResolveBindings(true);
		CompilationUnit compilationUnit = (CompilationUnit) astParser.createAST(null);
		return compilationUnit;
	}
}
