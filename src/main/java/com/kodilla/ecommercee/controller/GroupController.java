package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.dto.GroupDTO;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupMapper.mapToGroupDTOList(groupService.getAllGroups()));
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO dto) {
        Group group = groupMapper.mapToGroup(dto);
        return ResponseEntity.ok(groupMapper.mapToGroupDTO(groupService.createGroup(group)));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long groupId)
            throws GroupNotFoundException {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(groupMapper.mapToGroupDTO(group));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long groupId, @RequestBody GroupDTO dto)
            throws GroupNotFoundException {
        Group group = groupMapper.mapToGroup(dto);
        return ResponseEntity.ok(groupMapper.mapToGroupDTO(groupService.updateGroup(groupId, group)));
    }
}
