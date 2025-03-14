package com.prac.restdocs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.restdocs.domain.User;
import com.prac.restdocs.domain.UserRequestDto;
import com.prac.restdocs.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 사용자 조회 API 테스트")
    public void getAllUsersTest() throws Exception {
        // given
        User user1 = User.builder()
                .name("홍길동")
                .email("hong@example.com")
                .build();
        User user2 = User.builder()
                .name("김철수")
                .email("kim@example.com")
                .build();

        List<User> users = Arrays.asList(user1, user2);
        given(userService.getAllUsers()).willReturn(users);

        // when
        ResultActions result = mockMvc.perform(
                get("/users")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andDo(document("users-get-all",
                        responseFields(
                                fieldWithPath("[].id").description("사용자 ID"),
                                fieldWithPath("[].name").description("사용자 이름"),
                                fieldWithPath("[].email").description("사용자 이메일")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 생성 API 테스트")
    public void createUserTest() throws Exception {
        // given (준비): 테스트에 필요한 데이터 준비
        UserRequestDto requestDto = UserRequestDto.builder()
                .name("홍길동")
                .email("hong@example.com")
                .build();

        User createdUser = User.builder()
                .name("홍길동")
                .email("hong@example.com")
                .build();

        when(userService.createUser(any(UserRequestDto.class))).thenReturn(createdUser);

        // when
        ResultActions result = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then
        result
                .andExpect(status().isOk())
                .andDo(document("users-create",
                        requestFields(
                                fieldWithPath("name").description("사용자 이름"),
                                fieldWithPath("email").description("사용자 이메일")
                        ),
                        responseFields(
                                fieldWithPath("id").description("사용자 ID"),
                                fieldWithPath("name").description("사용자 이름"),
                                fieldWithPath("email").description("사용자 이메일")
                        )
                ));
    }
}