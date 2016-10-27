package io.teknek.darkwing;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

public class  DeciderBuilder<Input,Returns> {
  
  private int seed;
  private final Map<Returns, Double> choices = new LinkedHashMap<>();
  private ByteAble<Input> byteAble;
  
  /**
   * @param seed a seed that changes how the hash function is initialized
   * @return mutable builder
   */
  public DeciderBuilder<Input,Returns> withSeed(int seed){
    this.seed = seed;
    return this;
  }
  
  /**
   * By use this interface to convert the input to bytes instead of relying on java's hash code
   * @param byteAble
   * @return mutable builder
   */
  public DeciderBuilder<Input,Returns> withByteMaker(ByteAble<Input> byteAble){
    this.byteAble = byteAble;
    return this;
  }
  
  /**
   * 
   * @param name the choice
   * @param weight the random percentage of values that will hash to this choice
   * @return mutable builder
   */
  public DeciderBuilder<Input,Returns> withChoice(Returns name, double weight){
    if (weight <= 0){
      throw new IllegalArgumentException(String.format("weight must be > 0. Specified %s", weight));
    }
    Objects.requireNonNull(name, "Name must not be null");
    choices.put(name, weight);
    return this;
  }
  
  /**
   * The last choice will take the remainder of the random space
   * @param name the choice
   * @return the mutable builder
   */
  public Decider<Input,Returns> withLastChoice(Returns name){
    Objects.requireNonNull(name, "Name must not be null");
    @SuppressWarnings("unchecked")
    final Returns [] choiceNames = (Returns[]) new Object[choices.size() + 1];
    final int [] ranges = new int [choices.size()];
    final int thisSeed = seed;
    final ByteAble<Input> thisByteAble = this.byteAble;
    int i = 0;
    int lastRange = 0;
    for (Entry<Returns, Double> l : choices.entrySet()){
      choiceNames[i] = l.getKey();
      ranges[i] = lastRange + (int) (l.getValue() * Integer.MAX_VALUE);
      lastRange = ranges[i];
      i++;
    }
    choiceNames[i] = name;
    
    return new Decider<Input,Returns>(){
      @Override
      public Returns decide(Input input) {
        if (input == null){
          return choiceNames[0];
        }
        int hash = 0;
        if (thisByteAble != null){
          byte [] b = thisByteAble.toBytes(input);
          hash = HashUtil.hash32AndAbs(b, b.length, thisSeed);
        } else {
          hash = HashUtil.safeAbs(input.hashCode()); 
        }
        int i = 0;
        for ( ; i < ranges.length; i++) {
          if (hash < ranges[i]){
            return choiceNames[i];
          }
        }
        return choiceNames[i];
      }
    };
  }
}
