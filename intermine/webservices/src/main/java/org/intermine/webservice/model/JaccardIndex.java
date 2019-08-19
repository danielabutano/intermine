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
 * JaccardIndex
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-20T18:29:46.239+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "input", "lists", "executionTime", "wasSuccessful", "error", "statusCode" })
public class JaccardIndex extends JSONModel  {
  @JsonProperty("input")
  private String input = null;

  @JsonProperty("lists")
  @Valid
  private List<Object> lists = null;

  public JaccardIndex input(String input) {
    this.input = input;
    return this;
  }

  /**
   * Get input
   * @return input
  **/
  @ApiModelProperty(value = "")

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public JaccardIndex lists(List<Object> lists) {
    this.lists = lists;
    return this;
  }

  public JaccardIndex addListsItem(Object listsItem) {
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
    JaccardIndex jaccardIndex = (JaccardIndex) o;
    return Objects.equals(this.input, jaccardIndex.input) &&
        Objects.equals(this.lists, jaccardIndex.lists) &&
        Objects.equals(this.executionTime, jaccardIndex.executionTime) &&
        Objects.equals(this.wasSuccessful, jaccardIndex.wasSuccessful) &&
        Objects.equals(this.error, jaccardIndex.error) &&
        Objects.equals(this.statusCode, jaccardIndex.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, lists, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JaccardIndex {\n");
    
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
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
