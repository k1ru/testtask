package com.arrival.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.nodes.Element;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YandexLoginPage {
    private int statusCode;
    private Element title;
    private Element userName;
    private Element userEmail;
    private Element hrefExit;

}
