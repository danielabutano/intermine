package org.intermine.webservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PossibleValues
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "path", "field", "count", "type", "class", "results", "executionTime", "wasSuccessful", "error", "statusCode" })
public class PossibleValues extends JSONModel  {
  @JsonProperty("path")
  private String path = null;

  @JsonProperty("field")
  private String field = null;

  @JsonProperty("count")
  private Integer count = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("class")
  private String propertyClass = null;

  @JsonProperty("results")
  @Valid
  private List<Object> results = null;

  public PossibleValues path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(value = "")

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public PossibleValues field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
  **/
  @ApiModelProperty(value = "")

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public PossibleValues count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
  **/
  @ApiModelProperty(value = "")

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public PossibleValues type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public PossibleValues propertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
    return this;
  }

  /**
   * Get propertyClass
   * @return propertyClass
  **/
  @ApiModelProperty(value = "")

  public String getPropertyClass() {
    return propertyClass;
  }

  public void setPropertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
  }

  public PossibleValues results(List<Object> results) {
    this.results = results;
    return this;
  }

  public PossibleValues addResultsItem(Object resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<Object>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   * @return results
  **/
  @ApiModelProperty(value = "")

  public List<Object> getResults() {
    return results;
  }

  public void setResults(List<Object> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PossibleValues possibleValues = (PossibleValues) o;
    return Objects.equals(this.path, possibleValues.path) &&
        Objects.equals(this.field, possibleValues.field) &&
        Objects.equals(this.count, possibleValues.count) &&
        Objects.equals(this.type, possibleValues.type) &&
        Objects.equals(this.propertyClass, possibleValues.propertyClass) &&
        Objects.equals(this.results, possibleValues.results) &&
        Objects.equals(this.executionTime, possibleValues.executionTime) &&
        Objects.equals(this.wasSuccessful, possibleValues.wasSuccessful) &&
        Objects.equals(this.error, possibleValues.error) &&
        Objects.equals(this.statusCode, possibleValues.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, field, count, type, propertyClass, results, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PossibleValues {\n");
    
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    propertyClass: ").append(toIndentedString(propertyClass)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
