/*
 * $HeadURL$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.impl.nio.mockup;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.nio.NHttpConnection;
import org.apache.http.nio.protocol.EventListener;

public class CountingEventListener implements EventListener {

    private volatile int connCount = 0;
    
    public CountingEventListener() {
        super();
    }
    
    public int getConnCount() {
        return this.connCount;
    }
    
    private void incrementConnCount() {
        synchronized (this) {
            this.connCount++;
            this.notifyAll();
        }
    }
    
    public void await(int connCount, long timeout) throws InterruptedException {
        synchronized (this) {
            while (this.connCount < connCount) {
                this.wait(timeout);
            }
        }
    }
    
    public void connectionOpen(final NHttpConnection conn) {
    }

    public void connectionTimeout(final NHttpConnection conn) {
    }

    public void connectionClosed(final NHttpConnection conn) {
        incrementConnCount();
    }

    public void fatalIOException(final IOException ex, final NHttpConnection conn) {
        ex.printStackTrace();
    }

    public void fatalProtocolException(final HttpException ex, NHttpConnection conn) {
        ex.printStackTrace();
    }
        
}
