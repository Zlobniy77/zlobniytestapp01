package com.zlobniy.twilio.survey.models;

public class Survey {

  private Long id;

  private String phone;

  private boolean done;

  private int index;

  private Response[] responses;

  // Constructors
  public Survey(String phone) {
    this.responses = new Response[1];
    this.done = false;
    this.phone = phone;
    this.index = 0;
  }
  
  public Survey(Survey anotherSurvey) {
    System.arraycopy(anotherSurvey.responses, 0, this.responses, 0, anotherSurvey.responses.length);
    this.id = anotherSurvey.id;
    this.phone = anotherSurvey.phone;
    this.index = anotherSurvey.index;
    this.done = anotherSurvey.done;
  }
  
  public Survey() {
    this("+0000000000");
  }

  // Accessors
  public Long getId() {
    return id;
  }

  public String getPhone() {
    return phone;
  }

  public Response[] getResponses() {
    return responses;
  }

  public boolean isDone() {
    if (index > 1) {
      this.markDone();
    }
    return done;

  }

  public int getIndex() {
    return index;
  }

  // Mutators
  public void appendResponse(Response response) {
    if (!this.isDone()) {
      this.responses[index++] = response;
    }
  }

  public void markDone() {
    this.done = true;
  }

  public void setId( Long id ){
    this.id = id;
  }

  public void setPhone( String phone ){
    this.phone = phone;
  }

  public void setDone( boolean done ){
    this.done = done;
  }

  public void setIndex( int index ){
    this.index = index;
  }

  public void setResponses( Response[] responses ){
    this.responses = responses;
  }
}
