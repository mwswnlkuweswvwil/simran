package com.blopapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min=2, message = "Title should be atleast 2 character")
    private String title;

    @NotEmpty(message = "Should not be empty")
    @Size(min=4)
    private String description;

    @NotEmpty(message = "Name may not be empty")
    private String content;
}
