package com.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerSettings implements Serializable {
  private static final long serialVersionUID = 5069148695519603836L;

  @NotNull
  private String lang;

  public PlayerSettings() {
  }

  public PlayerSettings(final String lang) {
    this.lang = lang;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("lang", lang)
      .toString();
  }
}

