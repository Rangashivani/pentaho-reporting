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


package gui;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.function.ConvertToNumberExpression;
import org.pentaho.reporting.engine.classic.core.metadata.AbstractMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.DefaultExpressionMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.DefaultExpressionPropertyMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.ExpressionMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.ExpressionPropertyMetaData;
import org.pentaho.reporting.engine.classic.core.metadata.ExpressionRegistry;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.xmlns.common.ParserUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

public class EditableExpressionMetaData extends AbstractEditableMetaData {
  private EditableExpressionPropertyMetaData[] propertyMetaDatas;

  public EditableExpressionMetaData( final DefaultExpressionMetaData backend ) {
    super( backend );
    final ExpressionPropertyMetaData[] datas = backend.getPropertyDescriptions();
    this.propertyMetaDatas = new EditableExpressionPropertyMetaData[ datas.length ];
    for ( int i = 0; i < datas.length; i++ ) {
      final DefaultExpressionPropertyMetaData data = (DefaultExpressionPropertyMetaData) datas[ i ];
      propertyMetaDatas[ i ] = new EditableExpressionPropertyMetaData( data );
    }
  }

  public void sort( Locale locale ) {
    Arrays.sort( propertyMetaDatas, new GroupedMetaDataComparator( locale ) );
  }

  public String printBundleText( final Locale locale ) {
    final AbstractMetaData backend = getBackend();
    final String[] properties =
      { "display-name", "short-name", "icon", "selected-icon", "grouping", "grouping.ordinal",
        "ordinal", "description", "deprecated" };

    final StringWriter writer = new StringWriter();
    PrintWriter p = new PrintWriter( writer );
    for ( int i = 0; i < properties.length; i++ ) {
      final String property = properties[ i ];
      final String attribute = getMetaAttribute( property, locale );
      if ( StringUtils.isEmpty( attribute ) ) {
        if ( "icon".equals( property ) ||
          "selected-icon".equals( property ) ||
          "short-name".equals( property ) ) {
          continue;
        }
      }
      PropertyHelper.saveConvert( backend.getKeyPrefix(), PropertyHelper.ESCAPE_KEY, p );
      PropertyHelper.saveConvert( backend.getName(), PropertyHelper.ESCAPE_KEY, p );
      p.print( '.' );
      PropertyHelper.saveConvert( property, PropertyHelper.ESCAPE_KEY, p );
      p.print( '=' );
      PropertyHelper.saveConvert( attribute, PropertyHelper.ESCAPE_VALUE, p );
      p.println();
    }
    p.println();

    for ( int i = 0; i < propertyMetaDatas.length; i++ ) {
      final EditableExpressionPropertyMetaData metaData = propertyMetaDatas[ i ];
      p.println( metaData.printBundleText( locale ) );
      p.println();
    }
    p.close();
    return writer.toString();
  }

  public boolean isModified() {
    for ( int i = 0; i < propertyMetaDatas.length; i++ ) {
      final EditableExpressionPropertyMetaData data = propertyMetaDatas[ i ];
      if ( data.isModified() ) {
        return true;
      }
    }
    return super.isModified();
  }

  public boolean isValid( final Locale locale, final boolean deepCheck ) {
    if ( deepCheck ) {
      for ( int i = 0; i < propertyMetaDatas.length; i++ ) {
        final EditableExpressionPropertyMetaData data = propertyMetaDatas[ i ];
        if ( data.isValid( locale, deepCheck ) == false ) {
          return false;
        }
      }
    }

    final String[] textProperties = { "display-name", "grouping" };
    for ( int i = 0; i < textProperties.length; i++ ) {
      final String property = textProperties[ i ];
      if ( StringUtils.isEmpty( getMetaAttribute( property, locale ) ) ) {
        return false;
      }
    }

    final String[] optionalTextProperties = { "description", "deprecated" };
    for ( int i = 0; i < optionalTextProperties.length; i++ ) {
      final String property = optionalTextProperties[ i ];
      if ( getMetaAttribute( property, locale ) == null ) {
        return false;
      }
    }


    final String[] integerProperties = { "grouping.ordinal", "ordinal" };
    for ( int i = 0; i < integerProperties.length; i++ ) {
      final String property = integerProperties[ i ];
      if ( ParserUtil.parseInt( getMetaAttribute( property, locale ), Integer.MAX_VALUE ) == Integer.MAX_VALUE ) {
        return false;
      }
    }
    return true;
  }

  public static void main( String[] args ) {
    ClassicEngineBoot.getInstance().start();
    final ExpressionMetaData[] allExpressionMetaDatas = ExpressionRegistry.getInstance().getAllExpressionMetaDatas();
    final ExpressionMetaData data =
      ExpressionRegistry.getInstance().getExpressionMetaData( ConvertToNumberExpression.class.getName() );
    System.out.println(
      new EditableExpressionMetaData( (DefaultExpressionMetaData) data ).isValid( Locale.getDefault(), false ) );
  }

  public EditableExpressionPropertyMetaData[] getProperties() {
    return propertyMetaDatas;
  }
}
