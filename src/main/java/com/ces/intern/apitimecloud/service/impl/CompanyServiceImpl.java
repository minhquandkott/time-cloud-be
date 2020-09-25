package com.ces.intern.apitimecloud.service.impl;


import com.ces.intern.apitimecloud.dto.CompanyDTO;
import com.ces.intern.apitimecloud.dto.UserDTO;
import com.ces.intern.apitimecloud.entity.*;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.CompanyRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.repository.UserRoleRepository;
import com.ces.intern.apitimecloud.service.CompanyService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import com.ces.intern.apitimecloud.util.Role;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class CompanyServiceImpl implements CompanyService {

    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Autowired
    public CompanyServiceImpl(ModelMapper modelMapper,
                              CompanyRepository companyRepository,
                              UserRepository userRepository,
                              UserRoleRepository userRoleRepository
                              ){
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;

    }




    @Override
    public CompanyDTO getCompany(Integer companyId) {

       Optional<CompanyEntity> optional = companyRepository.findById(companyId);

       CompanyEntity company = optional
               .orElseThrow(()
                       -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with " +companyId ) );

       return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    @Transactional
    public CompanyDTO createCompany(CompanyDTO company, Integer userId) {

        Date date = new Date();
        CompanyEntity companyEntity = modelMapper.map(company, CompanyEntity.class);

        EmbedEntity embedEntity = new EmbedEntity(date, userId, date, userId);

        companyEntity.setEmbedEntity(embedEntity);
        companyRepository.save(companyEntity);
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + "user with" + userId));

        UserRoleEntity userRole = UserRoleEntity
                .builder()
                .user(userEntity)
                .role(Role.ADMIN.getRoleEntity())
                .company(companyEntity)
                .build();

        userRoleRepository.save(userRole);

        return  modelMapper.map(companyEntity, CompanyDTO.class);
    }


    @Override
    @Transactional
    public CompanyDTO updateCompany(Integer companyId, CompanyDTO company, Integer userId) {

        CompanyEntity companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + companyId));

        TypeMap<CompanyDTO, CompanyEntity> tm = modelMapper
                .typeMap(CompanyDTO.class, CompanyEntity.class);
        tm.setPropertyCondition(Conditions.isNotNull());
        tm.map(company, companyEntity);
        companyEntity.getEmbedEntity().setModifyAt(new Date());
        companyEntity.getEmbedEntity().setModifiedBy(userId);

        companyRepository.save(companyEntity);

        return modelMapper.map(companyEntity, CompanyDTO.class);
    }

    @Override
    public void deleteCompany(Integer companyId) {
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new RuntimeException(ExceptionMessage.NOT_FOUND_RECORD.getMessage() + " with" + companyId));
        companyRepository.delete(company);
    }

    @Override
    public CompanyEntity getFullInFor(Integer companyId) {
        return null;
    }


    @Override
    public List<UserEntity> getMemberByRole(Integer companyId, String role) {
        return null;
    }


}
