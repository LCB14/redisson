/**
 * Copyright (c) 2013-2024 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.rx;

import org.redisson.RedissonSet;
import org.redisson.RedissonSetMultimapCache;
import org.redisson.api.RSetRx;
import org.redisson.api.RedissonRxClient;

/**
 *
 * @author Marnix Kammer
 *
 * @param <K> key type
 * @param <V> value type
 */
public class RedissonSetMultimapCacheRx<K, V> {

    private final RedissonSetMultimapCache<K, V> instance;
    private final CommandRxExecutor commandExecutor;
    private final RedissonRxClient redisson;

    public RedissonSetMultimapCacheRx(RedissonSetMultimapCache<K, V> instance, CommandRxExecutor commandExecutor,
                                      RedissonRxClient redisson) {
        this.instance = instance;
        this.redisson = redisson;
        this.commandExecutor = commandExecutor;
    }

    public RSetRx<V> get(K key) {
        RedissonSet<V> set = (RedissonSet<V>) instance.get(key);
        return RxProxyBuilder.create(commandExecutor, set, new RedissonSetRx<>(set, redisson), RSetRx.class);
    }
}
