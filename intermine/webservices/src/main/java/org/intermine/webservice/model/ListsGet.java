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
 * ListsGet
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-16T22:35:41.810+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "lists", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListsGet extends JSONModel  {
  @JsonProperty("lists")
  @Valid
  private List<Object> lists = null;

  public ListsGet lists(List<Object> lists) {
    this.lists = lists;
    return this;
  }

  public ListsGet addListsItem(Object listsItem) {
    if (this.lists == null) {
      this.lists = new ArrayList<Object>();
    }
    this.lists.add(listsItem);
    return this;
  }

  /**
   * Get lists
   * @return lists
  **/
  @ApiModelProperty(value = "")

  public List<Object> getLists() {
    return lists;
  }

  public void setLists(List<Object> lists) {
    this.lists = lists;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListsGet listsGet = (ListsGet) o;
    return Objects.equals(this.lists, listsGet.lists) &&
        Objects.equals(this.executionTime, listsGet.executionTime) &&
        Objects.equals(this.wasSuccessful, listsGet.wasSuccessful) &&
        Objects.equals(this.error, listsGet.error) &&
        Objects.equals(this.statusCode, listsGet.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lists, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListsGet {\n");
    
    sb.append("    lists: ").append(toIndentedString(lists)).append("\n");
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
