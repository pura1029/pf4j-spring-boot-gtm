package com.vmware.fiaasco.plugin.demo;

import org.pf4j.Extension;

import com.vmware.fiaasco.plugin.api.Greeting;

/**
 * Insert your comment for WelcomeGreeting here
 *
 * @author kumargautam
 */
@Extension
public class WelcomeGreeting implements Greeting {

    public String getGreeting() {
        return "Welcome";
    }

}