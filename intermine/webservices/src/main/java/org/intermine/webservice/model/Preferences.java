package org.intermine.webservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Preferences
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-10T00:10:10.729+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "preferences", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Preferences extends JSONModel   {
  @JsonProperty("preferences")
  private Object preferences = null;

  public Preferences preferences(Object preferences) {
    this.preferences = preferences;
    return this;
  }

  /**
   * Get preferences
   * @return preferences
  **/
  @ApiModelProperty(value = "")

  public Object getPreferences() {
    return preferences;
  }

  public void setPreferences(Object preferences) {
    this.preferences = preferences;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Preferences preferences = (Preferences) o;
    return Objects.equals(this.preferences, preferences.preferences) &&
        Objects.equals(this.executionTime, preferences.executionTime) &&
        Objects.equals(this.wasSuccessful, preferences.wasSuccessful) &&
        Objects.equals(this.error, preferences.error) &&
        Objects.equals(this.statusCode, preferences.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(preferences, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Preferences {\n");
    
    sb.append("    preferences: ").append(toIndentedString(preferences)).append("\n");
    sb.append("    executionTime: ").append(toIndentedString(executionTime)).append("\n");
    sb.append("    wasSuccessful: ").append(toIndentedString(wasSuccessful)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
