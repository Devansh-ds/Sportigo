package com.sadds.controller;

import com.sadds.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bets")
public class BetController {

    private final BetService betService;

}
