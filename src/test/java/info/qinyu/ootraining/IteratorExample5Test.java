package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import java.util.function.BinaryOperator;

import static io.reactivex.Observable.intervalRange;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class IteratorExample5Test {

  private IteratorExample5 iterators;
  private BinaryOperator<String> stringConcat;

  @Before
  public void setUp() throws Exception {
    iterators = new IteratorExample5();
    stringConcat = (string, string2) -> string + (string.isEmpty() ? "" : " ") + string2;
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 0, (sum, i) -> sum + i), is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 1, (multiply, i) -> multiply * i), is(24));
  }

  @Test
  public void should_return_hello_world_if_sum_hello_to_world() {
    assertThat(iterators.reduce(asList("hello", "world"), "", stringConcat), is("hello world"));
  }

  @Test
  public void should_return_foo_bar_if_concat_for_to_bar() {
    assertThat(iterators.reduce(of("foo", "bar"), "", stringConcat), is("foo bar"));
  }

  @Test(timeout = 5000)
  public void should_return_10_if_sum_1_to_4_async() {

    assertThat(iterators.reduce(intervalRange(1, 4, 1, 1, SECONDS), 0L, (sum, l) -> sum + l), is(10L));
  }
}