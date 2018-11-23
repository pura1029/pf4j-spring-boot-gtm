package com.vmware.fiaasco.plugin.api;

import org.pf4j.Extension;

/**
 * Insert your comment for WhazzupGreeting here
 *
 * @author kumargautam
 */
@Extension
public class WhazzupGreeting implements Greeting {

    /* (non-Javadoc)
     * @see com.vmware.fiaasco.plugin.api.Greeting#getGreeting()
     */
    public String getGreeting() {
        return "Whazzup";
    }

}