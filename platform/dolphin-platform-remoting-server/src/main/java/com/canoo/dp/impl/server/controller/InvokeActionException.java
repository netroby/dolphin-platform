/*
 * Copyright 2015-2017 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.server.controller;

import com.canoo.platform.remoting.server.DolphinAction;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * This exception is thrown if a an dolphin action (see {@link DolphinAction}) throws an exception
 */
@API(since = "0.x", status = INTERNAL)
public class InvokeActionException extends Exception {

    /**
     * Default constructor
     */
    public InvokeActionException() {
    }

    /**
     * Constructor
     * @param message the message
     */
    public InvokeActionException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param message the message
     * @param cause the cause
     */
    public InvokeActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     * @param cause the cause
     */
    public InvokeActionException(Throwable cause) {
        super(cause);
    }
}
