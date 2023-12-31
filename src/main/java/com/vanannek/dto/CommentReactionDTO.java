package com.vanannek.dto;

import com.vanannek.customenum.EReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentReactionDTO {
    private Long id;
    private String username;
    private Long commentId;
    private EReactionType type;
}
