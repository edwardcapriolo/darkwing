package io.teknek.darkwing.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.teknek.darkwing.Decider;

public class FileBackedConfigSource implements ConfigSource {

  private ObjectMapper om = new ObjectMapper();
  {
      om.enableDefaultTyping();
  }
  
  private File configFile;
  
  @Override
  public Map<String, Decider<?, ?>> loadConfig() {
    try {
      return om.readValue(configFile, new TypeReference<Map<String,Decider<?,?>>>() { } );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
