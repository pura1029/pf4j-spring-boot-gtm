/*
 * Copyright (c) 2018 VMware, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.vmware.fiaasco.plugin.demo;

import lombok.extern.log4j.Log4j2;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * Insert your comment for WelcomePlugin here
 *
 * @author kumargautam
 */
@Log4j2
public class WelcomePlugin extends Plugin {

    public WelcomePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        log.info("Starting plugin {}", wrapper.getPluginId());
    }

    @Override
    public void stop() {
        log.info("Stopping plugin {}", wrapper.getPluginId());
    }
}