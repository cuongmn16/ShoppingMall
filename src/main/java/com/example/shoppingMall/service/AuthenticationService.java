package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.AuthenticationRequest;
import com.example.shoppingMall.dto.request.IntrospectRequest;
import com.example.shoppingMall.dto.response.AuthenticationResponse;
import com.example.shoppingMall.dto.response.IntrospectResponse;
import com.example.shoppingMall.model.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}

