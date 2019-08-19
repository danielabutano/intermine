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
 * ListSharingGet
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T15:10:07.571+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "lists", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListSharingGet extends JSONModel  {
  @JsonProperty("lists")
  private Object lists = null;

  public ListSharingGet lists(Object lists) {
    this.lists = lists;
    return this;
  }

  /**
   * Get lists
   * @return lists
  **/
  @ApiModelProperty(value = "")

  public Object getLists() {
    return lists;
  }

  public void setLists(Object lists) {
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
    ListSharingGet listSharingGet = (ListSharingGet) o;
    return Objects.equals(this.lists, listSharingGet.lists) &&
        Objects.equals(this.executionTime, listSharingGet.executionTime) &&
        Objects.equals(this.wasSuccessful, listSharingGet.wasSuccessful) &&
        Objects.equals(this.error, listSharingGet.error) &&
        Objects.equals(this.statusCode, listSharingGet.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lists, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListSharingGet {\n");
    
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
