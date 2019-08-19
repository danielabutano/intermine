package org.intermine.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Model
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-26T23:19:30.817+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "model", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Model extends JSONModel  {
  @JsonProperty("model")
  private Object model = null;

  public Model model(Object model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
  **/
  @ApiModelProperty(value = "")

  public Object getModel() {
    return model;
  }

  public void setModel(Object model) {
    this.model = model;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Model model = (Model) o;
    return Objects.equals(this.model, model.model) &&
        Objects.equals(this.executionTime, model.executionTime) &&
        Objects.equals(this.wasSuccessful, model.wasSuccessful) &&
        Objects.equals(this.error, model.error) &&
        Objects.equals(this.statusCode, model.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Model {\n");

    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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
