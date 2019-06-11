package org.intermine.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * VersionRelease
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
public class VersionRelease   {
  @JsonProperty("version")
  private String version = null;

  @JsonProperty("executionTime")
  private String executionTime = null;

  @JsonProperty("wasSuccessful")
  private Boolean wasSuccessful = null;

  @JsonProperty("error")
  private String error = null;

  @JsonProperty("statusCode")
  private Integer statusCode = null;

  public VersionRelease version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public VersionRelease executionTime(String executionTime) {
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

  public VersionRelease wasSuccessful(Boolean wasSuccessful) {
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

  public VersionRelease error(String error) {
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

  public VersionRelease statusCode(Integer statusCode) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionRelease versionRelease = (VersionRelease) o;
    return Objects.equals(this.version, versionRelease.version) &&
        Objects.equals(this.executionTime, versionRelease.executionTime) &&
        Objects.equals(this.wasSuccessful, versionRelease.wasSuccessful) &&
        Objects.equals(this.error, versionRelease.error) &&
        Objects.equals(this.statusCode, versionRelease.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionRelease {\n");

    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
