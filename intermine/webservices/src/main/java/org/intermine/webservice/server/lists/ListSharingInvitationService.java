package org.intermine.webservice.server.lists;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.intermine.api.InterMineAPI;
import org.intermine.api.bag.SharedBagManager;
import org.intermine.api.bag.SharingInvite;
import org.intermine.api.profile.InterMineBag;
import org.intermine.api.profile.Profile;
import org.intermine.util.Emailer;
import org.intermine.web.context.InterMineContext;
import org.intermine.web.context.MailAction;
import org.intermine.webservice.JSONServiceSpring;
import org.intermine.webservice.model.ListInvitationSingle;
import org.intermine.webservice.server.Format;
import org.intermine.webservice.server.core.JSONService;
import org.intermine.webservice.server.exceptions.ResourceNotFoundException;

/**
 * Invite a user to share a list.
 * @author Alex Kalderimis
 *
 */
public class ListSharingInvitationService extends JSONServiceSpring
{

    private static final Logger LOG = Logger.getLogger(ListSharingInvitationService.class);
    private static final String EMAIL_PROPERTY = "sharing-invite";

    private String bagName;
    private String to;
    private Boolean notifyParam;

    public ListInvitationSingle getListInvitationSingle() {
        return listInvitationSingle;
    }

    private ListInvitationSingle listInvitationSingle;

    /** @param im The InterMine state object **/
    public ListSharingInvitationService(InterMineAPI im, Format format, String bagName, String to, Boolean notifyParam) {
        super(im, format);
        this.bagName = bagName;
        this.to = to;
        this.notifyParam = notifyParam;
        listInvitationSingle = new ListInvitationSingle();
    }

    @Override
    protected String getResultsKey() {
        return "invitation";
    }

    private final class UserInput
    {
        final Profile owner;
        final InterMineBag bag;
        final String invitee;
        final boolean notify;

        UserInput() {
            owner = getAuthenticatedUser();
            bag = owner.getSavedBags().get(bagName);
            if (bag == null) {
                throw new ResourceNotFoundException("You do not own a list called " + bagName);
            }
            invitee = to;
            notify = notifyParam;
        }
    }

    @Override
    protected void execute() {
        UserInput input = new UserInput();

        SharingInvite invite = SharedBagManager.inviteToShare(input.bag, input.invitee);

        listInvitationSingle.setInvitation(marshallInvite(input, invite));

        if (input.notify) {
            notifyInvitee(input, invite);
        }
    }

    private Map<String, Object> marshallInvite(UserInput input, SharingInvite invite) {
        Map<String, Object> toSerialise = new HashMap<String, Object>();
        toSerialise.put("invite-token", invite.getToken());
        toSerialise.put("list", input.bag.getName());
        toSerialise.put("invitee", input.invitee);
        return toSerialise;
    }

    private void notifyInvitee(final UserInput input, final SharingInvite invite) {
        final InterMineBag bag = invite.getBag();
        boolean queued = InterMineContext.queueMessage(new MailAction() {
            @Override
            public void act(Emailer emailer) throws Exception {
                emailer.email(
                    invite.getInvitee(), EMAIL_PROPERTY,
                    input.owner.getName(),
                    bag.getType(), bag.getName(), bag.getSize(),
                    webProperties.getProperty("webapp.baseurl"),
                    webProperties.getProperty("webapp.path"),
                    invite.getToken(),
                    webProperties.getProperty("project.title"));
            }
        });

        if (!queued) {
            LOG.error("Mail queue full, could not send message");
        }
    }

}
