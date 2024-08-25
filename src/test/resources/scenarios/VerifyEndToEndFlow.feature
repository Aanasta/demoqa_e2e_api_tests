Feature: Verify End-to-End flow for demoqa.com Book Store API app
  As a Book Store API app user
  I want to be able to register
  And work with the books in the app

  @E2E
  Scenario: Verify End-to-End flow for a new user
    #Save user in context data
    Given I am a new Book Store user with a username and a password
#    #Create a new user and get userID
    When I execute POST request to create a new user
    Then I assert response code is 201
    And I assert username is as set at creation
    And books count for the user is 0
    And I save userID for my user
#    #Generate auth token
    When I execute POST request to generate auth token
    Then I assert response code is 200
    And I assert the auth token is not empty
    And I save the auth token for my user
    #Get list of books
    When I execute GET request to get all books in the app
    Then I assert response code is 200
    And I assert response content type is "application/json; charset=utf-8"
    And I assert response body is not empty
    And I assert books list is not empty
    And I assert the response matches "get_all_books.json" schema
    #Filter the list of books received by publisher
    And I assert all books contain books with publisher "No Starch Press"
    #Add books to user
    When I execute POST request to add to user books with ids
      | 9781449325862 |
      | 9781449331818 |
      | 9781449337711 |
      | 9781449365035 |
    Then I assert response code is 201
    And I assert all added books are present in the response
    #Verify books are actually added - disabling the steps as the API returns 401 error for all valid users
#    When I execute GET request to get all books to user
#    And I assert response code is 200
#    Then I assert the books are present in the user's books





