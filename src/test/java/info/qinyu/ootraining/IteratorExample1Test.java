package info.qinyu.ootraining;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IteratorExample1Test {

  private IteratorExample1 iterator;

  @Before
  public void setUp() throws Exception {
    iterator = new IteratorExample1();
  }

  @Test
  public void should_return_10_if_sum_1_to_4() {
    assertThat(iterator.sumCollection(asList(1, 2, 3, 4)), is(10));
  }

  @Test
  public void should_return_24_if_multiply_1_to_4() {
    assertThat(iterator.multiplyCollection(asList(1, 2, 3, 4)), is(24));
  }
}