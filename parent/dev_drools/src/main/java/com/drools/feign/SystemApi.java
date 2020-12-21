package com.drools.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(value = "dev-system")
public interface SystemApi {
    @GetMapping("/sys/menu/feign/permissions/{userId}")
    public Set<String> permissions(@PathVariable("userId") Long userId);

    @GetMapping("/sys/menu/feign/getEntity/{token}")
    public String getEntity(@PathVariable("token") String token);

    @GetMapping("/sys/menu/feign/getUser/{userId}")
    public String getUser(@PathVariable("userId") Long userId);
}
