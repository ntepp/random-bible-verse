package com.perinfinity.com.randomverse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Verse(String reference, String text, String translation_name) {
}
