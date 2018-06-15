package com.camsolute.code.camp.lib.contract.display;

import com.camsolute.code.camp.lib.contract.display.Display.DisplayChannel;
import com.camsolute.code.camp.lib.contract.display.Display.DisplayLayout;

public interface DisplayHandler {

  public void process(Display display, DisplayChannel channel, DisplayLayout layout);

  public class DefaultDisplayHandler implements DisplayHandler {
    public void process(Display display, DisplayChannel channel, DisplayLayout layout) {}
  }
}
