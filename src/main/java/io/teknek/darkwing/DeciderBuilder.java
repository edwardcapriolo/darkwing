package io.teknek.darkwing;

import java.util.LinkedHashMap;
import java.util.Objects;

public class DeciderBuilder<Input,Returns> {
  
  private int seed;
  private final LinkedHashMap<Returns, Double> choices = new LinkedHashMap<>();
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
    return new DefaultDecider<Input,Returns>(seed, byteAble, choices, name);
  }
}
