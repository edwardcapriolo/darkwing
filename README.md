Darkwing
======
Dark launch and feature flag system. 

Motivation
-------
When developing features for a highly utilized system it is not a good idea to release a new feature to all users at once. That could result in poor performance, unexpected bugs, or random experiences for users. A first cut idea might be to introduce the feature to a small percentage of the population. 

Usage
=====

Suppose one wants to divide there userbase into groups

    Decider<String,String> n = Darkwing.<String,String> newDeciderBuilder()
            .withChoice("group-a", .33)
            .withChoice("group-b", .33)
            .withLastChoice("group-c");
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-b", n.decide("bob"));
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-c", n.decide("pete"));


