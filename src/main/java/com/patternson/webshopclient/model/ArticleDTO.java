package com.patternson.webshopclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * Created by Tobias Pettersson 20180116
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  ArticleDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 200)
    private String description;

    @Min(0)
    @Max(9999999)
    private BigDecimal price;

    @NotBlank
    @Size(min = 2, max = 50)
    private String company;

    @Min(0)
    @Max(9999)
    private BigInteger stock;

    @JsonProperty("article_url")
    private String articleUrl;
}