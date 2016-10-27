package io.teknek.darkwing;

import org.junit.Assert;
import org.junit.Test;

import io.teknek.darkwing.Darkwing;
import io.teknek.darkwing.Decider;

public class DeciderTest {

  @Test
  public void nWayTest(){
    Decider<String,String> n = Darkwing.<String,String> newDeciderBuilder()
            .withSeed(0)
            .withByteMaker((s) -> { return s.getBytes(); })
            .withChoice("group-a", .33)
            .withChoice("group-b", .33)
            .withLastChoice("group-c");
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-b", n.decide("bob"));
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-a", n.decide("sara"));
    Assert.assertEquals("group-c", n.decide("pete"));
  }
}
