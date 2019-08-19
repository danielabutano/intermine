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
 * Schema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-18T00:14:58.996+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "schemata", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Schema extends JSONModel  {
  @JsonProperty("schemata")
  private Object schemata = null;

  public Schema schemata(Object schemata) {
    this.schemata = schemata;
    return this;
  }

  /**
   * Get schemata
   * @return schemata
  **/
  @ApiModelProperty(value = "")

  public Object getSchemata() {
    return schemata;
  }

  public void setSchemata(Object schemata) {
    this.schemata = schemata;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Schema schema = (Schema) o;
    return Objects.equals(this.schemata, schema.schemata) &&
        Objects.equals(this.executionTime, schema.executionTime) &&
        Objects.equals(this.wasSuccessful, schema.wasSuccessful) &&
        Objects.equals(this.error, schema.error) &&
        Objects.equals(this.statusCode, schema.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schemata, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Schema {\n");
    
    sb.append("    schemata: ").append(toIndentedString(schemata)).append("\n");
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
