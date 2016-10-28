package io.teknek.darkwing;

import io.teknek.darkwing.config.ConfigReloader;

public class Darkwing {
  
  private ConfigReloader reloader;
  
  public Darkwing(ConfigReloader reloader){
    this.reloader = reloader;
  }
  
  public void init(){
    reloader.init();
  }
  
  public void shutdown(){
    reloader.shutdown();
  }
  
  public <Input,Result> Result decide(String key, Input input){
    @SuppressWarnings("unchecked")
    Decider<Input, Result> l = (Decider<Input, Result>) reloader.getConfig().get(key);
    if (l == null){
      return null;
    }
    return l.decide(input);
  }
  
  //public <Input, Result> 
  public <Input,Result> Result decide(String key, Input input, Result defaultValue){
    @SuppressWarnings("unchecked")
    Decider<Input, Result> l = (Decider<Input, Result>) reloader.getConfig().get(key);
    if (l == null){
      return defaultValue;
    }
    return l.decide(input);
  }
  
  /**
   * 
   * @return a builder which constructs the parameters for the feature-flag or dark launch
   */
  public static <Input,Result> DeciderBuilder<Input,Result> newDeciderBuilder(){
    return new DeciderBuilder<Input,Result>();
  }
}

