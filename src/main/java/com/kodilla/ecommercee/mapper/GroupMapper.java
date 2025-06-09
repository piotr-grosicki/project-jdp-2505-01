package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.GroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMapper {

    public GroupDTO mapToGroupDTO(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedAt(),
                group.getProducts().stream()
                        .map(Product::getId)
                        .toList()
        );
    }

    public List<GroupDTO> mapToGroupDTOList(List<Group> groups) {
        return groups.stream()
                .map(this::mapToGroupDTO)
                .toList();
    }

    public Group mapToGroup(GroupDTO groupDTO) {
        return Group.builder()
                .id(groupDTO.id())
                .name(groupDTO.name())
                .description(groupDTO.description())
                .createdAt(groupDTO.createdAt())
                .build();
    }
}
