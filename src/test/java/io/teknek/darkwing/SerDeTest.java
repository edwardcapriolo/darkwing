package io.teknek.darkwing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerDeTest {

  ObjectMapper om = new ObjectMapper();
  {
      om.enableDefaultTyping();
  }

  @Test
  public void testSerialization() throws IOException{
    Decider<String,String> n = Darkwing.<String,String> newDeciderBuilder()
            .withSeed(0)
            .withByteMaker(new StringByteAble())
            .withChoice("group-a", .33)
            .withChoice("group-b", .33)
            .withLastChoice("group-c");
    Assert.assertEquals("group-a", n.decide("ed"));
    @SuppressWarnings("unchecked")
    Decider<String,String> back = om.readValue( om.writeValueAsString(n), Decider.class);
    Assert.assertEquals("group-a", back.decide("ed"));
  }
}
