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
 * Templates
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-05T13:01:43.086+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "templates", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Templates  extends JSONModel {
  @JsonProperty("templates")
  private Object templates = null;

  public Templates templates(Object templates) {
    this.templates = templates;
    return this;
  }

  /**
   * Get templates
   * @return templates
  **/
  @ApiModelProperty(value = "")

  public Object getTemplates() {
    return templates;
  }

  public void setTemplates(Object templates) {
    this.templates = templates;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Templates templates = (Templates) o;
    return Objects.equals(this.templates, templates.templates) &&
        Objects.equals(this.executionTime, templates.executionTime) &&
        Objects.equals(this.wasSuccessful, templates.wasSuccessful) &&
        Objects.equals(this.error, templates.error) &&
        Objects.equals(this.statusCode, templates.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templates, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Templates {\n");
    
    sb.append("    templates: ").append(toIndentedString(templates)).append("\n");
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
