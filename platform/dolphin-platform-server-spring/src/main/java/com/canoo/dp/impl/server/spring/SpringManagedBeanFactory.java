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
package com.canoo.dp.impl.server.spring;

import com.canoo.platform.server.spi.components.ManagedBeanFactory;
import org.apiguardian.api.API;
import org.springframework.context.ApplicationContext;

import static org.apiguardian.api.API.Status.INTERNAL;

/**
 * Spring specific implementation of the {@link ManagedBeanFactory} interface
 *
 * @author Hendrik Ebbers
 */
@API(since = "0.x", status = INTERNAL)
public class SpringManagedBeanFactory extends AbstractSpringManagedBeanFactory {

    /**
     * Returns the Spring {@link org.springframework.context.ApplicationContext} for the current {@link javax.servlet.ServletContext}
     *
     * @return the spring context
     */
    protected ApplicationContext getContext() {
        return DolphinPlatformSpringBootstrap.getApplicationContext();
    }
}
