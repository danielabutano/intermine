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
 * ToList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-28T16:14:25.939+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "listId", "listName", "listSize", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ToList extends JSONModel  {
  @JsonProperty("listId")
  private Integer listId = null;

  @JsonProperty("listName")
  private String listName = null;

  @JsonProperty("listSize")
  private Integer listSize = null;

  public ToList listId(Integer listId) {
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

  public ToList listName(String listName) {
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

  public ToList listSize(Integer listSize) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToList toList = (ToList) o;
    return Objects.equals(this.listId, toList.listId) &&
        Objects.equals(this.listName, toList.listName) &&
        Objects.equals(this.listSize, toList.listSize) &&
        Objects.equals(this.executionTime, toList.executionTime) &&
        Objects.equals(this.wasSuccessful, toList.wasSuccessful) &&
        Objects.equals(this.error, toList.error) &&
        Objects.equals(this.statusCode, toList.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listId, listName, listSize, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ToList {\n");
    
    sb.append("    listId: ").append(toIndentedString(listId)).append("\n");
    sb.append("    listName: ").append(toIndentedString(listName)).append("\n");
    sb.append("    listSize: ").append(toIndentedString(listSize)).append("\n");
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
