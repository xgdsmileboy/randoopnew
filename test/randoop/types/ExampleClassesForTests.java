package randoop.types;

import java.util.ArrayList;
import java.util.List;

class A<T> implements Comparable<T> {
  @Override
  public int compareTo(T o) {
    return 0;
  }
}

class B extends A<String> {}

class C extends A<Integer> {}

class D<S, T> extends A<T> {}

class E<S, T> {}

class F<T, S> extends E<S, T> {}

class G<S> {}

class H<T> extends G<T> implements Comparable<T> {
  @Override
  public int compareTo(T o) {
    return 0;
  }
}

class I {}

class J<T> extends I {}

// these are for arrays

class ArrayHarvest<T> {
  public T[] genericArrayArg1() {
    return null;
  }

  public List<T>[] genericArrayArg2() {
    return null;
  }

  public int[] concreteArrayArg1() {
    return null;
  }

  public List<Integer>[] concreteArrayArg2() {
    return null;
  }
}

class GenericWithOperations<T> {
  public T theField;
  public List<T> theList;

  public GenericWithOperations(T theValue) {
    this.theField = theValue;
    this.theList = new ArrayList<>();
  }

  public T getTheField() {
    return theField;
  }

  public void setTheField(T theValue) {
    theField = theValue;
  }

  public List<T> getTheList() {
    return theList;
  }

  public void addAll(T[] a) {
    for (T t : a) {
      theList.add(t);
    }
  }
}

class ConcreteWithOperations extends GenericWithOperations<String> {
  public ConcreteWithOperations(String theValue) {
    super(theValue);
  }
}
