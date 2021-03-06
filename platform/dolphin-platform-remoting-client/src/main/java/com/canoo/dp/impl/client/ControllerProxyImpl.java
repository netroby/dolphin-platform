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
package com.canoo.dp.impl.client;

import com.canoo.dp.impl.client.legacy.communication.AbstractClientConnector;
import com.canoo.dp.impl.client.legacy.communication.OnFinishedHandler;
import com.canoo.dp.impl.platform.core.Assert;
import com.canoo.dp.impl.remoting.Converters;
import com.canoo.dp.impl.remoting.InternalAttributesBean;
import com.canoo.dp.impl.remoting.MappingException;
import com.canoo.dp.impl.remoting.commands.CallActionCommand;
import com.canoo.dp.impl.remoting.commands.DestroyControllerCommand;
import com.canoo.platform.remoting.client.ControllerActionException;
import com.canoo.platform.remoting.client.ControllerInitalizationException;
import com.canoo.platform.remoting.client.ControllerProxy;
import com.canoo.platform.remoting.client.Param;
import com.canoo.platform.remoting.spi.converter.ValueConverterException;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class ControllerProxyImpl<T> implements ControllerProxy<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerProxyImpl.class);

    private final String controllerId;

    private final AbstractClientConnector clientConnector;

    private final ClientPlatformBeanRepository platformBeanRepository;

    private final ControllerProxyFactory controllerProxyFactory;

    private final Converters converters;

    private T model;

    private volatile boolean destroyed = false;

    public ControllerProxyImpl(final String controllerId, final T model, final AbstractClientConnector clientConnector, final ClientPlatformBeanRepository platformBeanRepository, final ControllerProxyFactory controllerProxyFactory, final Converters converters) {
        this.clientConnector = Assert.requireNonNull(clientConnector, "clientConnector");
        this.controllerId = Assert.requireNonBlank(controllerId, "controllerId");
        this.controllerProxyFactory = Assert.requireNonNull(controllerProxyFactory, "controllerProxyFactory");
        this.model = Assert.requireNonNull(model, "model");
        this.platformBeanRepository = Assert.requireNonNull(platformBeanRepository, "platformBeanRepository");
        this.converters = Assert.requireNonNull(converters, "converters");
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public CompletableFuture<Void> invoke(String actionName, Map<String, ?> params) {
        final List<Param> paramList = params.entrySet().stream().
                map(e -> new Param(e.getKey(), e.getValue())).
                collect(Collectors.toList());
        return invoke(actionName, paramList.toArray(new Param[paramList.size()]));
    }

    @Override
    public CompletableFuture<Void> invoke(String actionName, Param... params) {
        if (destroyed) {
            throw new IllegalStateException("The controller was already destroyed");
        }

        final ClientControllerActionCallBean bean = platformBeanRepository.createControllerActionCallBean(controllerId, actionName, params);

        CallActionCommand callActionCommand = new CallActionCommand();
        callActionCommand.setControllerId(getId());
        callActionCommand.setActionName(actionName);
        if(params != null) {
            for (Param param : params) {
                Object value = param.getValue();
                if(value == null) {
                    callActionCommand.addParam(param.getName(), null);
                } else {
                    try {
                        callActionCommand.addParam(param.getName(), converters.getConverter(value.getClass()).convertToDolphin(value));
                    } catch (ValueConverterException e) {
                        throw new MappingException("Error in value conversion of param '" + param.getName() + "' for action '" + actionName + "'", e);
                    }
                }
            }
        }

        final CompletableFuture<Void> result = new CompletableFuture<>();
        clientConnector.send(callActionCommand, new OnFinishedHandler(){
            @Override
            public void onFinished() {
                if (bean.isError()) {
                    result.completeExceptionally(new ControllerActionException("Error on calling action on the server. Please check the server log."));
                } else {
                    result.complete(null);
                }
                bean.unregister();
            }
        });
        return result;
    }

    @Override
    public CompletableFuture<Void> destroy() {
        if (destroyed) {
            throw new IllegalStateException("The controller was already destroyed");
        }
        destroyed = true;

        final InternalAttributesBean bean = platformBeanRepository.getInternalAttributesBean();

        final CompletableFuture<Void> ret = new CompletableFuture<>();

        final DestroyControllerCommand destroyControllerCommand = new DestroyControllerCommand();
        destroyControllerCommand.setControllerId(getId());

        clientConnector.send(destroyControllerCommand, new OnFinishedHandler() {
            @Override
            public void onFinished() {
                model = null;
                ret.complete(null);
            }
        });
        return ret;
    }

    @Override
    public String getId() {
        return controllerId;
    }

    @Override
    public <C> CompletableFuture<ControllerProxy<C>> createController(String name) {
        Assert.requireNonBlank(name, "name");

        return controllerProxyFactory.<C>create(name, getId()).handle(new BiFunction<ControllerProxy<C>, Throwable, ControllerProxy<C>>() {
            @Override
            public ControllerProxy<C> apply(ControllerProxy<C> cControllerProxy, Throwable throwable) {
                if (throwable != null) {
                    throw new ControllerInitalizationException(throwable);
                }
                return cControllerProxy;
            }
        });
    }
}
