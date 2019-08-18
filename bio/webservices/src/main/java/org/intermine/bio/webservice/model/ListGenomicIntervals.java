package org.intermine.bio.webservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

import org.intermine.webservice.model.JSONModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListGenomicIntervals
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-08-04T21:58:55.348+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "listName", "listSize", "type", "invalidSpans", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListGenomicIntervals extends JSONModel {
  @JsonProperty("listName")
  private String listName = null;

  @JsonProperty("listSize")
  private Integer listSize = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("invalidSpans")
  @Valid
  private List<String> invalidSpans = null;

  public ListGenomicIntervals listName(String listName) {
    this.listName = listName;
    return this;
  }

  /**
   * Get listName
   * @return listName
  **/
  @ApiModelProperty(value = "")

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public ListGenomicIntervals listSize(Integer listSize) {
    this.listSize = listSize;
    return this;
  }

  /**
   * Get listSize
   * @return listSize
  **/
  @ApiModelProperty(value = "")

  public Integer getListSize() {
    return listSize;
  }

  public void setListSize(Integer listSize) {
    this.listSize = listSize;
  }

  public ListGenomicIntervals type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ListGenomicIntervals invalidSpans(List<String> invalidSpans) {
    this.invalidSpans = invalidSpans;
    return this;
  }

  public ListGenomicIntervals addInvalidSpansItem(String invalidSpansItem) {
    if (this.invalidSpans == null) {
      this.invalidSpans = new ArrayList<String>();
    }
    this.invalidSpans.add(invalidSpansItem);
    return this;
  }

  /**
   * Get invalidSpans
   * @return invalidSpans
  **/
  @ApiModelProperty(value = "")

  public List<String> getInvalidSpans() {
    return invalidSpans;
  }

  public void setInvalidSpans(List<String> invalidSpans) {
    this.invalidSpans = invalidSpans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListGenomicIntervals listGenomicIntervals = (ListGenomicIntervals) o;
    return Objects.equals(this.listName, listGenomicIntervals.listName) &&
        Objects.equals(this.listSize, listGenomicIntervals.listSize) &&
        Objects.equals(this.type, listGenomicIntervals.type) &&
        Objects.equals(this.invalidSpans, listGenomicIntervals.invalidSpans) &&
        Objects.equals(this.executionTime, listGenomicIntervals.executionTime) &&
        Objects.equals(this.wasSuccessful, listGenomicIntervals.wasSuccessful) &&
        Objects.equals(this.error, listGenomicIntervals.error) &&
        Objects.equals(this.statusCode, listGenomicIntervals.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listName, listSize, type, invalidSpans, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListGenomicIntervals {\n");
    
    sb.append("    listName: ").append(toIndentedString(listName)).append("\n");
    sb.append("    listSize: ").append(toIndentedString(listSize)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    invalidSpans: ").append(toIndentedString(invalidSpans)).append("\n");
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
