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
 * GeneratedCode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
public class GeneratedCode   {
  @JsonProperty("code")
  private Object code = null;

  @JsonProperty("executionTime")
  private String executionTime = null;

  @JsonProperty("wasSuccessful")
  private Boolean wasSuccessful = null;

  @JsonProperty("error")
  private String error = null;

  @JsonProperty("statusCode")
  private Integer statusCode = null;

  public GeneratedCode code(Object code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(value = "")

  public Object getCode() {
    return code;
  }

  public void setCode(Object code) {
    this.code = code;
  }

  public GeneratedCode executionTime(String executionTime) {
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

  public GeneratedCode wasSuccessful(Boolean wasSuccessful) {
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

  public GeneratedCode error(String error) {
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

  public GeneratedCode statusCode(Integer statusCode) {
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
    GeneratedCode generatedCode = (GeneratedCode) o;
    return Objects.equals(this.code, generatedCode.code) &&
        Objects.equals(this.executionTime, generatedCode.executionTime) &&
        Objects.equals(this.wasSuccessful, generatedCode.wasSuccessful) &&
        Objects.equals(this.error, generatedCode.error) &&
        Objects.equals(this.statusCode, generatedCode.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneratedCode {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
