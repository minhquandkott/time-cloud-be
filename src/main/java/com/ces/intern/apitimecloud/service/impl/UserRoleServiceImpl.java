package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.dto.UserRoleDTO;
import com.ces.intern.apitimecloud.entity.CompanyEntity;
import com.ces.intern.apitimecloud.entity.BaseEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.entity.UserRoleEntity;
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
import java.util.Date;
import java.util.List;

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

    public List<UserRoleDTO> getAllUserByCompanyId(Integer companyId){

        return null;
    }


    @Override
    public UserRoleDTO addRoleUserInCompany(Integer userId, Integer companyId, Integer roleId) {
        return null;
    }

    @Override
    @Transactional
    public UserDTO addUserToCompany(Integer userId, Integer companyId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + userId));

        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ) );

        Date date = new Date();

        UserRoleEntity userRoleEntity = new UserRoleEntity(user, company, Role.MEMBER.getRoleEntity());
        userRoleEntity.setBasicInfo (date, userId, date, userId);

        return modelMapper.map(user, UserDTO.class);
    }
}
