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
 * QueryResultsJsonCount
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-20T18:29:46.239+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "count", "executionTime", "wasSuccessful", "error", "statusCode" })
public class QueryResultsJsonCount extends JSONModel  {
  @JsonProperty("count")
  private Integer count = null;

  public QueryResultsJsonCount count(Integer count) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryResultsJsonCount queryResultsJsonCount = (QueryResultsJsonCount) o;
    return Objects.equals(this.count, queryResultsJsonCount.count) &&
        Objects.equals(this.executionTime, queryResultsJsonCount.executionTime) &&
        Objects.equals(this.wasSuccessful, queryResultsJsonCount.wasSuccessful) &&
        Objects.equals(this.error, queryResultsJsonCount.error) &&
        Objects.equals(this.statusCode, queryResultsJsonCount.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryResultsJsonCount {\n");
    
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
