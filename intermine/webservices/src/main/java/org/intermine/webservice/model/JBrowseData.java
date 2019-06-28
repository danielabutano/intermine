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
 * JBrowseData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "results", "executionTime", "wasSuccessful", "error", "statusCode" })
public class JBrowseData extends JSONModel  {
  @JsonProperty("results")
  private Object results = null;

  public JBrowseData results(Object results) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JBrowseData jbrowseData = (JBrowseData) o;
    return Objects.equals(this.results, jbrowseData.results) &&
        Objects.equals(this.executionTime, jbrowseData.executionTime) &&
        Objects.equals(this.wasSuccessful, jbrowseData.wasSuccessful) &&
        Objects.equals(this.error, jbrowseData.error) &&
        Objects.equals(this.statusCode, jbrowseData.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(results, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JBrowseData {\n");
    
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
