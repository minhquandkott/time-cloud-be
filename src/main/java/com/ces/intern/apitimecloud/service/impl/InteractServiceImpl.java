package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.InteractDTO;
import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import com.ces.intern.apitimecloud.entity.InteractEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.repository.DiscussionRepository;
import com.ces.intern.apitimecloud.repository.InteractRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.InteractService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InteractServiceImpl implements InteractService {

    private final InteractRepository interactRepository;
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final ModelMapper modelMapper;

    public InteractServiceImpl(
            InteractRepository interactRepository,
            UserRepository userRepository,
            DiscussionRepository discussionRepository,
            ModelMapper modelMapper
            ){
        this.interactRepository = interactRepository;
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public InteractDTO create(Integer userId, Integer discussionId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()+ " with userID" + userId));
        DiscussionEntity discussionEntity = discussionRepository
                .findById(discussionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()+ " with discussionId" + discussionId));
        InteractEntity interactEntity = new InteractEntity();
        interactEntity.setUser(userEntity);
        interactEntity.setDiscussion(discussionEntity);
        return modelMapper.map(interactRepository.save(interactEntity), InteractDTO.class);
    }

    @Override
    public void delete(Integer userId, Integer discussionId) {

        InteractEntity interactEntity = interactRepository
                .findById(new InteractEntity.EmbedId(userId, discussionId))
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        interactRepository.delete(interactEntity);
    }

    @Override
    public List<InteractDTO> getAllByDiscussionId(Integer discussionId) {
        List<InteractEntity> interactEntities = interactRepository
                .getAllByIdDiscussionId(discussionId);
        return interactEntities
                .stream()
                .map(interactEntity -> modelMapper.map(interactEntity, InteractDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<InteractDTO> getAllByUserId(Integer userId) {
        List<InteractEntity> interactEntities = interactRepository
                .getAllByIdUserId(userId);
        return interactEntities
                .stream()
                .map(interactEntity -> modelMapper.map(interactEntity, InteractDTO.class))
                .collect(Collectors.toList());
    }
}
