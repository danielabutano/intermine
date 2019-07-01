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
 * WhoAmI
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-01T19:07:21.543+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "user", "executionTime", "wasSuccessful", "error", "statusCode" })
public class WhoAmI extends JSONModel  {
  @JsonProperty("user")
  private Object user = null;

  public WhoAmI user(Object user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")

  public Object getUser() {
    return user;
  }

  public void setUser(Object user) {
    this.user = user;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhoAmI whoAmI = (WhoAmI) o;
    return Objects.equals(this.user, whoAmI.user) &&
        Objects.equals(this.executionTime, whoAmI.executionTime) &&
        Objects.equals(this.wasSuccessful, whoAmI.wasSuccessful) &&
        Objects.equals(this.error, whoAmI.error) &&
        Objects.equals(this.statusCode, whoAmI.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhoAmI {\n");
    
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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
