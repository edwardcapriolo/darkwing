package io.teknek.darkwing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import io.teknek.darkwing.config.ConfigReloader;
import io.teknek.darkwing.config.ConfigSource;

public class EndToEndTest {

  String groupA = "group-a";
  String groupB = "group-b";
  String feature = "should_log";

  @Test
  public void test(){
    WebServer w = new WebServer();
    w.processRequest("/bla", "ed");
    w.processRequest("/bla", "sara");
    w.processRequest("/bla", "tony");
    w.processRequest("/bla", "pete");
    Assert.assertEquals(new TreeSet<String>(Arrays.asList("ed", "sara", "tony")), w.logged);
  }
  
  class WebServer {

    private Set<String> logged = new TreeSet<>();
    private Darkwing darkwing = new Darkwing(new ConfigReloader(createConfig(groupA, groupB), 
            30, TimeUnit.SECONDS));
    
    public WebServer(){
      darkwing.init();
    }
    
    public void processRequest(String url, String user){
      if (groupA.equals(darkwing.decide(feature, user))){
        logRequest(user);
      }
    }
    
    private void logRequest(String user){
      logged.add(user);
    }
  }
  
  private ConfigSource createConfig(String groupA, String groupB) {
    return new ConfigSource() {
      @Override
      public Map<String, Decider<?, ?>> loadConfig() {
        Map<String, Decider<?, ?>> m = new HashMap<>();
        m.put(feature, new DeciderBuilder<String, String>()
                .withByteMaker(new StringByteAble())
                .withChoice(groupA, .50)
                .withLastChoice(groupB));
        return m;
      }

    };
  }
}
