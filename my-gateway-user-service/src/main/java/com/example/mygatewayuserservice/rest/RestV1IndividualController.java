package com.example.mygatewayuserservice.rest;

import com.example.mygatewayuserservice.client.IndividualClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class RestV1IndividualController {

    private final IndividualClient individualClient;

//    @GetMapping("/me")
//    public
}
