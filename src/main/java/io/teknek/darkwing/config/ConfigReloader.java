package io.teknek.darkwing.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.teknek.darkwing.Decider;

public class ConfigReloader {

  private final ConfigSource configSource;
  private final ScheduledExecutorService service;
  private final long delay;
  private final TimeUnit unit;
  private volatile Map<String, Decider<?, ?>> config; 
  
  public ConfigReloader(ConfigSource configSource, long delay, TimeUnit unit){
    this.configSource = configSource;
    this.delay = delay;
    this.unit = unit;
    config = new HashMap<>();
    service = Executors.newScheduledThreadPool(1);
  }
  
  public void init(){
    //TODO callable with timeout
    try {
      runOnce();
    } catch (RuntimeException ex){
      
    }
    service.schedule(() -> {
      runOnce();
    }, delay, unit);
  }
  
  private void runOnce(){
    Map<String, Decider<?, ?>> c = configSource.loadConfig();
    if (c != null){
      config = Collections.unmodifiableMap(c);
    }
  }

  public Map<String, Decider<?, ?>> getConfig() {
    return config;
  }
  
  public void shutdown(){
    service.shutdown();
    try {
      service.awaitTermination(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
    }
  }
}
