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
 * IdResolutionResults
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "results", "message", "executionTime", "wasSuccessful", "error", "statusCode" })
public class IdResolutionResults extends JSONModel  {
  @JsonProperty("results")
  private Object results = null;

  @JsonProperty("message")
  private String message = null;

  public IdResolutionResults results(Object results) {
    this.results = results;
    return this;
  }

  /**
   * Get results
   * @return results
  **/
  @ApiModelProperty(value = "")

  public Object getResults() {
    return results;
  }

  public void setResults(Object results) {
    this.results = results;
  }

  public IdResolutionResults message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   **/
  @ApiModelProperty(value = "")

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdResolutionResults idResolutionResults = (IdResolutionResults) o;
    return Objects.equals(this.results, idResolutionResults.results) &&
        Objects.equals(this.message, idResolutionResults.message) &&
            Objects.equals(this.executionTime, idResolutionResults.executionTime) &&
        Objects.equals(this.wasSuccessful, idResolutionResults.wasSuccessful) &&
        Objects.equals(this.error, idResolutionResults.error) &&
        Objects.equals(this.statusCode, idResolutionResults.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(results, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdResolutionResults {\n");
    
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
