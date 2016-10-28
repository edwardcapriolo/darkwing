package io.teknek.darkwing;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * @author Sathish Dhinakaran (byteme media llc)
 *
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type_of_class")
@JsonSubTypes({
        //@Type(value = Event.class, name = "Event"),

        })
public interface ByteAble<Input> {
  /**
   * Convert given input to a byte so we can compute a hash value for it
   * @param input data to be hashed
   * @return a byte array representing the input
   */
  byte [] toBytes(Input input);
}
