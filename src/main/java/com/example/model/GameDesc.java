package com.example.model;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class GameDesc {

  private Integer gameId;
  private String kind;
  private String name;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("gameId", gameId)
      .add("kind", kind)
      .add("name", name)
      .toString();
  }
}
