package org.intermine.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * VersionRelease
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "version", "executionTime", "wasSuccessful", "error", "statusCode" })
public class VersionRelease extends JSONModel  {
  @JsonProperty("version")
  private String version = null;

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
