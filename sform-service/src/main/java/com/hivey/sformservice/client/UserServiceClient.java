package com.hivey.sformservice.client;

import com.hivey.sformservice.dto.form.FormDataDto.GetUserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserServiceClient {

    @GetMapping(value = "/user-service/users/{userId}")
    GetUserRes getUsers(@PathVariable("userId") Long userId);
}
