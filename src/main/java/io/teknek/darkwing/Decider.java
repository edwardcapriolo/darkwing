package io.teknek.darkwing;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 * For a given input decide what result (group) it is in
 *
 * @param <Input> data passed into the decider
 * @param <Result> data returned from the decider
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type_of_class")
@JsonSubTypes({
        //@Type(value = Event.class, name = "Event"),

        })
public interface Decider <Input, Result> {
  Result decide(Input input);
}
