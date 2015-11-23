package com.springapp.thrift;

/**
 * Created by kay on 15-11-18.
 */
public interface ConnectionStatusListener {
    /**
     * Called when the connection has been lost.
     */
    public void connectionLost();

    /**
     * Called when the connection has been established.
     */
    public void connectionEstablished();
}
