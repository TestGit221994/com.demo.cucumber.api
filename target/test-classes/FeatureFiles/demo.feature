Feature: Activities
  Description : Verify Activities API

  @Demo
  Scenario Outline: Verify Activities GET API
    Given I want to create test case "<TestName>"
    When I hit the "<RequestMethod>" method of get all activities api with "<URL>" end point
    Then Validate the status code in api response "<StatusCode>"
    Then Validate the content-type header in api response
    Then Validate all activities details in api response
    Then Validate Schema of 'SchemaOfResponseBody_Activities_GET_API' Activities GET API

    Examples:
      | TestName           | URL               | RequestMethod | StatusCode |
      | Activities GET API | api/v1/Activities | GET           | 200        |

  @Demo
  Scenario Outline: Verify Activities POST API
    Given I want to create test case "<TestName>"
    When I hit the "<RequestMethod>" method of create activities api with "<URL>" end point
    Then Validate Schema of 'SchemaOfRequestBody_Activities_POST_API' Request Body
    Then Validate the status code in api response "<StatusCode>"
    Then Validate the content-type header in api response
    Then Validate created activity details in api response
    Then Validate Schema of 'SchemaOfResponseBody_Activities_POST_API' Activities POST API

    Examples:
      | TestName            | URL               | RequestMethod | StatusCode |
      | Activities POST API | api/v1/Activities | POST          | 200        |

  @Demo
  Scenario Outline: Verify Activity details GET API
    Given I want to create test case "<TestName>"
    When I hit the "<POSTRequestMethod>" method of activity details api with "<URL>" end point
    When I hit the "<GETRequestMethod>" method of activity details api with "<URL>" end point
    Then Validate the status code in api response "<StatusCode>"
    Then Validate the content-type header in api response
    Then Validate activity details in api response
    Then Validate Schema of 'SchemaOfResponseBody_Activities_POST_API' Activity details GET API

    Examples:
      | TestName                 | URL               | GETRequestMethod | POSTRequestMethod | StatusCode |
      | Activity details GET API | api/v1/Activities | GET              | POST              | 200        |

  @Demo
  Scenario Outline: Verify Activities PUT API
    Given I want to create test case "<TestName>"
    When I hit the "<RequestMethod>" method of update activity api with "<URL>" end point
    Then Validate Request Schema of 'SchemaOfRequestBody_Activities_POST_API' Activities PUT API
    Then Validate the status code in api response "<StatusCode>"
    Then Validate the content-type header in api response
    Then Validate activity update details in response
    Then Validate Schema of 'SchemaOfResponseBody_Activities_POST_API' Activities PUT API

    Examples:
      | TestName           | URL               | RequestMethod | StatusCode |
      | Activities PUT API | api/v1/Activities | PUT           | 200        |

  @Demo
  Scenario Outline: Verify Activities DELETE API
    Given I want to create test case "<TestName>"
    When I hit the "<POSTRequestMethod>" method of activity details api with "<URL>" end point
    When I hit the "<DELETERequestMethod>" method of delete activity api with "<URL>" end point
    Then Validate the status code in api response "<StatusCode>"

    Examples:
      | TestName              | URL               | POSTRequestMethod | DELETERequestMethod | StatusCode |
      | Activities DELETE API | api/v1/Activities | POST              | DELETE              | 200        |

