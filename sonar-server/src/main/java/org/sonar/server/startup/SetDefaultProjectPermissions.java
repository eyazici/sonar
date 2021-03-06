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
package org.sonar.server.startup;

import com.google.common.collect.Maps;
import org.slf4j.LoggerFactory;
import org.sonar.server.platform.PersistentSettings;

import java.util.Map;

/**
 * @since 3.2
 */
public class SetDefaultProjectPermissions {
  private static final String SONAR_ADMINISTRATORS = "sonar-administrators";
  private static final String ANYONE_AND_USERS = "Anyone,sonar-users";

  private final PersistentSettings persistentSettings;

  public SetDefaultProjectPermissions(PersistentSettings persistentSettings) {
    this.persistentSettings = persistentSettings;
  }

  public void start() {
    if (persistentSettings.getSettings().getKeysStartingWith("sonar.role.").isEmpty()) {
      LoggerFactory.getLogger(SetDefaultProjectPermissions.class).info("Setting default project permissions");
      Map<String, String> props = Maps.newHashMap();
      props.put("sonar.role.admin.TRK.defaultGroups", SONAR_ADMINISTRATORS);
      props.put("sonar.role.user.TRK.defaultGroups", ANYONE_AND_USERS);
      props.put("sonar.role.codeviewer.TRK.defaultGroups", ANYONE_AND_USERS);

      // Support old versions of Views plugin
      props.put("sonar.role.admin.VW.defaultGroups", SONAR_ADMINISTRATORS);
      props.put("sonar.role.user.VW.defaultGroups", ANYONE_AND_USERS);
      props.put("sonar.role.codeviewer.VW.defaultGroups", ANYONE_AND_USERS);
      props.put("sonar.role.admin.SVW.defaultGroups", SONAR_ADMINISTRATORS);
      props.put("sonar.role.user.SVW.defaultGroups", ANYONE_AND_USERS);
      props.put("sonar.role.codeviewer.SVW.defaultGroups", ANYONE_AND_USERS);

      persistentSettings.saveProperties(props);
    }
  }
}
