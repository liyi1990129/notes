package com.ly.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class Test {

    // 根据IP分不同的令牌桶, 每天自动清理缓存
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) throws Exception {
                    // 新的IP初始化 (限流每秒两个令牌响应)
                    return RateLimiter.create(2);
                }
            });

    public static void main(String[] args) throws MyException {
        test();
    }
    public static void test() throws MyException {
        if("true".equals("true")){
            throw new MyException("参数不能为 true");
        }
    }
}
