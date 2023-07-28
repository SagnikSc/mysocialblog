package com.socialblog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min=2, message = "title should be atleast 2 char")
    private String title;

    @NotEmpty
    @Size(min = 4, message = "description should be atleast 4 char")
    private String description;

    @NotEmpty
    private String content;
}
