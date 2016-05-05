package randoop.mine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

public class FileOperate {
	
    //read file content into a string
    public static String readFileToString(String filePath){
      
      if(null == filePath){
        System.out.println("FileOperate @12 test file path: "+filePath);
        System.exit(1);
      }
      
      if(!filePath.endsWith(".java")){
        filePath += ".java";
      }
      
      int index = filePath.lastIndexOf('.');
      filePath = filePath.substring(0, index).replace('.', '/')+filePath.substring(index);
      
      StringBuilder fileData = new StringBuilder(1000);
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(filePath));
      } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
   
      char[] buf = new char[10];
      int numRead = 0;
      try{
      while ((numRead = reader.read(buf)) != -1) {
        String readData = String.valueOf(buf, 0, numRead);
        fileData.append(readData);
        buf = new char[1024];
      }
   
      reader.close();
      }catch (Exception e ){
        e.printStackTrace();
      }
   
      return  fileData.toString();  
    }
    
	public static List<String> ergodic(File file,List<String> resultFileName){
		
        File[] files = file.listFiles();
        if(files==null)return resultFileName;
        for (File f : files) {
            if(f.isDirectory()){
                ergodic(f,resultFileName);
            }else
            	if(f.getName().endsWith(".java")){
            		String path = f.getPath();
            		resultFileName.add(path.substring(path.indexOf("src/")+"src/".length(), path.indexOf(".java")).replace("/", "."));
            	}
        }
        return resultFileName;
    }
}
