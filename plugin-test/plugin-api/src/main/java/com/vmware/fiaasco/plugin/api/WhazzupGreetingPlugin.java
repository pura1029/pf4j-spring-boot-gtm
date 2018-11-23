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

package com.vmware.fiaasco.plugin.api;

import lombok.extern.log4j.Log4j2;
import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;

/**
 * Insert your comment for WhazzupGreeting here
 *
 * @author kumargautam
 */
@Log4j2
public class WhazzupGreetingPlugin extends Plugin {


    public WhazzupGreetingPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() throws PluginException {
        log.info("Starting plugin {}", wrapper.getPluginId());
        super.start();
    }

    @Override
    public void stop() throws PluginException {
        log.info("Stopping plugin {}", wrapper.getPluginId());
        super.stop(); // to close applicationContext
    }
}
