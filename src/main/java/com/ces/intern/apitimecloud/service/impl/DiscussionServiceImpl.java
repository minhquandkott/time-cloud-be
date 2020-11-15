package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.DiscussionDTO;
import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import com.ces.intern.apitimecloud.entity.ProjectEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.BadRequestException;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.DiscussionRequest;
import com.ces.intern.apitimecloud.repository.DiscussionRepository;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.DiscussionService;
import com.ces.intern.apitimecloud.util.Classifications;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    DiscussionServiceImpl(DiscussionRepository discussionRepository,
                          ModelMapper modelMapper,
                          ProjectRepository projectRepository,
                          UserRepository userRepository){
        this.discussionRepository = discussionRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    @Override
    public DiscussionDTO create(DiscussionRequest input) throws Exception {
        UserEntity userEntity = userRepository
                .findById(input.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));
        ProjectEntity projectEntity = projectRepository
                .findById(input.getProjectId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        DiscussionEntity discussionEntity = new DiscussionEntity();
        try{

            Date date = new Date();
            discussionEntity.setBasicInfo(date, input.getUserId(), date, input.getUserId());
            discussionEntity.setContent(input.getContent());
            discussionEntity.setUser(userEntity);
            discussionEntity.setProject(projectEntity);
            Integer type = Classifications.classifyType(input.getContent());
            discussionEntity.setType(type);
        }
        catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return modelMapper.map(discussionRepository.save(discussionEntity), DiscussionDTO.class);

    }

    @Override
    public DiscussionDTO update(DiscussionRequest input, Integer discussionId, Integer userId) {
        DiscussionEntity discussionEntity = discussionRepository
                .findById(discussionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        discussionEntity.setContent(input.getContent());
        discussionEntity.setType(input.getType());
        discussionEntity.setModifyAt(new Date());
        discussionEntity.setModifiedBy(userId);
        return modelMapper.map(discussionRepository.save(discussionEntity), DiscussionDTO.class);
    }

    @Override
    public void delete(Integer discussionId) {
        DiscussionEntity discussionEntity = discussionRepository
                .findById(discussionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));
        discussionRepository.delete(discussionEntity);
    }

    @Override
    public List<DiscussionDTO> getAllByProjectId(Integer projectId, Integer limit, Integer page, String sortBy, String order) {
        Pageable p = PageRequest.of(page, limit);

        if(!sortBy.isEmpty()){
            if("ASC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).ascending());
            }else if("DESC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).descending());
            }else{
                throw new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + " order (DESC, ASC)");
            }
        }
        List<DiscussionEntity> discussionEntities = discussionRepository
                .getAllByProjectId(projectId, p)
                .get()
                .collect(Collectors.toList());
        return discussionEntities
                .stream()
                .map(discussionEntity -> modelMapper.map(discussionEntity, DiscussionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDTO> getAllByProjectIdAndType(Integer projectId, Integer type, Integer limit, Integer page, String sortBy, String order ) {
        Pageable p = PageRequest.of(page, limit);

        if(!sortBy.isEmpty()){
            if("ASC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).ascending());
            }else if("DESC".equals(order)){
                p = PageRequest.of(page, limit, Sort.by(sortBy).descending());
            }else{
                throw new BadRequestException(ExceptionMessage.FIELD_NOT_CORRECT.getMessage() + " order (DESC, ASC)");
            }
        }
        List<DiscussionEntity> discussionEntities = discussionRepository
                .getAllByProjectIdAndType(projectId, type, p)
                .get()
                .collect(Collectors.toList());
        return discussionEntities
                .stream()
                .map(discussionEntity -> modelMapper.map(discussionEntity, DiscussionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDTO> getAllByUserIdInProject(Integer userId, Integer limit, Integer page) {

        List<DiscussionEntity> discussionEntities = discussionRepository
                .getAllByUserIdInProject(userId, limit, page * limit);

        return discussionEntities
                .stream()
                .map(discussionEntity -> modelMapper.map(discussionEntity, DiscussionDTO.class))
                .collect(Collectors.toList());

    }
}
