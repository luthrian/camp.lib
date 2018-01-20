/**
 * ***************************************************************************** Copyright (C) 2017
 * Christopher Campbell (campbellccc@gmail.com)
 *
 * <p>This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * <p>Contributors: Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial
 * release ****************************************************************************
 */
package com.camsolute.code.camp.lib;

import java.util.UUID;

public class InstanceId implements InstanceIdInterface {

  public static boolean _DEBUG = true;

  private final String id;

  public InstanceId() {
    this.id = UUID.randomUUID().toString();
  }

  public InstanceId(String id) {
    if (id.isEmpty() || id == null) {
      this.id = UUID.randomUUID().toString();
    } else {
      this.id = id;
    }
  }

  public String id() {
    return this.id;
  }

  @Override
  public InstanceId clone() {
    return new InstanceId(this.id);
  }

  public static InstanceId clone(InstanceId instance) {
    return new InstanceId(instance.id());
  }

  @Override
  public String toString() {
    return this.id;
  }
}
