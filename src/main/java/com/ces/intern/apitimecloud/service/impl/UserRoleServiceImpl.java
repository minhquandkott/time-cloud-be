package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.entity.*;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.repository.UserRoleRepository;
import com.ces.intern.apitimecloud.service.UserRoleService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository,
                               UserRepository userRepository,
                               CompanyRepository companyRepository,
                               ModelMapper modelMapper){
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    @Transactional
    public UserRoleDTO addRoleUserInCompany(Integer userId, Integer companyId, Integer roleId) {

        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));

        CompanyEntity companyEntity = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with company " + companyId));

        RoleEntity roleEntity = Arrays.stream(Role.values())
                .map(role -> role.getRoleEntity())
                .filter(roleEntity1 -> roleEntity1.getId() == roleId)
                .findFirst().get();

        Date date = new Date();
        UserRoleEntity userRoleEntity = new UserRoleEntity(userEntity, companyEntity, roleEntity);
        userRoleEntity.setBasicInfo (date, userId);

        userRoleRepository.save(userRoleEntity);

        return modelMapper.map(userRoleEntity, UserRoleDTO.class);
    }

    @Override
    public UserDTO addUserToCompany(Integer userId, Integer companyId) {
        return addRoleUserInCompany(userId, companyId, Role.MEMBER.ordinal()).getUser();
    }

    @Override
    public List<UserRoleDTO> getAllByCompanyIdAndRoleId(Integer companyId, Integer roleId) {

        List<UserRoleEntity> userRoles = userRoleRepository.findAllByEmbedIdCompanyIdAndRoleId(companyId, roleId);


        return userRoles
                .stream()
                .map(userRole -> modelMapper.map(userRole, UserRoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDTO> getAllByCompanyIdAndUserId(Integer companyId, Integer userId) {
        List<UserRoleEntity> userRoles = userRoleRepository.findAllByEmbedIdCompanyIdAndUserId(companyId, userId);

        return userRoles
                .stream()
                .map(userRole -> modelMapper.map(userRole, UserRoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDTO> getAllByCompanyId(Integer companyId) {
        List<UserRoleEntity> userRoles = userRoleRepository.findAllByEmbedIdCompanyId(companyId);


        return userRoles
                .stream()
                .map(userRole -> modelMapper.map(userRole, UserRoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserRole(Integer userId, Integer companyId, Integer roleId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with user " + userId));

        CompanyEntity companyEntity = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "with company " + companyId));

        userRoleRepository.deleteById(new UserRoleEntity.EmbedId(userId, companyId, roleId));
    }
}
