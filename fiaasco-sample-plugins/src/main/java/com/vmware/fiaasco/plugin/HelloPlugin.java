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

package com.vmware.fiaasco.plugin;

import org.pf4j.Extension;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vmware.fiaasco.plugin.api.Greeting;
import com.vmware.fiaasco.plugin.config.PluginConfig;
import com.vmware.fiaasco.plugin.config.SpringConfiguration;

/**
 * Insert your comment for HelloPlugin here
 *
 * @author kumargautam
 */
public class HelloPlugin extends SpringPlugin {

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("HelloPlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("HelloPlugin.stop()");
        super.stop(); // to close applicationContext
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        applicationContext.register(SpringConfiguration.class);
        applicationContext.refresh();

        return applicationContext;
    }

    @Extension(ordinal = 1)
    public static class HelloGreeting implements Greeting {

        public String getGreeting() {
            return "Hello";
        }


    }

    @Extension(ordinal = 2)
    public static class WelcomeGreeting implements Greeting {

        public String getGreeting() {
            return "Welcome1";
        }

    }


}
