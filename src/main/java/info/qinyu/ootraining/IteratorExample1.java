package info.qinyu.ootraining;

import java.util.Collection;

/**
 * Created by yqin on 4/20/17.
 */
public class IteratorExample1 {

  public int sumCollection(Collection<Integer> list) {
    int sum = 0;
    for (int i : list) {
      sum += i;
    }
    return sum;
  }

  public int multiplyCollection(Collection<Integer> list) {
    int multiply = 1;
    for (int i : list) {
      multiply *= i;
    }
    return multiply;
  }

}
