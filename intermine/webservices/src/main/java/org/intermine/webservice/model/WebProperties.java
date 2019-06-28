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
 * WebProperties
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "web-properties", "executionTime", "wasSuccessful", "error", "statusCode" })
public class WebProperties extends JSONModel  {
  @JsonProperty("web-properties")
  private Object webProperties = null;

  public WebProperties webProperties(Object webProperties) {
    this.webProperties = webProperties;
    return this;
  }

  /**
   * Get webProperties
   * @return webProperties
  **/
  @ApiModelProperty(value = "")

  public Object getWebProperties() {
    return webProperties;
  }

  public void setWebProperties(Object webProperties) {
    this.webProperties = webProperties;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebProperties webProperties = (WebProperties) o;
    return Objects.equals(this.webProperties, webProperties.webProperties) &&
        Objects.equals(this.executionTime, webProperties.executionTime) &&
        Objects.equals(this.wasSuccessful, webProperties.wasSuccessful) &&
        Objects.equals(this.error, webProperties.error) &&
        Objects.equals(this.statusCode, webProperties.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(webProperties, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebProperties {\n");
    
    sb.append("    webProperties: ").append(toIndentedString(webProperties)).append("\n");
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
