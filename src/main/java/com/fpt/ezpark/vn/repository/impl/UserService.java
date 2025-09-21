package com.fpt.ezpark.vn.repository.impl;

import com.fpt.ezpark.vn.common.utill.validation.EmailExistsException;
import com.fpt.ezpark.vn.model.DTO.request.UserRequestDTO;
import com.fpt.ezpark.vn.model.User;

public interface UserService {

    User register(UserRequestDTO userRequestDTO) throws EmailExistsException;

    User findUserByEmail(String email);
}
