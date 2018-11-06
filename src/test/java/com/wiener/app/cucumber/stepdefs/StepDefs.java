package com.wiener.app.cucumber.stepdefs;

import com.wiener.app.WienerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = WienerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
