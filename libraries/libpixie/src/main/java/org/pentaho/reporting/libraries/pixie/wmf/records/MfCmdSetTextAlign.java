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


package org.pentaho.reporting.libraries.pixie.wmf.records;

import org.pentaho.reporting.libraries.pixie.wmf.MfDcState;
import org.pentaho.reporting.libraries.pixie.wmf.MfRecord;
import org.pentaho.reporting.libraries.pixie.wmf.MfType;
import org.pentaho.reporting.libraries.pixie.wmf.WmfFile;

/**
 * The SetTextAlign function sets the text-alignment flags for the specified device context.
 * <p/>
 * Specifies the text alignment by using a mask of the values in the following list. Only one flag can be chosen from
 * those that affect horizontal and vertical alignment. In addition, only one of the two flags that alter the current
 * position can be chosen.
 * <p/>
 * The default values are TA_LEFT, TA_TOP, and TA_NOUPDATECP
 */
public class MfCmdSetTextAlign extends MfCmd {
  private static final int RECORD_SIZE = 1;
  private static final int POS_TEXT_ALIGNMENT = 0;

  private int textAlignMode;

  public MfCmdSetTextAlign() {
  }

  /**
   * Replays the command on the given WmfFile.
   *
   * @param file the meta file.
   */
  public void replay( final WmfFile file ) {
    final MfDcState state = file.getCurrentState();
    state.setTextAlign( textAlignMode );
  }

  /**
   * Creates a empty unintialized copy of this command implementation.
   *
   * @return a new instance of the command.
   */
  public MfCmd getInstance() {
    return new MfCmdSetTextAlign();
  }

  /**
   * Reads the command data from the given record and adjusts the internal parameters according to the data parsed.
   * <p/>
   * After the raw record was read from the datasource, the record is parsed by the concrete implementation.
   *
   * @param record the raw data that makes up the record.
   */
  public void setRecord( final MfRecord record ) {
    final int id = record.getParam( POS_TEXT_ALIGNMENT );
    setTextAlignMode( id );
  }


  /**
   * Creates a new record based on the data stored in the MfCommand.
   *
   * @return the created record.
   */
  public MfRecord getRecord()
    throws RecordCreationException {
    final MfRecord record = new MfRecord( RECORD_SIZE );
    record.setParam( POS_TEXT_ALIGNMENT, getTextAlignMode() );
    return record;
  }

  /**
   * Reads the function identifier. Every record type is identified by a function number corresponding to one of the
   * Windows GDI functions used.
   *
   * @return the function identifier.
   */
  public int getFunction() {
    return MfType.SET_TEXT_ALIGN;
  }

  public int getTextAlignMode() {
    return textAlignMode;
  }

  public void setTextAlignMode( final int id ) {
    this.textAlignMode = id;
  }

  public String toString() {
    final StringBuffer b = new StringBuffer();
    b.append( "[SET_TEXT_ALIGN] textAlign=" );
    b.append( getTextAlignMode() );
    return b.toString();
  }

  /**
   * A callback function to inform the object, that the x scale has changed and the internal coordinate values have to
   * be adjusted.
   */
  protected void scaleXChanged() {
  }

  /**
   * A callback function to inform the object, that the y scale has changed and the internal coordinate values have to
   * be adjusted.
   */
  protected void scaleYChanged() {
  }
}
