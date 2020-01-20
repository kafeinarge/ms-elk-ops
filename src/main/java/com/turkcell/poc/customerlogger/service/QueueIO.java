package com.turkcell.poc.customerlogger.service;

public interface QueueIO {

    void storeToES() throws InterruptedException;

}
