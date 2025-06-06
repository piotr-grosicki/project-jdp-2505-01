package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    private final Group group = Group.builder()
            .name("Test Group")
            .description("This is a test group")
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    void testCreateGroup() {
        // Given & When
        Group savedGroup = groupRepository.save(group);

        // Then
        try {
            assertNotNull(savedGroup.getId());
            assertEquals("Test Group", savedGroup.getName());
            assertEquals("This is a test group", savedGroup.getDescription());
        } catch (Exception e) {
            fail("Group creation failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
        }
    }

    @Test
    void testFindGroupById() {
        // Given
        Group savedGroup = groupRepository.save(group);

        // When
        Group foundGroup = groupRepository.findById(savedGroup.getId()).orElse(null);

        // Then
        try {
            assertNotNull(foundGroup);
            assertEquals(savedGroup.getId(), foundGroup.getId());
            assertEquals(savedGroup.getName(), foundGroup.getName());
            assertEquals(savedGroup.getDescription(), foundGroup.getDescription());
        } catch (Exception e) {
            fail("Group retrieval failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
        }
    }

//    @Test
//    void testUpdateGroup() {
//        // Given
//        Group savedGroup = groupRepository.save(group);
//        savedGroup.setName("Updated Group");
//        savedGroup.setDescription("This is an updated test group");
//
//        // When
//        Group updatedGroup = groupRepository.save(savedGroup);
//
//        // Then
//        try {
//            assertNotNull(updatedGroup);
//            assertEquals("Updated Group", updatedGroup.getName());
//            assertEquals("This is an updated test group", updatedGroup.getDescription());
//        } catch (Exception e) {
//            fail("Group update failed: " + e.getMessage());
//        } finally {
//            groupRepository.delete(updatedGroup);
//        }
//    }
}