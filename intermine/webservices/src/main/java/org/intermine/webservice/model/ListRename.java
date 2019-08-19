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
 * ListRename
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-16T22:35:41.810+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "listId", "listName", "listSize", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListRename extends JSONModel  {
  @JsonProperty("listId")
  private Integer listId = null;

  @JsonProperty("listName")
  private String listName = null;

  @JsonProperty("listSize")
  private Integer listSize = null;

  public ListRename listId(Integer listId) {
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

  public ListRename listName(String listName) {
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

  public ListRename listSize(Integer listSize) {
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
    ListRename listRename = (ListRename) o;
    return Objects.equals(this.listId, listRename.listId) &&
        Objects.equals(this.listName, listRename.listName) &&
        Objects.equals(this.listSize, listRename.listSize) &&
        Objects.equals(this.executionTime, listRename.executionTime) &&
        Objects.equals(this.wasSuccessful, listRename.wasSuccessful) &&
        Objects.equals(this.error, listRename.error) &&
        Objects.equals(this.statusCode, listRename.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listId, listName, listSize, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListRename {\n");
    
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
