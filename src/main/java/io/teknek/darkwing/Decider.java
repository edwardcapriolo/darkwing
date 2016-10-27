package io.teknek.darkwing;

/**
 * 
 * For a given input decide what result (group) it is in
 *
 * @param <Input> data passed into the decider
 * @param <Result> data returned from the decider
 */
public interface Decider <Input, Result> {
  Result decide(Input input);
}
