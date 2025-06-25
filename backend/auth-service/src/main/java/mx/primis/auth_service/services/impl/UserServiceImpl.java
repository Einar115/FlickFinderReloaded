package mx.primis.auth_service.services.impl;

import mx.primis.auth_service.mappers.UserMapper;
import mx.primis.auth_service.model.dto.requests.AuthRequest;
import mx.primis.auth_service.model.dto.requests.UserRequest;
import mx.primis.auth_service.model.dto.responses.AuthResponse;
import mx.primis.auth_service.model.dto.responses.UserResponse;
import mx.primis.auth_service.model.entities.RoleEntity;
import mx.primis.auth_service.model.entities.UserEntity;
import mx.primis.auth_service.repositories.RoleRepository;
import mx.primis.auth_service.repositories.UserRepository;
import mx.primis.auth_service.services.AuthService;
import mx.primis.auth_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private AuthService authService;

    @Override
    public AuthResponse register(UserRequest registrationDTO) throws RuntimeException{
        if (userRepository.existsByEmail(registrationDTO.email())) {
            throw new RuntimeException("El email ya está en uso");
        }

        if (userRepository.existsByUsername(registrationDTO.username())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        UserEntity user = userMapper.toEntity(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.password()));
        user.setEnable(true);

        RoleEntity userRole = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        user.setRole(userRole);

        UserEntity savedUser = userRepository.save(user);

        return authService.authenticate(
                new AuthRequest(registrationDTO.email(), registrationDTO.password())
        );
    }



    @Override
    public UserResponse getUserById(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDTO(user);
    }


}
