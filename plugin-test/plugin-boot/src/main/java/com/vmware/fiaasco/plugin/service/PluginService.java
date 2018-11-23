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

package com.vmware.fiaasco.plugin.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmware.fiaasco.plugin.api.Greeting;
import com.vmware.fiaasco.plugin.exception.FileStorageException;

/**
 * Service class for Plugin controller.
 *
 * @author kumargautam
 */
@Service
@Log4j2
public class PluginService {

    @Autowired
    private SpringPluginManager pluginManager;
    @Autowired
    private FileStorageService storageService;

    public Map<String, Object> getPlugins() {
        List<Class<Greeting>> greetingClasses = pluginManager.getExtensionClasses(Greeting.class);
        for (Class<Greeting> class1 : greetingClasses) {
            log.info("Ext class Name : " + class1.getName());
        }
        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        List<String> extNames = new ArrayList<>();
        for (PluginWrapper plugin : startedPlugins) {
            log.info("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
            Set<String> extensionClassNames = pluginManager.getExtensionClassNames(plugin.getPluginId());
            for (String extensionClassName : extensionClassNames) {
                log.info("Register extension '{}' as bean", extensionClassName);
                extNames.add(extensionClassName);
            }
        }
        List<PluginWrapper> pluginWrapperList = pluginManager.getPlugins();
        Map<String, Object> extMap = new HashMap<>();
        extMap.put("extensions", extNames);
        List<Object> plugins = new ArrayList<>();
        pluginWrapperList.forEach(pluginWrapper -> plugins.add(pluginWrapper.getDescriptor()));
        extMap.put("plugins", plugins);
        return extMap;
    }

    public Greeting getPlugins(String extensionName) {
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        Greeting ext = null;
        for (Greeting greeting : greetings) {
            if (extensionName.equals(greeting.getClass().getSimpleName())) {
                ext = greeting;
                break;
            }
        }
        return ext;
    }

    public List<String> getExtensionNames() {
        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        List<String> extNames = new ArrayList<>();
        for (PluginWrapper plugin : startedPlugins) {
            log.info("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
            Set<String> extensionClassNames = pluginManager.getExtensionClassNames(plugin.getPluginId());
            for (String extensionClassName : extensionClassNames) {
                log.info("Register extension '{}' as bean", extensionClassName);
                extNames.add(extensionClassName);
            }
        }
        return extNames;
    }

    public String loadPlugin(String pluginName) {
        Path pluginPath = storageService.getFileStorageLocation().resolve(pluginName);
        String pluginId = pluginManager.loadPlugin(pluginPath);
        pluginManager.startPlugins();
        if (pluginId == null) {
            throw new FileStorageException("Plugin load Failed!");
        }
        return pluginId;
    }

    public boolean unloadPlugin(String pluginId) {
        boolean status = false;
        try {
            status = pluginManager.unloadPlugin(pluginId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(e.getMessage(), e);
        }
        if (!status) {
            throw new FileStorageException("Plugin unload Failed!");
        }
        return status;
    }

    public boolean disablePlugin(String pluginId) {
        boolean status = false;
        try {
            status = pluginManager.disablePlugin(pluginId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(e.getMessage(), e);
        }
        if (!status) {
            throw new FileStorageException("Plugin disable Failed!");
        }
        return status;
    }

    public boolean enablePlugin(String pluginId) {
        boolean status = false;
        PluginState state;
        try {
            status = pluginManager.enablePlugin(pluginId);
            state = pluginManager.startPlugin(pluginId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(e.getMessage(), e);
        }
        if (!status && !state.equals(PluginState.STARTED)) {
            throw new FileStorageException("Plugin enable Failed!");
        }
        return status;
    }

    public boolean deletePlugin(String pluginId) {
        boolean status = false;
        try {
            status = pluginManager.deletePlugin(pluginId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(e.getMessage(), e);
        }
        if (!status) {
            throw new FileStorageException("Plugin delete Failed!");
        }
        return status;
    }


}
