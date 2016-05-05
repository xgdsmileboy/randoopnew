package randoop.mine.constant;

import java.util.Set;

import org.eclipse.jdt.core.dom.CompilationUnit;

import randoop.mine.util.ASTBuild;
import randoop.mine.util.FileOperate;

public class ConstantMining {
    private ConstantVisitor constantVisitor;
    
    public ConstantMining(String path){
      
      CompilationUnit cu = ASTBuild.parse(FileOperate.readFileToString(path));
      
      constantVisitor = new ConstantVisitor();
      
      cu.accept(constantVisitor);
      
      
    }
    
    public Set getSeedsSet(){
      return constantVisitor.getConstantsList();
    }
    
}