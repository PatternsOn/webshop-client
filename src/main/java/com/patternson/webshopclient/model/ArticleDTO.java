package com.patternson.webshopclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 *
 * Created by Tobias Pettersson 20180116
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  ArticleDTO {


    @Size(min = 2, max = 20)
    private String name;
    private String description;
    private BigDecimal price;
    private String company;
    private Integer stock;

    @JsonProperty("article_url")
    private String articleUrl;
}