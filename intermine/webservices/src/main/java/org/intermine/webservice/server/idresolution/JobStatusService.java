package org.intermine.webservice.server.idresolution;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.api.InterMineAPI;
import org.intermine.api.idresolution.IDResolver;
import org.intermine.api.idresolution.Job;
import org.intermine.api.idresolution.Job.JobStatus;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.IdResolutionStatus;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;

/** @author Alex Kalderimis **/
public class JobStatusService extends JSONServiceSpring
{

    private final String jobId;

    public IdResolutionStatus getIdResolutionStatus() {
        return idResolutionStatus;
    }

    private IdResolutionStatus idResolutionStatus;

    /**
     * Construct a handler for this request.
     * @param im The InterMine state object.
     * @param jobId The id of the job.
     */
    public JobStatusService(InterMineAPI im, Format format, String jobId) {
        super(im, format);
        this.jobId = jobId;
        idResolutionStatus = new IdResolutionStatus();
    }

    @Override
    protected void execute() throws Exception {
        Job job = IDResolver.getInstance().getJobById(jobId);
        if (job != null) {
            if (job.getStatus() == JobStatus.ERROR) {
                idResolutionStatus.setMessage(job.getError().getMessage());
            }
            idResolutionStatus.setStatus(job.getStatus().name());
        } else {
            throw new ResourceNotFoundException("No such job: " + jobId);
        }
    }

    @Override
    protected String getResultsKey() {
        return "status";
    }

}
