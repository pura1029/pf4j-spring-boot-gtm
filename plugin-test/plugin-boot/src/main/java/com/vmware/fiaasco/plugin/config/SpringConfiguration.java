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

import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vmware.fiaasco.plugin.file.FileStorageProperties;
import com.vmware.fiaasco.plugin.service.FileStorageService;

/**
 * Insert your comment for SpringConfiguration here
 *
 * @author kumargautam
 */
@Configuration
@EnableConfigurationProperties({ FileStorageProperties.class })
public class SpringConfiguration {

    @Autowired
    private FileStorageService storageService;

    @Bean
    public SpringPluginManager pluginManager() {
        System.setProperty("pf4j.pluginsDir", storageService.getFileStorageLocation().toString());
        storageService.createDefaultFile();
        return new SpringPluginManager(storageService.getFileStorageLocation());
    }
}
