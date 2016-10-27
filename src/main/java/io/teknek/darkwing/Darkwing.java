package io.teknek.darkwing;

public class Darkwing {
  
  /**
   * 
   * @return a builder which constructs the parameters for the feature-flag or dark launch
   */
  public static <Input,Returns> DeciderBuilder<Input,Returns> newDeciderBuilder(){
    return new DeciderBuilder<Input,Returns>();
  }
}

