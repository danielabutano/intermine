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
 * IdResolutionPost
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T19:02:56.453+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "uid", "executionTime", "wasSuccessful", "error", "statusCode" })
public class IdResolutionPost extends JSONModel  {
  @JsonProperty("uid")
  private String uid = null;

  public IdResolutionPost uid(String uid) {
    this.uid = uid;
    return this;
  }

  /**
   * Get uid
   * @return uid
  **/
  @ApiModelProperty(value = "")

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdResolutionPost idResolutionPost = (IdResolutionPost) o;
    return Objects.equals(this.uid, idResolutionPost.uid) &&
        Objects.equals(this.executionTime, idResolutionPost.executionTime) &&
        Objects.equals(this.wasSuccessful, idResolutionPost.wasSuccessful) &&
        Objects.equals(this.error, idResolutionPost.error) &&
        Objects.equals(this.statusCode, idResolutionPost.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdResolutionPost {\n");
    
    sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
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
