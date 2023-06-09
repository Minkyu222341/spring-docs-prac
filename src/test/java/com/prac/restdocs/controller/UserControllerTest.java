package com.prac.restdocs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.restdocs.domain.User;
import com.prac.restdocs.domain.UserRequestDto;
import com.prac.restdocs.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  private RestDocumentationResultHandler document;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .alwaysDo(document)
            .build();


    this.document = MockMvcRestDocumentation.document("{method-name}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            HeaderDocumentation.requestHeaders(
                    HeaderDocumentation.headerWithName("Content-Type").description("요청 본문의 컨텐츠 타입")
            ),
            HeaderDocumentation.responseHeaders(
                    HeaderDocumentation.headerWithName("Content-Type").description("응답 본문의 컨텐츠 타입")
            ),
            RequestDocumentation.pathParameters(
                    RequestDocumentation.parameterWithName("id").description("조회할 사용자의 ID")
            ),
            PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("사용자의 이름"),
                    PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("사용자의 이메일 주소")
            ),
            PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("사용자의 이름"),
                    PayloadDocumentation.fieldWithPath("email").type(JsonFieldType.STRING).description("사용자의 이메일 주소")
            )
    );
  }

  @Test
  void testGetAllUsers() throws Exception {
    // given
    User user1 = new User("John Doe", "john.doe@example.com");
    User user2 = new User("Jane Smith", "jane.smith@example.com");
    List<User> users = Arrays.asList(user1, user2);

    given(userService.getAllUsers()).willReturn(users);

    // when
    mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andDo(document("users/get-all-users",
                    responseFields(
                            fieldWithPath("[].name").type(JsonFieldType.STRING).description("사용자의 이름"),
                            fieldWithPath("[].email").type(JsonFieldType.STRING).description("사용자의 이메일 주소")
                    )
            ));

    // then
    verify(userService, times(1)).getAllUsers();
  }

  @Test
  void testCreateUser() throws Exception {
    // given
    UserRequestDto requestDto = new UserRequestDto();
    requestDto.setName("John Doe");
    requestDto.setEmail("john.doe@example.com");

    User createdUser = new User("John Doe", "john.doe@example.com");

    given(userService.createUser(any(UserRequestDto.class))).willReturn(createdUser);

    // when
    mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andDo(document("users/create-user",
                    requestFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("사용자의 이름"),
                            fieldWithPath("email").type(JsonFieldType.STRING).description("사용자의 이메일 주소")
                    ),
                    responseFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("사용자의 이름"),
                            fieldWithPath("email").type(JsonFieldType.STRING).description("사용자의 이메일 주소")
                    )
            ));

    // then
    verify(userService, times(1)).createUser(any(UserRequestDto.class));
  }
}
