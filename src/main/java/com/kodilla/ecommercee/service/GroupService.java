package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupNotFoundByIdException;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group createGroup(Group group) {
        Group newGroup = Group.builder()
                .name(group.getName())
                .description(group.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        return groupRepository.save(newGroup);
    }

    public Group getGroupById(Long groupId) throws GroupNotFoundByIdException {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundByIdException(groupId));
    }

    public Group updateGroup(Long groupId, Group group) throws GroupNotFoundByIdException {
        Group updatedGroup = getGroupById(groupId);
        updatedGroup.setName(group.getName());
        updatedGroup.setDescription(group.getDescription());
        return groupRepository.save(updatedGroup);
    }

}
