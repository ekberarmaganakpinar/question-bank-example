/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.questionbank.questionbank.entity.Parent;
import com.questionbank.questionbank.service.ParentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParentControllerTest {

    @Mock
    private ParentService parentService;

    @InjectMocks
    private ParentController parentController;

    @Test
    void findParentByStudentId() {
        final var parent = new Parent();
        when(parentService.findParentByStudentId(anyLong())).thenReturn(List.of(parent));
        final var responseEntity = parentController.findParentByStudentId(anyLong());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody().get(0).getFirstName()).isEqualTo(null);
    }
}
