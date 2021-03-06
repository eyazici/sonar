/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.api.notifications;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.common.collect.Maps;

/**
 * <p>
 * This class represents a notification that will be delivered to users. This is a general concept and it has no
 * knowledge of the possible ways to be delivered (see {@link NotificationChannel}) or of the users who should
 * receive it (see {@link NotificationDispatcher}).
 * </p> 
 * 
 * @since 2.10
 */
public class Notification implements Serializable {

  private String type;

  private HashMap<String, String> fields = Maps.newHashMap(); // NOSONAR false-positive due to serialization : usage of HashMap instead of
                                                              // Map

  /**
   * <p>
   * Create a new {@link Notification} of the given type.
   * </p>
   * Example: type = "new-violations"
   * 
   * @param type the type of notification
   */
  public Notification(String type) {
    this.type = type;
  }

  /**
   * Returns the type of the notification
   * 
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Adds a field (kind of property) to the notification
   * 
   * @param field the name of the field (= the key)
   * @param value the value of the field
   * @return the notification itself
   */
  public Notification setFieldValue(String field, String value) {
    fields.put(field, value);
    return this;
  }

  /**
   * Returns the value of a field.
   * 
   * @param field the field
   * @return the value of the field
   */
  public String getFieldValue(String field) {
    return fields.get(field);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Notification)) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    Notification other = (Notification) obj;
    return this.type.equals(other.type) && this.fields.equals(other.fields);
  }

  @Override
  public int hashCode() {
    return type.hashCode() * 31 + fields.hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
