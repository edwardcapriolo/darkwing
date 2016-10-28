package io.teknek.darkwing;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultDecider<Input, Result> implements Decider<Input, Result> {

  private final int seed;

  private final LinkedHashMap<Result, Double> choices;

  private final ByteAble<Input> byteAble;

  private final Result[] choiceNames;

  private final int[] ranges;

  @SuppressWarnings("unchecked")
  @JsonCreator
  public DefaultDecider(@JsonProperty("seed") int seed, 
          @JsonProperty("byteAble") ByteAble<Input> byteAble, 
          @JsonProperty("choices") LinkedHashMap<Result, Double> choices,
          @JsonProperty("lastChoice") Result lastChoice) {
    this.choices = choices;
    this.seed = seed;
    this.byteAble = byteAble;
    choiceNames = (Result[]) new Object[choices.size() + 1];
    ranges = new int[choices.size()];
    int i = 0;
    int lastRange = 0;
    for (Entry<Result, Double> l : choices.entrySet()) {
      choiceNames[i] = l.getKey();
      ranges[i] = lastRange + (int) (l.getValue() * Integer.MAX_VALUE);
      lastRange = ranges[i];
      i++;
    }
    choiceNames[i] = lastChoice;
  }

  @Override
  public Result decide(Input input) {
    if (input == null) {
      return choiceNames[0];
    }
    int hash = 0;
    if (byteAble != null) {
      byte[] b = byteAble.toBytes(input);
      hash = HashUtil.hash32AndAbs(b, b.length, seed);
    } else {
      hash = HashUtil.safeAbs(input.hashCode());
    }
    int i = 0;
    for (; i < ranges.length; i++) {
      if (hash < ranges[i]) {
        return choiceNames[i];
      }
    }
    return choiceNames[i];
  }

  public int getSeed() {
    return seed;
  }

  public LinkedHashMap<Result, Double> getChoices() {
    return choices;
  }

  public ByteAble<Input> getByteAble() {
    return byteAble;
  }

  public Result[] getChoiceNames() {
    return choiceNames;
  }

  public int[] getRanges() {
    return ranges;
  }
  
  
}
