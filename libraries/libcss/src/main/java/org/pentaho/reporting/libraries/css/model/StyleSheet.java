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


package org.pentaho.reporting.libraries.css.model;

import org.pentaho.reporting.libraries.base.util.Empty;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A CSS stylesheet. Unlike the W3C stylesheet classes, this class is a minimal set of attributes, designed with
 * usablity and performance in mind.
 * <p/>
 * Stylesheets are resolved by looking at the elements. For the sake of simplicity, stylesheet objects itself do not
 * hold references to their parent stylesheets.
 * <p/>
 * The W3C media list is omited - this library assumes the visual/print media. The media would have been specified in
 * the document anyway, so we do not care.
 * <p/>
 * This class is a union of the W3C CSSStyleSheet and the CSSStyleRuleList. It makes no sense to separate them in this
 * context.
 *
 * @author Thomas Morgner
 */
public class StyleSheet implements Cloneable, Serializable {
  private transient ResourceManager resourceManager;
  private transient Map roNamespaces;

  private boolean readOnly;
  private ResourceKey source;
  private ArrayList rules;
  private ArrayList styleSheets;
  private HashMap namespaces;
  private StyleKeyRegistry styleKeyRegistry;
  private static final String[] EMPTY_STRINGS = new String[ 0 ];

  public StyleSheet() {
    this.styleKeyRegistry = StyleKeyRegistry.getRegistry();
  }

  public StyleKeyRegistry getStyleKeyRegistry() {
    return styleKeyRegistry;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  protected void setReadOnly( final boolean readOnly ) {
    this.readOnly = readOnly;
  }

  public ResourceKey getSource() {
    return source;
  }

  public void setSource( final ResourceKey href ) {
    if ( isReadOnly() ) {
      throw new IllegalStateException();
    }
    this.source = href;
  }

  public void setResourceManager( final ResourceManager resourceManager ) {
    this.resourceManager = resourceManager;
  }

  public ResourceManager getResourceManager() {
    if ( resourceManager == null ) {
      resourceManager = new ResourceManager();
      resourceManager.registerDefaults();
    }
    return resourceManager;
  }

  public void addRule( final StyleRule rule ) {
    if ( isReadOnly() ) {
      throw new IllegalStateException();
    }
    if ( rules == null ) {
      rules = new ArrayList();
    }
    rules.add( rule );
  }

  public void insertRule( final int index, final StyleRule rule ) {
    if ( isReadOnly() ) {
      throw new IllegalStateException();
    }
    if ( rules == null ) {
      rules = new ArrayList();
    }
    rules.add( index, rule );
  }

  public void deleteRule( final int index ) {
    if ( isReadOnly() ) {
      throw new IllegalStateException();
    }
    if ( rules == null ) {
      throw new IndexOutOfBoundsException();
    }
    rules.remove( index );
  }

  public int getRuleCount() {
    if ( rules == null ) {
      return 0;
    }
    return rules.size();
  }

  public StyleRule getRule( final int index ) {
    if ( rules == null ) {
      throw new IndexOutOfBoundsException();
    }
    return (StyleRule) rules.get( index );
  }

  public void addStyleSheet( final StyleSheet styleSheet ) {
    if ( styleSheets == null ) {
      styleSheets = new ArrayList();
    }
    styleSheets.add( styleSheet );
  }

  public int getStyleSheetCount() {
    if ( styleSheets == null ) {
      return 0;
    }
    return styleSheets.size();
  }

  public StyleSheet getStyleSheet( final int index ) {
    if ( styleSheets == null ) {
      throw new IndexOutOfBoundsException();
    }
    return (StyleSheet) styleSheets.get( index );
  }

  public void removeStyleSheet( final StyleSheet styleSheet ) {
    if ( styleSheets == null ) {
      throw new IndexOutOfBoundsException();
    }
    styleSheets.remove( styleSheet );
  }

  public void addNamespace( final String prefix, final String uri ) {
    if ( isReadOnly() ) {
      throw new IllegalStateException();
    }
    if ( prefix == null ) {
      throw new NullPointerException();
    }
    if ( uri == null ) {
      throw new NullPointerException();
    }
    if ( namespaces == null ) {
      namespaces = new HashMap();
    }
    namespaces.put( prefix, uri );
    roNamespaces = null;
  }

  public String getNamespaceURI( final String prefix ) {
    if ( namespaces == null ) {
      return null;
    }
    return (String) namespaces.get( prefix );
  }

  public String[] getNamespacePrefixes() {
    if ( namespaces == null ) {
      return EMPTY_STRINGS;
    }
    return (String[]) namespaces.keySet().toArray( new String[ namespaces.size() ] );
  }

  public Map getNamespaces() {
    if ( namespaces == null ) {
      return Empty.MAP;
    }
    if ( roNamespaces == null ) {
      roNamespaces = Collections.unmodifiableMap( namespaces );
    }
    return roNamespaces;
  }

  public Object clone()
    throws CloneNotSupportedException {
    final StyleSheet styleSheet = (StyleSheet) super.clone();
    // todo: Implement the cloneable hierarchy ..
    return styleSheet;
  }
}
