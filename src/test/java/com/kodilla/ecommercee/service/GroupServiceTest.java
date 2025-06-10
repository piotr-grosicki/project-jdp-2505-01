package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void shouldGetAllGroups() {
        // Given
        Group g1 = Group.builder()
                .id(1L).
                name("A").
                description("desc")
                .createdAt(LocalDateTime.now())
                .build();
        Group g2 = Group.builder()
                .id(2L)
                .name("B")
                .description("desc")
                .createdAt(LocalDateTime.now())
                .build();
        when(groupRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        // When
        List<Group> result = groupService.getAllGroups();

        // Then
        assertThat(result).hasSize(2)
                .containsExactly(g1, g2);
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateGroup() {
        // Given
        Group input = Group.builder()
                .name("NewGroup")
                .description("NewDesc")
                .build();
        Group saved = Group.builder()
                .id(5L)
                .name("NewGroup")
                .description("NewDesc")
                .createdAt(LocalDateTime.now())
                .build();
        when(groupRepository.save(any(Group.class))).thenReturn(saved);

        // When
        Group result = groupService.createGroup(input);

        // Then
        assertThat(result).isEqualTo(saved);
        verify(groupRepository).save(argThat(group ->
                group.getId() == null &&
                        "NewGroup".equals(group.getName()) &&
                        "NewDesc".equals(group.getDescription()) &&
                        group.getCreatedAt() != null &&
                        !group.getCreatedAt().isAfter(LocalDateTime.now())
        ));
    }

    @Test
    void shouldGetGroupById() throws GroupNotFoundException {
        // Given
        Group g = Group.builder()
                .id(10L)
                .name("X")
                .description("Y")
                .createdAt(LocalDateTime.now())
                .build();
        when(groupRepository.findById(10L)).thenReturn(Optional.of(g));

        // When
        Group result = groupService.getGroupById(10L);

        // Then
        assertThat(result).isSameAs(g);
        verify(groupRepository).findById(10L);
    }

    @Test
    void shouldThrowWhenGetGroupByIdNotFound() {
        // Given
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> groupService.getGroupById(99L))
                .isInstanceOf(GroupNotFoundException.class);
        verify(groupRepository).findById(99L);
    }

    @Test
    void shouldUpdateGroup() throws GroupNotFoundException {
        // Given
        LocalDateTime origTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        Group existing = Group.builder()
                .id(7L)
                .name("OldName")
                .description("OldDesc")
                .createdAt(origTime)
                .build();
        when(groupRepository.findById(7L)).thenReturn(Optional.of(existing));

        Group updateData = Group.builder()
                .name("NewName")
                .description("NewDesc")
                .build();

        Group saved = Group.builder()
                .id(7L)
                .name("NewName")
                .description("NewDesc")
                .createdAt(origTime)
                .build();
        when(groupRepository.save(any(Group.class))).thenReturn(saved);

        // When
        Group result = groupService.updateGroup(7L, updateData);

        // Then
        assertThat(result.getName()).isEqualTo("NewName");
        assertThat(result.getDescription()).isEqualTo("NewDesc");
        assertThat(result.getCreatedAt()).isEqualTo(origTime);

        verify(groupRepository).findById(7L);
        verify(groupRepository).save(argThat(group ->
                Long.valueOf(7L).equals(group.getId()) &&
                        "NewName".equals(group.getName()) &&
                        "NewDesc".equals(group.getDescription()) &&
                        origTime.equals(group.getCreatedAt())
        ));
    }

    @Test
    void shouldThrowWhenUpdateGroupNotFound() {
        // Given
        when(groupRepository.findById(123L)).thenReturn(Optional.empty());
        Group updateData = Group.builder()
                .name("X")
                .description("Y")
                .build();

        // When & Then
        assertThatThrownBy(() -> groupService.updateGroup(123L, updateData))
                .isInstanceOf(GroupNotFoundException.class);
        verify(groupRepository).findById(123L);
        verify(groupRepository, never()).save(any());
    }
}
