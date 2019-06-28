package org.intermine.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * SummaryFields
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "classes", "executionTime", "wasSuccessful", "error", "statusCode" })
public class SummaryFields extends JSONModel {
  @JsonProperty("classes")
  private Object classes = null;

  public SummaryFields classes(Object classes) {
    this.classes = classes;
    return this;
  }

  /**
   * Get classes
   * @return classes
  **/
  @ApiModelProperty(value = "")

  public Object getClasses() {
    return classes;
  }

  public void setClasses(Object classes) {
    this.classes = classes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SummaryFields summaryfields = (SummaryFields) o;
    return Objects.equals(this.classes, summaryfields.classes) &&
        Objects.equals(this.executionTime, summaryfields.executionTime) &&
        Objects.equals(this.wasSuccessful, summaryfields.wasSuccessful) &&
        Objects.equals(this.error, summaryfields.error) &&
        Objects.equals(this.statusCode, summaryfields.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(classes, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SummaryFields {\n");

    sb.append("    classes: ").append(toIndentedString(classes)).append("\n");
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
