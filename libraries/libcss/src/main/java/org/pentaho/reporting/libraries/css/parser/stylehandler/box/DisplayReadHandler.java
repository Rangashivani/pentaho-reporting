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


package org.pentaho.reporting.libraries.css.parser.stylehandler.box;

import org.pentaho.reporting.libraries.css.keys.box.BoxStyleKeys;
import org.pentaho.reporting.libraries.css.keys.box.DisplayModel;
import org.pentaho.reporting.libraries.css.keys.box.DisplayRole;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSCompoundValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.w3c.css.sac.LexicalUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * Creation-Date: 27.11.2005, 20:51:57
 *
 * @author Thomas Morgner
 */
public class DisplayReadHandler implements CSSCompoundValueReadHandler {
  private static class DisplayMapEntry {
    private CSSConstant model;
    private CSSConstant role;

    public DisplayMapEntry( final CSSConstant model, final CSSConstant role ) {
      this.model = model;
      this.role = role;
    }

    public CSSConstant getModel() {
      return model;
    }

    public CSSConstant getRole() {
      return role;
    }

    public boolean equals( final Object o ) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }

      final DisplayMapEntry that = (DisplayMapEntry) o;

      if ( !model.equals( that.model ) ) {
        return false;
      } else if ( !role.equals( that.role ) ) {
        return false;
      }

      return true;
    }

    public int hashCode() {
      int result = model.hashCode();
      result = 29 * result + role.hashCode();
      return result;
    }
  }

  private HashMap values;

  public DisplayReadHandler() {
    values = new HashMap();
    values.put( "inline", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.INLINE ) );
    values.put( "inline-block", new DisplayMapEntry( DisplayModel.BLOCK_INSIDE, DisplayRole.INLINE ) );
    values.put( "inline-table", new DisplayMapEntry( DisplayModel.TABLE, DisplayRole.INLINE ) );
    values.put( "ruby", new DisplayMapEntry( DisplayModel.RUBY, DisplayRole.INLINE ) );
    values.put( "block", new DisplayMapEntry( DisplayModel.BLOCK_INSIDE, DisplayRole.BLOCK ) );
    values.put( "table", new DisplayMapEntry( DisplayModel.TABLE, DisplayRole.BLOCK ) );

    values.put( "list-item", new DisplayMapEntry( DisplayModel.BLOCK_INSIDE, DisplayRole.LIST_ITEM ) );
    values.put( "run-in", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.RUN_IN ) );
    values.put( "compact", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.COMPACT ) );
    values.put( "table-cell", new DisplayMapEntry( DisplayModel.BLOCK_INSIDE, DisplayRole.TABLE_CELL ) );
    values.put( "table-caption", new DisplayMapEntry( DisplayModel.BLOCK_INSIDE, DisplayRole.TABLE_CAPTION ) );
    values.put( "table-row-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_ROW_GROUP ) );
    values
      .put( "table-header-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_HEADER_GROUP ) );
    values
      .put( "table-footer-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_FOOTER_GROUP ) );
    values.put( "table-column", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_COLUMN ) );
    values
      .put( "table-column-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_COLUMN_GROUP ) );
    values.put( "table-row", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.TABLE_ROW ) );
    values.put( "ruby-base", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.RUBY_BASE ) );
    values.put( "ruby-text", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.RUBY_TEXT ) );
    values.put( "ruby-base-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.RUBY_BASE_GROUP ) );
    values.put( "ruby-text-group", new DisplayMapEntry( DisplayModel.INLINE_INSIDE, DisplayRole.RUBY_TEXT_GROUP ) );
    values.put( "absolute", new DisplayMapEntry( DisplayModel.CANVAS, DisplayRole.CANVAS ) );
  }

  /**
   * Parses the LexicalUnit and returns a map of (StyleKey, CSSValue) pairs.
   *
   * @param unit
   * @return
   */
  public Map createValues( LexicalUnit unit ) {
    if ( unit.getLexicalUnitType() != LexicalUnit.SAC_IDENT ) {
      return null;
    }

    final Map map = new HashMap();
    final String key = unit.getStringValue().toLowerCase();
    if ( key.equals( "none" ) ) {
      map.put( BoxStyleKeys.DISPLAY_ROLE, DisplayRole.NONE );
      return map;
    }
    final DisplayMapEntry entry = (DisplayMapEntry)
      values.get( key );
    if ( entry == null ) {
      return null;
    }

    map.put( BoxStyleKeys.DISPLAY_ROLE, entry.getRole() );
    map.put( BoxStyleKeys.DISPLAY_MODEL, entry.getModel() );
    return map;
  }

  public StyleKey[] getAffectedKeys() {
    return new StyleKey[] {
      BoxStyleKeys.DISPLAY_ROLE,
      BoxStyleKeys.DISPLAY_MODEL
    };
  }
}
