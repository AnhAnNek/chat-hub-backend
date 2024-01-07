package com.vanannek.socialmedia.commentreaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.commentreaction.service.CommentReactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentReactionControllerTest {

    @Mock
    private CommentReactionService commentReactionService;

    @InjectMocks
    private CommentReactionController commentReactionController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentReactionController).build();
    }

    @Test
    public void testAddReactionSuccessfully() throws Exception {
        CommentReactionDTO commentReactionDTO = new CommentReactionDTO("vanlong", EReactionType.LIKE.name());

        when(commentReactionService.add(any(CommentReactionDTO.class))).thenReturn(commentReactionDTO);

        mockMvc.perform(post("/api/comment-reactions/add-reaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentReactionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(ReactionUtils.REACTION_ADDED_SUCCESSFULLY));

        verify(commentReactionService).add(any(CommentReactionDTO.class));
    }

    @Test
    public void testAddReactionWithException() throws Exception {
        CommentReactionDTO commentReactionDTO = new CommentReactionDTO();

        when(commentReactionService.add(any(CommentReactionDTO.class)))
                .thenThrow(new DuplicateCommentReactionException("Simulated exception during add"));

        mockMvc.perform(post("/api/comment-reactions/add-reaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentReactionDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Simulated exception during add"));

        verify(commentReactionService).add(any(CommentReactionDTO.class));
    }
}