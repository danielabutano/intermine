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
 * ListInvitationMultiple
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-21T15:10:07.571+05:30[Asia/Kolkata]")
@JsonPropertyOrder({ "invitation", "executionTime", "wasSuccessful", "error", "statusCode" })
public class ListInvitationMultiple extends JSONModel  {
  @JsonProperty("invitation")
  @Valid
  private List<Object> invitation = null;

  public ListInvitationMultiple invitation(List<Object> invitation) {
    this.invitation = invitation;
    return this;
  }

  public ListInvitationMultiple addInvitationItem(Object invitationItem) {
    if (this.invitation == null) {
      this.invitation = new ArrayList<Object>();
    }
    this.invitation.add(invitationItem);
    return this;
  }

  /**
   * Get invitation
   * @return invitation
  **/
  @ApiModelProperty(value = "")

  public List<Object> getInvitation() {
    return invitation;
  }

  public void setInvitation(List<Object> invitation) {
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
    ListInvitationMultiple listInvitationMultiple = (ListInvitationMultiple) o;
    return Objects.equals(this.invitation, listInvitationMultiple.invitation) &&
        Objects.equals(this.executionTime, listInvitationMultiple.executionTime) &&
        Objects.equals(this.wasSuccessful, listInvitationMultiple.wasSuccessful) &&
        Objects.equals(this.error, listInvitationMultiple.error) &&
        Objects.equals(this.statusCode, listInvitationMultiple.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invitation, executionTime, wasSuccessful, error, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListInvitationMultiple {\n");
    
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
