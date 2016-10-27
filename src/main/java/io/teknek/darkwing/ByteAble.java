package io.teknek.darkwing;

/**
 * 
 * @author Sathish Dhinakaran (byteme media llc)
 *
 */
public interface ByteAble<Input> {
  /**
   * Convert given input to a byte so we can compute a hash value for it
   * @param input data to be hashed
   * @return a byte array representing the input
   */
  byte [] toBytes(Input input);
}
