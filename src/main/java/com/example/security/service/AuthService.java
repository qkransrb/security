package com.example.security.service;

import com.example.security.domain.User;
import com.example.security.dto.auth.SignUpRequest;
import com.example.security.exception.UserAlreadyExistsException;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signup(SignUpRequest signUpRequest) {
        // 기 가입 체크 (username - unique constraint)
        Optional<User> user = userRepository.findByUsername(signUpRequest.getUsername());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        // User Entity 생성
        User entity = User.builder()
                .username(signUpRequest.getUsername())
                .password(encodedPassword)
                .email(signUpRequest.getEmail())
                .build();

        // 회원가입 정보 저장
        User savedUser = userRepository.save(entity);

        // ADMIN 권한 부여
        if ("checked".equals(String.valueOf(signUpRequest.getIsAdmin()))) {
            savedUser.setRole("ROLE_ADMIN");
        }
    }
}
