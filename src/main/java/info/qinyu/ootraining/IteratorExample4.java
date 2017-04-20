package info.qinyu.ootraining;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class IteratorExample4 {

  public <T> T reduce(Collection<T> list, T identity, BinaryOperator<T> accumulator) {
    return reduce(list.parallelStream(), identity, accumulator);
  }

  public <T> T reduce(Stream<T> tStream, T identity, BinaryOperator<T> accumulator) {
    return tStream.reduce(identity, accumulator);
  }

}
