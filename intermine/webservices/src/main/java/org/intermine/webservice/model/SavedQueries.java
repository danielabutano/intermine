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
 * SavedQueries
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "queries", "executionTime", "wasSuccessful", "error", "statusCode" })
public class SavedQueries extends JSONModel  {
  @JsonProperty("queries")
  private Object queries = null;

  public SavedQueries queries(Object queries) {
    this.queries = queries;
    return this;
  }

  /**
   * Get queries
   * @return queries
  **/
  @ApiModelProperty(value = "")

  public Object getQueries() {
    return queries;
  }

  public void setQueries(Object queries) {
    this.queries = queries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SavedQueries savedQueries = (SavedQueries) o;
    return Objects.equals(this.queries, savedQueries.queries) &&
        Objects.equals(this.executionTime, savedQueries.executionTime) &&
        Objects.equals(this.wasSuccessful, savedQueries.wasSuccessful) &&
        Objects.equals(this.error, savedQueries.error) &&
        Objects.equals(this.statusCode, savedQueries.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(queries, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavedQueries {\n");
    
    sb.append("    queries: ").append(toIndentedString(queries)).append("\n");
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
