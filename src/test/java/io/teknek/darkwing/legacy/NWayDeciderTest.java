package io.teknek.darkwing.legacy;

import org.junit.Assert;
import org.junit.Test;

import io.teknek.darkwing.legacy.NWayBuilder;
import io.teknek.darkwing.legacy.NWayDecider;

public class NWayDeciderTest {

  @Test
  public void nWayTest(){
    NWayDecider n = new NWayBuilder()
            .withHashSeed(0)
            .withChoice("a", .33)
            .withChoice("b", .33)
            .withLastChoice("c");
    Assert.assertEquals(0, n.decide("ed"));
    Assert.assertEquals(1, n.decide("bob"));
    Assert.assertEquals(0, n.decide("ed"));
    Assert.assertEquals(0, n.decide("sara"));
    Assert.assertEquals(2, n.decide("pete"));
  }
  
}