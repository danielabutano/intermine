package org.intermine.webservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * QueryResultsJson
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-20T18:29:46.239+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "modelName", "columnHeaders", "rootClass", "start", "views", "results", "executionTime", "wasSuccessful", "error", "statusCode" })
public class QueryResultsJson extends JSONModel  {
  @JsonProperty("modelName")
  private String modelName = null;

  @JsonProperty("columnHeaders")
  @Valid
  private List<String> columnHeaders = null;

  @JsonProperty("rootClass")
  private String rootClass = null;

  @JsonProperty("start")
  private Integer start = null;

  @JsonProperty("views")
  @Valid
  private List<String> views = null;

  @JsonProperty("results")
  @Valid
  private List<List<String>> results = null;

  public QueryResultsJson modelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  /**
   * Get modelName
   * @return modelName
  **/
  @ApiModelProperty(value = "")

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public QueryResultsJson columnHeaders(List<String> columnHeaders) {
    this.columnHeaders = columnHeaders;
    return this;
  }

  public QueryResultsJson addColumnHeadersItem(String columnHeadersItem) {
    if (this.columnHeaders == null) {
      this.columnHeaders = new ArrayList<String>();
    }
    this.columnHeaders.add(columnHeadersItem);
    return this;
  }

  /**
   * Get columnHeaders
   * @return columnHeaders
  **/
  @ApiModelProperty(value = "")

  public List<String> getColumnHeaders() {
    return columnHeaders;
  }

  public void setColumnHeaders(List<String> columnHeaders) {
    this.columnHeaders = columnHeaders;
  }

  public QueryResultsJson rootClass(String rootClass) {
    this.rootClass = rootClass;
    return this;
  }

  /**
   * Get rootClass
   * @return rootClass
  **/
  @ApiModelProperty(value = "")

  public String getRootClass() {
    return rootClass;
  }

  public void setRootClass(String rootClass) {
    this.rootClass = rootClass;
  }

  public QueryResultsJson start(Integer start) {
    this.start = start;
    return this;
  }

  /**
   * Get start
   * @return start
  **/
  @ApiModelProperty(value = "")

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public QueryResultsJson views(List<String> views) {
    this.views = views;
    return this;
  }

  public QueryResultsJson addViewsItem(String viewsItem) {
    if (this.views == null) {
      this.views = new ArrayList<String>();
    }
    this.views.add(viewsItem);
    return this;
  }

  /**
   * Get views
   * @return views
  **/
  @ApiModelProperty(value = "")

  public List<String> getViews() {
    return views;
  }

  public void setViews(List<String> views) {
    this.views = views;
  }

  public QueryResultsJson results(List<List<String>> results) {
    this.results = results;
    return this;
  }

  public QueryResultsJson addResultsItem(List<String> resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<List<String>>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   * @return results
  **/
  @ApiModelProperty(value = "")
  @Valid
  public List<List<String>> getResults() {
    return results;
  }

  public void setResults(List<List<String>> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryResultsJson queryResultsJson = (QueryResultsJson) o;
    return Objects.equals(this.modelName, queryResultsJson.modelName) &&
        Objects.equals(this.columnHeaders, queryResultsJson.columnHeaders) &&
        Objects.equals(this.rootClass, queryResultsJson.rootClass) &&
        Objects.equals(this.start, queryResultsJson.start) &&
        Objects.equals(this.views, queryResultsJson.views) &&
        Objects.equals(this.results, queryResultsJson.results) &&
        Objects.equals(this.executionTime, queryResultsJson.executionTime) &&
        Objects.equals(this.wasSuccessful, queryResultsJson.wasSuccessful) &&
        Objects.equals(this.error, queryResultsJson.error) &&
        Objects.equals(this.statusCode, queryResultsJson.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelName, columnHeaders, rootClass, start, views, results, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryResultsJson {\n");
    
    sb.append("    modelName: ").append(toIndentedString(modelName)).append("\n");
    sb.append("    columnHeaders: ").append(toIndentedString(columnHeaders)).append("\n");
    sb.append("    rootClass: ").append(toIndentedString(rootClass)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    views: ").append(toIndentedString(views)).append("\n");
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
