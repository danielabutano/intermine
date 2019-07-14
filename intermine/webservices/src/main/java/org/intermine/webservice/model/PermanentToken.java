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
 * PermanentToken
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-13T20:52:32.591+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "token", "executionTime", "wasSuccessful", "error", "statusCode" })
public class PermanentToken extends JSONModel  {
  @JsonProperty("token")
  private Object token = null;

  public PermanentToken token(Object token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
  **/
  @ApiModelProperty(value = "")

  public Object getToken() {
    return token;
  }

  public void setToken(Object token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermanentToken permanentToken = (PermanentToken) o;
    return Objects.equals(this.token, permanentToken.token) &&
        Objects.equals(this.executionTime, permanentToken.executionTime) &&
        Objects.equals(this.wasSuccessful, permanentToken.wasSuccessful) &&
        Objects.equals(this.error, permanentToken.error) &&
        Objects.equals(this.statusCode, permanentToken.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermanentToken {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
