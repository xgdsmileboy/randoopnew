package mytest;

public class TestClazz {
	
	public double compute(MyClazz myClazz){
		int a = myClazz.getA();
		double b = myClazz.getB();
		return a*b;
	}
	
	public MyClazz createNewMyClazz(int a, double b){
		return new MyClazz(a, b);
	}
	
	public double compute(double a,double b){
		return a*b;
	}
	
	class clazz2{
		
	}
}

class Clazz1{
	
}
