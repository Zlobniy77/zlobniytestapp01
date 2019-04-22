package com.zlobniy.domain.implementation.view;

public class IncomingCall {

  private String from;
  private String recordingUrl;
  private String digits;
  private String transcriptionText;

  // Constructor
  public IncomingCall(String from, String recordingUrl, String digits, String transcriptionText) {
    this.from = from;
    this.recordingUrl = recordingUrl;
    this.digits = digits;
    this.transcriptionText = transcriptionText;
  }

  public IncomingCall() {
    this("+0000000000", null, null, null);
  }

  // Accessors
  public String getFrom() {
    return from;
  }

  public String getInput() {
    if (recordingUrl != null) {
      return recordingUrl;
    } else {
      return digits;
    }
  }

  public String getTranscriptionText() {
    return transcriptionText;
  }

  public void setFrom( String from ) {
    this.from = from;
  }

  public String getRecordingUrl() {
    return recordingUrl;
  }

  public void setRecordingUrl( String recordingUrl ) {
    this.recordingUrl = recordingUrl;
  }

  public String getDigits() {
    return digits;
  }

  public void setDigits( String digits ) {
    this.digits = digits;
  }

  public void setTranscriptionText( String transcriptionText ) {
    this.transcriptionText = transcriptionText;
  }
}
