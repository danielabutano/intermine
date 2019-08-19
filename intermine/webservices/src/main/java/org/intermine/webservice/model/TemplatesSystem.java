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
 * TemplatesSystem
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T01:19:48.599+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "templates", "executionTime", "wasSuccessful", "error", "statusCode" })
public class TemplatesSystem extends JSONModel  {
  @JsonProperty("templates")
  private Object templates = null;

  public TemplatesSystem templates(Object templates) {
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
  public int hashCode() {
    return Objects.hash(templates, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplatesSystem {\n");
    
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
