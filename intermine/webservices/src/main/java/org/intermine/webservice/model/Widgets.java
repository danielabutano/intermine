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
 * Widgets
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-20T18:29:46.239+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "widgets", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Widgets extends JSONModel  {
  @JsonProperty("widgets")
  @Valid
  private List<Object> widgets = null;

  public Widgets widgets(List<Object> widgets) {
    this.widgets = widgets;
    return this;
  }

  public Widgets addWidgetsItem(Object widgetsItem) {
    if (this.widgets == null) {
      this.widgets = new ArrayList<Object>();
    }
    this.widgets.add(widgetsItem);
    return this;
  }

  /**
   * Get widgets
   * @return widgets
  **/
  @ApiModelProperty(value = "")

  public List<Object> getWidgets() {
    return widgets;
  }

  public void setWidgets(List<Object> widgets) {
    this.widgets = widgets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Widgets widgets = (Widgets) o;
    return Objects.equals(this.widgets, widgets.widgets) &&
        Objects.equals(this.executionTime, widgets.executionTime) &&
        Objects.equals(this.wasSuccessful, widgets.wasSuccessful) &&
        Objects.equals(this.error, widgets.error) &&
        Objects.equals(this.statusCode, widgets.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(widgets, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Widgets {\n");
    
    sb.append("    widgets: ").append(toIndentedString(widgets)).append("\n");
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
