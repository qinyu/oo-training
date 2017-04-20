package info.qinyu.ootraining;

import java.util.Collection;
import java.util.function.BinaryOperator;

public class IteratorExample3 {

  public <T> T reduce(Collection<T> list, T identity, BinaryOperator<T> accumulator) {
    final T[] result = (T[]) new Object[]{identity};
    list.forEach(integer -> result[0] = accumulator.apply(result[0], integer));
    return result[0];
  }

}
