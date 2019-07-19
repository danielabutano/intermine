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
 * ListAppend
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-16T22:35:41.810+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "listId", "listName", "listSize", "unmatchedIdentifiers", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListAppend extends JSONModel  {
  @JsonProperty("listId")
  private Integer listId = null;

  @JsonProperty("listName")
  private String listName = null;

  @JsonProperty("listSize")
  private Integer listSize = null;

  @JsonProperty("unmatchedIdentifiers")
  @Valid
  private List<String> unmatchedIdentifiers = null;

  public ListAppend listId(Integer listId) {
    this.listId = listId;
    return this;
  }

  /**
   * Get listId
   * @return listId
  **/
  @ApiModelProperty(value = "")

  public Integer getListId() {
    return listId;
  }

  public void setListId(Integer listId) {
    this.listId = listId;
  }

  public ListAppend listName(String listName) {
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

  public ListAppend listSize(Integer listSize) {
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

  public ListAppend unmatchedIdentifiers(List<String> unmatchedIdentifiers) {
    this.unmatchedIdentifiers = unmatchedIdentifiers;
    return this;
  }

  public ListAppend addUnmatchedIdentifiersItem(String unmatchedIdentifiersItem) {
    if (this.unmatchedIdentifiers == null) {
      this.unmatchedIdentifiers = new ArrayList<String>();
    }
    this.unmatchedIdentifiers.add(unmatchedIdentifiersItem);
    return this;
  }

  /**
   * Get unmatchedIdentifiers
   * @return unmatchedIdentifiers
  **/
  @ApiModelProperty(value = "")

  public List<String> getUnmatchedIdentifiers() {
    return unmatchedIdentifiers;
  }

  public void setUnmatchedIdentifiers(List<String> unmatchedIdentifiers) {
    this.unmatchedIdentifiers = unmatchedIdentifiers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListAppend listAppend = (ListAppend) o;
    return Objects.equals(this.listId, listAppend.listId) &&
        Objects.equals(this.listName, listAppend.listName) &&
        Objects.equals(this.listSize, listAppend.listSize) &&
        Objects.equals(this.unmatchedIdentifiers, listAppend.unmatchedIdentifiers) &&
        Objects.equals(this.executionTime, listAppend.executionTime) &&
        Objects.equals(this.wasSuccessful, listAppend.wasSuccessful) &&
        Objects.equals(this.error, listAppend.error) &&
        Objects.equals(this.statusCode, listAppend.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listId, listName, listSize, unmatchedIdentifiers, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListAppend {\n");
    
    sb.append("    listId: ").append(toIndentedString(listId)).append("\n");
    sb.append("    listName: ").append(toIndentedString(listName)).append("\n");
    sb.append("    listSize: ").append(toIndentedString(listSize)).append("\n");
    sb.append("    unmatchedIdentifiers: ").append(toIndentedString(unmatchedIdentifiers)).append("\n");
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
