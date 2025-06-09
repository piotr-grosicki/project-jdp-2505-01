package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.GroupDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GroupMapper {

    public GroupDTO mapToGroupDTO(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedAt(),
                getIds(group)
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

    private static List<Long> getIds(Group group) {

        return Stream.ofNullable(group.getProducts())
                .flatMap(Collection::stream)
                .filter(p -> p != null && p.getId() != null)
                .map(Product::getId)
                .toList();
    }
}
