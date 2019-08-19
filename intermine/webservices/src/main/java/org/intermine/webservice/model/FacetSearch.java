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
 * FacetSearch
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-19T19:35:11.443+05:30[Asia/Kolkata]")
public class FacetSearch  extends JSONModel {
  @JsonProperty("facets")
  private Object facets = null;

  public FacetSearch facets(Object facets) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FacetSearch facetSearch = (FacetSearch) o;
    return Objects.equals(this.facets, facetSearch.facets) &&
        Objects.equals(this.executionTime, facetSearch.executionTime) &&
        Objects.equals(this.wasSuccessful, facetSearch.wasSuccessful) &&
        Objects.equals(this.error, facetSearch.error) &&
        Objects.equals(this.statusCode, facetSearch.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facets, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetSearch {\n");
    
    sb.append("    facets: ").append(toIndentedString(facets)).append("\n");
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
