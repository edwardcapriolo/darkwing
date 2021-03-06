Darkwing
======
Dark launch and feature flag system. 

Motivation
-------
When developing features for a highly utilized system it is not a good plan to release a new feature to all users at once. The new feature could result in poor performance, unexpected bugs, or a poor experience for users. Darkwing provides a framework for feature flags to role out features in a controlable manner.

See https://www.facebook.com/notes/facebook-engineering/hammering-usernames/96390263919/ for more information and practical use cases.

Decider
=====

A `Decider` segements input into groups. It is important to note that groups are not simply on/off 50%/50% groups "a" and "b". Darkwing allows N groups, where each group is a configurable portion of the space.

Here we devide the userspace into three groups of roughly 33% each.

    Decider<String,String> n = Darkwing.<String,String> newDeciderBuilder()
            .withChoice("group-a", .33)
            .withChoice("group-b", .33)
            .withLastChoice("group-c");
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-b", n.decide("bob"));
    Assert.assertEquals("group-a", n.decide("ed"));
    Assert.assertEquals("group-c", n.decide("pete"));

We could just as easily create 4 groups with different percentages

    Decider<String,String> n = Darkwing.<String,String> newDeciderBuilder()
            .withChoice("group-a", .03)
            .withChoice("group-b", .50)
            .withChoice("group-c", .10)
            .withLastChoice("group-d");

Note: the `last choice`  is always the remainder of the space. In this case the size of group-d is roughly 37% of the space 100 - (3 + 50 + 10)

We do this by diving the hash space into linear chunks.

Reloadable and plugable based configuration sources
-------

With the decider api is great, but you surely do not need it to implement case statements in your code. Darkwing brings the framework to load and reload the configuration of system from plugable sources. So for example imagine we wish to ab test a feature by starting at 1% of the population and moving all the way to 100%. Darkwing has build in support to reload it's configuration at a user configurable rate. The configuration could come from anywhere, a config file, memcache, a NoSQL Database etc.

For example imagine we want a to reload our configuration periodically from memcache. Something like this can be easily created:

    Darkwing darkwing = new Darkwing(new ConfigReloader(new MemcacheConfig(host, port), 
            30, TimeUnit.SECONDS));
    darkwing.init();

Now the portions of our users base with request logging enabled can be controlled via a flag

    public void processRequest(String url, String user){
      if (groupA.equals(darkwing.decide("should_log", user, "group-b"))){
        logRequest(user);
      }
    }

What about (xyz) alternative
-------

Poking around I rarely find things that do exactly what darkwing does the way it does it. Feature flag 4j has a `client flipping strategy` but it is very manual https://github.com/clun/ff4j/wiki/Flipping-Strategies#clientfilterstrategy  and the `pondering strategy` only supports an a and b side
https://github.com/clun/ff4j/wiki/Flipping-Strategies#darklaunchstrategy

Darkwing is inherently pondering N sided decisions and always flipping based on user.
