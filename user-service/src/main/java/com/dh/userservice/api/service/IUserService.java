package com.dh.userservice.api.service;

import com.dh.userservice.Exceptions.BadRequestException;
import com.dh.userservice.domain.dto.UserShowDto;
import com.dh.userservice.domain.model.AuthenticationRequest;
import com.dh.userservice.domain.model.User;
import com.dh.userservice.shared.GenericServiceAPI;

public interface IUserService extends GenericServiceAPI<User, Long> {


    UserShowDto authenticate(AuthenticationRequest authenticationRequest) throws BadRequestException;

}
