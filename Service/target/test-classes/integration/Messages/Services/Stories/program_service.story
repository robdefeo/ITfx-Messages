Program Service story

Meta:
@progress done

Narrative:
All maintenance of programs

Scenario: insert a program into the database
Given A name of <name>
And An id of <id>
When I perform an insert
Then The response status is <status>
And I should get a program back
And the program has an Id which is <id>
And the program name should be <name>

Examples:
|name|id|status
|a program of messages|4009|200

Scenario: Get a program by Id
Given An id of <id>
When I perform a get
Then The response status is <status>
And I should get a program back
And the program has an Id which is <id>

Examples:
|id|status
|4009|200
