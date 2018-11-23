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

package com.vmware.fiaasco.plugin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.fiaasco.plugin.api.Greeting;
import com.vmware.fiaasco.plugin.api.ModelExtensionPoint;
import com.vmware.fiaasco.plugin.api.TaskExtensionPoint;
import com.vmware.fiaasco.plugin.exception.FileStorageException;
import com.vmware.fiaasco.plugin.service.PluginService;

/**
 * Insert your comment for PluginController here
 *
 * @author kumargautam
 */
@RestController
@RequestMapping(value = "/rest/api/v1/plugin")
@Log4j2
public class PluginController {

    @Autowired
    private SpringPluginManager pluginManager;
    @Autowired
    private PluginService pluginService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPlugins() {
        log.info("PluginController getPlugins() Start.............");
        return new ResponseEntity<>(pluginService.getPlugins(), HttpStatus.OK);
    }

    @GetMapping(value = "/{extensionName}")
    public ResponseEntity<String> getPlugins(@PathVariable("extensionName") String extensionName) {
        log.info("PluginController getPlugins(.) Start.............");
        Greeting ext = pluginService.getPlugins(extensionName);
        if (ext != null) {
            return new ResponseEntity<>("Extension Found with msg " + ext.getGreeting(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Extension Not Found " + ext, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> loadPlugin(@RequestParam("pluginName") String pluginName) {
        log.info("PluginController loadPlugin() Start............");
        pluginService.loadPlugin(pluginName);
        return new ResponseEntity<>(pluginService.getExtensionNames(), HttpStatus.OK);
    }

    @PutMapping(value = "/unload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> unloadPlugin(@RequestParam("pluginId") String pluginId) {
        log.info("PluginController unloadPlugin() Start..................");
        pluginService.unloadPlugin(pluginId);
        return new ResponseEntity<>(pluginService.getExtensionNames(), HttpStatus.OK);
    }

    @PutMapping(value = "/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> disablePlugin(@RequestParam("pluginId") String pluginId) {
        log.info("PluginController disablePlugin() Start..................");
        pluginService.disablePlugin(pluginId);
        return new ResponseEntity<>(pluginService.getExtensionNames(), HttpStatus.OK);
    }

    @PutMapping(value = "/enable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> enablePlugin(@RequestParam("pluginId") String pluginId) {
        log.info("PluginController enablePlugin() Start..................");

        pluginService.enablePlugin(pluginId);
        return new ResponseEntity<>(pluginService.getExtensionNames(), HttpStatus.OK);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> deletePlugin(@RequestParam("pluginId") String pluginId) {
        log.info("PluginController deletePlugin() Start..................");
        pluginService.deletePlugin(pluginId);
        return new ResponseEntity<>(pluginService.getExtensionNames(), HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping(value = "/model-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelExtensionPoint> modelTest(@RequestBody String jsonStr) throws JSONException {
        log.info("PluginController modelTest() Start............");
        JSONObject jsonObject = new JSONObject(jsonStr);
        String extensionName = jsonObject.getString("specName");
        List<ModelExtensionPoint> models = pluginManager.getExtensions(ModelExtensionPoint.class);
        ModelExtensionPoint ext = null;
        for (ModelExtensionPoint model : models) {
            if (extensionName.equals(model.getClass().getSimpleName())) {
                ext = model;
                break;
            }
        }

        String taskName = jsonObject.getString("taskName");
        List<TaskExtensionPoint> tasks = pluginManager.getExtensions(TaskExtensionPoint.class);
        TaskExtensionPoint taskExt = null;
        for (TaskExtensionPoint task : tasks) {
            if (taskName.equals(task.getClass().getSimpleName())) {
                taskExt = task;
                break;
            }
        }
        if (ext != null && taskExt != null) {
            ModelExtensionPoint modelExtensionPoint =
                    jsonToObject(jsonObject.get("specValue").toString(), ext.getClass());
            taskExt.setData(modelExtensionPoint);
            taskExt.run();
            return new ResponseEntity<>(modelExtensionPoint, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public static <T> T jsonToObject(String json, Class<T> object) {
        try {
            return new ObjectMapper().readValue(json, object);
        } catch (JsonProcessingException jse) {
            log.error("Input String is not a valid json" + jse.getMessage());
            return null;
        } catch (IOException e) {
            throw new FileStorageException(String.format("Failed to process Json : %s", json), e);
        }
    }
}
