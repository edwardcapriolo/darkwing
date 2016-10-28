package io.teknek.darkwing.config;

import java.util.Map;

import io.teknek.darkwing.Decider;

public interface ConfigSource {

  Map<String,Decider<?,?>> loadConfig();

}
