package info.qinyu.ootraining;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class IteratorExample5 {

  public <T> T reduce(Collection<T> list, T identity, BinaryOperator<T> accumulator) {
    return reduce(list.parallelStream(), identity, accumulator);
  }

  public <T> T reduce(Stream<T> stream, T identity, BinaryOperator<T> accumulator) {
    return stream.reduce(identity, accumulator);
  }

  public <T> T reduce(Observable<T> observable, T seed, BiFunction<T, T, T> reducer) {
    return observable.reduce(seed, reducer).blockingGet();
  }
}
