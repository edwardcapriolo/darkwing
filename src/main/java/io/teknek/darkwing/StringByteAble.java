package io.teknek.darkwing;

public class StringByteAble implements ByteAble<String>{

  @Override
  public byte[] toBytes(String input) {
    return input.getBytes();
  }

}
