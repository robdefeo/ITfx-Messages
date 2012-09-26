User Service story

Meta:
@progress done

Narrative:
All maintenance of users

Scenario: insert a user into the database
Given A delivery address of <deliveryAddress>
And A delivery type of <deliveryType>
And A first name of <firstName>
When I perform an insert
Then The response status is <status>
And I should get a user back
And the user has an Id which is a UUID
And the user delivery address should be <deliveryAddress>
And the user delivery type should be <deliveryType>
And the user first name should be <firstName>

Examples:
|deliveryAddress|deliveryType|firstName|status
|test@something.com|MultipleSms|Mike|200
