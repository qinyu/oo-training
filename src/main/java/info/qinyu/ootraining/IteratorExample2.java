package info.qinyu.ootraining;

import java.util.Collection;
import java.util.function.BinaryOperator;

public class IteratorExample2 {

  public int reduce(Collection<Integer> list, int identity, BinaryOperator<Integer> accumulator) {
    final int[] result = {identity};
    list.forEach(integer -> result[0] = accumulator.apply(result[0], integer));
    return result[0];
  }

}
