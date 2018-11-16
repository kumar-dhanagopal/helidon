/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package io.helidon.config.tests.mappers2;

import io.helidon.common.CollectionsHelper;
import java.math.BigInteger;
import java.util.OptionalInt;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

/**
 * Module {@code module-mappers-2-override} overrides built-in mappers for {@link Integer}, {@link OptionalInt}
 * and {@link BigInteger}.
 */
public abstract class AbstractDifferentIntMapperServicesTest {

    protected static final String KEY = "int-p";
    protected static final String CONFIGURED_VALUE = "2147483647";
    protected static final int CONFIGURED_INT = Integer.parseInt(CONFIGURED_VALUE);

    protected Config.Builder configBuilder() {
        return Config.builder()
                .sources(ConfigSources.from(CollectionsHelper.mapOf(KEY, CONFIGURED_VALUE)));
    }

    abstract protected int expected();

    @Test
    public void testDifferentInts() {
        Config config = configBuilder().build().get(KEY);

        assertThat(config.asInt(), is(expected()));
        assertThat(config.as(Integer.class), is(expected()));
        assertThat(config.as(BigInteger.class), is(BigInteger.valueOf(expected())));

        assertThat(config.as(OptionalInt.class).getAsInt(), is(expected()));
        assertThat(config.asInt().getAsInt(), is(expected()));
        assertThat(configas(Integer.class).get(), is(expected()));
        assertThat(configas(BigInteger.class).get(), is(BigInteger.valueOf(expected())));
    }

}
