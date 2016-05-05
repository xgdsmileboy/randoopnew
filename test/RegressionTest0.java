
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest0 {

  public static boolean debug = false;

  @Test
  public void test1() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test1"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    mytest.MyClazz myClazz3 = testClazz0.createNewMyClazz(0, 0.0d);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertNotNull(myClazz3);

  }

  @Test
  public void test2() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test2"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    mytest.MyClazz myClazz3 = new mytest.MyClazz(0, 0.0d);
    boolean b4 = testClazz0.setXXX(myClazz3);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertTrue(b4 == true);

  }

  @Test
  public void test3() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test3"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    mytest.MyClazz myClazz1 = null;
    boolean b2 = testClazz0.setXXX(myClazz1);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertTrue(b2 == true);

  }

  @Test
  public void test4() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test4"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    double[] d_array3 = testClazz0.compute(0.0d, 0.0d);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertNotNull(d_array3);

  }

  @Test
  public void test5() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test5"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    mytest.MyClazz myClazz1 = null;
    // The following exception was thrown during execution in test generation
    try {
      double d2 = testClazz0.compute(myClazz1);
      org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException");
    } catch (java.lang.NullPointerException e) {
      // Expected exception.
      if (! e.getClass().getCanonicalName().equals("java.lang.NullPointerException")) {
        org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException, got " + e.getClass().getCanonicalName());
      }
    }

  }

  @Test
  public void test6() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test6"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    mytest.MyClazz myClazz3 = new mytest.MyClazz(0, 0.0d);
    double d4 = testClazz0.compute(myClazz3);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertTrue(d4 == 0.0d);

  }

  @Test
  public void test7() throws Throwable {

    if (debug) { System.out.format("%n%s%n","RegressionTest0.test7"); }


    mytest.TestClazz testClazz0 = new mytest.TestClazz();
    double[][] d_array_array2 = testClazz0.compute(0.0d);
    
    // Regression assertion (captures the current behavior of the code)
    org.junit.Assert.assertNotNull(d_array_array2);

  }

}
