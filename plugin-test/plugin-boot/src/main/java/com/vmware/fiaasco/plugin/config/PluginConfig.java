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

package com.vmware.fiaasco.plugin.config;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.log4j.Log4j2;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.vmware.fiaasco.plugin.api.Greeting;

/**
 * PluginConfig here
 *
 * @author kumargautam
 */
@Configuration
@Log4j2
public class PluginConfig {


    private SpringPluginManager pluginManager;

    @Autowired
    public PluginConfig(SpringPluginManager pm) {
        log.info("Plugin Config Start...");
        this.pluginManager = pm;
    }

    @PostConstruct
    public void init() {
        log.info("Plugin init Start...");
        showDefaultExtension();
    }

    @PreDestroy
    public void cleanup() {
        log.info("Plugin cleanup Start...");
        pluginManager.stopPlugins();
    }

    private void showDefaultExtension() {
        log.info("Start execution of showDefaultExtension()...");
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        for (Greeting greeting : greetings) {
            log.info("Register default extension '{}' as bean", greeting.getClass().getSimpleName());
        }
    }
}
