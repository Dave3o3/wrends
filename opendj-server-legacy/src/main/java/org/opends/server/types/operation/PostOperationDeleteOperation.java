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
 * Portions Copyright 2014-2016 ForgeRock AS.
 */
package org.opends.server.types.operation;



import org.forgerock.opendj.ldap.ByteString;
import org.forgerock.opendj.ldap.DN;
import org.opends.server.types.Entry;



/**
 * This class defines a set of methods that are available for use by
 * post-operation plugins for delete operations.  Note that this
 * interface is intended only to define an API for use by plugins and
 * is not intended to be implemented by any custom classes.
 */
@org.opends.server.types.PublicAPI(
     stability=org.opends.server.types.StabilityLevel.UNCOMMITTED,
     mayInstantiate=false,
     mayExtend=false,
     mayInvoke=true)
public interface PostOperationDeleteOperation
       extends PostOperationOperation
{
  /**
   * Retrieves the raw, unprocessed entry DN as included in the client
   * request.
   *
   * @return  The raw, unprocessed entry DN as included in the client
   *          request.
   */
  ByteString getRawEntryDN();



  /**
   * Retrieves the DN of the entry to delete.
   *
   * @return  The DN of the entry to delete.
   */
  DN getEntryDN();



  /**
   * Retrieves the entry to be deleted.
   *
   * @return  The entry to be deleted.
   */
  Entry getEntryToDelete();
}

