package com.patternson.webshopclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * Created by Tobias Pettersson 20180116
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDTO {

    List<ArticleDTO> articles;
}
