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
 * Tokens
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-13T20:52:32.591+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "tokens", "executionTime", "wasSuccessful", "error", "statusCode" })
public class Tokens extends JSONModel  {
  @JsonProperty("tokens")
  @Valid
  private Object tokens = null;

  public Tokens tokens(List<String> tokens) {
    this.tokens = tokens;
    return this;
  }

  /**
   * Get tokens
   * @return tokens
  **/
  @ApiModelProperty(value = "")

  public Object getTokens() {
    return tokens;
  }

  public void setTokens(Object tokens) {
    this.tokens = tokens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tokens tokens = (Tokens) o;
    return Objects.equals(this.tokens, tokens.tokens) &&
        Objects.equals(this.executionTime, tokens.executionTime) &&
        Objects.equals(this.wasSuccessful, tokens.wasSuccessful) &&
        Objects.equals(this.error, tokens.error) &&
        Objects.equals(this.statusCode, tokens.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokens, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tokens {\n");
    
    sb.append("    tokens: ").append(toIndentedString(tokens)).append("\n");
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
