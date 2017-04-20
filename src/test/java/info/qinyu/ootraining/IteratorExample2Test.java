package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by yqin on 4/20/17.
 */
public class IteratorExample2Test {

  private IteratorExample2 iterator;

  @Before
  public void setUp() throws Exception {
    iterator = new IteratorExample2();
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    int sum = iterator.reduce(asList(1, 2, 3, 4), 0, (integer, integer2) -> integer + integer2);
    assertThat(sum, is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    int multiply = iterator.reduce(asList(1, 2, 3, 4), 1, (integer, integer2) -> integer * integer2);
    assertThat(multiply, is(24));
  }
}