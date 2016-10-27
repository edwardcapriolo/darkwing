Darkwing
======
Dark launch and feature flag utilities

Usage
=====

Suppose one wants to divide there userbase into groups

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


