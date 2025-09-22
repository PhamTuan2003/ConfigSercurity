package com.fpt.ezpark.vn.service;

import com.fpt.ezpark.vn.common.utill.validation.EmailExistsException;
import com.fpt.ezpark.vn.model.DTO.request.UserRequestDTO;
import com.fpt.ezpark.vn.model.entity.User;

public interface UserService {

    User register(UserRequestDTO userRequestDTO) throws EmailExistsException;

    User findUserByEmail(String email);
}
