package org.intermine.webservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FacetList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T03:15:59.349+05:30[Asia/Kolkata]")
public class FacetList   {
  @JsonProperty("facetlist")
  private Object facetlist = null;

  @JsonProperty("executionTime")
  private String executionTime = null;

  @JsonProperty("wasSuccessful")
  private Boolean wasSuccessful = null;

  @JsonProperty("error")
  private String error = null;

  @JsonProperty("statusCode")
  private Integer statusCode = null;

  public FacetList facetlist(Object facetlist) {
    this.facetlist = facetlist;
    return this;
  }

  /**
   * Get facetlist
   * @return facetlist
  **/
  @ApiModelProperty(value = "")

  public Object getFacetlist() {
    return facetlist;
  }

  public void setFacetlist(Object facetlist) {
    this.facetlist = facetlist;
  }

  public FacetList executionTime(String executionTime) {
    this.executionTime = executionTime;
    return this;
  }

  /**
   * Get executionTime
   * @return executionTime
  **/
  @ApiModelProperty(value = "")

  public String getExecutionTime() {
    return executionTime;
  }

  public void setExecutionTime(String executionTime) {
    this.executionTime = executionTime;
  }

  public FacetList wasSuccessful(Boolean wasSuccessful) {
    this.wasSuccessful = wasSuccessful;
    return this;
  }

  /**
   * Get wasSuccessful
   * @return wasSuccessful
  **/
  @ApiModelProperty(value = "")

  public Boolean isWasSuccessful() {
    return wasSuccessful;
  }

  public void setWasSuccessful(Boolean wasSuccessful) {
    this.wasSuccessful = wasSuccessful;
  }

  public FacetList error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
  **/
  @ApiModelProperty(value = "")

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public FacetList statusCode(Integer statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Get statusCode
   * @return statusCode
  **/
  @ApiModelProperty(value = "")

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FacetList facetList = (FacetList) o;
    return Objects.equals(this.facetlist, facetList.facetlist) &&
        Objects.equals(this.executionTime, facetList.executionTime) &&
        Objects.equals(this.wasSuccessful, facetList.wasSuccessful) &&
        Objects.equals(this.error, facetList.error) &&
        Objects.equals(this.statusCode, facetList.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facetlist, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetList {\n");
    
    sb.append("    facetlist: ").append(toIndentedString(facetlist)).append("\n");
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
