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
 * SavedTemplate
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-13T20:52:32.591+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "template", "executionTime", "wasSuccessful", "error", "statusCode" })
public class SavedTemplate extends JSONModel  {
  @JsonProperty("template")
  private Object template = null;

  public SavedTemplate template(Object template) {
    this.template = template;
    return this;
  }

  /**
   * Get template
   * @return template
  **/
  @ApiModelProperty(value = "")

  public Object getTemplate() {
    return template;
  }

  public void setTemplate(Object template) {
    this.template = template;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SavedTemplate savedTemplate = (SavedTemplate) o;
    return Objects.equals(this.template, savedTemplate.template) &&
        Objects.equals(this.executionTime, savedTemplate.executionTime) &&
        Objects.equals(this.wasSuccessful, savedTemplate.wasSuccessful) &&
        Objects.equals(this.error, savedTemplate.error) &&
        Objects.equals(this.statusCode, savedTemplate.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(template, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavedTemplate {\n");
    
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
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
