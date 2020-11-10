package com.ces.intern.apitimecloud.service.impl;

import com.ces.intern.apitimecloud.dto.CommentDTO;
import com.ces.intern.apitimecloud.entity.CommentEntity;
import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import com.ces.intern.apitimecloud.entity.UserEntity;
import com.ces.intern.apitimecloud.http.exception.NotFoundException;
import com.ces.intern.apitimecloud.http.request.CommentRequest;
import com.ces.intern.apitimecloud.repository.CommentRepository;
import com.ces.intern.apitimecloud.repository.DiscussionRepository;
import com.ces.intern.apitimecloud.repository.ProjectRepository;
import com.ces.intern.apitimecloud.repository.UserRepository;
import com.ces.intern.apitimecloud.service.CommentService;
import com.ces.intern.apitimecloud.util.ExceptionMessage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    CommentServiceImpl(UserRepository userRepository,
                       DiscussionRepository discussionRepository,
                       CommentRepository commentRepository,
                       ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CommentDTO> getAllCommentByDiscussionId(Integer discussionId) {
        DiscussionEntity discussionEntity = discussionRepository
                .findById(discussionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        List<CommentEntity> list = commentRepository.getAllByDiscussionId(discussionId);
        return list.stream().map(item->modelMapper.map(item,CommentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO create(CommentRequest input) {

        UserEntity userEntity = userRepository
                .findById(input.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        DiscussionEntity discussionEntity = discussionRepository
                .findById(input.getDiscussionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        CommentEntity commentEntity = new CommentEntity();
        Date date = new Date();
        commentEntity.setBasicInfo(date,input.getUserId(),date,input.getUserId());
        commentEntity.setContent(input.getContent());
        commentEntity.setDiscussion(discussionEntity);
        commentEntity.setUser(userEntity);

        return modelMapper.map(commentRepository.save(commentEntity),CommentDTO.class);
    }

    @Override
    public CommentDTO update(CommentRequest input, Integer commentId, Integer userId) {
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        commentEntity.setContent(input.getContent());
        commentEntity.setModifyAt(new Date());
        commentEntity.setModifiedBy(userId);

        commentRepository.save(commentEntity);

        return modelMapper.map(commentEntity,CommentDTO.class);
    }

    @Override
    public void delete(Integer commentId) {
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.name()));

        commentRepository.delete(commentEntity);
    }
}
