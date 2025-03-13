package org.example;

class GroupNotExistsException extends Exception {
  public GroupNotExistsException(String line) {
    super("GroupNotExistsException: Group does not exist. ## (" + line);
  }
}

class GroupThresholdException extends Exception {
  public GroupThresholdException(String line) {
    super("GroupThresholdException: Group cannot have more than 10 members. ## (" + line);
  }
}

class GuideExistsException extends Exception {
  public GuideExistsException(String line) {
    super("GuideExistsException: Guide already exists. ## (" + line);
  }
}

class GuideTypeException extends Exception {
  public GuideTypeException(String line) {
    super("GuideTypeException: Guide must be a professor. ## (" + line);
  }
}

class PersonNotExistsException extends Exception {
  public PersonNotExistsException(String line) {
    super("PersonNotExistsException: Person was not found in the group. ## (" + line);
  }
}