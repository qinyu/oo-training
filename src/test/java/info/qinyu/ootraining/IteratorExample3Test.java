package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by yqin on 4/20/17.
 */
public class IteratorExample3Test {

  private IteratorExample3 iterators;

  @Before
  public void setUp() throws Exception {
    iterators = new IteratorExample3();
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 0, (sum, integer) -> sum + integer), is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    assertThat(iterators.reduce(asList(1, 2, 3, 4), 1, (multiply, integer) -> multiply * integer), is(24));
  }

  @Test
  public void should_return_hello_world_if_sum_hello_to_world() {
    assertThat(iterators.reduce(asList("hello", "world"), "", (concatString, string) -> concatString + (concatString.isEmpty() ? "" : " ") + string), is("hello world"));
  }
}