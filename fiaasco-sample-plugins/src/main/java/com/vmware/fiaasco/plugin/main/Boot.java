package com.vmware.fiaasco.plugin.main;

import java.util.List;

import org.pf4j.PluginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vmware.fiaasco.plugin.api.Greeting;
import com.vmware.fiaasco.plugin.config.SpringConfiguration;

/**
 * Hello world!
 *
 */
public class Boot {
    public static void main(String[] args) {
        // retrieves the spring application context
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // stop plugins
        PluginManager pluginManager = applicationContext.getBean(PluginManager.class);
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        // retrieves manually the extensions for the Greeting.class extension point

        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        for (Greeting greeting : greetings) {
            System.out.println(">>> " + greeting.getGreeting());
        }

        // stop and unload all plugins
        pluginManager.stopPlugins();
    }
}
