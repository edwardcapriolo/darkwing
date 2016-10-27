package io.teknek.darkwing.legacy;

import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import io.teknek.darkwing.HashUtil;

@Deprecated
public class NWayBuilder {
  private int seed;
  private final Map<String, Double> choices = new LinkedHashMap<>();
  
  public NWayBuilder withHashSeed(int seed){
    this.seed = seed;
    return this;
  }
  
  public NWayBuilder withChoice(String name, double weight){
    if (weight <= 0){
      throw new IllegalArgumentException(String.format("weight must be > 0. Specified %s", weight));
    }
    Objects.requireNonNull(name, "Name must not be null");
    choices.put(name, weight);
    return this;
  }
  
  public NWayDecider withLastChoice(String name){
    final String [] choiceNames = new String[choices.size() + 1];
    final int [] ranges = new int [choices.size()];
    final int thisSeed = seed;
    int i = 0;
    int lastRange = 0;
    for (Entry<String, Double> l : choices.entrySet()){
      choiceNames[i] = l.getKey();
      ranges[i] = lastRange + (int) (l.getValue() * Integer.MAX_VALUE);
      lastRange = ranges[i];
      i++;
    }
    choiceNames[choices.size() -1] = name;
    return new NWayDecider(){
      
      @Override
      public int decide(String s) {
        if (s == null){
          return 0;
        }
        int hash = HashUtil.hash32AndAbs(s.getBytes(), s.length(), thisSeed);
        int i = 0;
        for ( ; i < ranges.length; i++) {
          if (hash < ranges[i]){
            return i;
          }
        }
        return i;
      }

    };
  }
}