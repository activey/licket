package org.licket.core.model;

import org.fest.assertions.Assertions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LicketComponentModelTest {

  @Test
  public void shouldComputeJsonPatchWhenOldModelExists() {
    // given
    LicketComponentModel<TestModel> componentModel = LicketComponentModel.ofModelObject(new TestModel(123, "test1"));

    // when
    componentModel.set(new TestModel(666, "test2"));

    // then
    String jsonPatch = componentModel.getPatch().getJsonPatch().toString();
    assertThat(jsonPatch).isEqualTo("[{\"op\":\"replace\",\"path\":\"/intValue\",\"value\":666},{\"op\":\"replace\",\"path\":\"/stringValue\",\"value\":\"test2\"}]");
  }

  @Test
  public void shouldComputeJsonPatchWhenOldModelDoesNotExist() {
    // given
    LicketComponentModel<TestModel> componentModel = LicketComponentModel.emptyComponentModel();

    // when
    componentModel.set(new TestModel(666, "test2"));

    // then
    String jsonPatch = componentModel.getPatch().getJsonPatch().toString();
    assertThat(jsonPatch).isEqualTo("[{\"op\":\"add\",\"path\":\"/intValue\",\"value\":666},{\"op\":\"add\",\"path\":\"/stringValue\",\"value\":\"test2\"}]");
  }
}