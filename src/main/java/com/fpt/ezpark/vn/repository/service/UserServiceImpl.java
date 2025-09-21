package com.fpt.ezpark.vn.repository.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

import com.fpt.ezpark.vn.common.utill.validation.EmailExistsException;
import com.fpt.ezpark.vn.configuration.security.PermissionService;
import com.fpt.ezpark.vn.model.User;
import com.fpt.ezpark.vn.model.DTO.request.UserRequestDTO;
import com.fpt.ezpark.vn.repository.UserRepository;
import com.fpt.ezpark.vn.repository.impl.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final PermissionService permissionService;

    @Override
    public User register(UserRequestDTO userRequestDTO) throws EmailExistsException {
        if (emailExist(userRequestDTO.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + userRequestDTO.getEmail());
        }

        User user = User.builder()
                .email(userRequestDTO.getEmail())
                // mã hoá password thông qua PasswordService
                .password(passwordService.encodePassword(userRequestDTO.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        // Gán quyền ADMINISTRATION cho chính user đó trên chính user object
        // Điều này cho phép user có thể quản lý thông tin của chính họ
        permissionService.addPermissionForUser(savedUser, BasePermission.ADMINISTRATION, savedUser.getEmail());

        return savedUser;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean emailExist(final String email) {
        final User user = userRepository.findByEmail(email);
        return user != null;
    }

}
