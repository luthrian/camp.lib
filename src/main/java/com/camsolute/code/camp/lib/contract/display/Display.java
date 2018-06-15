package com.camsolute.code.camp.lib.contract.display;

import com.camsolute.code.camp.lib.contract.display.DisplayHandler.DefaultDisplayHandler;
import com.camsolute.code.camp.lib.models.Attribute;

public interface Display {
  public static enum DisplayLayout {
    list,
    list_entry,
    detailed,
    brief,
    minimal,
    regular;
  }

  public static enum DisplayChannel {
    mobile,
    browser,
    app,
    api;
  }

  public static enum DisplayFormat {
    json,
    html,
    xml,
    csv;
  }

  public void initialize(DisplayHandler handler);

  public void display();

  public class AttributeDisplay implements Display {

    private Attribute<?> attributeReference;

    private DisplayHandler handler;

    public AttributeDisplay(Attribute<?> attribute) {
      attributeReference = attribute;
      initialize(new DefaultDisplayHandler());
    }

    public void initialize(DisplayHandler handler) {
      this.handler = handler;
    }

    public void display() {
      // TODO Auto-generated method stub

    }
  }
}
