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
 * ListSharingPost
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T15:10:07.571+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "share", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListSharingPost extends JSONModel  {
  @JsonProperty("share")
  private Object share = null;

  public ListSharingPost share(Object share) {
    this.share = share;
    return this;
  }

  /**
   * Get share
   * @return share
  **/
  @ApiModelProperty(value = "")

  public Object getShare() {
    return share;
  }

  public void setShare(Object share) {
    this.share = share;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListSharingPost listSharingPost = (ListSharingPost) o;
    return Objects.equals(this.share, listSharingPost.share) &&
        Objects.equals(this.executionTime, listSharingPost.executionTime) &&
        Objects.equals(this.wasSuccessful, listSharingPost.wasSuccessful) &&
        Objects.equals(this.error, listSharingPost.error) &&
        Objects.equals(this.statusCode, listSharingPost.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(share, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListSharingPost {\n");
    
    sb.append("    share: ").append(toIndentedString(share)).append("\n");
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
