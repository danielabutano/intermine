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
 * ListInvitationSingle
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T15:10:07.571+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "invitation", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListInvitationSingle extends JSONModel  {
  @JsonProperty("invitation")
  private Object invitation = null;

  public ListInvitationSingle invitation(Object invitation) {
    this.invitation = invitation;
    return this;
  }

  /**
   * Get invitation
   * @return invitation
  **/
  @ApiModelProperty(value = "")

  public Object getInvitation() {
    return invitation;
  }

  public void setInvitation(Object invitation) {
    this.invitation = invitation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListInvitationSingle listInvitationSingle = (ListInvitationSingle) o;
    return Objects.equals(this.invitation, listInvitationSingle.invitation) &&
        Objects.equals(this.executionTime, listInvitationSingle.executionTime) &&
        Objects.equals(this.wasSuccessful, listInvitationSingle.wasSuccessful) &&
        Objects.equals(this.error, listInvitationSingle.error) &&
        Objects.equals(this.statusCode, listInvitationSingle.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invitation, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListInvitationSingle {\n");
    
    sb.append("    invitation: ").append(toIndentedString(invitation)).append("\n");
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
