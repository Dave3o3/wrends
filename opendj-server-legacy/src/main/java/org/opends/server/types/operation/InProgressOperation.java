/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2006-2008 Sun Microsystems, Inc.
 * Portions Copyright 2011-2016 ForgeRock AS.
 */
package org.opends.server.types.operation;
import org.forgerock.i18n.LocalizableMessage;



import java.util.List;

import org.opends.server.types.*;
import org.forgerock.opendj.ldap.DN;
import org.forgerock.opendj.ldap.ResultCode;
import org.forgerock.i18n.LocalizableMessageBuilder;


/**
 * This class defines a set of methods that are available for use by
 * plugins for operations that are currently in the middle of their
 * "core" processing (e.g., for examining search result entries or
 * references before they are sent to the client).  Note that this
 * interface is intended only to define an API for use by plugins and
 * is not intended to be implemented by any custom classes.
 */
@org.opends.server.types.PublicAPI(
     stability=org.opends.server.types.StabilityLevel.UNCOMMITTED,
     mayInstantiate=false,
     mayExtend=false,
     mayInvoke=true)
public interface InProgressOperation
       extends PluginOperation
{
  /**
   * Adds the provided control to the set of controls to include in
   * the response to the client.
   *
   * @param  control  The control to add to the set of controls to
   *                  include in the response to the client.
   */
  void addResponseControl(Control control);



  /**
   * Removes the provided control from the set of controls to include
   * in the response to the client.
   *
   * @param  control  The control to remove from the set of controls
   *                  to include in the response to the client.
   */
  void removeResponseControl(Control control);



  /**
   * Retrieves the result code for this operation.
   *
   * @return  The result code associated for this operation, or
   *          <CODE>UNDEFINED</CODE> if the operation has not yet
   *          completed.
   */
  ResultCode getResultCode();



  /**
   * Specifies the result code for this operation.
   *
   * @param  resultCode  The result code for this operation.
   */
  void setResultCode(ResultCode resultCode);



  /**
   * Retrieves the error message for this operation.  Its contents may
   * be altered by the caller.
   *
   * @return  The error message for this operation.
   */
  LocalizableMessageBuilder getErrorMessage();



  /**
   * Specifies the error message for this operation.
   *
   * @param  errorMessage  The error message for this operation.
   */
  void setErrorMessage(LocalizableMessageBuilder errorMessage);



  /**
   * Appends the provided message to the error message buffer.  If the
   * buffer has not yet been created, then this will create it first
   * and then add the provided message.
   *
   * @param  message  The message to append to the error message
   */
  void appendErrorMessage(LocalizableMessage message);



  /**
   * Retrieves the matched DN for this operation.
   *
   * @return  The matched DN for this operation, or <CODE>null</CODE>
   *          if the operation has not yet completed or does not have
   *          a matched DN.
   */
  DN getMatchedDN();



  /**
   * Specifies the matched DN for this operation.
   *
   * @param  matchedDN  The matched DN for this operation.
   */
  void setMatchedDN(DN matchedDN);



  /**
   * Retrieves the set of referral URLs for this operation.  Its
   * contents must not be altered by the caller.
   *
   * @return  The set of referral URLs for this operation, or
   *          <CODE>null</CODE> if the operation is not yet complete
   *          or does not have a set of referral URLs.
   */
  List<String> getReferralURLs();



  /**
   * Specifies the set of referral URLs for this operation.
   *
   * @param  referralURLs  The set of referral URLs for this
   *                       operation.
   */
  void setReferralURLs(List<String> referralURLs);



  /**
   * Sets the response elements for this operation based on the
   * information contained in the provided
   * <CODE>DirectoryException</CODE> object.
   *
   * @param  directoryException  The exception containing the
   *                             information to use for the response
   *                             elements.
   */
  void setResponseData(DirectoryException directoryException);



  /**
   * Retrieves the authorization DN for this operation.  In many
   * cases, it will be the same as the DN of the authenticated user
   * for the underlying connection, or the null DN if no
   * authentication has been performed on that connection.  However,
   * it may be some other value if special processing has been
   * requested (e.g., the operation included a proxied authorization
   * control).
   *
   * @return  The authorization DN for this operation.
   */
  DN getAuthorizationDN();



  /**
   * Returns an unmodifiable list containing the additional log items for this
   * operation, which should be written to the log but not included in the
   * response to the client.
   *
   * @return An unmodifiable list containing the additional log items for this
   *         operation.
   */
  List<AdditionalLogItem> getAdditionalLogItems();



  /**
   * Adds an additional log item to this operation, which should be written to
   * the log but not included in the response to the client. This method may not
   * be called by post-response plugins.
   *
   * @param item
   *          The additional log item for this operation.
   */
  void addAdditionalLogItem(AdditionalLogItem item);
}

