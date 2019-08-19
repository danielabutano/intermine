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
 * QuickSearch
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-22T06:09:52.181+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "totalHits", "facets", "results", "executionTime", "wasSuccessful", "error", "statusCode" })
public class QuickSearch extends JSONModel  {
  @JsonProperty("totalHits")
  private Object totalHits = null;

  @JsonProperty("facets")
  private Object facets = null;

  @JsonProperty("results")
  private Object results = null;

  public QuickSearch totalHits(Object totalHits) {
    this.totalHits = totalHits;
    return this;
  }

  /**
   * Get totalHits
   * @return totalHits
  **/
  @ApiModelProperty(value = "")

  public Object getTotalHits() {
    return totalHits;
  }

  public void setTotalHits(Object totalHits) {
    this.totalHits = totalHits;
  }

  public QuickSearch facets(Object facets) {
    this.facets = facets;
    return this;
  }

  /**
   * Get facets
   * @return facets
  **/
  @ApiModelProperty(value = "")

  public Object getFacets() {
    return facets;
  }

  public void setFacets(Object facets) {
    this.facets = facets;
  }

  public QuickSearch results(Object results) {
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
    QuickSearch quickSearch = (QuickSearch) o;
    return Objects.equals(this.totalHits, quickSearch.totalHits) &&
        Objects.equals(this.facets, quickSearch.facets) &&
        Objects.equals(this.results, quickSearch.results) &&
        Objects.equals(this.executionTime, quickSearch.executionTime) &&
        Objects.equals(this.wasSuccessful, quickSearch.wasSuccessful) &&
        Objects.equals(this.error, quickSearch.error) &&
        Objects.equals(this.statusCode, quickSearch.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalHits, facets, results, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuickSearch {\n");
    
    sb.append("    totalHits: ").append(toIndentedString(totalHits)).append("\n");
    sb.append("    facets: ").append(toIndentedString(facets)).append("\n");
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
