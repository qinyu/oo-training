package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import java.util.function.BinaryOperator;

import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;



/**
 * Created by yqin on 4/20/17.
 */
public class IteratorExample4Test {

  private IteratorExample4 iterators;
  private BinaryOperator<String> stringConcator;

  @Before
  public void setUp() throws Exception {
    iterators = new IteratorExample4();
    stringConcator = (string, string2) -> string + (string.isEmpty() ? "" : " ") + string2;
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 0, (integer, integer2) -> integer + integer2), is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 1, (integer, integer2) -> integer * integer2), is(24));
  }

  @Test
  public void should_return_hello_world_if_sum_hello_to_world() {
    assertThat(iterators.reduce(asList("hello", "world"), "", stringConcator), is("hello world"));
  }

  @Test
  public void should_return_foo_bar_if_concat_for_to_bar() {
    assertThat(iterators.reduce(of("foo", "bar"), "", stringConcator), is("foo bar"));
  }
}