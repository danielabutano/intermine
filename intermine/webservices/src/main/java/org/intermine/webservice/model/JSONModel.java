package org.intermine.webservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * A generic JSON data model for json specific requests.
 */
@Validated
public class JSONModel {

    @JsonProperty("executionTime")
    protected String executionTime = null;

    @JsonProperty("wasSuccessful")
    protected Boolean wasSuccessful = null;

    @JsonProperty("error")
    protected String error = null;

    @JsonProperty("statusCode")
    protected Integer statusCode = null;

    public JSONModel executionTime(String executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    /**
     * Get executionTime
     * @return executionTime
     **/
    @ApiModelProperty(value = "")

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public JSONModel wasSuccessful(Boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
        return this;
    }

    /**
     * Get wasSuccessful
     * @return wasSuccessful
     **/
    @ApiModelProperty(value = "")

    public Boolean isWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(Boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public JSONModel error(String error) {
        this.error = error;
        return this;
    }

    /**
     * Get error
     * @return error
     **/
    @ApiModelProperty(value = "")

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public JSONModel statusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Get statusCode
     * @return statusCode
     **/
    @ApiModelProperty(value = "")

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
