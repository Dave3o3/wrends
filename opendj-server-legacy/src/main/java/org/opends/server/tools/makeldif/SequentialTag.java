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
package org.opends.server.tools.makeldif;
import org.forgerock.i18n.LocalizableMessage;



import java.util.List;

import org.opends.server.types.InitializationException;

import static org.opends.messages.ToolMessages.*;



/**
 * This class defines a tag that is used to include a sequentially-incrementing
 * integer in the generated values.
 */
public class SequentialTag
       extends Tag
{
  /** Indicates whether to reset for each parent. */
  private boolean resetOnNewParents;

  /** The initial value in the sequence. */
  private int initialValue;

  /** The next value in the sequence. */
  private int nextValue;



  /** Creates a new instance of this sequential tag. */
  public SequentialTag()
  {
    // No implementation required.
  }



  /**
   * Retrieves the name for this tag.
   *
   * @return  The name for this tag.
   */
  @Override
  public String getName()
  {
    return "Sequential";
  }



  /**
   * Indicates whether this tag is allowed for use in the extra lines for
   * branches.
   *
   * @return  <CODE>true</CODE> if this tag may be used in branch definitions,
   *          or <CODE>false</CODE> if not.
   */
  @Override
  public boolean allowedInBranch()
  {
    return true;
  }



  /**
   * Performs any initialization for this tag that may be needed while parsing
   * a branch definition.
   *
   * @param  templateFile  The template file in which this tag is used.
   * @param  branch        The branch in which this tag is used.
   * @param  arguments     The set of arguments provided for this tag.
   * @param  lineNumber    The line number on which this tag appears in the
   *                       template file.
   * @param  warnings      A list into which any appropriate warning messages
   *                       may be placed.
   *
   * @throws  InitializationException  If a problem occurs while initializing
   *                                   this tag.
   */
  @Override
  public void initializeForBranch(TemplateFile templateFile, Branch branch,
                                  String[] arguments, int lineNumber,
                                  List<LocalizableMessage> warnings)
         throws InitializationException
  {
    initializeInternal(templateFile, arguments, lineNumber);
  }



  /**
   * Performs any initialization for this tag that may be needed while parsing
   * a template definition.
   *
   * @param  templateFile  The template file in which this tag is used.
   * @param  template      The template in which this tag is used.
   * @param  arguments     The set of arguments provided for this tag.
   * @param  lineNumber    The line number on which this tag appears in the
   *                       template file.
   * @param  warnings      A list into which any appropriate warning messages
   *                       may be placed.
   *
   * @throws  InitializationException  If a problem occurs while initializing
   *                                   this tag.
   */
  @Override
  public void initializeForTemplate(TemplateFile templateFile,
                                    Template template, String[] arguments,
                                    int lineNumber, List<LocalizableMessage> warnings)
         throws InitializationException
  {
    initializeInternal(templateFile, arguments, lineNumber);
  }



  /**
   * Performs any initialization for this tag that may be needed for this tag.
   *
   * @param  templateFile  The template file in which this tag is used.
   * @param  arguments     The set of arguments provided for this tag.
   * @param  lineNumber    The line number on which this tag appears in the
   *                       template file.
   *
   * @throws  InitializationException  If a problem occurs while initializing
   *                                   this tag.
   */
  private void initializeInternal(TemplateFile templateFile, String[] arguments,
                                  int lineNumber)
          throws InitializationException
  {
    switch (arguments.length)
    {
      case 0:
        initialValue      = 0;
        nextValue         = 0;
        resetOnNewParents = true;
        break;
      case 1:
        try
        {
          initialValue = Integer.parseInt(arguments[0]);
        }
        catch (NumberFormatException nfe)
        {
          LocalizableMessage message = ERR_MAKELDIF_TAG_CANNOT_PARSE_AS_INTEGER.get(
              arguments[0], getName(), lineNumber);
          throw new InitializationException(message);
        }

        nextValue         = initialValue;
        resetOnNewParents = true;
        break;
      case 2:
        try
        {
          initialValue = Integer.parseInt(arguments[0]);
        }
        catch (NumberFormatException nfe)
        {
          LocalizableMessage message = ERR_MAKELDIF_TAG_CANNOT_PARSE_AS_INTEGER.get(
              arguments[0], getName(), lineNumber);
          throw new InitializationException(message);
        }

        if (arguments[1].equalsIgnoreCase("true"))
        {
          resetOnNewParents = true;
        }
        else if (arguments[1].equalsIgnoreCase("false"))
        {
          resetOnNewParents = false;
        }
        else
        {
          LocalizableMessage message = ERR_MAKELDIF_TAG_CANNOT_PARSE_AS_BOOLEAN.get(
              arguments[1], getName(), lineNumber);
          throw new InitializationException(message);
        }

        nextValue = initialValue;
        break;
      default:
        LocalizableMessage message = ERR_MAKELDIF_TAG_INVALID_ARGUMENT_RANGE_COUNT.get(
            getName(), lineNumber, 0, 2, arguments.length);
        throw new InitializationException(message);
    }
  }



  /**
   * Performs any initialization for this tag that may be needed when starting
   * to generate entries below a new parent.
   *
   * @param  parentEntry  The entry below which the new entries will be
   *                      generated.
   */
  @Override
  public void initializeForParent(TemplateEntry parentEntry)
  {
    if (resetOnNewParents)
    {
      nextValue = initialValue;
    }
  }



  /**
   * Generates the content for this tag by appending it to the provided tag.
   *
   * @param  templateEntry  The entry for which this tag is being generated.
   * @param  templateValue  The template value to which the generated content
   *                        should be appended.
   *
   * @return  The result of generating content for this tag.
   */
  @Override
  public TagResult generateValue(TemplateEntry templateEntry,
                                 TemplateValue templateValue)
  {
    templateValue.getValue().append(nextValue++);
    return TagResult.SUCCESS_RESULT;
  }
}

