package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IteratorExample2Test {

  private IteratorExample2 iterator;

  @Before
  public void setUp() throws Exception {
    iterator = new IteratorExample2();
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    assertThat(iterator.reduce(asList(1, 2, 3, 4), 0, (sum, i) -> sum + i), is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    assertThat(iterator.reduce(asList(1, 2, 3, 4), 1, (multiply, i) -> multiply * i), is(24));
  }
}