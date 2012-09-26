Message Service story

Meta:
@progress done

Narrative:
All maintenance of messages

Scenario: insert a message into the database
Given A message content of <content>
And An order of <order>
And A type of <type>
When I perform an insert
Then The response status is <status>
And I should get a message back
And the message has an Id which is a UUID
And the message content should be <content>
And the message order should be <order>
And the message type should be <type>

Examples:
|content|order|type|status
|things are not as they seam|3|SingleSms|200
