/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.reporting.libraries.css.parser.stylehandler.text;

import org.pentaho.reporting.libraries.css.keys.text.TextStyleKeys;
import org.pentaho.reporting.libraries.css.model.StyleKey;

/**
 * Creation-Date: 28.11.2005, 20:44:02
 *
 * @author Thomas Morgner
 */
public class LetterSpacingLimitReadHandler extends SpacingLimitReadHandler {

  public LetterSpacingLimitReadHandler() {
  }

  protected StyleKey getMinimumKey() {
    return TextStyleKeys.X_MIN_LETTER_SPACING;
  }

  protected StyleKey getMaximumKey() {
    return TextStyleKeys.X_MAX_LETTER_SPACING;
  }

  protected StyleKey getOptimumKey() {
    return TextStyleKeys.X_OPTIMUM_LETTER_SPACING;
  }

}
